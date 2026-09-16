import io.github.cdimascio.dotenv.Dotenv;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import java.time.Instant;
import java.util.List;

public class TelemetryPipeline {
    public static void main(String[] args) {
        // 0. Load environment variables dynamically from .env
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("INFLUX_TOKEN");
        String bucket = dotenv.get("INFLUX_BUCKET");
        String org = dotenv.get("INFLUX_ORG");
        String url = dotenv.get("INFLUX_URL");

        // 1. Initialize Spark in Local Mode
        SparkSession spark = SparkSession.builder()
                .appName("TelemetryETL")
                .master("local[*]")
                .getOrCreate();

        // 2. EXTRACT: Read the CSV File from resources
        Dataset<Row> df = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("src/main/resources/satellite_data.csv");

        // 3. TRANSFORM: Clean data by dropping missing values
        Dataset<Row> cleanDf = df.na().drop();

        // 4. LOAD: Write to InfluxDB Time-Series Database
        try (InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket)) {
            WriteApiBlocking writeApi = client.getWriteApiBlocking();
            List<Row> rows = cleanDf.collectAsList();

            for (Row row : rows) {
                Point point = Point.measurement("satellite_telemetry")
                        .addTag("satellite_id", "SAT-1")
                        .addField("voltage", (Number) row.getAs("voltage"))
                        .addField("temperature", (Number) row.getAs("temperature"))
                        .time(Instant.now(), WritePrecision.MS);

                writeApi.writePoint(point);
            }
            System.out.println("Data successfully written to InfluxDB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        spark.stop();
    }
}
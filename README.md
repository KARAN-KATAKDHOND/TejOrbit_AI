# 🛰️ TejOrbit-AI

> **A Resource-Constrained, Low-Latency Anomaly Detection Pipeline for Satellite Telemetry**[cite: 4]

TejOrbit-AI is a cognitive monitoring platform designed to establish baseline normal satellite behaviors and identify early precursors to critical failure[cite: 3]. Built entirely within a unified Java ecosystem, it bypasses traditional cloud-heavy, cross-language bottlenecks to deliver high-speed, localized predictive maintenance for aerospace engineering[cite: 4].

---

## 📖 Overview

Modern satellite systems continuously transmit thousands of highly complex telemetry signals regarding voltage, thermal characteristics, and attitude control[cite: 2]. Traditional spacecraft health monitoring heavily relies on Out-of-Limits (OOL) rule-based thresholds (e.g., $T > 100^{\circ}C$)[cite: 2]. While effective for catastrophic single-sensor failures, they miss subtle, multi-variate drifts that precede systemic breakdowns[cite: 1, 2].

**TejOrbit-AI solves this by:**
* Replacing static rules with Deep Learning sequential models capable of capturing long-range temporal dependencies[cite: 2].
* Unifying the Extract, Transform, Load (ETL) pipeline, database storage, and AI inference within a single JVM environment to eradicate cross-ecosystem serialization micro-latency[cite: 4].
* Providing zero-overhead **Explainable AI (XAI)** by mathematically isolating native reconstruction errors per feature, allowing engineers to instantly pinpoint specific failing sensors without relying on heavy Python wrappers like SHAP[cite: 2, 4].

---

## 🏗️ Architecture & Workflow

*(Placeholder: Upload the system architecture diagram from your project presentation here)*
![TejOrbit-AI Architecture](docs/architecture_diagram.png)

The system utilizes a strict **Champion-Challenger** methodology[cite: 3, 4]:
1. **Data Ingestion:** Apache Spark processes large-scale, high-frequency telemetry logs efficiently via in-memory batch computation[cite: 2, 3].
2. **Time-Series Storage:** Cleaned data is pushed to InfluxDB, explicitly separating metadata tags from sensor fields to maintain rapid querying[cite: 3].
3. **Champion Model (LSTM Autoencoder):** The primary deep learning model natively learns chronological dependencies and flags anomalies based on reconstruction error thresholds[cite: 2, 3, 4].
4. **Challenger Model (1D-CNN + LSTM):** A lightweight hybrid model evaluated for extracting local spatial features alongside long-term temporal trends[cite: 4].

---

## 🛠️ Technological Stack

* **Language:** Java 17
* **Data Processing (ETL):** Apache Spark (Local Mode via Java API)[cite: 1, 4]
* **Time-Series Database:** InfluxDB (v2)[cite: 1, 3]
* **Deep Learning Framework:** Eclipse Deeplearning4j (DL4J)[cite: 1, 3]
* **Build Tool & Dependency Management:** Apache Maven[cite: 3, 4]

---

## 📊 Datasets

The models are trained and benchmarked using expert-labeled telemetry datasets from NASA to ensure reproducible and academically rigorous results[cite: 4]:
* **SMAP** (Soil Moisture Active Passive) Satellite Telemetry[cite: 4]
* **MSL** (Mars Science Laboratory) Rover Telemetry[cite: 4]

---


## 🔒 License & Contributions

**Closed Source / Academic Project**
This repository contains academic coursework and proprietary research implementation. It is currently **closed for open-source contributions**. The source code, architecture, and associated documentation are strictly for evaluation and portfolio demonstration purposes. Unauthorized copying, distribution, or modification is prohibited.
# 🧠 Elasticsearch Local Setup

โปรเจกต์นี้เป็นตัวอย้่งการสร้าง CRUD **Elasticsearch** แบบ local ด้วย **Docker Compose** ในภาษา Java

Before you begin, make sure you have the following installed:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17+](https://adoptium.net/) (if running local backend)
- [Maven](https://maven.apache.org/) (optional if using wrapper)

--

## 📂 โครงสร้างหลัก

<img width="453" height="736" alt="image" src="https://github.com/user-attachments/assets/e1d025db-a6a5-414e-af33-a1cb19ee1b9d" />

## ⚙️ การใช้งาน

### 1. run docker-compose.yml
```bash
cd elastic-start-local/elastic-start-local
docker-compose up -d
```


### 2. run โปรเจกต์ ElasticsearchApplication

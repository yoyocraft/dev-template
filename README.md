# APP Template

## Project Structure

[Project Structure UML](./doc/project_structure.puml)

## Quick Start

### 1. Run DDL & DML

- **DDL**: [Schema DDL](./app-component/schema/ddl.sql)
- **DML**: [Schema DML](./app-component/schema/dml.sql)

### 2. Edit Configuration

1. Create a `local.properties` file in `app-runner/src/main/resources`.

   > **Reminder**: Be sure to modify the configuration values in `local.properties` according to your environment (e.g.,
   database credentials, log paths, server ports, etc.).

```properties
### Application ###
APP_NAME=XXX
### Database ###
DB_IP=127.0.0.1
DB_PORT=3306
DB_DATABASE=xxx
DB_USERNAME=xxx
DB_PASSWORD=xxx
DB_URL=jdbc:mysql://${DB_IP}:${DB_PORT}/${DB_DATABASE}?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
DB_DRIVER=com.mysql.cj.jdbc.Driver
### Log ###
LOG_PATH=/path/to/logs
### Web ###
SERVER_PORT=8080
CONTEXT_PATH=/api
### Spring Task ###
TASK_POOL_SIZE=4
TASK_THREAD_NAME_PREFIX=socialx-task-
TASK_SHUTDOWN_AWAIT_TERMINATION=true
TASK_SHUTDOWN_AWAIT_TERMINATION_PERIOD=60
```

### 3. Package the Application

Run the following command to build the project:

```shell
mvn clean package -Plocal
```

### 4. Run the Application

Execute the MainApplication.java class to start the application.
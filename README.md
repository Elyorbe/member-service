# Member Service

무신사 과제

## Requirements
- Java 17 이상
- MySQL 8 or MariaDB 10 이상 (임베디드 데이터베이스를 사용하지 않는 경우에만)

```shell
git clone https://github.com/Elyorbe/member-service.git
cd member-service
```
아래 모든 지침은 리포지토리가 clone되었다고 가정한다.

## Run tests

```shell
./gradlew test
```

## Build executable fat jar
```shell
./gradlew bootJar
```
위 명령어를 실행하면 build/libs/member-service-0.0.1.jar 파일이 만들어진다

## Run with Embedded DB
디폴트 프로필은 test이고 임베디드 데이터베이스를 사용한다

```shell
./gradlew bootRun
```

## Build and Run with MySql or MariaDB

`src/main/resources/application-local.yml`  및 `conf/flyway.conf`파일의 데이터베이스 URL, 사용자 및 암호를 업데이트하고 db migration을한다:
```shell
./gradlew -Dflyway.configFiles=./conf/flyway.conf flywayMigrate
```
local profilef로 실핸한다:
```shell
./gradlew bootRun --args='--spring.profiles.active=local'
```

## Testing REST endpoints
`test profile url = localhost:8798`

`local profile url = localhost:8797`

Field namings:

```
id = 회원 번호
username = 아이디
password = 비밀번호
email = 이메일
phoneNumber = 휴대폰번호
```

### 회원 가입

`POST` `/api/v1/members/signup`

Sample curl request:
```shell
curl --location --request POST 'localhost:8797/api/v1/members/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "elyorbek",
    "password": "El#Vdw5211@",
    "email": "elyor@musinsa.com",
    "phoneNumber": "010-7777-7777"
}'
```

### 회원 조회

`GET` `/api/v1/members/{id}`

Sample curl request:
```shell
curl --location --request GET 'localhost:8797/api/v1/members/1'
```

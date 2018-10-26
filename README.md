![Revolut](https://www.vectorlogo.zone/logos/revolut/revolut-card.png)

RESTFul API for Money Transfer / Challenge @ Revolut

## Task:
Design and implement a RESTful API in Java or Scala for money transfers between accounts.
* Rules:

1) Spring framework is not allowed
2) The implementation must include the design and support of the data model
3) The database must run in-memory
4) Produce a standalone program that can run without any dependencies
5) Tests should be provided to prove the API works as expected

## Tech Stack:
![Java](https://img.shields.io/badge/Java-1.8-red.svg?style=plastic)
![Scala](https://img.shields.io/badge/Scala-2.12.7-blue.svg?style=plastic)
![Play](https://img.shields.io/badge/Play%20Framework-2.6.18-green.svg?style=plastic)

Play is a solid framework with full support to reactive programming (asynchronous and non-blocking). Its hot reload feature makes the development easier and it plays well with both Java and Scala.

![H2](https://img.shields.io/badge/h2Database-1.4.192-blue.svg?style=plastic)

As we need an in-memory database my first choice would be Redis. However, Play offers support to H2 out of the box and its libraries and implementations for H2 are more mature compared to Redis.

![Jooq](https://img.shields.io/badge/Jooq-3.11.5-blue.svg?style=plastic)

Play has its own implementation of the Java Persistence API. I have chosen Jooq for two reasons:
1) The Play JPA implementation for a simple task aimed at this project may be overkill.
2) Considering that Revolut does not want the implementation using Spring at all, I guess that this is due to the need of a clearer understanding of my process of thought and less use of "black magic". Using Jooq I still have a certain level of abstraction but I have to be descriptive at the same time. I can still say "what" I would like to do but also "how".

![Lombok](https://img.shields.io/badge/lombok-1.18.2-blue.svg?style=plastic)

Lombok reduces the code ceremony required by Java to do simple tasks and offers top-notch implementations of some of the most common patterns and tasks that we implement over and over such as Builders, Logs and HashCode.

## Endpoints:
### ![POST](https://img.shields.io/badge/POST-red.svg?style=plastic) - Transfers money between two accounts

```
http://localhost:9000/rest/v1/transfers
```

* HTTP Request:
```json
POST /rest/v1/transfers HTTP/1.1
Accept: application/json
Host: localhost:9000

{
	"originAccountId": 928321248,
	"destinationAccountId": 432925330,
	"amount": 1554.67,
	"currency": "BRL"
}
```

* HTTP Response:
```json
{
    "status": "SUCCESS",
    "originAccount": {
        "id": 2,
        "accountNumber": 928321248,
        "balance": -1554.67,
        "currency": "BRL"
    },
    "destinationAccount": {
        "id": 1,
        "accountNumber": 432925330,
        "balance": 7500,
        "currency": "EUR"
    },
    "amount": 1554.67,
    "currency": "BRL"
}
```

## Running:
The server runs on port 9000.
### Standalone server:
1) Download the money-transfer-api-1.0.zip file available in the root of the project. Alternatively, the zip file will be downloaded automatically when cloning the repo.
2) Extract the zip file in a folder of your preference. The Unix command line should look like this: <kbd>> unzip money-transfer-api-1.0.zip</kbd>
3) You can either navigate to the extracted folder or execute the app from the folder you are. If you choose the latter, the command line for a Unix system will be similar to this: <kbd>> money-transfer-api-1.0/bin/money-transfer-api -Dplay.http.secret.key=revolut</kbd>

Where:
   * money-transfer-api-1.0 : the name of the extracted folder
   * bin : the folder where the startup scripts are located
   * money-transfer-api : the name of the script. There are two scripts: Windows and Unix
   * -Dplay.http.secret.key=revolut : mandatory  play framework parameter


If you are running on Unix it is possible that you need to set the scripts permission to execute it. Otherwise, the system will block it and a "permission denied" message will be shown.

4) After running the server your terminal should display logs similar to below:
```
> money-transfer-api-1.0/bin/money-transfer-api -Dplay.http.secret.key=revolut
[info] p.a.d.DefaultDBApi - Database [default] initialized at jdbc:h2:mem:play;DB_CLOSE_DELAY=-1
[info] application - Creating Pool for datasource 'default'
[info] application - In-Memory Customers:
+----+-----------+----------+----------+
|  id|national_id|first_name|last_name |
+----+-----------+----------+----------+
|   1|12900MA    |Michael   |Jackson   |
|   2|18933DU    |George    |Washington|
|   3|12344CW    |Mikhail   |Koklyaev  |
+----+-----------+----------+----------+

[info] application - In-Memory Accounts:
+----+--------------+-----------+-------+--------+
|  id|account_number|customer_id|balance|currency|
+----+--------------+-----------+-------+--------+
|   1|     432925330|          1|5945.33|EUR     |
|   2|     928321248|          2|   0.00|BRL     |
|   3|     538238213|          3|  27.30|USD     |
+----+--------------+-----------+-------+--------+

[warn] o.h.v.m.ParameterMessageInterpolator - HV000184: ParameterMessageInterpolator has been chosen, EL interpolation will not be supported
[info] play.api.Play - Application started (Prod)
[info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000
```

5) You can now request to one of the endpoints:
![Request](https://image.ibb.co/kTapbV/Screenshot-2018-10-26-at-01-58-50.png)

## SBT (running server in place):
### Requirements:
1) Java JDK 1.8
2) Scala SBT 1.2.6
### Running:
From project root folder:
1) <kbd>sbt clean compile run</kbd>
2) You can now request to one of the endpoints as described at the steps 4 and 5 above.

## Testing:
### Requirements:
1) Java JDK 1.8
2) Scala SBT 1.2.6
### Running:
From project root folder:
1) <kbd>sbt test</kbd>
2) Tou can now check the test results in the terminal
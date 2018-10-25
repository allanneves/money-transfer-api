## Stack:
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

* HTTP Request:
```
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
```
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
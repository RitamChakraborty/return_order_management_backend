# Return Order Management

Backend of the **Return Order Management** full stack application written with *Java* and *Spring Boot* 
in *Microservices* architecture.

The application is consist of 5 microservices. 

- [Service Registry](#service-registry)
- [Authorization](#authorization)
- [Packaging and Delivery](#packaging-and-delivery)
- [Component Processing](#component-processing)
- [API Gateway](#api-gateway)

### Service Registry 

Through the Service Registry microservice all the microservices are registered. So that they can communicate between themselves by referring the name of the services. 

#### Dependencies used

- spring-boot-starter-actuator
- spring-cloud-starter-netflix-eureka-server

#### UI

![service-registry](./.screenshots/service-registry.png)

### Authorization

Authorization microservices is responsible authenticating user and authorization access to other microservices.

The authorization uses JWT based authentication. JWT tokens will have a expiry time of 30 minutes. 

The user details are saved in the application level in memory database using H2.

#### Endpoints

> **Login**

*Request*
```http
POST http://localhost:8080/authorization/api/login
Content-Type: application/x-www-form-urlencoded

username=ritam@gmail.com&password=password
```

*Response*

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRhbUBnbWFpbC5jb20iLCJleHAiOjE2NTc2MDcxNzcsImlhdCI6MTY1NzYwNTM3N30.381ML9ZV9_uxxoAbkhgSCTP3nRfyr1Ige23BjuKU14YVNX54HIpJ1rJYocBWqMLO_BiyQjchfm5wU1mPAkbc0g"
}
```

> **Authenticate**

*Request*

```http
GET http://localhost:8080/authorization/api/authenticate
Accept: application/json
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRhbUBnbWFpbC5jb20iLCJleHAiOjE2NTc2MDcxNzcsImlhdCI6MTY1NzYwNTM3N30.381ML9ZV9_uxxoAbkhgSCTP3nRfyr1Ige23BjuKU14YVNX54HIpJ1rJYocBWqMLO_BiyQjchfm5wU1mPAkbc0g

```

*Response*

```json
{
  "email": "ritam@gmail.com",
  "firstName": "Ritam",
  "lastName": "Chakraborty",
  "contactNumber": "9876543210"
}
```

> **Create New User**

*Request*

```http
POST http://localhost:8080/authorization/api/signup
Accept: application/json
Content-Type: application/json

{
  "email": "shuktika.mahanty@gmail.com",
  "password": "Password@123",
  "firstName": "Shuktika",
  "lastName": "Mahanty",
  "contactNumber": "7086623484"
}
```

*Response*

```
{
  "email": "shuktika.mahanty@gmail.com",
  "firstName": "Shuktika",
  "lastName": "Mahanty",
  "contactNumber": "7086623484"
}
```

#### Dependencies used

- spring-boot-starter-actuator
- spring-boot-starter-web
- lombok
- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- h2
- spring-boot-starter-security
- java-jwt
- jacoco-maven-plugin
- springdoc-openapi-ui
- spring-boot-starter-test
- mockito-junit-jupiter

### Packaging and Delivery

Packaging and Delivery is middle wire microservice. It's only responsible for doing the price calculation for packaging and delivery. I'll be called by the Component Processing microservice through feign client.

#### Endpoints

> **Packaging and Delivery Charges**

*Request*
```http
GET http://localhost:8082/packaging-and-delivery/api/packaging-and-delivery-charge?componentType=integral-item&count=2
Content-Type: application/json
```

*Response*

```json
{
  "packagingCharge": 300,
  "deliveryCharge": 400
}
```

#### Dependencies used

- spring-boot-starter-actuator
- spring-boot-starter-web
- lombok
- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-validation
- jacoco-maven-plugin
- springdoc-openapi-ui
- spring-boot-devtools
- spring-boot-starter-test
- mockito-junit-jupiter

### Component Processing

Component Processing calculate the total price for ordering some component. It also create order, and save the details in H2 database. 

It calls the Packaging and Delivery microservices through feign client finding it by the service discovery.

#### Endpoints

> **Get Process Details**

*Request*

```http
POST http://localhost:8081/component-processing/api/process-detail
Content-Type: application/json
Accept: application/json

{
  "name": "Miss Mahanty",
  "contactNumber": "7980546384",
  "componentType": "integral-item",
  "componentName": "lipstick",
  "quantity": 5
}
```

*Response*

```json
{
  "requestId": "95dff85b-4429-4fb0-a2c7-2bc7eba9164f",
  "processingCharge": 500,
  "packagingAndDeliveryCharge": 1750,
  "dateOfDelivery": "2022-07-17"
}
```

> **Payment Processing**

*Request*

```http
GET http://localhost:8081/component-processing/api/complete-processing?requestId=c4219465-46e9-4ac6-9390-2bc3f4844478&creditCardNumber=1234123134123412&creditLimit=851&processingCharge=850
```

*Response*

```json
{
  "response": "success"
}
```

> **Place Order**

*Request*

```http
POST http://localhost:8081/component-processing/api/place-order
x-auth-customer-email: ritam@gmail.com
Content-Type: application/json
Accept: application/json
```

*Response*

```json
{
  "orderId": 1,
  "customerEmail": "ritam@gmail.com",
  "processRequest": {
    "name": "Miss Mahanty",
    "contactNumber": "7986546384",
    "componentType": "integral-item",
    "componentName": "lipstick",
    "quantity": 5
  },
  "processResponse": {
    "requestId": "eda98175-ef1b-4403-89c2-8acf0810d40b",
    "processingCharge": 500,
    "packagingAndDeliveryCharge": 1750,
    "dateOfDelivery": "2022-05-17"
  }
}
```

> **Get All Orders**

*Request*

```http
GET http://localhost:8081/component-processing/api/order-details
x-auth-customer-email: ritam@gmail.com
Accept: application/json
```

*Response*

```json
[
  {
    "orderId": 1,
    "customerEmail": "ritam@gmail.com",
    "processRequest": {
      "name": "Miss Mahanty",
      "contactNumber": "7986546384",
      "componentType": "integral-item",
      "componentName": "lipstick",
      "quantity": 5
    },
    "processResponse": {
      "requestId": "eda98175-ef1b-4403-89c2-8acf0810d40b",
      "processingCharge": 500,
      "packagingAndDeliveryCharge": 1750,
      "dateOfDelivery": "2022-05-17"
    }
  }
]
```

> **Get Single Order**

*Request*

```http
GET http://localhost:8081/component-processing/api/order-details?orderId=1
x-auth-customer-email: ritam@gmail.com
Accept: application/json
```

*Response*

```json
{
    "orderId": 1,
    "customerEmail": "ritam@gmail.com",
    "processRequest": {
      "name": "Miss Mahanty",
      "contactNumber": "7986546384",
      "componentType": "integral-item",
      "componentName": "lipstick",
      "quantity": 5
    },
    "processResponse": {
      "requestId": "eda98175-ef1b-4403-89c2-8acf0810d40b",
      "processingCharge": 500,
      "packagingAndDeliveryCharge": 1750,
      "dateOfDelivery": "2022-05-17"
    }
  }
```

#### Dependencies Used

- spring-boot-starter-actuator
- spring-boot-starter-web
- lombok
- h2
- spring-boot-starter-data-jpa
- spring-cloud-starter-netflix-eureka-client
- spring-cloud-starter-openfeign
- spring-boot-starter-validation
- jacoco-maven-plugin
- springdoc-openapi-ui
- spring-boot-starter-test
- mockito-junit-jupiter

### API Gateway

API Gateway Microservice is one way entry to all the microservices. It uses spring cloud gateway to route the request to particular requests to their corresponding endpoints depending on the predicate. It finds the services through service discovery. 

It also uses *resilience4j* circuit breaker pattern to pass the request to the fallback controller if the asked service is down. 

Any request to the component processing microservice is authenticated with an authorization filter. The filter will internally call the authorization/api/authenticate endpoint to validate. Thus it's must to pass the jwt token for any kinda request. 

> **Get Process Details with Valid token**

*Request*

```http
POST http://localhost:8181/component-processing/api/process-detail
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRhbUBnbWFpbC5jb20iLCJleHAiOjE2NTc2MTU3MDAsImlhdCI6MTY1NzYxMzkwMH0.ebLFGFUYHL49UnpO5NmROcwHH792IoBCS4UTftLhGdfJQtUwiH5pp2oA6WtF_rhw-V9E8QjScp60PEw3Hty2mg
Content-Type: application/json
Accept: application/json

{
  "name": "Miss Mahanty",
  "contactNumber": "7980546384",
  "componentType": "integral-item",
  "componentName": "lipstick",
  "quantity": 5
}
```

*Response*

```json
{
  "requestId": "a03af734-0d94-44d7-b8e6-fdd3105fe055",
  "processingCharge": 500,
  "packagingAndDeliveryCharge": 1750,
  "dateOfDelivery": "2022-07-17"
}
```

> **Get Process Details with Invalid Token**

```http
POST http://localhost:8181/component-processing/api/process-detail
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaXRhbUBnbWFpbC5jb20iLCJleHAiOjE2NTczMTY0MDMsImlhdCI6MTY1NzMxNDYwM30.eQ2hA3WNk45rDXLqcPLs23qwf3dAeMbB5iQ-hutX2oA8fNYufzI7PDcUQyK7-8KxW6qktO3768GdzdH7bLWRwA
Content-Type: application/json
Accept: application/json

{
  "name": "Miss Mahanty",
  "contactNumber": "7980546384",
  "componentType": "integral-item",
  "componentName": "lipstick",
  "quantity": 5
}
```

*Response*

```json
{
  "timestamp": "2022-07-12T08:19:19.642+00:00",
  "path": "/component-processing/api/process-detail",
  "status": 401,
  "error": "Unauthorized",
  "requestId": "df2352c9-2"
}
```

#### Dependencies Used

- spring-cloud-starter-gateway
- spring-boot-starter-actuator
- spring-cloud-starter-netflix-hystrix
- spring-cloud-starter-circuitbreaker-reactor-resilience4j
- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-webflux
- spring-cloud-starter-openfeign
- lombok
- spring-boot-starter-test
- reactor-test
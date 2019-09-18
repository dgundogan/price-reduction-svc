# 'Price Reduction' Rest Service

`price-reduction-svc` is a REST microservice application.

## Implementation

Following tech spec is used for the TDD based implementation.

- *Kotlin*
- *Spring Boot*
- *Gradle*
- *JUnit*

The project is organized as a *Gradle* project and in order to compile, test and create a package *Gradle* is used.

### Building the application

You could use Gradle to test and build the jar file.

* In the root directory of the project run the following commands

```bash
# Build
./gradlew clean build

#Test
./gradlew clean testClasses


```

## Using the API

### Running the application

* Start the application with the following command:

```bash

#Run
./gradlew run

```


### Request

1) Get Usage:

|End Point                                                                   | Operation |Port  |
|----------------------------------------------------------------------------|-----------|------|
|http://localhost:8080/categories/{categoryId}/reductions                    |GET        | 8080 |



Example:
http://localhost:8080/categories/600001506/reductions


2) Query Param:

|End Point                                                                                      | Operation |Port  |
|-----------------------------------------------------------------------------------------------|-----------|------|
|http://localhost:8080/categories/{categoryId}/reductions?labelType=labelType                   |GET        | 8080 |


labelType
- ShowWasNow
- ShowWasThenNow
- ShowPercDscount
- empty

Example:
http://localhost:8080/categories/600001506/reductions?labelType=ShowWasThenNow


### Response

#### Response Model

```bash
[
    {
        "productId": "3467432",
        "title": "Boden Rosamund Posy Stripe Dress",
        "colorSwatches": [
            {
                "color": "Navy",
                "rgbColor": "0000FF",
                "skuid": "237334043"
            },
            {
                "color": "Mimosa Yellow",
                "rgbColor": "FFFF00",
                "skuid": "237334029"
            }
        ],
        "nowPrice": "£63",
        "priceLabel": "70.0% off - now £63.00"
    }
]
```

#### Error Model

```bash
{
    "time": "2019-09-18T19:58:35.025+0000",
    "message": "Invalid Parameter",
    "details": "Failed ..."
}
```



·  Looks like “nowPrice” will round off the pence in the ProductTransformer - I don’t think this is the intention of the exercise
·  Rollups should be used in ProductTransformerTest
·  Integration tests should be included to start the application fully and test the endpoint
·  Unit tests need to test more bounds, for example the point above
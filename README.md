# phone-numbers-categorizer
A spring boot project for phone numbers categorization
### How to install (using docker container)
1. To build docker image with maven (including running all tests), run ./mvnw spring-boot:build-image
2. To build using normal docker command:
	docker build -t jumia/phone-categorizer-docker .\
	docker run -p 8080:8080 jumia/phone-categorizer-docker

After that, open the browser and take and type  the endpoint: http://localhost:8080/api/swagger-ui/

### How to use
Curl (https://curl.se/) could be used inside the terminal, with the following optional parameters:
  1. validPhoneNumbersOnly BOOLEAN: filters customers list by the validity of the phone number
  2. countryName String: a string contains the country name among MOROCCO, CAMEROON, ETHIOPIA, UGANDA and MOZAMBIQUE.
  3. countryCode String: the code of the country among 237, 251, 212, 258, 256
  4. localNumber String: the local number of the user without the country code
  5. page INT: set the page that will be returned of the database
  6. size INT: limits number of elements returned on a page from the database

- Example:
```console
curl -X GET 'http://localhost:8080/api/customer' -H "accept: */*"
```
- Example with Parameters:
```console
curl -X GET "http://localhost:8080/api/customer?countryName=Morocoo&page=0&size=10&validPhoneNumbersOnly=false" -H "accept: */*"
```
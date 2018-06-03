Build with: `mvn clean install`
Run with: `mvn spring-boot:run`
A web server on port 8080 will start. Start and test the api by using cURL or browser request.\
Tests found under docs package:

STORE-REQUEST{DE(Hund)->EN(dog)} -> RESPONSE{OK}

`curl -i -H "Content-Type: application/json" -X POST -d '{
            "fromLanguage": "DE",
            "fromWord": "Hund",
            "toLanguage": "EN",
            "toWord": "dog"
        }' localhost:8080/translator/add`
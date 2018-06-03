# Simple Spring Boot Web API example. #

**Project use case, a simple "Translator App" where a user can store pair translations (see examples bellow) and retrieve them.**

*As	a	user	I	want	to	be	able	to	store	translations	for	multiple	language-combinations.*\
`STORE-REQUEST{DE(Hund)->EN(dog)} -> RESPONSE{OK}`

*As	a	user	I	want	to	be	able	to	get	a	previous	stored	translation*\
`STORE-REQUEST{DE(Hund)->EN(dog)} -> RESPONSE{OK}`\
`GET-REQUEST{DE(Hund)->EN}	-> RESPONSE{dog}`

*As	a	user	I	want	to	be	able	to	get	a	previous	stored	translation	in	the	opposite	order*
`STORE-REQUEST{DE(Hund)->EN(dog)} -> RESPONSE{OK}`\
`GET-REQUEST{EN(dog)->DE}	-> RESPONSE{Hund}`

*As	a	user	I	want	to	get	a	guess	of	a	translation	using	other	translations	if	possible*\
`STORE-REQUEST{DE(Katze)->ES(gato)} -> RESPONSE{OK}`\
`STORE-REQUEST{ES(gato)->EN(cat)} -> RESPONSE{OK}`\
`STORE-REQUEST{EN(cat)->FR(chat)} -> RESPONSE{OK}`\
`GET-REQUEST{DE(Katze)->EN} -> RESPONSE{cat}`\
`GET-REQUEST{DE(Katze)->FR} -> RESPONSE{chat}`

# Installation guide: #
Build with: `mvn clean install`\
Run with: `mvn spring-boot:run`

***A web server on port 8080 will start. Start and test the API by using cURL or browser request.\
Curl tests found under docs package.***
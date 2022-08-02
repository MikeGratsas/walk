# walk
Walk preparation project for Spring Boot

To execute Spring Boot application the following environment variables must be set:
* SPRING_DATASOURCE_URL
* SPRING_DATASOURCE_USERNAME
* SPRING_DATASOURCE_PASSWORD

Test using maven command:

mvn test


Run using maven command:

mvn spring-boot:run

and access the page at the urls: http://localhost:8080/api/items

```
- (get) http://localhost:8080/api/items (get list of all items)
- (post) http://localhost:8080/api/items (post create new item sending JSON info. Example {"name":"burger","measuringUnit":""} )
- (put) http://localhost:8080/api/items (update existing item sending JSON info. Example {"id":17,"name":"sandwich","measuringUnit":""} )
- (get) http://localhost:8080/api/items/17 (get item info with id=17)
- (delete) http://localhost:8080/api/items/17 (delete item info with id=17)
```

To get list of items required to walk to a distance planned at the specified start and finish time for a determined number of humans and dogs perform POST request sending JSON info to the url:

http://localhost:8080/api/prepare

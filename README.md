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

and access the items endpoint: http://localhost:8080/api/items

```
- (get) http://localhost:8080/api/items (get list of all items)
- (post) http://localhost:8080/api/items (post create new item sending JSON info. Example {"name":"burger","measuringUnit":""} )
- (put) http://localhost:8080/api/items (update existing item sending JSON info. Example {"id":17,"name":"sandwich","measuringUnit":""} )
- (get) http://localhost:8080/api/items/17 (get item info with id=17)
- (delete) http://localhost:8080/api/items/17 (delete item info with id=17)
```

To get list of items needed to walk the planned distance at the specified start and end time for a certain number of humans and dogs execute a POST request by sending JSON data to the endpoint:

http://localhost:8080/api/prepare

```json
{
  "subjects": { "human": 2, "dog": 1 },
  "distance": 100,
  "start": "2022-07-31T08:38:50",
  "finish": "2022-08-01T21:34:00"
}
```

The rules are preconfigured for the application, but can be modified.


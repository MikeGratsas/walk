package com.tietoevry.walk.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.tietoevry.walk.form.ItemModel;
import com.tietoevry.walk.form.ItemRuleModel;
import com.tietoevry.walk.form.WalkItemModel;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class WalkControllerTest {
	
    private static final String ITEMS_ENDPOINT = "/api/items";

	private static final String PREPARE_ENDPOINT = "/api/prepare";
    
	@Autowired
	private TestRestTemplate template;

	@Test
    void testWalk()
    {
		final ParameterizedTypeReference<List<WalkItemModel>> typeReference = new ParameterizedTypeReference<List<WalkItemModel>>() {
		};

		HttpEntity<Object> walkEntity = getHttpEntity("{\"distance\": 100, \"start\": \"2022-07-31T08:38:50\", \"finish\": \"2022-08-01T21:34:00\" }");
		ResponseEntity<String> response = template.postForEntity(PREPARE_ENDPOINT, walkEntity, String.class);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assertions.assertFalse(response.getBody().isEmpty());

		walkEntity = getHttpEntity("{\"subjects\": {\"cat\": 10}, \"distance\": 100, \"start\": \"2022-07-31T08:38:50\", \"finish\": \"2022-08-01T21:34:00\" }");
		ResponseEntity<List<WalkItemModel>> walkResponse = template.exchange(PREPARE_ENDPOINT, HttpMethod.POST, walkEntity, typeReference);
		Assertions.assertEquals(HttpStatus.OK, walkResponse.getStatusCode());
		List<WalkItemModel> walkItemsBody = walkResponse.getBody();
		Assertions.assertFalse(walkItemsBody.isEmpty());

		walkEntity = getHttpEntity("{\"subjects\": {\"human\": 1, \"dog\": 1}, \"distance\": 100, \"start\": \"2022-07-31T08:38:50\", \"finish\": \"2022-08-01T21:34:00\" }");
		walkResponse = template.exchange(PREPARE_ENDPOINT, HttpMethod.POST, walkEntity, typeReference);
		Assertions.assertEquals(HttpStatus.OK, walkResponse.getStatusCode());
		walkItemsBody = walkResponse.getBody();
		Assertions.assertFalse(walkItemsBody.isEmpty());

		walkEntity = getHttpEntity("{\"subjects\": {\"human\": 2, \"dog\": 1}, \"distance\": 100, \"start\": \"2022-07-31T08:38:50\", \"finish\": \"2022-08-01T21:34:00\" }");
		walkResponse = template.exchange(PREPARE_ENDPOINT, HttpMethod.POST, walkEntity, typeReference);
		Assertions.assertEquals(HttpStatus.OK, walkResponse.getStatusCode());
		walkItemsBody = walkResponse.getBody();
		Assertions.assertFalse(walkItemsBody.isEmpty());
		int walkItemsSize = walkItemsBody.size();
		
		ResponseEntity<ItemModel[]> itemsResponse = template.getForEntity(ITEMS_ENDPOINT, ItemModel[].class);
		Assertions.assertEquals(HttpStatus.OK, itemsResponse.getStatusCode());
		Assertions.assertEquals(16, itemsResponse.getBody().length);
		
		itemsResponse = template.getForEntity(ITEMS_ENDPOINT + "/findByMeasuringUnitName?name={name}", ItemModel[].class, "kg");
		Assertions.assertEquals(HttpStatus.OK, itemsResponse.getStatusCode());
		Assertions.assertEquals(1, itemsResponse.getBody().length);
		
		HttpEntity<Object> itemEntity = getHttpEntity("{\"name\": \"burger\", \"measuringUnit\": \"\" }");
		ResponseEntity<ItemModel> itemResponse = template.postForEntity(ITEMS_ENDPOINT, itemEntity, ItemModel.class);
		Assertions.assertEquals(HttpStatus.CREATED, itemResponse.getStatusCode());
		ItemModel itemBody = itemResponse.getBody();
		final String itemName = itemBody.getName();
		final Long itemId = itemBody.getId();
		final LocalDateTime itemCreated = itemBody.getCreated();
		final LocalDateTime itemLastUpdated = itemBody.getLastUpdated();
		Assertions.assertNotNull(itemId);

		itemEntity = getHttpEntity("{\"id\": " + itemId + ", \"name\": \"sandwich\", \"measuringUnit\": \"\", \"created\": \"" + itemCreated + "\", \"lastUpdated\": \"" + itemLastUpdated + "\"  }");
		itemResponse = template.exchange(ITEMS_ENDPOINT, HttpMethod.PUT, itemEntity, ItemModel.class);
		Assertions.assertEquals(HttpStatus.OK, itemResponse.getStatusCode());
		itemBody = itemResponse.getBody();
		Assertions.assertEquals(itemId, itemBody.getId());
		Assertions.assertEquals("sandwich", itemBody.getName());
		Assertions.assertEquals(itemCreated, itemBody.getCreated());
		Assertions.assertTrue(itemLastUpdated.isBefore(itemBody.getLastUpdated()));

		itemResponse = template.getForEntity(ITEMS_ENDPOINT + "/findByName?name={name}", ItemModel.class, itemName);
		Assertions.assertEquals(HttpStatus.OK, itemResponse.getStatusCode());
		itemBody = itemResponse.getBody();
		Assertions.assertEquals(itemId, itemBody.getId());
		
		itemsResponse = template.getForEntity(ITEMS_ENDPOINT, ItemModel[].class);
		Assertions.assertEquals(HttpStatus.OK, itemsResponse.getStatusCode());
		Assertions.assertEquals(17, itemsResponse.getBody().length);
		
		ResponseEntity<ItemRuleModel> itemRuleResponse = template.getForEntity(ITEMS_ENDPOINT + "/{itemId}/rules/add?rule={rule}&quantity={quantity}", ItemRuleModel.class, itemId, "evening", 1.0);
		Assertions.assertEquals(HttpStatus.OK, itemRuleResponse.getStatusCode());
		Assertions.assertNotNull(itemBody.getId());

		walkEntity = getHttpEntity("{\"subjects\": {\"human\": 2, \"dog\": 1}, \"distance\": 100, \"start\": \"2022-07-31T08:38:50\", \"finish\": \"2022-08-01T21:34:00\" }");
		walkResponse = template.exchange(PREPARE_ENDPOINT, HttpMethod.POST, walkEntity, typeReference);
		Assertions.assertEquals(HttpStatus.OK, walkResponse.getStatusCode());
		Assertions.assertFalse(walkItemsBody.isEmpty());
		Assertions.assertEquals(walkItemsSize + 2, itemsResponse.getBody().length);
		
		template.delete("/api/items/{itemId}", itemId);
		
		itemResponse = template.getForEntity(ITEMS_ENDPOINT + "/{itemId}", ItemModel.class, itemId);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, itemResponse.getStatusCode());
    }
     
	private HttpEntity<Object> getHttpEntity(Object body) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
}

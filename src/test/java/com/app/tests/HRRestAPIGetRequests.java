package com.app.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class HRRestAPIGetRequests {
  
	/*
	 * When I send a GET request to REST Url http://34.223.219.142:1212/ords/hr/employees
	   Then response status should be 200
	 * 
	 * 
	 */
	@Test
	public void simpleGet() {
		when()
		.get("http://34.223.219.142:1212/ords/hr/employees")
		  .then()
		  .statusCode(200);
}
	
	 /*
	   * When I send a GET request to REST Url http://34.223.219.142:1212/ords/hr/countries
		   Then I should see JSON Response
	   * 
	   */
	
	@Test
	public void printResponse() {
		when().get("http://34.223.219.142:1212/ords/hr/countries")
		.body().prettyPrint();
	}
	
	/*
	   * When I send a GET request to REST Api Url
		http://34.223.219.142:1212/ords/hr/countries/US
		And Accept type is "application/json"
		Then response status code should be 200
	   */
	
	@Test
	  public void getWithHeaders() {
		with().accept(ContentType.JSON) // This is the accept request from header, we need to put the headers first, then we send the requests
		                                // headers are not belongs to requests.
		.when().get("http://34.223.219.142:1212/ords/hr/countries/US")
		.then().statusCode(200);
		
		
	}
	
	/*
	   * When I send a GET request to REST URL:
	   * http://34.223.219.142:1212/ords/hr/employees/1234
	   * Then status code is 404
	   * And Response body error message is "Not Found"
	   */
	  @Test
	  public void negativeGet() {
//		  when().get("http://34.223.219.142:1212/ords/hr/employees/1234")
//		  .then().statusCode(404);
		  
		  Response response =  when().get("http://34.223.219.142:1212/ords/hr/employees/1234");
		  assertEquals(response.statusCode(), 404);
		  assertTrue(response.asString().contains("Not Found"));	 
		  response.prettyPrint();

	  }
	  
	  /*  
	   * When I send a GET request to REST URL:
	   * http://34.223.219.142:1212/ords/hr/employees/100
	   * And Accept type is json
	   * Then status code is 200
	   * And Response content should be json
	   * 
	   * WITH,WHEN,GET,ASSERTTHAT,ACCEPT,CONTENTTYPE
	   */

	  @Test
	  public void VerifyContentTypeWithAssertThat() {
		  String url = "http://34.223.219.142:1212/ords/hr/employees/100";
		  given().accept(ContentType.JSON) // Instead of with we can use given() method also.
		  .when().get(url).then().assertThat().statusCode(200)  // assertThat() will allow us to use more method.
		  .and().contentType(ContentType.JSON);
	  }
		
	  /*Given Accept type is JSON
	   * When I send a GET request to REST URL:
	   * http://34.223.219.142:1212/ords/hr/employees/100
	   * Then status code is 200
	   * And Response content should be json
	   * And first name should be "Steven
	   * And employee id is 100
	   */
	  
	  @Test
	  public void verifyFirstName() throws URISyntaxException {
		  
		  URI uri = new URI("http://34.223.219.142:1212/ords/hr/employees/100"); //Instead of storing the url in String, we can also store it in URI class.
		  
		  given().accept(ContentType.JSON)
		  .when().get(uri)
		  .then().assertThat().statusCode(200)
		  .and().assertThat().contentType(ContentType.JSON)
//		  .and().assertThat().body("first_name", Matchers.equalTo("Steven"))
//		  .and().assertThat().body("employee_id", Matchers.equalTo(100));
		  .and().assertThat().body("first_name", equalTo("Steven")) // We made matcher assertion static and added .*, so we do not need to type Matchers when assert.
		  .and().assertThat().body("employee_id",equalTo(100));
		  
	  }
  
}

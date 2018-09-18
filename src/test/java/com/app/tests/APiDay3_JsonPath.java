package com.app.tests;

import org.testng.annotations.Test;

import com.app.utilities.ConfigurationReader;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class APiDay3_JsonPath {

	
	/*
	 * Given Accept type is Json
	 * And Params are limit=100
	 * And path param is 110
	 * When I send get request to 
	 * http://34.223.219.142:1212/ords/hr/employee 
	 * Then status code is 200 
	 * And Response content should be json 
	 * And following data should be returned:
	 * "employee_id": 110,
	    "first_name": "John",
	    "last_name": "Chen",
	    "email": "JCHEN",
	    
	    "employee_id": 110,
    "first_name": "Ahmet",
    "last_name": "Turkkahraman",
    "email": "EM110",
	 */
	
	@Test
	public void testWithPathParameter() {
		given().accept(ContentType.JSON)
		.and().param("limit", 100)
		.and().pathParam("employee_id", 110)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}")
		.then().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("employee_id", equalTo(110), 
				                 "first_name", equalTo("Ahmet"),
				                  "last_name", equalTo("Turkkahraman"),
				                   "email", equalTo("EM110"));
	}
	
	
	/*
	 * Given Accept type is Json
	 * And Params are limit=100
	 * When I send get request to 
	 * http://34.223.219.142:1212/ords/hr/employee 
	 * Then status code is 200 
	 * And Response content should be json 
	 * all employee ids should be returned
	 */
	
	@Test
	public void testWithJsonPath() {
		
		Map<String, Integer> rParamMap = new HashMap<>();
		rParamMap.put("limit", 100);
		
		Response response = given().accept(ContentType.JSON)//header
				.and().params(rParamMap) //query param/request param
				.and().pathParams("employee_id", 7222) //path param
				.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}");
		
		JsonPath json = response.jsonPath();
		
		System.out.println(json.getInt("employee_id"));
		System.out.println(json.getString("last_name"));
		System.out.println(json.getString("job_id"));
		System.out.println(json.getInt("salary"));
		System.out.println(json.getString("links[0].href"));
		
		List<String> hrefs = json.getList("links.href");
		for(String href :hrefs) {
			System.out.println(href);
		}
		System.out.println(hrefs);
	}
	
	/*
	 * Given Accept type is Json
	 * And Params are limit=100
	 * When I send get request to 
	 * http://34.223.219.142:1212/ords/hr/employee 
	 * Then status code is 200 
	 * And Response content should be json 
	 * all employee data should be returned
	 */
	
	@Test
	public void testJsonPathWithLists() {
		
		Map<String,Integer> rParamMap = new HashMap<>();
		rParamMap.put("limit", 100);
		
		Response response = given().accept(ContentType.JSON)//header
							.and().params(rParamMap) //query param/request param
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees");
		
		assertEquals(response.statusCode(),200); //check status code
		
		JsonPath json = response.jsonPath();
		//JsonPath json = new JsonPath(new File(FilePath.json));
		//JsonPath json = new JsonPath(response.asString());
		
		//get all employee ids into an arraylist
//		List<Integer> empIds = json.getList("items.employee_id");
//		System.out.println(empIds);
//		
//		List<String> empEmails = json.getList("items.email");
//		System.out.println(empEmails.size());
//		assertEquals(empEmails.size(), 100);
//		
//		List<Integer> id = json.getList("items.findAll{it.employee_id>150}.employee_id");
//	    System.out.print(id);
	    
	    List<Integer> empLastName = json.getList("items.findAll{it.salary>10000}.last_name");
	    System.out.println("Salary: " + empLastName);
	    
	    JsonPath jsonFile = new JsonPath(new File("/Users/emin/Desktop/EMPLOYEE.json"));
	    List<String> emails = jsonFile.getList("items.email");
	    System.out.println(emails);
	}

}

package com.app.tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.app.utilities.BrowserUtils;
import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonValidationOfRegions {
	
	
	/*
	 * 
	 * "items": [
        {
            "region_id": 349,
            "region_name": "NOT Murodil's Region",
            "links": [
                {
                    "rel": "self",
                    "href": "http://34.223.219.142:1212/ords/hr/regions/349"
                }
            ]
        },
	 * 
	 * 
	 */
	
	@Test
	public void testRegions() {
		
		Response response = given().accept(ContentType.JSON)
		.and().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/regions");
		
		assertEquals(response.statusCode(), 200);
		
		List<Map> map = response.jsonPath().getList("items", Map.class); 
		System.out.println(map.get(0));
		
		assertEquals(map.get(0).get("region_id"), 349);
	}
	
	@Test
	public void testRegions1() {
		
		Response response = given().accept(ContentType.JSON)
		.and().param("items", 10)		
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/regions");
	
		assertEquals(response.statusCode(), 200);
		
		JsonPath json = response.jsonPath();
		BrowserUtils.waitFor(2);
//		assertEquals(json.getString("items[0].region_id"), 349);
		System.out.println(json.getString("items[0].region_name"));
		assertEquals(json.getString("items[0].region_name"), "NOT Murodil's Region");
		
		System.out.println(json.getString("items[0].links[0].rel"));
		assertEquals(json.getString("items[0].links[0].rel"), "self");
		
		System.out.println(json.getString("items[1].region_id"));
		assertEquals(json.getString("items[1].region_id"), 4023);
		
		
	}
	
	@Test
	public void testRegions2() {
		
		
		Response rs = given().accept(ContentType.JSON)
		.and().get(ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions");
		
		JsonPath js = rs.jsonPath();
		List<Map> maps = js.getList("items", Map.class);
		
		Map<Integer, String> regions = new HashMap();
	
		regions.put(349, "NOT Murodil's Region");
		regions.put(4023, "NOT Murodil's Region");
		regions.put(1110, "NOT Murodil's Region");
		regions.put(2301, "NOT Murodil's Region");
		
		for(Integer regionId : regions.keySet()) {
			System.out.println("My Region ID: " + regionId);
			for(Map eachMap : maps) {
				if(eachMap.get("region_id") == regionId) {
					assertEquals(eachMap.get("region_name"), regions.get(regionId));
				}
			}
		}
				
		}
		


	
		@Test
		public void testRegions3() {
			
		List<String> data = Arrays.asList("349 NOT Murodil's Region", "4023 NOT Murodil's Region", "1110 NOT Murodil's Region", "2301 NOT Murodil's Region");
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions";
		
		Response response = given().accept(ContentType.JSON).and().get(url);
		assertEquals(response.statusCode(), 200);
		
//		List<String> regionName = new ArrayList<>();
//		JsonPath js = response.jsonPath();
//		for(Object item : js.getList("items")) {
//		    regionName.add(((HashMap) item).get("region_id").toString()+" "
//		    + ((HashMap) item).get("region_name").toString());
//		}
//		assertTrue(regionName.containsAll(data));
//	
		Map<String, String> map = new HashMap();
		JsonPath js = response.jsonPath();
		for(Map each : js.getList("items",Map.class)) {
//			System.out.println(each.get("region_id")+" "+each.get("region_name"));
			for(String str : data) {
				if(str == each.get("region_id")+" "+each.get("region_name")) {
					System.out.println(each.get("region_id")+" "+each.get("region_name"));
					assertEquals(str, each.get("region_id")+" "+each.get("region_name"));
				}
				
			}
		}
			
	}
		@Test
		public void testRegions4() {
			
	Response response = given().accept(ContentType.JSON)
		.and().get(ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions");
		assertEquals(response.statusCode(), 200);
		
		List<String> lst = new ArrayList<>();
		
		JsonPath js = response.jsonPath();
		Map<Integer, String> regions = new HashMap();
		
		regions.put(349, "NOT Murodil's Region");
		regions.put(4023, "NOT Murodil's Region");
		regions.put(1110, "NOT Murodil's Region");
		regions.put(2301, "NOT Murodil's Region");
		
		for (int i = 0; i < regions.size(); i++) {
			assertEquals()
		}
			
		}
			
			
			
			
		}
}

package com.app.tests;

import org.testng.annotations.Test;

import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class APIDay5_POSTthenGET {
	
	/*
	Given Content type and Accept type is Json
	When I post a new Employee with "1100" id
	Then Status code is 201
	And Response Json should contain Employee info
	When i send a GET request with "1100" id 
	Then status code is 200
	And employee JSON Response Data should match the posted JSON data
	*/
	
	/*
	 * AD_PRES
		AD_VP
		AD_ASST
		FI_MGR
		FI_ACCOUNT
		AC_MGR
		AC_ACCOUNT
		SA_MAN
		SA_REP
		PU_MAN
		PU_CLERK
		ST_MAN
		ST_CLERK
		SH_CLERK
		IT_PROG
		MK_MAN
		MK_REP
		HR_REP
		PR_REP
	 */
	
	@Test
	public void PostEmployeeThenGetEmployee() {
		int randomId = new Random().nextInt(99999);
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/employees/";
		
		Map reqEmployee = new HashMap();
		
		reqEmployee.put("employee_id", randomId);
		reqEmployee.put("first_name","POSTNAME");
		reqEmployee.put("last_name","POSTLNAME");
		reqEmployee.put("email","EM"+randomId);
		reqEmployee.put("phone_number", "202.123.4567");
		reqEmployee.put("hire_date","2018-04-24T07:25:00Z");
		reqEmployee.put("job_id","IT_PROG");
		reqEmployee.put("salary",7000);
		reqEmployee.put("commission_pct",null);
		reqEmployee.put("manager_id",null);
		reqEmployee.put("department_id",90);
		

		Response response = given().accept(ContentType.JSON)
		.and().contentType(ContentType.JSON).and().log().all()
		.and().body(reqEmployee).when().post(url);
		
		Map postRestEmployee = response.body().as(Map.class);
		
		for(Object each : reqEmployee.keySet()) {
			System.out.println(each);
			assertEquals(reqEmployee.get(each), postRestEmployee.get(each) );
			
		}
                 response =  given().accept(ContentType.JSON)
                   .when().get(url+randomId);
                   
                   assertEquals(response.statusCode(), 200);
                   
		Map getResMap = response.body().as(Map.class);
                   for(Object each : reqEmployee.keySet()) {
                	   System.out.println(each +": "+ reqEmployee.get(each) +  "<>" + getResMap.get(each));
                	   assertEquals(getResMap.get(each), reqEmployee.get(each));
                   }
	}

}










Feature: Employee Rest Api request


@ApiPost
Scenario: Post an Employee method test

    Given Content type and Accept type is Json
	When I post a new Employee with "random" id
	Then Status code is 201
	And Response Json should contain Employee info
	When i send a GET request with "random" id 
	Then Status code is 200
	And employee JSON Response Data should match the posted JSON data

Scenario: Post an Employee and Verify in UI



	
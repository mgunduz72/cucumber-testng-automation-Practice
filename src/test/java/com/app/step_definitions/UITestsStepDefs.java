package com.app.step_definitions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.app.pages.SuiteCRMLoginPage;
import com.app.utilities.ConfigurationReader;
import com.app.utilities.Driver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class UITestsStepDefs {


		private WebDriver driver = Driver.getDriver();
		SuiteCRMLoginPage loginPage = new SuiteCRMLoginPage();
		

		@Given("I logged into suiteCRM")
		public void i_logged_into_suiteCRM() {
			driver.get(ConfigurationReader.getProperty("url"));
			loginPage.login(ConfigurationReader.getProperty("username"), ConfigurationReader.getProperty("password"));
		}

		@Then("CRM name should be SuiteCRM")
		public void crm_name_should_be_SuiteCRM() {
			
		}

		@Then("Modules should be displayed")
		public void modules_should_be_displayed() {
			
	
}
}
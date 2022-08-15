/*
 * Author: Sachitha Nanayakkara
 * Date: 15/08/2022
*/
package stepDefinitions;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ToDoHomePage;

public class StepsToAddItems {
	
	public static Logger logger;
	WebDriver driver=null;
	ToDoHomePage hp;

	//Cucumber hook for teardown the web browser after each scenario
	@After
	public void tearDown() {
		
		logger.info("--------Closing the browser-----------");
		driver.quit();
	}

	
	//This is used as scenario background in each scenario
	@Given("an empty Todo list")
	public void an_empty_todo_list() {
		
		//Setup and Launching chrome browser
		logger = Logger.getLogger("DTTesting");
		PropertyConfigurator.configure("log4j.properties");
		logger.info("--------Launching the browser-----------");
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/Drivers/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		//Opening the url
		logger.info("--------Opening the url-----------");
		driver.get("http://todomvc.com/examples/vue/");
		
		//Maximizing browser window
		driver.manage().window().maximize();
		
		//Initializing todo page object
		hp = new ToDoHomePage(driver);

		logger.info("--------Checking whether Todo textbox is empty-----------");
		if (hp.istxtTodoEmpty()==true) {
			logger.info("--------Todo textbox is empty-----------");

		}else {
			logger.warn("--------Todo textbox is not empty-----------");
		}

	}

	//Same method used to add a todo item by 'Added items can be removed from the list' and 'New item can be added to the empty list' scenarios
	@When("MVC User adds a Todo for {string}")
	@Given("a Todo list with item {string}")
	public void mvc_user_adds_a_todo_for(String inputItem) throws InterruptedException {
		logger.info("--------Adding new todo item-----------");
		hp.setNewTodo(inputItem);
		logger.info("--------New todo item added-----------");
		Thread.sleep(2000);
	}

	//This is used by 'New item can be added to the empty list' scenario to check item's availability
	@Then("only that item is listed")
	public void only_that_item_is_listed() {
		if (hp.checkItemExists("first")==true) {
			Assert.assertTrue(true);
			logger.info("--------Added first item is listed-----------");
		}else {
			Assert.assertTrue(false);
			logger.error("--------Item is not listed-----------");
		}

	}


	//To check remaining item count and the summary message
	@And("the list summary is {string}")
	public void the_list_summary_is(String summary) {
		logger.info("--------Checking remaining todo item count----------");
		boolean rem= hp.getRemainMsg().contains(summary);

		if(rem==true)
		{
			Assert.assertTrue(true);
			logger.info("--------Remaining item count is correct-----------");
		}else {
			Assert.assertTrue(false);
			logger.error("--------Remaining item count is incorrect-----------");
		}

	}

	//To check the setup of 'All', 'Completed' and 'Acive' filters
	@And("the list’s filter is set to All with Completed and Active are unset")
	public void the_list_s_filter_is_set_to_all_with_completed_and_active_are_unset() {
		
		logger.info("--------Checking filter setup-----------");
		if(hp.isFilterAllSelected()==true && hp.isFilterActiveSelected()==true && hp.isFilterCompletedSelected()==true) 
		{
			Assert.assertTrue(true);
			logger.info("--------'All' is set, 'Active' and 'Completed' are unset-----------");

		}else {
			Assert.assertTrue(false);
			logger.error("--------filter setup is incorrect-----------");
		}

	}

	//To check 'Clear Completed' filter availability
	@And("Clear Completed filter is unavailable")
	public void clear_completed_filter_is_unavailable() throws InterruptedException {
		
		logger.info("--------Checking availability of 'Clear Completed' filter-----------");
		if(hp.checkFilterClrCompleteExists()==true)
		{
			Assert.assertTrue(true);
			logger.info("--------Clear Completed filter is not exiting-----------");
			Thread.sleep(2000);
		}else {
			Assert.assertTrue(false);
			logger.error("--------Clear Completed filter is still available-----------");
		}
		driver.quit();
	}

	//To add two todo items, used by 'Two new items can be added to the empty list' and 'Added items can be cleared' scenarios
	@When("MVC User adds Todos for {string} and {string}")
	@Given("a Todo list with items {string} and {string}")
	public void mvc_user_adds_todos_for_and(String string1, String string2) throws InterruptedException {
		logger.info("--------Adding two new Todo items-----------");
		hp.setNewTodo(string1);
		hp.setNewTodo(string2);
		logger.info("--------Added two new Todo items-----------");
		Thread.sleep(2000);
	}

	//To check the availability of added todo items
	@Then("only those two items are listed")
	public void only_those_two_items_are_listed() {
		if (hp.checkItemExists("both")==true) {
			Assert.assertTrue(true);
			logger.info("--------Added both todo items are listed-----------");
		}else {
			logger.error("--------New todos are not listed correctly-----------");
			Assert.assertTrue(false);

		}
	}

	//To remove added items
	@When("the item is removed")
	public void the_item_is_removed() throws InterruptedException{
		
		logger.info("--------Removing item-----------");
		hp.removeItem();
		logger.info("--------Item is removed-----------");
		Thread.sleep(2000);

	}

	//to check the availability of removed items
	@Then("nothing is listed")
	public void nothing_is_listed() {

		logger.info("--------Checking availability of removed todos-----------");
		if (hp.isRemovedItemPresent()==false) {
			Assert.assertTrue(true);
			logger.info("--------Item is removed from the list-----------");
		}else {
			logger.error("--------Item is still available in the list-----------");
			Assert.assertTrue(false);

		}
	}

	//to complete added todo item
	@And("the first item is completed")
	public void the_first_item_is_completed() throws InterruptedException {
		hp.selectCompleteItem();
		logger.info("--------Todo item is selected as completed-----------");
	}

	//to click on 'Clear Completed' button
	@When("‘Clear completed’ is clicked")
	public void clear_completed_is_clicked() throws InterruptedException {
		hp.clickClearCompleted();
		logger.info("--------Clicked on 'Clear Completed'-----------");
	}

	//to check the availability of uncompleted items
	@Then("only the second item is listed")
	public void only_the_second_item_is_listed() {
		
		logger.info("--------Checking cleared todos are displayed-----------");
		boolean isItem2 = hp.checkItemExists("second");
		boolean isItem1 = hp.isRemovedItemPresent();

		if(isItem1 && isItem2) {
			Assert.assertTrue(false);
			logger.error("--------Cleared item is still available in the list-----------");

		}else {
			Assert.assertTrue(true);
			logger.info("--------Cleared item is removed and next todo is available in the list-----------");
		}
	}

}

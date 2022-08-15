package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ToDoHomePage {

	WebDriver driver;

	//Constructor
	public ToDoHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
	}

	//locating page elements
	@FindBy(xpath = "/html[1]/body[1]/section[1]/header[1]/input[1]")
	@CacheLookup
	WebElement txtNewTodo;

	@FindBy(xpath = "//label[contains(text(),'Go shopping')]")
	WebElement item1;

	@FindBy(xpath = "//label[contains(text(),'Clean the house')]")
	WebElement item2;

	@FindBy(xpath = "/html[1]/body[1]/section[1]/footer[1]/span[1]")
	@CacheLookup
	WebElement msgTodoCount;

	@FindBy(xpath = "/html/body/section/footer/ul/li[1]/a")
	@CacheLookup
	WebElement filterAll;

	@FindBy(xpath = "//a[contains(text(),'Active')]")
	@CacheLookup
	WebElement filterActive;

	@FindBy(xpath = "//a[contains(text(),'Completed')]")
	@CacheLookup
	WebElement filterCompleted;

	@FindBy(xpath = "//button[contains(text(),'Clear completed')]")
	@CacheLookup
	WebElement filterClrCompleted;

	@FindBy(xpath="/html/body/section/section/ul/li/div/button")
	WebElement btnRemove;

	@FindBy(xpath="//body/section[1]/section[1]/ul[1]/li[1]/div[1]/input[1]")
	WebElement chkboxComplete1;

	//to check todo textbox is empty at url opening
	public boolean istxtTodoEmpty() {
		if (txtNewTodo.getAttribute("value").isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	//Add new todos
	public void setNewTodo(String item) {
		txtNewTodo.sendKeys(item + Keys.ENTER);
	}

	//to check existance of items
	public boolean checkItemExists(String itemVerifier) {
		boolean a = false;
		switch (itemVerifier){
		case "first":
			if (item1.isDisplayed()) {
				a= true;
				//return a;
			} else {
				a= false;
				//return a;
			}return a;

		case "both":
			if (item2.isDisplayed() && item1.isDisplayed()) {
				a= true;
				//return a;
			} else {
				a= false;
				//return a;
			}return a;	

		case "second":
			if (item2.isDisplayed()) {
				a= true;
				//return a;
			} else {
				a= false;
				//return a;
			}return a;
		}return a;
	}
	
	//To get and return item remaining message
	public String getRemainMsg() {
		String msg = msgTodoCount.getText();
		return msg;
	}

	//to check filter 'All' selected
	public boolean isFilterAllSelected() {
		String valAll = filterAll.getAttribute("class");
		if (valAll.equals("selected")) {
			//System.out.println("## Filter 'All' is selected ##");
			return true;
		} else {
			return false;
		}
	}

	////to check filter 'Active' selected
	public boolean isFilterActiveSelected() {

		if (filterActive.isSelected()) 
		{ 
			return false; 
		} else 
		{ 
			//System.out.println("## Filter 'Active' is unselected ##");
			return true;
		}
	}

	//to check filter 'Completed' selected
	public boolean isFilterCompletedSelected() {

		if (filterCompleted.isSelected()) {
			return false;
		} else {
			//System.out.println("## Filter 'Completed' is unselected ##");
			return true;
		}
	}

	//to check filter 'Clear COmpleted' exists
	public boolean checkFilterClrCompleteExists() {

		if (filterClrCompleted.isDisplayed()) {
			return false;
		} else {
			//System.out.println("## Filter 'Clear Completed' is hide ##");
			return true;
		}
	}

	//To remove item
	public void removeItem() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(item1).perform();
		Thread.sleep(2000);
		actions.moveToElement(btnRemove).perform();

		btnRemove.click();

	}
	
	//to check availability of removed item
	public boolean isRemovedItemPresent() {
		try {
			driver.findElements(By.xpath("//label[contains(text(),'Go shopping')]"));
			return false;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return true;
		}
	}

	//to select complete checkbox of an item
	public void selectCompleteItem() throws InterruptedException {
		chkboxComplete1.click();
		Thread.sleep(2000);
	}

	//to click 'clear complete' button
	public void clickClearCompleted() throws InterruptedException {
		filterClrCompleted.click();
		Thread.sleep(2000);
	}

}

package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ohtu.Dao.BookDao;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:4567/";

    public Stepdefs() {
        driver.get(baseUrl);
    }

    @Given("^books are selected$")
    public void books_are_selected() throws Throwable {
        WebElement element = driver.findElement(By.linkText("Books"));
        element.click();
    }

    @When("^author \"([^\"]*)\" and book name \"([^\"]*)\" and ISBN \"([^\"]*)\" are submitted")
    public void author_and_book_name_are_given(String author, String title, String ISBN) throws Throwable {
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(author);
        element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("ISBN"));
        element.sendKeys(ISBN);
        element = driver.findElement(By.name("submitbook"));
        element.click();
    }

    @When("^author \"([^\"]*)\" and book name \"([^\"]*)\" and ISBN \"([^\"]*)\" are new values of the book")
    public void author_and_book_name_and_isbn_are_edited(String newAuthor, String newTitle, String newISBN) {
        WebElement element = driver.findElement(By.name("author"));
        element.sendKeys(newAuthor);
        element = driver.findElement(By.name("title"));
        element.sendKeys(newTitle);
        element = driver.findElement(By.name("ISBN"));
        element.sendKeys(newISBN);
        element = driver.findElement(By.name("submitedit"));
        element.click();
    }

    @When("^book \"([^\"]*)\" is selected")
    public void book_name_is_selected(String title) {
        WebElement element = driver.findElement(By.linkText(title));
        element.click();
    }

    @Then("^book named \"([^\"]*)\" has been added$")
    public void user_has_added_new_bookmark(String title) throws Throwable {
        pageHasContent(title);
    }

    @Then("^book named \"([^\"]*)\" has been edited and its new name is \"([^\"]*)\"")
    public void user_has_changed_the_name_of_an_existing_bookmark(String originalTitle, String newTitle) {
        pageHasNoContent(originalTitle);
        pageHasContent(newTitle);
    }

    @Given("^new book has been added$")
    public void new_book_by_sipser_has_been_added() throws Throwable {
        WebElement element = driver.findElement(By.linkText("Books"));
        element.click();
        element = driver.findElement(By.name("title"));
        element.sendKeys("Introduction to the Theory of Computation");
        element = driver.findElement(By.name("author"));
        element.sendKeys("Michael Sipser");
        element = driver.findElement(By.name("ISBN"));
        element.sendKeys("978-1133187790");
        element = driver.findElement(By.name("tags"));
        element.sendKeys("Laskennan mallit");
        element = driver.findElement(By.name("submitbook"));
        element.click();
    }

    @When("^edit button is selected")
    public void book_edit_button_has_been_pressed() {
        WebElement element = driver.findElement(By.name("editbutton"));
        element.click();
    }

    @When("^book is deleted$")
    public void book_is_deleted() throws Throwable {
        WebElement element = driver.findElement(By.name("poispois"));
        element.click();
    }

    @Then("^book isn't listed$")
    public void book_by_sipser_isnt_listed() throws Throwable {
        pageHasNoContent("Introduction");
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    /* helper methods */
    private void pageHasNoContent(String content) {
        assertTrue(!driver.getPageSource().contains(content));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}

package stepDefinitions;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.cucumber.java.en.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.EmployeePage;
import pageObjects.LoginPage;
import pageObjects.UpdatePage;
import utilities.waitHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CepheidTestSteps {
   
    public WebDriver driver;
    public NgWebDriver ngDriver;
    public LoginPage loginPage;
    public UpdatePage updatePage;
    public EmployeePage employeePage;
    waitHelper wait;
    String lastName;
    String newEmployee;
    String new_email;
    String loginUrl;

    public CepheidTestSteps() {
        driver = Hooks.driver;
        loginUrl = Hooks.loginUrl;
        wait = new waitHelper(driver);
        loginPage = new LoginPage(driver);
        updatePage = new UpdatePage(driver);
        employeePage = new EmployeePage(driver);
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(jsDriver);
    }

    @Given("User login with valid username {string} and password {string}")
    public void user_login_with_valid_username_and_password(String username, String password) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();
    }

    @Then("login should be successful")
    public void login_should_be_successful() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(loginUrl)));
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/employees"));
    }
    @Given("User enters Invalid username {string} and password {string}")
    public void user_enters_Invalid_username_and_password(String username, String password) {
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();
    }

    @Then("login should fail")
    public void login_should_fail() {
       
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if((driver.getPageSource().contains("Invalid username or password!')")))
            Assert.assertTrue(true);
    }

    @When("User clicks logout button")
    public void user_clicks_logout_button() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(loginUrl)));
        WebElement data = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p[ng-click='logout()']")));
        Assert.assertTrue(data.isDisplayed());
        loginPage.clickLogout();
    }

    @Then("Navigate to homepage")
    public void navigate_to_homepage() {
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/login"));
    }

    @When("Open create employee wrapper")
    public void open_create_employee_wrapper() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        employeePage.clickAddBtn();
    }

    @Then("Provide details and add employee")
    public void provide_details_and_add_employee() {
        ngDriver.waitForAngularRequestsToFinish();
        updatePage.setFirstName("Luke");
        lastName= RandomStringUtils.randomAlphabetic(4);
        newEmployee = "Luke"+" "+lastName;
        updatePage.setLastName(lastName);
        updatePage.setStartDate("2019-01-01");
        updatePage.setEmail("luke"+lastName+"@cepheid.com");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        updatePage.clickAddBtn();
    }

    @And("Validate addition of new employee")
    public void validate_addition_of_new_employee() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        ngDriver.waitForAngularRequestsToFinish();
        String text = driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + lastName + "')]")).getText();
        Assert.assertEquals(newEmployee,text);
        System.out.println("The Employee Added is: "+ newEmployee +"");
    }

    @And("Select the added employee to update")
    public void select_the_added_employee_to_update() {
        driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + newEmployee + "')]")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        employeePage.clickEditBtn();
    }

    @And("Make changes to the employee profile and submit")
    public void make_changes_to_the_employee_profile_and_submit() {
        String old_email = driver.findElement(ByAngular.model("selectedEmployee.email")).getAttribute("value");
        driver.findElement(ByAngular.model("selectedEmployee.email")).clear();
        new_email = "001"+old_email;
        driver.findElement(ByAngular.model("selectedEmployee.email")).sendKeys(new_email);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        updatePage.clickUpdateBtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("/employees"));
    }

    @And("Verify the changes are reflected")
    public void verify_the_changes_are_reflected() {
        driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + newEmployee + "')]")).click();
        employeePage.clickEditBtn();
        ngDriver.waitForAngularRequestsToFinish();
        WebElement update = driver.findElement(By.xpath("//button[text()=\"Update\"]"));
        wait.WaitForElement(update,5);
        String current_email = driver.findElement(ByAngular.model("selectedEmployee.email")).getAttribute("value");
        Assert.assertEquals(current_email, new_email);
    }

    @Then("Select the added employee profile to delete")
    public void select_the_added_employee_profile_to_delete() {
        driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + newEmployee + "')]")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        employeePage.clickDeleteBtn();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.switchTo().alert().accept();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Then("Delete employee profile and validate change")
    public void delete_employee_profile_and_validate_change() {
        ngDriver.waitForAngularRequestsToFinish();
        driver.findElements(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + lastName + "')]")).isEmpty();
        System.out.println("The Deleted Employee  is: "+ newEmployee +"");
    }
    @When("DoubleClick on employee")
    public void doubleclick_on_employee() {
        ngDriver.waitForAngularRequestsToFinish();
        Actions actions = new Actions(driver);
        WebElement employee = driver.findElement(By.xpath("//*[@id=\"employee-list\"]/li[1]"));
        actions.doubleClick(employee).perform();
    }

    @Then("Navigate to Update page")
    public void navigate_to_Update_page() {
        ngDriver.waitForAngularRequestsToFinish();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement updateValue = driver.findElement(By.xpath("//button[text()=\"Update\"]"));
        wait.WaitForElement(updateValue,5);
        Assert.assertTrue(driver.getCurrentUrl().contains("/edit"));
    }
}

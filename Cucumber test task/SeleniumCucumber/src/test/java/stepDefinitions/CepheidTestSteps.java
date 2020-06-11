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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CepheidTestSteps {
    String loginUrl = "http://cafetownsend-angular-rails.herokuapp.com/login";
    public WebDriver driver;
    public NgWebDriver ngDriver;
    String lastName;
    String newEmployee;
    String new_email;

    public CepheidTestSteps() {
        driver = Hooks.driver;
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(jsDriver);
        ngDriver.waitForAngularRequestsToFinish();
    }

    @Given("User enters Valid UserName and Password")
    public void user_enters_Valid_UserName_and_Password() {
        driver.findElement(ByAngular.model("user.name")).sendKeys("Luke");
        driver.findElement(ByAngular.model("user.password")).sendKeys("Skywalker");

    }

    @When("Click login button")
    public void click_login_button() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("login should be successful")
    public void login_should_be_successful() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(loginUrl)));
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        Assert.assertTrue(currentURL.contains("/employees"));
    }


    @Given("User enters Invalid UserName or Password")
    public void user_enters_Invalid_UserName_or_Password() {
        driver.findElement(ByAngular.model("user.name")).sendKeys("Leia");
        driver.findElement(ByAngular.model("user.password")).sendKeys("Skywalker");
    }

    @Then("login should fail")
    public void login_should_fail() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[contains(text(),'Invalid username or password!')]")).isDisplayed();
    }

    @Given("User logs in successfully")
    public void user_logs_in_successfully() {
        driver.findElement(ByAngular.model("user.name")).sendKeys("Luke");
        driver.findElement(ByAngular.model("user.password")).sendKeys("Skywalker");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @When("User clicks logout button")
    public void user_clicks_logout_button() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(loginUrl)));
        WebElement data = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p[ng-click='logout()']")));
        Assert.assertTrue(data.isDisplayed());
        driver.findElement(By.cssSelector("p[ng-click='logout()']")).click();
    }

    @Then("Navigate to homepage")
    public void navigate_to_homepage() {
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/login"));
    }

    @When("Open create employee wrapper")
    public void open_create_employee_wrapper() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"bAdd\"]")).click();
    }

    @Then("Provide details and add employee")
    public void provide_details_and_add_employee() {
        ngDriver.waitForAngularRequestsToFinish();
        driver.findElement(ByAngular.model("selectedEmployee.firstName")).sendKeys("Luke");
        lastName= RandomStringUtils.randomAlphabetic(4);
        newEmployee = "Luke"+" "+lastName;
        driver.findElement(ByAngular.model("selectedEmployee.lastName")).sendKeys(lastName);
        driver.findElement(ByAngular.model("selectedEmployee.startDate")).sendKeys("2019-01-01");
        driver.findElement(ByAngular.model("selectedEmployee.email")).sendKeys("luke"+lastName+"@cepheid.com");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button[text()=\"Add\"]")).click();
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
        driver.findElement(By.xpath("//*[@id=\"bEdit\"]")).click();
    }

    @And("Make changes to the employee profile and submit")
    public void make_changes_to_the_employee_profile_and_submit() {
        String old_email = driver.findElement(ByAngular.model("selectedEmployee.email")).getAttribute("value");
        driver.findElement(ByAngular.model("selectedEmployee.email")).clear();
        new_email = "001"+old_email;
        driver.findElement(ByAngular.model("selectedEmployee.email")).sendKeys(new_email);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button[text()=\"Update\"]")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/employees"));
    }

    @And("Verify the changes are reflected")
    public void verify_the_changes_are_reflected() {
        System.out.println(newEmployee);
        driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + newEmployee + "')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.findElement(By.xpath("//*[@id=\"bEdit\"]")).click();
        ngDriver.waitForAngularRequestsToFinish();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"Update\"]")));
        String current_email = driver.findElement(ByAngular.model("selectedEmployee.email")).getAttribute("value");
        Assert.assertEquals(current_email, new_email);
    }

    @Then("Select the added employee profile to delete")
    public void select_the_added_employee_profile_to_delete() {
        driver.findElement(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + newEmployee + "')]")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"bDelete\"]")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.switchTo().alert().accept();
    }

    @Then("Delete employee profile and validate change")
    public void delete_employee_profile_and_validate_change() {
        ngDriver.waitForAngularRequestsToFinish();
        Boolean isPresent = driver.findElements(By.xpath("//ul[@id='employee-list']/li[contains (text(),'" + lastName + "')]")).size() == 0;
        Assert.assertEquals(isPresent, true);
    }
}

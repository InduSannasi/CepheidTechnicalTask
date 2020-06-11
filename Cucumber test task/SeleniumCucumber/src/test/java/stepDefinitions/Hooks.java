package stepDefinitions;



import com.paulhammant.ngwebdriver.NgWebDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Hooks {
    String loginUrl = "http://cafetownsend-angular-rails.herokuapp.com/login";
    public static WebDriver driver;
    public static NgWebDriver ngDriver;

    @Before
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(loginUrl);
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}

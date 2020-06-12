package pageObjects;

import com.paulhammant.ngwebdriver.ByAngularModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver rdriver)
    {
        driver = rdriver;
        PageFactory.initElements(rdriver,this);
    }
    @ByAngularModel.FindBy(model = "user.name")
    WebElement username;

    @ByAngularModel.FindBy(model = "user.password")
    WebElement password;

    @FindBy(css="button[type='submit']")
    WebElement login;

    @FindBy(css="p[ng-click='logout()']")
    WebElement logout;



    public void setUsername(String name){
        //username.clear();
        username.sendKeys(name);
    }

    public void setPassword(String word){
        password.clear();
        password.sendKeys(word);
    }

    public void clickLogin(){
        login.click();
    }

    public void clickLogout(){
        logout.click();
    }
}

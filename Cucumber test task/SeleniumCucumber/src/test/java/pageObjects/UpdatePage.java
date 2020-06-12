package pageObjects;

import com.paulhammant.ngwebdriver.ByAngularModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UpdatePage {
    public WebDriver driver;

    public UpdatePage(WebDriver rdriver)
    {
        driver = rdriver;
        PageFactory.initElements(rdriver,this);
    }

    @ByAngularModel.FindBy(model = "selectedEmployee.firstName")
    WebElement firstName;

    @ByAngularModel.FindBy(model = "selectedEmployee.lastName")
    WebElement lastName;

    @ByAngularModel.FindBy(model = "selectedEmployee.startDate")
    WebElement startDate;

    @ByAngularModel.FindBy(model = "selectedEmployee.email")
    WebElement email;

    @FindBy(xpath="//button[text()=\"Add\"]")
    WebElement addBtn;

    @FindBy(xpath="//button[text()=\"Update\"]")
    WebElement updateBtn;

    public void setFirstName(String name){
        firstName.clear();
        firstName.sendKeys(name);
    }

    public void setLastName(String word){
        lastName.clear();
        lastName.sendKeys(word);
    }

    public void setStartDate(String date){
        startDate.clear();
        startDate.sendKeys(date);
    }
    public void setEmail(String value){
        email.clear();
        email.sendKeys(value);
    }

    public void clickAddBtn(){
        addBtn.click();
    }

    public void clickUpdateBtn(){
        updateBtn.click();
    }
}


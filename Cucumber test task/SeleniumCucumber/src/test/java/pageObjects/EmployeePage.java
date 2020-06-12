package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmployeePage {
    public WebDriver driver;

    public EmployeePage(WebDriver rdriver)
    {
        driver = rdriver;
        PageFactory.initElements(rdriver,this);
    }
    @FindBy(xpath="//*[@id=\"bAdd\"]")
    WebElement addBtn;

    @FindBy(xpath="//*[@id=\"bEdit\"]")
    WebElement updateBtn;

    @FindBy(xpath="//*[@id=\"bDelete\"]")
    WebElement deleteBtn;

    public void clickAddBtn(){
        addBtn.click();
    }
    public void clickEditBtn(){
        updateBtn.click();
    }
    public void clickDeleteBtn(){
        deleteBtn.click();
    }
}

package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class waitHelper {
    public WebDriver driver;
    public waitHelper(WebDriver rdriver)
    {
        this.driver = rdriver;

    }

    public void  WaitForElement(WebElement element,long time){
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}

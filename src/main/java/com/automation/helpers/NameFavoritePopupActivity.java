package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 3/13/17.
 */

public class NameFavoritePopupActivity extends DefaultActivity {

    @FindBy(id = "com.waze:id/btnDone")
    public WebElement doneButton;
    @FindBy(id = "com.waze:id/btnCancel")
    public WebElement cancelButton;
    @FindBy(id = "com.waze:id/nameEditText")
    public WebElement nameOfFavorite;


    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public NameFavoritePopupActivity(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
}
package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 3/14/17.
 */
public class GoNowPopupActivity extends DefaultActivity {

    @FindBy(id ="com.waze:id/fragNavResGo")
    public WebElement goButton;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public GoNowPopupActivity(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

}

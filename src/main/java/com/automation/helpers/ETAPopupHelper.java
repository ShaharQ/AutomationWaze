package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 2/14/17.
 */
public class ETAPopupHelper extends DefaultHelper {

        @FindBy(id = "fragNavResStop")
        public WebElement stopButton;

        //Set Peopert for ATU Reporter Configuration
        {
            System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

        }

        public ETAPopupHelper(AppiumDriver driver) {
            super(driver);
            PageFactory.initElements(driver,this);
        }
}


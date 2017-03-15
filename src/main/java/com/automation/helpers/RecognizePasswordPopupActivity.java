package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 3/7/17.
 */
public class RecognizePasswordPopupActivity extends DefaultActivity {

        @FindBy(id = "com.google.android.gms:id/credential_save_reject")
        public WebElement neverSavePassword;

        //Set Peopert for ATU Reporter Configuration
        {
            System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

        }

        public RecognizePasswordPopupActivity(AppiumDriver driver) {
            super(driver);
            PageFactory.initElements(driver,this);
        }
}



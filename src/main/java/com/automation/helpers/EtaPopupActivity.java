package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 2/14/17.
 */
public class EtaPopupActivity extends DefaultActivity {


        @FindBy(id = "com.waze:id/fragNavResStop")
        public WebElement stopButton;
        @FindBy(id ="com.waze:id/fragNavResGo")
        public WebElement goButton;
        @FindBy(id ="com.waze:id/fragNavResRoutes")
        public WebElement routeButton;


        //Set Peopert for ATU Reporter Configuration
        {
            System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

        }

        public EtaPopupActivity(AppiumDriver driver) {
            super(driver);
            PageFactory.initElements(driver,this);
        }
}


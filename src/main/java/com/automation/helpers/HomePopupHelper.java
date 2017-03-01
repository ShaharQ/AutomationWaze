package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 3/1/17.
 */
public class HomePopupHelper extends Activity{

    @FindBy(id = "com.waze:id/bottomSheetItem")
    public WebElement removeButton;
    @FindBy(id = "com.waze:id/addressPreviewGoButton")
    public WebElement addAdressButton;



    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public HomePopupHelper(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
}



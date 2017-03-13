package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by mkalash on 3/13/17.
 */
public class WorkPopupHelper extends DefaultHelper {

    @FindBy(id = "com.waze:id/bottomSheetItem")
    public List<WebElement> removeButton;
    @FindBy(id = "com.waze:id/addressPreviewGoButton")
    public WebElement addAdressButton;



    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public WorkPopupHelper(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

}

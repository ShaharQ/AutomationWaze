package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by mkalash on 3/13/17.
 */
public class FavoriteActivity extends DefaultActivity {

    @FindBy(id = "com.waze:id/addressItem")
    public WebElement addFavoriteAddress;
    @FindBy(className = "android.widget.LinearLayout")
    public List<WebElement> stopButtonNew;


    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public FavoriteActivity(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
}

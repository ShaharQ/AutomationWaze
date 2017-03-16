package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by mkalash on 3/16/17.
 */
public class RoutesActivity extends DefaultActivity {

    @FindBy(id = "com.waze:id/routesMapButtonText")
    public WebElement mapView;
    @FindBy(id = "com.waze:id/routesListButtonText")
    public WebElement listView;
    @FindBy(className = "android.widget.LinearLayout")
    public List<WebElement> routes;
    @FindBy(id = "com.waze:id/routesMapGoText")
    public WebElement goButton;




    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public RoutesActivity(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

}

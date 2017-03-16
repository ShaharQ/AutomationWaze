package com.automation.helpers;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;

/**
 * Created by mkalash on 3/6/17.
 */
public class LoginActivity extends DefaultActivity {

    private MapActivity mapActivity;
    private NavigateActivity navigateActivity;
    private WazeAccountActivity wazeAccountHelper;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public LoginActivity(AppiumDriver driver) {
        super(driver);
    }

    public void loginAsWazer(DriverManager driverManager) throws InterruptedException, MalformedURLException {

        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");
        mapActivity.clickElement(mapActivity.searchButton , "Search button");
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.profiePicture,"Profile picture");
        wazeAccountHelper = new WazeAccountActivity(driver);
        wazeAccountHelper.clickElement(wazeAccountHelper.existAccount,"existing Account");
        wazeAccountHelper = new WazeAccountActivity(driver);
        wazeAccountHelper.clickElement(wazeAccountHelper.connectWithUserName,"connect with user name and password");
        wazeAccountHelper = new WazeAccountActivity(driver);
        wazeAccountHelper.sendKeysToWebElementInput(wazeAccountHelper.userName , "automation-il23");
        wazeAccountHelper.sendKeysToWebElementInput(wazeAccountHelper.password , "S3clientlogs");
        wazeAccountHelper.clickElement(wazeAccountHelper.connectButton,"Connect");
        //RecognizePasswordPopupActivity recognizePasswordPopupHelper = new RecognizePasswordPopupActivity(driver);
        //recognizePasswordPopupHelper.clickElement(recognizePasswordPopupHelper.neverSavePassword,"Never save password.");
    }
}

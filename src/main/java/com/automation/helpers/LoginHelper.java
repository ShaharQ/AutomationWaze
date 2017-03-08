package com.automation.helpers;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;

/**
 * Created by mkalash on 3/6/17.
 */
public class LoginHelper extends Activity{

    MapHelper mapHelper;
    SearchHelper searchHelper;
    WazeAccountHelper wazeAccountHelper;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public LoginHelper(AppiumDriver driver) {
        super(driver);
    }

    public void loginAsWazer(DriverManager driverManager) throws InterruptedException, MalformedURLException {

        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");
        mapHelper.clickElement(mapHelper.searchButton , "Search button");
        searchHelper =  new SearchHelper(driver);
        searchHelper.clickElement(searchHelper.profiePicture,"Profile picture");
        wazeAccountHelper = new WazeAccountHelper(driver);
        wazeAccountHelper.clickElement(wazeAccountHelper.existAccount,"existing Account");
        wazeAccountHelper = new WazeAccountHelper(driver);
        wazeAccountHelper.clickElement(wazeAccountHelper.connectWithUserName,"connect with user name and password");
        wazeAccountHelper = new WazeAccountHelper(driver);
        wazeAccountHelper.sendKeysToWebElementInput(wazeAccountHelper.userName , "automation-il23");
        wazeAccountHelper.sendKeysToWebElementInput(wazeAccountHelper.password , "S3clientlogs");
        wazeAccountHelper.clickElement(wazeAccountHelper.connectButton,"Connect");
        //RecognizePasswordPopupHelper recognizePasswordPopupHelper = new RecognizePasswordPopupHelper(driver);
        //recognizePasswordPopupHelper.clickElement(recognizePasswordPopupHelper.neverSavePassword,"Never save password.");
    }
}

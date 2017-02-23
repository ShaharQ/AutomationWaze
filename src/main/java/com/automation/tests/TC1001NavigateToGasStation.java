package com.automation.tests;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.helpers.*;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

/**
 * Created by mkalash on 2/22/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1001NavigateToGasStation {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;
    ETAPopupHelper etaPopupHelper;
    ConfirmHelper confirmHelper;
    String proccessName , phoneName;
    DriverManager driverManager = new DriverManager();

    @BeforeTest
    public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer");
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.openProcess( phoneName, proccessName);
        driver = driverManager.getDriver( phoneName, "4444");

    }

    @AfterTest
    public void closeAppium() {

        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, IOException, AWTException {

        //1. open new session
        //mapHelper.openNewSession("LG");

        //1.pre test if we get the popup of drive now or later
        confirmHelper = new ConfirmHelper(driver);
        if(confirmHelper.isElementDisplay(confirmHelper.driverNowButton)) {
            confirmHelper.clickElement(confirmHelper.driverNowButton, "drive now button");
            confirmHelper.clickBackOnTheDevice();
            confirmHelper.clickBackOnTheDevice();
        }

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //4.Tap the search box
        searchHelper =  new SearchHelper(driver);
        searchHelper.clickElement(searchHelper.searchBox,"Search box");

        //5.Tap 'Gas station' cell
        searchHelper.verifyThatTheTextOfTheElementIsAsExpected(searchHelper.autoCompleteSearchResults.get(0),"Gas stations","תחנות דלק");
        searchHelper.clickElement(searchHelper.autoCompleteSearchResults.get(0) , "Gas Station");

        //6.verify the screen title is 'Gas Stations'
        searchHelper.verifyThatTheTextOfTheElementIsAsExpected(searchHelper.titleBarText,"Gas stations","תחנות דלק" );

        //7.select the second Gas stations results
        searchHelper.clickElement(searchHelper.autoCompleteSearchResults.get(1) , "Second Gas stations");

        //8.Tap 'GO'
        searchHelper.clickElement(searchHelper.previewGoButton , "preview go");

        //9.Tap 'GO now'
        searchHelper.clickElement(searchHelper.goButton , "go now");

        //10.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getY() - mapHelper.minutesOfDriving.getLocation().getY()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //11.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //12.Tap 'No thanks'
        confirmHelper = new ConfirmHelper(driver);
        confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




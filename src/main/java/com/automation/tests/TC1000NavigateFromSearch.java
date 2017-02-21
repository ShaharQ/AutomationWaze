package com.automation.tests; /**
 * Created by OmriNissim on 31/01/2017.
 */

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.helpers.*;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.awt.*;
import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1000NavigateFromSearch {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;
    DirectionsHelper directionsHelper;
    ETAPopupHelper etaPopupHelper;
    ConfirmHelper confirmHelper;
    String proccessName;

    @BeforeTest
        public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer");
        proccessName  = System.getProperty("Phone") + "Node";
        Utils.openProcess( System.getProperty("Phone"), proccessName);
        driver = DriverManager.getDriver( System.getProperty("Phone"), "4444");

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
       if(confirmHelper.driverNowOrLaterPopUp.isDisplayed()) {
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

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        searchHelper =  new SearchHelper(driver);
        searchHelper.verifySearchViewOpen();

        //5.write hike in the edit box
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"nike");

        //6.Tap the close icon in the search box
        searchHelper.clickElement(searchHelper.exitSearch , "Exit button");

        //7.Enter the string 'hike' and tap enter
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"nike" );
        searchHelper.sendKeyboardKeys(66 , "Search");

        //8.Search results should appear after a few seconds
        searchHelper.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        searchHelper.clickOnTheBottomObject(2 , "Google");

        //10.Tap 'Places'
        searchHelper.clickOnTheBottomObject(1 , "Places");

        //11.Tap 'Search Results'
        searchHelper.clickOnTheBottomObject(0 , "Search Results");

        //12.Search the first results
        searchHelper.selectTheFirstResult();

        //13.Tap the 'more options' icon (the grey rectangle with the three white dots)
        searchHelper.clickElement(searchHelper.threeDots , "more options");

       //14.Tap back(the Android action button)
        searchHelper.clickBackOnTheDevice();

        //15.Tap 'GO'
        searchHelper.clickElement(searchHelper.previewGoButton , "preview go");

        //16.Tap 'GO now'
        searchHelper.clickElement(searchHelper.goButton , "go now");

        //17.Tap the navigation list bar(where the route directions are)
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.topBarButton , "navigation list bar");

        //18.Tap 'Reports Ahead'
        directionsHelper = new DirectionsHelper(driver);
        directionsHelper.clickElement(directionsHelper.reportAhead , "Reports Ahead");

        //19.Tap 'Next Turns'
        directionsHelper.clickElement(directionsHelper.nextTurns , "Next Turns");

        //20.Tap back(the Android action button)
        searchHelper.clickBackOnTheDevice();

        //21.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getY() - mapHelper.minutesOfDriving.getLocation().getY()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //22.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //23.Tap 'No thanks'
        confirmHelper = new ConfirmHelper(driver);
        confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");


        System.out.println("done");
    }
}




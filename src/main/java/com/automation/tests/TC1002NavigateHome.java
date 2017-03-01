package com.automation.tests;

/**
 * Created by mkalash on 3/1/17.
 */


        import atu.testng.reports.ATUReports;
        import atu.testng.reports.listeners.ATUReportsListener;
        import atu.testng.reports.listeners.ConfigurationListener;
        import atu.testng.reports.listeners.MethodListener;
        import atu.testng.reports.logging.LogAs;
        import io.appium.java_client.AppiumDriver;
        import org.testng.annotations.AfterTest;
        import org.testng.annotations.BeforeTest;
        import org.testng.annotations.Listeners;
        import org.testng.annotations.Test;
        import com.automation.helpers.*;

        import java.awt.*;
        import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1002NavigateHome {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;
    DirectionsHelper directionsHelper;
    ETAPopupHelper etaPopupHelper;
    ConfirmHelper confirmHelper;
    HomePopupHelper homePopupHelper;
    String proccessName , phoneName;
    DriverManager driverManager = new DriverManager();


    @BeforeTest
    public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer" , true);
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.startAppiumNode(proccessName);
        driver = driverManager.getDriver( System.getProperty("Phone"), "4444");

    }

    @AfterTest
    public void closeAppium() {

        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, IOException, AWTException {


        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //3.precondition : the user should have an empty home favorite ,
        searchHelper = new SearchHelper(driver);
        if(searchHelper.homeFavorite.isDisplayed()) {
            //3.1 tap the more options icon (the three grey dots)
            searchHelper.clickElement(searchHelper.homeFavoriteDot, "home three dots");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            homePopupHelper = new HomePopupHelper(driver);
            homePopupHelper.swipeDown();
            homePopupHelper.clickElement(homePopupHelper.removeButtom, "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        searchHelper =  new SearchHelper(driver);
        searchHelper.clickElement(searchHelper.homeFavorite, "home favorite");

        //5.enter the string 'rehovot' abd tap enter
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"rehovot");
        searchHelper.sendKeyboardKeys(66 , "Search");

        //6.Search the first results
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


        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




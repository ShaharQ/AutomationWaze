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

/**
 * Created by mkalash on 2/22/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1001NavigateToGasStation {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    private AppiumDriver driver;
    private MapHelper mapHelper;
    private NavigationHelper navigationHelper;
    private ETAPopupHelper etaPopupHelper;
    private ConfirmHelper confirmHelper;
    private String proccessName , phoneName;
    private DriverManager driverManager = new DriverManager();

    @BeforeTest
    public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer", true);
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.startAppiumNode(proccessName);
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
        navigationHelper =  new NavigationHelper(driver);
        navigationHelper.clickElement(navigationHelper.searchBox,"Search box");

        //5.Tap 'Gas station' cell
        navigationHelper.verifyThatTheTextOfTheElementIsAsExpected(navigationHelper.autoCompleteSearchResults.get(0),"Gas stations","תחנות דלק");
        navigationHelper.clickElement(navigationHelper.autoCompleteSearchResults.get(0) , "Gas Station");

        //6.verify the screen title is 'Gas Stations'
        navigationHelper.verifyThatTheTextOfTheElementIsAsExpected(navigationHelper.titleBarText,"Gas stations","תחנות דלק" );

        //7.select the second Gas stations results
        navigationHelper.clickElement(navigationHelper.autoCompleteSearchResults.get(1) , "Second Gas stations");

        //8.Tap 'GO'
        navigationHelper.clickElement(navigationHelper.previewGoButton , "preview go");

        //9.Tap 'GO now'
        navigationHelper.clickElement(navigationHelper.goButton , "go now");

        //10.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getY() - mapHelper.minutesOfDriving.getLocation().getY()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //11.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //12.Tap 'No thanks' +test
        confirmHelper = new ConfirmHelper(driver);
        confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




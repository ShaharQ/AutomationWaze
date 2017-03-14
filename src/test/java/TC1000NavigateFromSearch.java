import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.*;
import com.automation.helpers.*;

import java.awt.*;
import java.io.IOException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1000NavigateFromSearch {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    private AppiumDriver driver;
    private MapHelper mapHelper;
    private NavigationResultsHelper navigationResultsHelper;
    private NavigationHelper navigationHelper;
    private DirectionsHelper directionsHelper;
    private ETAPopupHelper etaPopupHelper;
    private ConfirmHelper confirmHelper;
    private String proccessName , phoneName;
    private DriverManager driverManager = new DriverManager();


    @BeforeClass
        public void setup() throws InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer" , true);
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.startAppiumNode(proccessName);
        driver = driverManager.getDriver( System.getProperty("Phone"), "4444");


    }

    @AfterClass
    public void closeAppium() throws InterruptedException {

        Utils.killAllCmd();
        Utils.killingTheGrid();
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException, IOException, AWTException {


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
        navigationHelper =  new NavigationHelper(driver);
        navigationHelper.verifySearchViewOpen();

        //5.write hike in the edit box
        navigationHelper.sendKeysToWebElementInput(navigationHelper.searchBox,"nike");

        //6.Tap the close icon in the search box
        navigationHelper.clickElement(navigationHelper.exitSearch , "Exit button");

        //7.Enter the string 'hike' and tap enter
        navigationHelper.sendKeysToWebElementInput(navigationHelper.searchBox,"nike" );
        navigationHelper.sendKeyboardKeys(navigationHelper.SEARCHBUTTON , "Search");

        //8.Search results should appear after a few seconds
        navigationResultsHelper = new NavigationResultsHelper(driver);
        navigationResultsHelper.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        navigationResultsHelper.clickOnTheBottomObject(2 , "Google");

        //10.Tap 'Places'
        navigationResultsHelper.clickOnTheBottomObject(1 , "Places");

        //11.Tap 'Search Results'
        navigationResultsHelper.clickOnTheBottomObject(0 , "Search Results");

        //12.Search the first results
        navigationResultsHelper.selectSearchResult(0);

        //13.Tap the 'more options' icon (the grey rectangle with the three white dots)
        navigationHelper.clickElement(navigationHelper.threeDots , "more options");

       //14.Tap back(the Android action button)
        navigationHelper.clickBackOnTheDevice();

        //15.Tap 'GO'
        navigationHelper.clickElement(navigationHelper.previewGoButton , "preview go");

        //16.Tap 'GO now'
        navigationHelper.clickElement(navigationHelper.goButton , "go now");

        //17.Tap the navigation list bar(where the route directions are)
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.topBarButton , "navigation list bar");

        //18.Tap 'Reports Ahead'
        directionsHelper = new DirectionsHelper(driver);
        directionsHelper.clickElement(directionsHelper.reportAhead , "Reports Ahead");

        //19.Tap 'Next Turns'
        directionsHelper.clickElement(directionsHelper.nextTurns , "Next Turns");

        //20.Tap back(the Android action button)
        navigationHelper.clickBackOnTheDevice();

        //21.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getX() - mapHelper.minutesOfDriving.getLocation().getX()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

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




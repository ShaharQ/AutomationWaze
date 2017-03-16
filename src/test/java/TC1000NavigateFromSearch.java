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
    private MapActivity mapActivity;
    private SearchResultsActivity searchResultsActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private NavigateActivity navigateActivity;
    private DirectionsActivity directionsActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
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

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.verifySearchViewOpen();

        //5.write hike in the edit box
        navigateActivity.sendKeysToWebElementInput(navigateActivity.searchBox,"nike");

        //6.Tap the close icon in the search box
        navigateActivity.clickElement(navigateActivity.exitSearch , "Exit button");

        //7.Enter the string 'hike' and tap enter
        navigateActivity.sendKeysToWebElementInput(navigateActivity.searchBox,"nike" );
        navigateActivity.sendKeyboardKeys(navigateActivity.SEARCHBUTTON , "Search");

        //8.Search results should appear after a few seconds
        searchResultsActivity = new SearchResultsActivity(driver);
        searchResultsActivity.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        searchResultsActivity.clickOnTheBottomObject(2 , "Google");

        //10.Tap 'Places'
        searchResultsActivity.clickOnTheBottomObject(1 , "Places");

        //11.Tap 'Search Results'
        searchResultsActivity.clickOnTheBottomObject(0 , "Search Results");

        //12.Search the first results
        searchResultsActivity.selectSearchResult(0);

        //13.Tap the 'more options' icon (the grey rectangle with the three white dots)
        navigateActivity.clickElement(navigateActivity.moreOptionButton , "more options");

       //14.Tap back(the Android action button)
        navigateActivity.clickBackOnTheDevice();

        //15.Tap 'GO'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.previewGoButton , "preview go");

        //16.Tap 'GO now'
        etaPopupActivity= new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

        //17.Tap the navigation list bar(where the route directions are)
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.topBarButton , "navigation list bar");

        //18.Tap 'Reports Ahead'
        directionsActivity = new DirectionsActivity(driver);
        directionsActivity.clickElement(directionsActivity.reportAhead , "Reports Ahead");

        //19.Tap 'Next Turns'
        directionsActivity.clickElement(directionsActivity.nextTurns , "Next Turns");

        //20.Tap back(the Android action button)
        directionsActivity.clickBackOnTheDevice();

        //21.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getX() - mapActivity.minutesOfDriving.getLocation().getX()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //22.Tap 'stop'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.stopButton , "stop button");

        //23.Tap 'No thanks'
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.noThanksButton.isDisplayed()) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




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
    private MapActivity mapActivity;
    private NavigateActivity navigateActivity;
    private SearchResultsActivity searchResultsActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
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


        //1.pre test if we get the popup of drive now or later
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.isElementDisplay(confirmPopupActivity.driverNowButton)) {
            confirmPopupActivity.clickElement(confirmPopupActivity.driverNowButton, "drive now button");
            confirmPopupActivity.clickBackOnTheDevice();
            confirmPopupActivity.clickBackOnTheDevice();
        }

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //4.Tap the search box
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.searchBox,"Search box");

        //5.Tap 'Gas station' cell
        navigateActivity.verifyThatTheTextOfTheElementIsAsExpected(navigateActivity.autoCompleteSearchResults.get(0),"Gas stations","תחנות דלק");
        navigateActivity.clickElement(navigateActivity.autoCompleteSearchResults.get(0) , "Gas Station");

        //6.verify the screen title is 'Gas Stations'
        searchResultsActivity = new SearchResultsActivity(driver);
        searchResultsActivity.verifyThatTheTextOfTheElementIsAsExpected(searchResultsActivity.titleBarText,"Gas stations","תחנות דלק" );

        //7.select the second Gas stations results
        searchResultsActivity.clickElement(searchResultsActivity.autoCompleteSearchResults.get(1) , "Second Gas stations");

        //8.Tap 'GO'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.previewGoButton , "preview go");

        //9.Tap 'GO now'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

        //10.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getY() - mapActivity.minutesOfDriving.getLocation().getY()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //11.Tap 'stop'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.stopButton , "stop button");

        //12.Tap 'No thanks'
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.noThanksButton.isDisplayed()) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




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
 * Created by mkalash on 3/16/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1005NavigateUsingAutoComplete {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    private AppiumDriver driver;
    private MapActivity mapActivity;
    private NavigateActivity navigateActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
    private RoutesActivity routesActivity;
    private String proccessName , phoneName;
    private DriverManager driverManager = new DriverManager();


    @BeforeTest
    public void setup() throws IOException, InterruptedException {

        Utils.openProcess("appium", "LaunchAppiumServer" , true);
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.startAppiumNode(proccessName);
        driver = driverManager.getDriver( System.getProperty("Phone"), "4444");

        //1.if we get the popup of drive now or later
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        confirmPopupActivity.checkIfPopUpAppeared();

        LoginActivity loginHelper = new LoginActivity(driver);
        loginHelper.loginAsWazer(driverManager);

    }

    @AfterTest
    public void closeAppium() {

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

        //4.Tap the search box
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.searchBox,"search box");

        //5.Enter the string ‘ramat gan’
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.sendKeysToWebElementInput(navigateActivity.searchBox , "ramat gan");

        //6.Hide keyboard
        navigateActivity.swipeUp();

        //7.Select the auto-complete 3rd result (their other 3 more frameLayout in this page)
        navigateActivity.selectSearchResultAutoComplete(5);

        //8.Tap 'GO'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.previewGoButton , "preview go");

        //9.Tap 'GO now'
        etaPopupActivity= new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

        //10.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getX() - mapActivity.minutesOfDriving.getLocation().getX()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //11.Tap 'route'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.routeButton , "route button");

        //12.Tap 'map view'
        routesActivity = new RoutesActivity(driver);
        routesActivity.clickElement(routesActivity.mapView , "map view");

        //13.Select the middle route (we have 2 more linearlayout in this page)
        routesActivity = new RoutesActivity(driver);
        routesActivity.clickElement(routesActivity.routes.get(3) , "middle route");

        //14. Tap 'Go'
        routesActivity.clickElement(routesActivity.goButton , "Go");

        //15.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getX() - mapActivity.minutesOfDriving.getLocation().getX()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //16.Tap 'stop'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.stopButton , "stop button");

        //17.Tap 'No thanks'
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.noThanksButton.isDisplayed()) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}
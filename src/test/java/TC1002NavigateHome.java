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
    private AppiumDriver driver;
    private MapActivity mapActivity;
    private NavigationActivity navigationActivity;
    private NavigationSearchResultsActivity navigationSearchResultsActivity;
    private EtaPopupActivity etaPopupActivity;
    private GoNowPopupActivity goNowPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
    private AddressItemOptionPopupActivity addressItemOptionPopupActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private String proccessName , phoneName;
    private DriverManager driverManager = new DriverManager();


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
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //3.precondition : the user should have an empty home favorite ,
        navigationActivity = new NavigationActivity(driver);
        if(navigationActivity.isElementDisplay(navigationActivity.homeFavoriteDot.get(0))) {
            //3.1 tap the more options icon (the three grey dots)
            navigationActivity.clickElement(navigationActivity.favoriteList.get(0), "more options");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            addressItemOptionPopupActivity = new AddressItemOptionPopupActivity(driver);
            addressItemOptionPopupActivity.swipeDown();
            addressItemOptionPopupActivity.clickElement(addressItemOptionPopupActivity.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigationActivity =  new NavigationActivity(driver);
        navigationActivity.clickElement(navigationActivity.favoriteList.get(0), "home favorite");

        //5.enter the string 'rehovot' abd tap enter
        navigationActivity.sendKeysToWebElementInput(navigationActivity.searchBox,"rehovot");
        navigationActivity.sendKeyboardKeys(navigationActivity.SEARCHBUTTON , "Search");

        //6.Search the first results
        navigationSearchResultsActivity = new NavigationSearchResultsActivity(driver);
        navigationSearchResultsActivity.selectSearchResult(0);

        //7. tap 'set work & go'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.addAdressButton, "add address button");

        //8.Tap 'GO now'
        goNowPopupActivity = new GoNowPopupActivity(driver);
        goNowPopupActivity.clickElement(goNowPopupActivity.goButton , "go now");

        //9.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getX() - mapActivity.minutesOfDriving.getLocation().getX()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //10.Tap 'stop'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.stopButton , "stop button");

        //11.Tap 'No thanks'
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.noThanksButton.isDisplayed()) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




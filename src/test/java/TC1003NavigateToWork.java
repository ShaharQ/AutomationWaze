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
 * Created by mkalash on 3/13/17.
 */


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC1003NavigateToWork {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    private AppiumDriver driver;
    private MapHelper mapHelper;
    private NavigationHelper navigationHelper;
    private ETAPopupHelper etaPopupHelper;
    private ConfirmHelper confirmHelper;
    private NavigationResultsHelper navigationResultsHelper;
    private WorkPopupHelper workPopupHelper;
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
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //3.precondition : the user should have an empty work favorite
        navigationHelper = new NavigationHelper(driver);
        if(navigationHelper.isTheWorkNavigationIsDefine()) {
            //3.1 tap the more options icon (the three grey dots)
            navigationHelper.clickElement(navigationHelper.favoriteList.get(1), "more option");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            workPopupHelper = new WorkPopupHelper(driver);
            workPopupHelper.swipeDown();
            workPopupHelper.clickElement(workPopupHelper.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigationHelper =  new NavigationHelper(driver);
        navigationHelper.clickElement(navigationHelper.favoriteList.get(1), "work favorite");

        //5.enter the string 'tel aviv' abd tap enter
        navigationHelper.sendKeysToWebElementInput(navigationHelper.searchBox,"tel aviv");
        navigationHelper.sendKeyboardKeys(navigationHelper.SEARCHBUTTON , "Search");

        //6.Search the first results
        navigationResultsHelper = new NavigationResultsHelper(driver);
        navigationResultsHelper.selectSearchResult(0);

        //7. tap 'set work & go'
        workPopupHelper = new WorkPopupHelper(driver);
        workPopupHelper.clickElement(workPopupHelper.addAdressButton, "add address button");

        //8.Tap 'GO now'
        navigationHelper.clickElement(navigationHelper.goButton , "go now");

        //9.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getX() - mapHelper.minutesOfDriving.getLocation().getX()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //10.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //11.Tap 'No thanks'
        confirmHelper = new ConfirmHelper(driver);
        if(confirmHelper.isElementDisplayWithOutSelect(confirmHelper.noThanksButton)) {
            confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




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
public class TC1004NavigateToFavorite {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    private AppiumDriver driver;
    private MapHelper mapHelper;
    private NavigationHelper navigationHelper;
    private NavigationResultsHelper navigationResultsHelper;
    private ETAPopupHelper etaPopupHelper;
    private ConfirmHelper confirmHelper;
    private FavoriteHelper favoriteHelper;
    private AddFavoriteHelper addFavoriteHelper;
    private NameFavoritePopupHelper nameFavoritePopupHelper;
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

        //3.precondition : if the user have empty favorite
        navigationHelper = new NavigationHelper(driver);
        navigationHelper.clickElement(navigationHelper.searchLayout.get(2) ,"favorite");

        //4.Select the first favorite which is not home or work
        String nameToPress =   addFavoriteHelper.findNameThatIsntWorkOrHome();
        if(nameToPress == null) {
            //5.Tap on the favorite
            favoriteHelper = new FavoriteHelper(driver);
            favoriteHelper.clickElement(favoriteHelper.addFavoriteAddress , "add favorite");

            //6.enter the string bat yam
            addFavoriteHelper = new AddFavoriteHelper(driver);
            addFavoriteHelper.sendKeysToWebElementInput(addFavoriteHelper.searchBoxFavorite,"bat yam");
            addFavoriteHelper.sendKeyboardKeys(navigationHelper.SEARCHBUTTON , "Search");

            //7.Search the first results
            navigationResultsHelper = new NavigationResultsHelper(driver);
            navigationResultsHelper.selectSearchResult(0);

            //8.Tap on finish
            nameFavoritePopupHelper = new NameFavoritePopupHelper(driver);
            nameFavoritePopupHelper.sendKeysToWebElementInput(nameFavoritePopupHelper.nameOfFavorite,"bat yam fav");
            nameFavoritePopupHelper.clickElement(nameFavoritePopupHelper.doneButton ,"done");

            //9.Verify that we return to the map
            mapHelper.verifyElementIsDisplayed(mapHelper.map , "Map");

            //10.click on the main menu icon(the magnifying glass icon)
            mapHelper.clickElement(mapHelper.searchButton , "Search button");

            //11.precondition : if the user have empty favorite
            navigationHelper.clickElement(navigationHelper.searchLayout.get(2) ,"favorite");

            nameToPress =   addFavoriteHelper.findNameThatIsntWorkOrHome();
            addFavoriteHelper.pressOnTheLayoutWithThatString(addFavoriteHelper.favoriteLayouts,nameToPress);
        } else {
            addFavoriteHelper.pressOnTheLayoutWithThatString(addFavoriteHelper.favoriteLayouts,nameToPress);
        }

        //11.Tap ‘Go now’
        navigationHelper.clickElement(navigationHelper.goButton , "go now");

        //12.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getX() - mapHelper.minutesOfDriving.getLocation().getX()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //13.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //14.Tap 'No thanks'
        confirmHelper = new ConfirmHelper(driver);
        if(confirmHelper.isElementDisplayWithOutSelect(confirmHelper.noThanksButton)) {
            confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");
        }


        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




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
    private MapActivity mapActivity;
    private NavigateActivity navigateActivity;
    private SearchResultsActivity searchResultsActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
    private FavoritesActivity favoritesActivity;
    private AddFavoriteActivity addFavoriteActivity;
    private NameFavoritePopupActivity nameFavoritePopupActivity;
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

        //3.precondition : if the user have empty favorite
        navigateActivity = new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.searchLayout.get(2) ,"favorite");

        //4.Select the first favorite which is not home or work
        addFavoriteActivity = new AddFavoriteActivity(driver);
        String nameToPress =   addFavoriteActivity.findNameThatIsntWorkOrHome();
        if(nameToPress == null) {
            //5.Tap on the favorite
            favoritesActivity = new FavoritesActivity(driver);
            favoritesActivity.clickElement(favoritesActivity.addFavoriteAddress , "add favorite");

            //6.enter the string bat yam
            addFavoriteActivity = new AddFavoriteActivity(driver);
            addFavoriteActivity.sendKeysToWebElementInput(addFavoriteActivity.searchBoxFavorite,"bat yam");
            addFavoriteActivity.sendKeyboardKeys(navigateActivity.SEARCHBUTTON , "Search");

            //7.Search the first results
            searchResultsActivity = new SearchResultsActivity(driver);
            searchResultsActivity.selectSearchResult(0);

            //8.Tap on finish
            nameFavoritePopupActivity = new NameFavoritePopupActivity(driver);
            nameFavoritePopupActivity.sendKeysToWebElementInput(nameFavoritePopupActivity.nameOfFavorite,"bat yam fav");
            nameFavoritePopupActivity.clickElement(nameFavoritePopupActivity.doneButton ,"done");

            //9.Verify that we return to the map
            mapActivity.verifyElementIsDisplayed(mapActivity.map , "Map");

            //10.click on the main menu icon(the magnifying glass icon)
            mapActivity.clickElement(mapActivity.searchButton , "Search button");

            //11.precondition : if the user have empty favorite
            navigateActivity.clickElement(navigateActivity.searchLayout.get(2) ,"favorite");

            nameToPress =   addFavoriteActivity.findNameThatIsntWorkOrHome();
            addFavoriteActivity.pressOnTheLayoutWithThatString(addFavoriteActivity.favoriteLayouts,nameToPress);
        } else {
            addFavoriteActivity.pressOnTheLayoutWithThatString(addFavoriteActivity.favoriteLayouts,nameToPress);
        }

        //11.Tap ‘Go now’
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

        //12.Open the ETA popup by tapping the blue eta arrow
        mapActivity = new MapActivity(driver);
        mapActivity.tapOnTheScreenByCoordinates(mapActivity.kmOfDriving.getLocation().getX() - mapActivity.minutesOfDriving.getLocation().getX()  , mapActivity.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //13.Tap 'stop'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.stopButton , "stop button");

        //14.Tap 'No thanks'
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        if(confirmPopupActivity.isElementDisplayWithOutSelect(confirmPopupActivity.noThanksButton)) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }
}




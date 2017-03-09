/**
 * Created by mkalash on 3/6/17.
 */
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
import java.net.MalformedURLException;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class NavigateSuite {
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    AppiumDriver driver;
    MapHelper mapHelper;
    SearchHelper searchHelper;
    DirectionsHelper directionsHelper;
    HomePopupHelper homePopupHelper;
    ETAPopupHelper etaPopupHelper;
    ConfirmHelper confirmHelper;
    String proccessName , phoneName;
    DriverManager driverManager = new DriverManager();
    int pid;

    @BeforeClass
    public void setup() throws InterruptedException, MalformedURLException {

        Utils.openProcess("appium", "LaunchAppiumServer" , true);
        phoneName  = driverManager.getPhoneModel();
        proccessName =  phoneName + "Node";
        Utils.startAppiumNode(proccessName);
        driver = driverManager.getDriver( System.getProperty("Phone"), "4444");

        //1.if we get the popup of drive now or later
        confirmHelper = new ConfirmHelper(driver);
        confirmHelper.checkIfPopUpAppeared();

        LoginHelper loginHelper = new LoginHelper(driver);
        loginHelper.loginAsWazer(driverManager);
    }

    @AfterClass
    public void closeAppium() throws InterruptedException {

        Utils.killAllCmd();
        Utils.killingTheGrid();
        driver.quit();
    }

    @Test
    public void NavigateToGasStation() throws InterruptedException, IOException, AWTException {

        System.out.println("Starting the test Navigate to Gas station.");
        ATUReports.add("Starting the test Navigate to Gas station.", LogAs.PASSED , null);

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
        searchHelper =  new SearchHelper(driver);
        searchHelper.clickElement(searchHelper.searchBox,"Search box");

        //5.Tap 'Gas station' cell
        searchHelper.verifyThatTheTextOfTheElementIsAsExpected(searchHelper.autoCompleteSearchResults.get(0),"Gas stations","תחנות דלק");
        searchHelper.clickElement(searchHelper.autoCompleteSearchResults.get(0) , "Gas Station");

        //6.verify the screen title is 'Gas Stations'
        searchHelper.verifyThatTheTextOfTheElementIsAsExpected(searchHelper.titleBarText,"Gas stations","תחנות דלק" );

        //7.select the second Gas stations results
        searchHelper.clickElement(searchHelper.autoCompleteSearchResults.get(1) , "Second Gas stations");

        //8.Tap 'GO'
        searchHelper.clickElement(searchHelper.previewGoButton , "preview go");

        //9.Tap 'GO now'
        searchHelper.clickElement(searchHelper.goButton , "go now");

        //10.Open the ETA popup by tapping the blue eta arrow
        mapHelper = new MapHelper(driver);
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getX() - mapHelper.minutesOfDriving.getLocation().getX()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //11.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //12.Tap 'No thanks' if display
        confirmHelper = new ConfirmHelper(driver);
        if(confirmHelper.isElementDisplayWithOutSelect(confirmHelper.noThanksButton)) {
            confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");
        }
        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }


    @Test
    public void NavigateFromSearch() throws InterruptedException, IOException, AWTException {

        System.out.println("Starting the test Navigate from search.");
        ATUReports.add("Starting the test Navigate from search.", LogAs.PASSED , null);

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        searchHelper =  new SearchHelper(driver);
        searchHelper.verifySearchViewOpen();

        //5.write hike in the edit box
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"nike");

        //6.Tap the close icon in the search box
        searchHelper.clickElement(searchHelper.exitSearch , "Exit button");

        //7.Enter the string 'hike' and tap enter
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"nike" );
        searchHelper.sendKeyboardKeys(66 , "Search");

        //8.Search results should appear after a few seconds
        searchHelper.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        searchHelper.clickOnTheBottomObject(2 , "Google");

        //10.Tap 'Places'
        searchHelper.clickOnTheBottomObject(1 , "Places");

        //11.Tap 'Search Results'
        searchHelper.clickOnTheBottomObject(0 , "Search Results");

        //12.Search the first results
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
        mapHelper.tapOnTheScreenByCoordinates(mapHelper.kmOfDriving.getLocation().getX() - mapHelper.minutesOfDriving.getLocation().getX()  , mapHelper.kmOfDriving.getLocation().getY(), "blue eta arrow");

        //22.Tap 'stop'
        etaPopupHelper = new ETAPopupHelper(driver);
        etaPopupHelper.clickElement(etaPopupHelper.stopButton , "stop button");

        //23.Tap 'No thanks'
        confirmHelper = new ConfirmHelper(driver);
        if(confirmHelper.noThanksButton.isDisplayed()) {
            confirmHelper.clickElement(confirmHelper.noThanksButton ,"No thanks");
        }


        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }


    @Test
    public void NavigateHome() throws InterruptedException, IOException, AWTException {

        System.out.println("Starting the test Navigate home.");
        ATUReports.add("Starting the test Navigate home.", LogAs.PASSED , null);

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //3.precondition : the user should have an empty home favorite ,
        searchHelper = new SearchHelper(driver);
        if(searchHelper.homeFavoriteDot.get(0).isDisplayed()) {
            //3.1 tap the more options icon (the three grey dots)
            searchHelper.clickElement(searchHelper.homeFavoriteDot.get(0), "home three dots");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            homePopupHelper = new HomePopupHelper(driver);
            homePopupHelper.swipeDown();
            homePopupHelper.clickElement(homePopupHelper.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        searchHelper =  new SearchHelper(driver);
        searchHelper.clickElement(searchHelper.favorite.get(0), "home favorite");

        //5.enter the string 'rehovot' abd tap enter
        searchHelper.sendKeysToWebElementInput(searchHelper.searchBox,"rehovot");
        searchHelper.sendKeyboardKeys(66 , "Search");

        //6.Search the first results
        searchHelper.selectTheFirstResult();

        //7. tap 'set home & go'
        homePopupHelper = new HomePopupHelper(driver);
        homePopupHelper.clickElement(homePopupHelper.addAdressButton, "add address button");

        //8.Tap 'GO now'
        searchHelper.clickElement(searchHelper.goButton , "go now");

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




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
    private AppiumDriver driver;
    private MapHelper mapHelper;
    private NavigationHelper navigationHelper;
    private DirectionsHelper directionsHelper;
    private WorkPopupHelper workPopupHelper;
    private HomePopupHelper homePopupHelper;
    private ETAPopupHelper etaPopupHelper;
    private ConfirmHelper confirmHelper;
    private NavigationResultsHelper navigationResultsHelper;
    private AddFavoriteHelper addFavoriteHelper;
    private NameFavoritePopupHelper nameFavoritePopupHelper;
    private FavoriteHelper favoriteHelper;
    private String proccessName , phoneName;
    private DriverManager driverManager = new DriverManager();

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
        navigationHelper = new NavigationHelper(driver);
        if(navigationHelper.homeFavoriteDot.get(0).isDisplayed()) {
            //3.1 tap the more options icon (the three grey dots)
            navigationHelper.clickElement(navigationHelper.homeFavoriteDot.get(0), "home three dots");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            homePopupHelper = new HomePopupHelper(driver);
            homePopupHelper.swipeDown();
            homePopupHelper.clickElement(homePopupHelper.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigationHelper =  new NavigationHelper(driver);
        navigationHelper.clickElement(navigationHelper.favoriteList.get(0), "home favorite");

        //5.enter the string 'rehovot' abd tap enter
        navigationHelper.sendKeysToWebElementInput(navigationHelper.searchBox,"rehovot");
        navigationHelper.sendKeyboardKeys(navigationHelper.SEARCHBUTTON , "Search");

        //6.Search the first results
        navigationResultsHelper.selectSearchResult(0);

        //7. tap 'set home & go'
        homePopupHelper = new HomePopupHelper(driver);
        homePopupHelper.clickElement(homePopupHelper.addAdressButton, "add address button");

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


    @Test
    public void NavigateToWork() throws InterruptedException, IOException, AWTException {


        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //3.precondition : the user should have an empty home favorite ,
        navigationHelper = new NavigationHelper(driver);
        if(navigationHelper.isTheWorkNavigationIsDefine()) {
            //3.1 tap the more options icon (the three grey dots)
            navigationHelper.clickElement(navigationHelper.homeFavoriteDot.get(1), "home three dots");

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
        navigationResultsHelper.selectSearchResult(0);

        //7. tap 'set home & go'
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

    @Test
    public void NavigateToFavorite() throws InterruptedException, IOException, AWTException {


        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapHelper = new MapHelper(driver);
        mapHelper.clickElement(mapHelper.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //3.precondition : if the user have empty favorite
        navigationHelper = new NavigationHelper(driver);
        navigationHelper.clickElement(navigationHelper.searchLayout.get(2) ,"favorite");

        //4.Tap on the favorite
        favoriteHelper = new FavoriteHelper(driver);
        favoriteHelper.clickElement(favoriteHelper.addFavoriteAddress , "add favorite");

        //5.enter the string bat yam
        addFavoriteHelper = new AddFavoriteHelper(driver);
        addFavoriteHelper.sendKeysToWebElementInput(addFavoriteHelper.searchBoxFavorite,"bat yam");
        addFavoriteHelper.sendKeyboardKeys(navigationHelper.SEARCHBUTTON , "Search");

        //6.Search the first results
        navigationResultsHelper = new NavigationResultsHelper(driver);
        navigationResultsHelper.selectSearchResult(0);

        //6.Tap on finish
        nameFavoritePopupHelper = new NameFavoritePopupHelper(driver);
        nameFavoritePopupHelper.sendKeysToWebElementInput(nameFavoritePopupHelper.nameOfFavorite,"bat yam fav");
        nameFavoritePopupHelper.clickElement(nameFavoritePopupHelper.doneButton ,"done");

        //7.Verify that we return to the map
        mapHelper.verifyElementIsDisplayed(mapHelper.map , "Map");

        //8.click on the main menu icon(the magnifying glass icon)
        mapHelper.clickElement(mapHelper.searchButton , "Search button");

        //9.precondition : if the user have empty favorite
        navigationHelper.clickElement(navigationHelper.searchLayout.get(2) ,"favorite");

        //10.Select the first favorite which is not home or work
        String nameToPress =   addFavoriteHelper.findNameThatIsntWorkOrHome();
        addFavoriteHelper.pressOnTheLayoutWithThatString(addFavoriteHelper.favoriteLayouts,nameToPress);

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




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
    private MapActivity mapActivity;
    private NavigationActivity navigationActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private DirectionsActivity directionsActivity;
    private AddressItemOptionPopupActivity addressItemOptionPopupActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
    private GoNowPopupActivity goNowPopupActivity;
    private NavigationSearchResultsActivity  navigationSearchResultsActivity;
    private AddFavoriteActivity addFavoriteActivity;
    private NameFavoritePopupActivity nameFavoritePopupActivity;
    private FavoriteActivity favoriteActivity;
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
        confirmPopupActivity = new ConfirmPopupActivity(driver);
        confirmPopupActivity.checkIfPopUpAppeared();

        LoginActivity loginHelper = new LoginActivity(driver);
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
//        confirmPopupActivity = new ConfirmPopupActivity(driver);
//        if(confirmPopupActivity.isElementDisplay(confirmPopupActivity.driverNowButton)) {
//            confirmPopupActivity.clickElement(confirmPopupActivity.driverNowButton, "drive now button");
//            confirmPopupActivity.clickBackOnTheDevice();
//            confirmPopupActivity.clickBackOnTheDevice();
//        }

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //4.Tap the search box
        navigationActivity =  new NavigationActivity(driver);
        navigationActivity.clickElement(navigationActivity.searchBox,"Search box");

        //5.Tap 'Gas station' cell
        navigationActivity.verifyThatTheTextOfTheElementIsAsExpected(navigationActivity.autoCompleteSearchResults.get(0),"Gas stations","תחנות דלק");
        navigationActivity.clickElement(navigationActivity.autoCompleteSearchResults.get(0) , "Gas Station");

        //6.verify the screen title is 'Gas Stations'
        navigationSearchResultsActivity = new NavigationSearchResultsActivity(driver);
        navigationSearchResultsActivity.verifyThatTheTextOfTheElementIsAsExpected(navigationSearchResultsActivity.titleBarText,"Gas stations","תחנות דלק" );

        //7.select the second Gas stations results
        navigationSearchResultsActivity.clickElement(navigationSearchResultsActivity.autoCompleteSearchResults.get(1) , "Second Gas stations");

        //8.Tap 'GO'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.previewGoButton , "preview go");

        //9.Tap 'GO now'
        goNowPopupActivity = new GoNowPopupActivity(driver);
        goNowPopupActivity.clickElement(goNowPopupActivity.goButton , "go now");

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


    @Test
    public void NavigateFromSearch() throws InterruptedException, IOException, AWTException {

        System.out.println("Starting the test Navigate from search.");
        ATUReports.add("Starting the test Navigate from search.", LogAs.PASSED , null);

        //1.pre test if we get the popup of drive now or later
//        confirmPopupActivity = new ConfirmPopupActivity(driver);
//       if(confirmPopupActivity.driverNowOrLaterPopUp.isDisplayed()) {
//           confirmPopupActivity.clickElement(confirmPopupActivity.driverNowButton, "drive now button");
//           confirmPopupActivity.clickBackOnTheDevice();
//           confirmPopupActivity.clickBackOnTheDevice();
//        }

        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //2.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //3.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //4.verify that the search page opened - (Tap the main menu icon(the magnifying glass icon)
        navigationActivity =  new NavigationActivity(driver);
        navigationActivity.verifySearchViewOpen();

        //5.write hike in the edit box
        navigationActivity.sendKeysToWebElementInput(navigationActivity.searchBox,"nike");

        //6.Tap the close icon in the search box
        navigationActivity.clickElement(navigationActivity.exitSearch , "Exit button");

        //7.Enter the string 'hike' and tap enter
        navigationActivity.sendKeysToWebElementInput(navigationActivity.searchBox,"nike" );
        navigationActivity.sendKeyboardKeys(navigationActivity.SEARCHBUTTON , "Search");

        //8.Search results should appear after a few seconds
        navigationSearchResultsActivity = new NavigationSearchResultsActivity(driver);
        navigationSearchResultsActivity.verifyThatWeCanSeeTheResults();

        //9.Tap 'Google'
        navigationSearchResultsActivity.clickOnTheBottomObject(2 , "Google");

        //10.Tap 'Places'
        navigationSearchResultsActivity.clickOnTheBottomObject(1 , "Places");

        //11.Tap 'Search Results'
        navigationSearchResultsActivity.clickOnTheBottomObject(0 , "Search Results");

        //12.Search the first results
        navigationSearchResultsActivity.selectSearchResult(0);

        //13.Tap the 'more options' icon (the grey rectangle with the three white dots)
        navigationActivity.clickElement(navigationActivity.moreOptionButton , "more options");

        //14.Tap back(the Android action button)
        navigationActivity.clickBackOnTheDevice();

        //15.Tap 'GO'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.previewGoButton , "preview go");

        //16.Tap 'GO now'
        goNowPopupActivity = new GoNowPopupActivity(driver);
        goNowPopupActivity.clickElement(goNowPopupActivity.goButton , "go now");

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


    @Test
    public void NavigateHome() throws InterruptedException, IOException, AWTException {

        System.out.println("Starting the test Navigate home.");
        ATUReports.add("Starting the test Navigate home.", LogAs.PASSED , null);

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


    @Test
    public void NavigateToWork() throws InterruptedException, IOException, AWTException {


        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //3.precondition : the user should have an empty work favorite
        navigationActivity = new NavigationActivity(driver);
        if(navigationActivity.isTheWorkNavigationIsDefine()) {
            //3.1 tap the more options icon (the three grey dots)
            navigationActivity.clickElement(navigationActivity.favoriteList.get(1), "more option");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            addressItemOptionPopupActivity = new AddressItemOptionPopupActivity(driver);
            addressItemOptionPopupActivity.swipeDown();
            addressItemOptionPopupActivity.clickElement(addressItemOptionPopupActivity.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigationActivity =  new NavigationActivity(driver);
        navigationActivity.clickElement(navigationActivity.favoriteList.get(1), "work favorite");

        //5.enter the string 'tel aviv' abd tap enter
        navigationActivity.sendKeysToWebElementInput(navigationActivity.searchBox,"tel aviv");
        navigationActivity.sendKeyboardKeys(navigationActivity.SEARCHBUTTON , "Search");

        //6.Search the first results
        navigationSearchResultsActivity = new NavigationSearchResultsActivity(driver);
        navigationSearchResultsActivity.selectSearchResult(0);

        //7. tap 'set work & go'
        addressPreviewActivity= new AddressPreviewActivity(driver);
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
        if(confirmPopupActivity.isElementDisplayWithOutSelect(confirmPopupActivity.noThanksButton)) {
            confirmPopupActivity.clickElement(confirmPopupActivity.noThanksButton ,"No thanks");
        }

        System.out.println("Done.");
        ATUReports.add("Message window.","Done." , "Done." , LogAs.PASSED , null);
    }

    @Test
    public void NavigateToFavorite() throws InterruptedException, IOException, AWTException {


        //pre test after the app startup all the tooltips and encouragments should be eliminated
        //1.click anywhere on the screen
        mapActivity = new MapActivity(driver);
        mapActivity.clickElement(mapActivity.map , "Map");

        //2.click on the main menu icon(the magnifying glass icon)
        mapActivity.clickElement(mapActivity.searchButton , "Search button");

        //3.precondition : if the user have empty favorite
        navigationActivity = new NavigationActivity(driver);
        navigationActivity.clickElement(navigationActivity.searchLayout.get(2) ,"favorite");

        //4.Select the first favorite which is not home or work
        addFavoriteActivity = new AddFavoriteActivity(driver);
        String nameToPress =   addFavoriteActivity.findNameThatIsntWorkOrHome();
        if(nameToPress == null) {
            //5.Tap on the favorite
            favoriteActivity = new FavoriteActivity(driver);
            favoriteActivity.clickElement(favoriteActivity.addFavoriteAddress , "add favorite");

            //6.enter the string bat yam
            addFavoriteActivity = new AddFavoriteActivity(driver);
            addFavoriteActivity.sendKeysToWebElementInput(addFavoriteActivity.searchBoxFavorite,"bat yam");
            addFavoriteActivity.sendKeyboardKeys(navigationActivity.SEARCHBUTTON , "Search");

            //7.Search the first results
            navigationSearchResultsActivity = new NavigationSearchResultsActivity(driver);
            navigationSearchResultsActivity.selectSearchResult(0);

            //8.Tap on finish
            nameFavoritePopupActivity = new NameFavoritePopupActivity(driver);
            nameFavoritePopupActivity.sendKeysToWebElementInput(nameFavoritePopupActivity.nameOfFavorite,"bat yam fav");
            nameFavoritePopupActivity.clickElement(nameFavoritePopupActivity.doneButton ,"done");

            //9.Verify that we return to the map
            mapActivity.verifyElementIsDisplayed(mapActivity.map , "Map");

            //10.click on the main menu icon(the magnifying glass icon)
            mapActivity.clickElement(mapActivity.searchButton , "Search button");

            //11.precondition : if the user have empty favorite
            navigationActivity.clickElement(navigationActivity.searchLayout.get(2) ,"favorite");

            nameToPress =   addFavoriteActivity.findNameThatIsntWorkOrHome();
            addFavoriteActivity.pressOnTheLayoutWithThatString(addFavoriteActivity.favoriteLayouts,nameToPress);
        } else {
            addFavoriteActivity.pressOnTheLayoutWithThatString(addFavoriteActivity.favoriteLayouts,nameToPress);
        }

        //11.Tap ‘Go now’
        goNowPopupActivity = new GoNowPopupActivity(driver);
        goNowPopupActivity.clickElement(goNowPopupActivity.goButton , "go now");

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




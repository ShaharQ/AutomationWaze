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
    private NavigateActivity navigateActivity;
    private AddressPreviewActivity addressPreviewActivity;
    private DirectionsActivity directionsActivity;
    private AddressItemOptionPopupActivity addressItemOptionPopupActivity;
    private EtaPopupActivity etaPopupActivity;
    private ConfirmPopupActivity confirmPopupActivity;
    private SearchResultsActivity searchResultsActivity;
    private AddFavoriteActivity addFavoriteActivity;
    private NameFavoritePopupActivity nameFavoritePopupActivity;
    private FavoritesActivity favoritesActivity;
    private RoutesActivity routesActivity;
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
        etaPopupActivity = new EtaPopupActivity(driver);
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
        navigateActivity = new NavigateActivity(driver);
        if(navigateActivity.isElementDisplay(navigateActivity.homeFavoriteDot.get(0))) {
            //3.1 tap the more options icon (the three grey dots)
            navigateActivity.clickElement(navigateActivity.favoriteList.get(0), "more options");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            addressItemOptionPopupActivity = new AddressItemOptionPopupActivity(driver);
            addressItemOptionPopupActivity.swipeDown();
            addressItemOptionPopupActivity.clickElement(addressItemOptionPopupActivity.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.favoriteList.get(0), "home favorite");

        //5.enter the string 'rehovot' abd tap enter
        navigateActivity.sendKeysToWebElementInput(navigateActivity.searchBox,"rehovot");
        navigateActivity.sendKeyboardKeys(navigateActivity.SEARCHBUTTON , "Search");

        //6.Search the first results
        searchResultsActivity = new SearchResultsActivity(driver);
        searchResultsActivity.selectSearchResult(0);

        //7. tap 'set work & go'
        addressPreviewActivity = new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.addAdressButton, "add address button");

        //8.Tap 'GO now'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

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
        navigateActivity = new NavigateActivity(driver);
        if(navigateActivity.isTheWorkNavigationIsDefine()) {
            //3.1 tap the more options icon (the three grey dots)
            navigateActivity.clickElement(navigateActivity.favoriteList.get(1), "more option");

            //3.2 tap the remove cell - this cell isn't fully visible when tapping
            //the more options icon. Notice that swiping is required to make it fully visible
            addressItemOptionPopupActivity = new AddressItemOptionPopupActivity(driver);
            addressItemOptionPopupActivity.swipeDown();
            addressItemOptionPopupActivity.clickElement(addressItemOptionPopupActivity.removeButton.get(6), "remove button");
        }
        //end precondition - home address is now removed
        //4.tap the home favorite cell
        navigateActivity =  new NavigateActivity(driver);
        navigateActivity.clickElement(navigateActivity.favoriteList.get(1), "work favorite");

        //5.enter the string 'tel aviv' abd tap enter
        navigateActivity.sendKeysToWebElementInput(navigateActivity.searchBox,"tel aviv");
        navigateActivity.sendKeyboardKeys(navigateActivity.SEARCHBUTTON , "Search");

        //6.Search the first results
        searchResultsActivity = new SearchResultsActivity(driver);
        searchResultsActivity.selectSearchResult(0);

        //7. tap 'set work & go'
        addressPreviewActivity= new AddressPreviewActivity(driver);
        addressPreviewActivity.clickElement(addressPreviewActivity.addAdressButton, "add address button");

        //8.Tap 'GO now'
        etaPopupActivity = new EtaPopupActivity(driver);
        etaPopupActivity.clickElement(etaPopupActivity.goButton , "go now");

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

    @Test
    public void NavigateUsingAutoComplete() throws InterruptedException, IOException, AWTException {


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




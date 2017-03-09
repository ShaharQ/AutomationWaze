package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import java.util.List;

/**
 * Created by mkalash on 2/12/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SearchHelper extends DefaultHelper {

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }
    @FindBy(id ="com.waze:id/menuSettings")
    public WebElement settingsButton;
    @FindBy(id ="com.waze:id/menuSwitchOff")
    public WebElement switchOffButton;
    @FindBy(id ="com.waze:id/myWazeProfileImage")
    public WebElement profileImage;
    @FindBy(id ="com.waze:id/searchBox")
    public WebElement searchBox;
    @FindBy(id ="com.waze:id/btnClearSearch")
    public WebElement exitSearch;
    @FindBy(id ="com.waze:id/mainContainer")
    public List<WebElement> searchResults;
    @FindBy(className ="android.widget.TextView")
    public List<WebElement> footerButtons;
    @FindBy(id ="com.waze:id/addressPreviewMore")
    public WebElement threeDots;
    @FindBy(id ="com.waze:id/addressPreviewGoButton")
    public WebElement previewGoButton;
    @FindBy(id ="com.waze:id/fragNavResGo")
    public WebElement goButton;
    @FindBy(id = "com.waze:id/tabStrip")
    public WebElement bottomTab;
    @FindBy(id = "com.waze:id/lblTitle")
    public List<WebElement> autoCompleteSearchResults;
    @FindBy(id = "com.waze:id/titleBarTitleText")
    public WebElement titleBarText;
    @FindBy(id = "com.waze:id/actionButton")
    public List<WebElement> homeFavoriteDot;
    @FindBy(id = "com.waze:id/addressItemTouch")
    public List<WebElement> favorite;
    @FindBy(id = "com.waze:id/myWazeProfileImage")
    public WebElement profiePicture;

    public SearchHelper(AppiumDriver driver) throws InterruptedException {
        super(driver);
        Thread.sleep(500);
        PageFactory.initElements(driver,this);
    }

    public void verifySearchViewOpen() {

        try {
            if(settingsButton.isDisplayed() && profileImage.isDisplayed() && switchOffButton.isDisplayed()) {
                System.out.println("Verify that search box appeared.");
                ATUReports.add("Verify that search box appeared.", "Success.", "Success.", LogAs.PASSED, null);
            } else {
                System.out.println("Not Verify that search box appeared.");
                ATUReports.add("Not Verify that search box appeared.", "Success.", "Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            }
        } catch(Exception e) {
            e.printStackTrace();
            ATUReports.add("The page can't load" + e.getMessage(), LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            Assert.assertTrue(false);
        }
    }

    public void verifyThatWeCanSeeTheResults() {

        try {

            if(searchResults.size() > 0  && searchResults.get(0).isDisplayed()) {
                System.out.println("Verify that we can see the results of the search menu.");
                ATUReports.add("Verify that we can see the results of the search menu.", "Success.","Success.", LogAs.PASSED, null);
            } else {
                System.out.println("Not Verify that we can see the results of the search menu.");
                ATUReports.add("Not Verify that we can see the results of the search menu.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            }

        } catch(Exception e) {
            System.out.println("Not Verify that we can see the results of the search menu.");
            ATUReports.add("Not Verify that we can see the results of the search menu.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }
    }

    public void selectTheFirstResult() {

        if(searchResults.get(0).isDisplayed() ) {
            clickElement(searchResults.get(0) , "First result");
            System.out.println("Clicked on the first search result.");
            ATUReports.add("Clicked on the first search result.", "Success.","Success.", LogAs.PASSED, null);
        } else {
            System.out.println("Can't Click on the first search result.");
            ATUReports.add("Can't Click on the first search result.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }

    }

    public void clickOnTheBottomObject(int number , String description) {

        try {
            wait.until(ExpectedConditions.visibilityOf(bottomTab));
            List<WebElement> bottomElements = bottomTab.findElements(By.className("android.widget.TextView"));
            bottomElements.get(number).click();
            System.out.println("Clicked on " + description + " element");
            ATUReports.add("Clicked on " + description + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                    null);
        } catch (Exception msg) {
            System.out.println("Can't Click on the button object.");
            ATUReports.add("Can't Click on the button object.", "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }
    }


}

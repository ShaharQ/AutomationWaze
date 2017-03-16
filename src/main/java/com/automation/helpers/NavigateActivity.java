package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import java.util.List;

/**
 * Created by mkalash on 2/12/17.
 */

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class NavigateActivity extends DefaultActivity {

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
    @FindBy(id ="com.waze:id/addressPreviewMore")
    public WebElement moreOptionButton;
    @FindBy(id = "com.waze:id/lblTitle")
    public List<WebElement> autoCompleteSearchResults;
    @FindBy(id = "com.waze:id/actionButton")
    public List<WebElement> homeFavoriteDot;
    @FindBy(id = "com.waze:id/addressItemTouch")
    public List<WebElement> favoriteList;
    @FindBy(id = "com.waze:id/myWazeProfileImage")
    public WebElement profiePicture;
    @FindBy(id = "com.waze:id/addressItem")
    public List<WebElement> searchLayout;


    public NavigateActivity(AppiumDriver driver) throws InterruptedException {
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

    public boolean isTheWorkNavigationIsDefine(){

        boolean isNavigationDefine = false;

        if(favoriteList.get(1).getText().equals("הגדר פעם אחת וסע") || favoriteList.get(1).getText().equals("Tap to add")) {
            System.out.println("The favorite navigation is not define.");
            ATUReports.add("The favorite navigation is not define.", LogAs.PASSED,  new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        } else {
            isNavigationDefine = true;
            System.out.println("The favorite navigation is define.");
            ATUReports.add("The favorite navigation is define.", LogAs.PASSED,  new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
        }

        return isNavigationDefine;
    }


}

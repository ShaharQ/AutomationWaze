package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by mkalash on 3/14/17.
 */
public class NavigationSearchResultsActivity extends DefaultActivity {

    @FindBy(id ="com.waze:id/mainContainer")
    public List<WebElement> searchResults;
    @FindBy(id = "com.waze:id/tabStrip")
    public WebElement bottomTab;
    @FindBy(id = "com.waze:id/titleBarTitleText")
    public WebElement titleBarText;
    @FindBy(id = "com.waze:id/lblTitle")
    public List<WebElement> autoCompleteSearchResults;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public NavigationSearchResultsActivity(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);

    }

    public void selectSearchResult(int selectSearch) {

        if(searchResults.get(selectSearch).isDisplayed() ) {
            clickElement(searchResults.get(selectSearch) , "First result");
            System.out.println("Clicked on the search result in the position: " + selectSearch);
            ATUReports.add("Clicked on the search result in the position: " + selectSearch, "Success.","Success.", LogAs.PASSED, null);
        } else {
            System.out.println("Can't Clicked on the search result in the position: " + selectSearch);
            ATUReports.add("Can't Click on the search result in the position: " + selectSearch, "Success.","Failed.", LogAs.FAILED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
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
}


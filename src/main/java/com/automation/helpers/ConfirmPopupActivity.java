package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by mkalash on 2/14/17.
 */
public class ConfirmPopupActivity extends DefaultActivity {


        @FindBy(className = "android.widget.LinearLayout")
        public WebElement driverNowOrLaterPopUp;
        @FindBy(id = "confirmCloseText")
        public WebElement driverLaterButton;
        @FindBy(id = "confirmSendText")
        public WebElement driverNowButton;
        @FindBy(id = "confirmClose")
        public WebElement noThanksButton;

        //Set Peopert for ATU Reporter Configuration
        {
            System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

        }

        public ConfirmPopupActivity(AppiumDriver driver) {

                super(driver);
                PageFactory.initElements(driver,this);
        }

        public void checkIfPopUpAppeared() {

            try {
                new WebDriverWait(driver , 5).until(ExpectedConditions.visibilityOf(driverNowButton));
                driverNowButton.click();
               System.out.println("Clicked on drive now element");
                    ATUReports.add("Clicked on drive now element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
                            new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
                    clickBackOnTheDevice();
                    clickBackOnTheDevice();

                } catch (Exception e){
                        System.out.println("Popup not appread.");
                }

        }
}



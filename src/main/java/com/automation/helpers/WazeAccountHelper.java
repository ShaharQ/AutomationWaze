package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by mkalash on 3/6/17.
 */
public class WazeAccountHelper extends DefaultHelper {

    @FindBy(id = "com.waze:id/myProfileLogOutButton")
    public WebElement existAccount;
    @FindBy(id = "com.waze:id/phoneEditText")
    public WebElement phoneEditText;
    @FindBy(id = "com.waze:id/btnUsernameLogin")
    public WebElement connectButton;
    @FindBy(id = "com.waze:id/validationEditText")
    public WebElement enterCodeText;
    @FindBy(id = "com.waze:id/lblEnterValidationCode")
    public WebElement enterCodeTitle;
    @FindBy(id = "com.waze:id/lblLoginWithUsernameLink")
    public WebElement connectWithUserName;
    @FindBy(id = "com.waze:id/usernameEditText")
    public WebElement userName;
    @FindBy(id = "com.waze:id/passwordEditText")
    public WebElement password;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public WazeAccountHelper(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

}

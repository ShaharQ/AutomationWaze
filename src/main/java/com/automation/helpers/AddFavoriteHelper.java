package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by mkalash on 3/13/17.
 */

public class AddFavoriteHelper extends DefaultHelper {

    @FindBy(id = "com.waze:id/searchBox")
    public WebElement searchBoxFavorite;
    @FindBy(id = "com.waze:id/addressItem")
    public List<WebElement> favoriteLayouts;
    @FindBy(id = "com.waze:id/addressItemName")
    public List<WebElement> favoriteNameList;

    //Set Peopert for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/main/resources/atu.properties");

    }

    public AddFavoriteHelper(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public String findNameThatIsntWorkOrHome() {

        String favoriteToReturn = null;
        for (WebElement we : favoriteNameList) {

            String currentName = we.getText();
            if (!currentName.equals("Work") && !currentName.equals("Home")
                    && !currentName.equals("עבודה") && !currentName.equals("בית") && !currentName.equals("Add new favorite")
                    && !currentName.equals("הוסף מיקום מועדף")) {
                favoriteToReturn = currentName;
                break;
            }

        }
        return favoriteToReturn;
    }
}

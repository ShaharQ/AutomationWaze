package com.automation.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkalash on 2/7/17.
 */
public class DriverManager {


    private static DesiredCapabilities capabilities;

    public static AppiumDriver getDriver(String deviceName, String port) throws MalformedURLException {

        AppiumDriver driver = null;
        
        JSONArray array = getJsonFromResources();
        JSONObject json = getTheDevicesProperties();
        
        for(int number_of_devices)        

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("deviceName", "test");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.waze");
        capabilities.setCapability("browserName", "");

        File apk = new File("src/main/resources/waze_4_16_0_1.apk");
        capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());

        if (deviceName.equals("LGG2")) {
            capabilities.setCapability("platformVersion", "5.0.2");
//          capabilities.setCapability("appPackage", "com.lge.filemanager");
//          capabilities.setCapability("appActivity", "com.lge.filemanager.MainActivity");
            capabilities.setCapability("appPackage", "com.waze");
            DriverManager.addCapbilities("appWaitPackage", "com.waze");
            DriverManager.addCapbilities("appActivity", "com.waze.FreeMapAppActivity");
            capabilities.setCapability("udid", "LGD802988c53e6");
            capabilities.setCapability("bp", "6204");

        } else if (deviceName.equals("SamsungS5")) {
            capabilities.setCapability("platformVersion", "6.0.1");
            //capabilities.setCapability("appWaitActivity", "HomeScreenActivity");
            //capabilities.setCapability("appActivity", "com.waze.MainActivity");
            capabilities.setCapability("appWaitPackage", "com.google.android.packageinstaller");
            capabilities.setCapability("appWaitActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
        }

        Object deviceType = capabilities.getCapability("platformName");

        if (deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://localhost:" + port + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
            firstAlertHandle(driver, deviceName);
        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
        }

        return driver;
    }

    private static JSONObject getTheDevicesProperties() {
    }

    public static void firstAlertHandle(AppiumDriver driver, String device) {
        if (device.equals("SamsungS5")) {
            WebElement okBtn = driver.findElementById("com.android.packageinstaller:id/permission_allow_button");
            if (okBtn.isDisplayed())
                okBtn.click();
        }
        WebElement wazeNextBtn = driver.findElementById("com.waze:id/btnNext");
        if (wazeNextBtn.isDisplayed())
            wazeNextBtn.click();


        WebElement wazecrollToBtn = driver.findElementById("com.waze:id/btnScrollToBottom");
        if (wazecrollToBtn.isDisplayed())
            wazecrollToBtn.click();


        WebElement wazeAcceptBtn = driver.findElementById("com.waze:id/AcceptButton");
        if (wazeAcceptBtn.isDisplayed())
            wazeAcceptBtn.click();

    }

    public static void addCapbilities(String capbilityName, String value) throws MalformedURLException {

        capabilities.setCapability(capbilityName, value);
    }

    public static void reloadDriver(AppiumDriver driver, String port) throws MalformedURLException {

        driver.quit();

        Object deviceType = capabilities.getCapability("platformName");

        if (deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://172.0.0.1:" + port + "/wd/hub"), capabilities);
        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
        }

    }

    public static JSONArray getJsonFromResources() {

        JSONArray json = null;
        File f = new File("src/main/resources/devices.json");
        if (f.exists()) {
            InputStream is = null;
            try {
                is = new FileInputStream("src/main/resources/devices.json");
                String jsonTxt = IOUtils.toString(is);
                json = new JSONArray(jsonTxt);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}

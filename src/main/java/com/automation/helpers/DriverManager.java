package com.automation.helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkalash on 2/7/17.
 */
public class DriverManager {


    private DesiredCapabilities capabilities;

    public AppiumDriver getDriver(String deviceName, String port) {

        try {
            AppiumDriver driver = null;

            if (deviceName != null) {
                getTheDataFromJson(deviceName);
            } else {
                getTheDateFromLocalProperties();
            }

            Object deviceType = capabilities.getCapability("platformName");
            Object device = capabilities.getCapability("deviceName");

            if (deviceType.equals("Android")) {
                driver = new AndroidDriver(new URL("http://localhost:" + port + "/wd/hub"), capabilities);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                firstAlertHandle(driver, device.toString());
            } else {
                driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }
            System.out.println("The driver start.");
            ATUReports.add("The driver start.", "Success.", "Success.", LogAs.PASSED, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            Assert.assertTrue(true);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add("The driver didn't start." + e.getMessage()  , "Success.", "Failed.", LogAs.WARNING, new CaptureScreen((CaptureScreen.ScreenshotOf.BROWSER_PAGE)));
            return null;
        }
    }

    private void getTheDataFromJson(String deviceName) {


        JSONArray array = getJsonFromResources();
        JSONObject json = getTheDevicesProperties(array,deviceName);

        try {
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("automationName", json.getString("automationName"));
            capabilities.setCapability("platformVersion", json.getString("platformVersion"));
            capabilities.setCapability("browserName", json.getString("browserName"));
            capabilities.setCapability("platformName", json.getString("platformName"));
            capabilities.setCapability("appPackage", json.getString("appPackage"));
            capabilities.setCapability("appWaitPackage", json.getString("appWaitPackage"));
            capabilities.setCapability("appWaitActivity", json.getString("appWaitActivity"));
            capabilities.setCapability("udid", json.getString("udid"));
            capabilities.setCapability("deviceName", json.getString("deviceName"));


            File apk = new File("src/main/resources/waze_4_16_0_1.apk");
            capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTheDateFromLocalProperties() {

        Properties prop = new Properties();


        try {

        prop.load(new FileInputStream("src/main/resources/local.properties"));

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", prop.getProperty("automationName"));
        capabilities.setCapability("platformName", prop.getProperty("platformName"));
        capabilities.setCapability("browserName", prop.getProperty("browserName"));
        capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
        capabilities.setCapability("appWaitPackage", prop.getProperty("appWaitPackage"));
        capabilities.setCapability("appWaitActivity", prop.getProperty("appWaitActivity"));
        capabilities.setCapability("udid", prop.getProperty("udid"));
        capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
        capabilities.setCapability("bp", prop.getProperty("bp"));

        File apk = new File("src/main/resources/waze_4_16_0_1.apk");
        capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getTheDevicesProperties(JSONArray array,String deviceName) {

        JSONObject jSONObject = null;
        try {
            for (int number_of_devices = 0; number_of_devices < array.length(); number_of_devices++) {
                jSONObject = array.getJSONObject(number_of_devices);
                if (jSONObject.getString("deviceName").equals(deviceName)) {
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void firstAlertHandle(AppiumDriver driver, String device) {
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

    public void addCapbilities(String capbilityName, String value) throws MalformedURLException {

        capabilities.setCapability(capbilityName, value);
    }

    public void reloadDriver(AppiumDriver driver, String port) throws MalformedURLException {

        driver.quit();

        Object deviceType = capabilities.getCapability("mobileOs");

        if (deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://172.0.0.1:" + port + "/wd/hub"), capabilities);
        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
        }

    }

    public JSONArray getJsonFromResources() {

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

    public String getPhoneModel() {
        String phoneName = null;
        try {
        phoneName =  System.getProperty("Phone");
        if( phoneName == null) {
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/local.properties"));
            phoneName = prop.getProperty("deviceName");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneName;
    }
}

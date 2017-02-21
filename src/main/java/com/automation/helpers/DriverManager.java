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
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by mkalash on 2/7/17.
 */
public class DriverManager {


    private DesiredCapabilities capabilities;

    public AppiumDriver getDriver(String deviceName, String port) throws MalformedURLException {

        AppiumDriver driver = null;
        
        if(deviceName != null) {
            getTheDataFromJson(deviceName);
        } else {
            getTheDateFromLocalProperties();
        }


//        if (deviceName.equals("LGG2")) {
//            capabilities.setCapability("platformVersion", "5.0.2");
////          capabilities.setCapability("appPackage", "com.lge.filemanager");
////          capabilities.setCapability("appActivity", "com.lge.filemanager.MainActivity");
//            capabilities.setCapability("appPackage", "com.waze");
//            DriverManager.addCapbilities("appWaitPackage", "com.waze");
//            DriverManager.addCapbilities("appWaitPackage", "com.waze.FreeMapAppActivity");
//            capabilities.setCapability("udid", "LGD802988c53e6");
//            capabilities.setCapability("bp", "6204");
//
//        } else if (deviceName.equals("SamsungS5")) {
//            capabilities.setCapability("platformVersion", "6.0.1");
//            //capabilities.setCapability("appWaitActivity", "HomeScreenActivity");
//            //capabilities.setCapability("appActivity", "com.waze.MainActivity");
//            capabilities.setCapability("appWaitPackage", "com.google.android.packageinstaller");
//            capabilities.setCapability("appWaitActivity", "com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
//        }

        Object deviceType = capabilities.getCapability("mobileOs");

        if (deviceType.equals("Android")) {
            driver = new AndroidDriver(new URL("http://localhost:" + port + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
            firstAlertHandle(driver, deviceName);
        } else {
            driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        }

        return driver;
    }

    private void getTheDataFromJson(String deviceName) {


        JSONArray array = getJsonFromResources();
        JSONObject json = getTheDevicesProperties(array,deviceName);

        try {
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("automationName", json.getString("automationName"));
            capabilities.setCapability("mobileOs", json.getString("mobileOs"));
            capabilities.setCapability("appPackage", json.getString("appPackage"));
            capabilities.setCapability("browserName", json.getString("browserName"));
            capabilities.setCapability("osVersion", json.getString("osVersion"));
            capabilities.setCapability("appPackage", json.getString("appPackage"));
            capabilities.setCapability("appWaitPackage", json.getString("appWaitPackage"));
            capabilities.setCapability("appWaitActivity", json.getString("appWaitActivity"));
            capabilities.setCapability("appWaitPackage", json.getString("appWaitPackage"));
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
        InputStream input = null;

        try {
        String filenamePath = "src/main/resources//local.properties";
        input = getClass().getClassLoader().getResourceAsStream(filenamePath);
        prop.load(input);

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", prop.getProperty("automationName"));
        capabilities.setCapability("mobileOs", prop.getProperty("mobileOs"));
        capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
        capabilities.setCapability("browserName", prop.getProperty("browserName"));
        capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
        capabilities.setCapability("appWaitPackage", prop.getProperty("appWaitPackage"));
        capabilities.setCapability("appWaitActivity", prop.getProperty("appWaitActivity"));
        capabilities.setCapability("appWaitPackage", prop.getProperty("appWaitPackage"));
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

    public String getPhoneModel() throws IOException {

        String phoneName =  System.getProperty("Phone");
        if( phoneName == null) {
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/local.properties"));
            phoneName = prop.getProperty("deviceName");
        }
        return phoneName;
    }
}

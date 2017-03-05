
package com.automation.helpers;

        import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.chrome.ChromeDriver;

        import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mkalash on 2/13/17.
 */
public class Utils {

    public static String url_for_killing_the_grid = "localhost:4444/lifecycle-manager?action=shutdown";

    public static void openProcess(String name,String proccessName,boolean isHub ) throws InterruptedException, IOException {
        String successMessage = "";
        if(isHub)
            successMessage = "Started @";
        else
            successMessage = "Responding to client with success";

        try {
            if (System.getProperty("os.name").startsWith("Mac OS X")) {

                File proccessFile = new File("src/main/resources/" + proccessName + ".sh");
                ProcessBuilder pb = new ProcessBuilder(proccessFile.getAbsolutePath());
                pb.redirectErrorStream(true);
                File dir = new File("src/main/resources/");
                pb.directory(dir);
                System.out.println("About to start " + proccessFile.getAbsolutePath());
                Process p = pb.start();

            } else {

                File proccessFile = new File("src/main/resources/" + proccessName + ".bat");
                final ProcessBuilder pb = new ProcessBuilder(proccessFile.getAbsolutePath());
                pb.redirectErrorStream(true);
                File dir = new File("src/main/resources/");
                pb.directory(dir);
                Process p = pb.start();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (!line.contains(successMessage)) {
                    builder.append(line);
                    line = reader.readLine();
                }
                reader.close();

            }

            System.out.println("The process:" + name + " has started.");
            ATUReports.add("The process:" + name + " has not started.","True." ,"False.", LogAs.PASSED, null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The process:" + name + " has not started.");
            ATUReports.add("The process:" + name + " has not started.","True." ,"False.", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        }

        //Thread.sleep(15000);

    }

    public static void startAppiumNode(String nodeName) throws IOException, InterruptedException {


        if (System.getProperty("os.name").startsWith("Mac OS X")) {

            File proccessFile = new File("src/main/resources/" + nodeName + ".sh");
            ProcessBuilder pb = new ProcessBuilder(proccessFile.getAbsolutePath());
            pb.redirectErrorStream(true);
            File dir = new File("src/main/resources/");
            pb.directory(dir);
            System.out.println("About to start " + proccessFile.getAbsolutePath());
            Process p = pb.start();

        } else {
             String node = "C:\\bats\\"+nodeName+".bat";
             Process appium =  Runtime.getRuntime().exec("cmd /c start " + node);
        }
        Thread.sleep(15000);

    }
    public static void killAllCmd()
    {
        try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void killingTheGrid() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exec");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(url_for_killing_the_grid);
        Thread.sleep(3000);
        webDriver.close();

    }
}
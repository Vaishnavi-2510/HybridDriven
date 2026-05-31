package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {

        // loading config.properties file

        FileReader file = new FileReader("./resources/config.properties");

        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        // Remote execution

        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("windows")) {

                capabilities.setPlatform(Platform.WIN11);

            } else if (os.equalsIgnoreCase("mac")) {

                capabilities.setPlatform(Platform.MAC);

            } else if (os.equalsIgnoreCase("linux")) {

                capabilities.setPlatform(Platform.LINUX);

            } else {

                System.out.println("No matching OS");
                return;
            }

            switch (br.toLowerCase()) {

            case "chrome":

                ChromeOptions options = new ChromeOptions();

                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444/wd/hub"),
                        options);

                break;

            case "edge":

                capabilities.setBrowserName("MicrosoftEdge");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444/wd/hub"),
                        capabilities);

                break;

            case "firefox":

                capabilities.setBrowserName("firefox");

                driver = new RemoteWebDriver(
                        new URL("http://localhost:4444/wd/hub"),
                        capabilities);

                break;

            default:

                System.out.println("No matching browser");
                return;
            }
        }

        // Local execution

        if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {

            case "chrome":

                ChromeOptions chOptions = new ChromeOptions();

                chOptions.addArguments("--remote-allow-origins=*");

                driver = new ChromeDriver(chOptions);

                break;

            case "edge":

                driver = new EdgeDriver();

                break;

            case "firefox":

                driver = new FirefoxDriver();

                break;

            default:

                System.out.println("Invalid browser name..");
                return;
            }
        }

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(p.getProperty("appURL"));

        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }

    public String randomString() {

        String generatedstring = RandomStringUtils.randomAlphabetic(5);

        return generatedstring;
    }

    public String randomNumber() {

        String generatednumber = RandomStringUtils.randomNumeric(10);

        return generatednumber;
    }

    public String randomAlphNumeric() {

        String generatednumber = RandomStringUtils.randomNumeric(3);

        String generatedstring = RandomStringUtils.randomAlphabetic(3);

        return (generatednumber + generatedstring);
    }

    public String captureScreen(String testName) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String targetFilePath = System.getProperty("user.dir")
                + "\\screenshots\\"
                + testName + "_" + timeStamp + ".png";

        TakesScreenshot ts = (TakesScreenshot) driver;

        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;
    }
}
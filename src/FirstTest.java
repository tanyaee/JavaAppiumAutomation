import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.1");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/tetiana.yeremeieva/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia_50334_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }


    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "Cannot find Skip button",
                1);
        waitForElementAndClick(
                By.xpath("//*[contains(@resource-id,'search_container')]"),
                "Cannot find search input",
                3);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@resource-id,'search_container')]"),
                "Cannot find search input",
                5,
                "Java"
        );
        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by 'Java'",
                15
        );


    }


    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                3
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                10,
                "Java"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                10
        );

//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Cannot find close icon",
//                5
//        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );

    }


    @Test
    public void testCompareArticleTitle()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                3
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                10,
                "Java"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by 'Java'",
                15
        );

        WebElement title_element = waitForElementPresent(
                By.xpath("//*[@content-desc=\"Java (programming language)\"]"),
                "Cannot find article subtitle",
                15
        );

        String article_title = title_element.getAttribute("name");
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    @Test
    public void testSearchFieldHint()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                3
        );

        assertElementHasText(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Search Wikipedia",
                "'Search' field contains unexpected hint"
        );

    }


    @Test
    public void testCheckSearchResultAndCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                3
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                10,
                "OOP"
        );

        assertElementHasText(
                By.xpath("//*[@text='OOP']"),
                "OOP",
                "Article with provided title not found"
        );

        assertElementHasText(
                By.xpath("//*[@text='Oops!... I Did It Again (album)']"),
                "Oops!... I Did It Again (album)",
                "Article with provided title not found"
        );

        assertElementHasText(
                By.xpath("//*[@text='Oopiri']"),
                "Oopiri",
                "Article with provided title not found"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find close icon",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='OOP']"),
                "Search result is still shown",
                5
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Oopiri']"),
                "Search result is still shown",
                5
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Oops!... I Did It Again (album)']"),
                "Search result is still shown",
                5
        );

    }

    @Test
    public void testCheckSearchWordInSearchResponse()
    {

        String search_query = "OOP";

        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                3
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                10,
                search_query
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search results elements are not found",
                5
        );

        List<WebElement> search_result = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));

        for(int i = 0; i < search_result.size(); i++)
        {
            assertElementContainsText(
                    search_result.get(i),
                    search_query,
                    "Search response doesn't contain search query"
            );
        }

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );

    }

    private WebElement waitForElementAndClick(By by, String errr_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errr_message, timeoutInSeconds);
        element.click();
        return element;

    }


    private WebElement waitForElementAndSendKeys(By by, String errr_message, long timeoutInSeconds, String value){
        WebElement element = waitForElementPresent(by, errr_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }


    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds )
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 5);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected text",
                expected_text,
                actual_text

        );
    }

    private void assertElementContainsText(WebElement element, String expected_text, String error_message){
        String search_response = element.getAttribute("name").toLowerCase();
        if (search_response.contains(expected_text.toLowerCase())){
            Assert.assertTrue(true);
        }
        else
        {
            Assert.fail(error_message);
        }

    }
}

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/tetiana.yeremeieva/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void firstTest() {

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
    public void testCancelSearch() {


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
    public void testCompareArticleTitle() {
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
    public void testSearchFieldHint() {
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
    public void testCheckSearchResultAndCancelSearch() {
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
    public void testCheckSearchWordInSearchResponse() {

        String search_query = "OOP";

//        waitForElementAndClick(
//                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
//                "Cannot find 'Skip' button",
//                3
//        );

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

        for (int i = 0; i < search_result.size(); i++) {
            assertElementContainsText(
                    search_result.get(i),
                    search_query,
                    "Search response doesn't contain search query"
            );
        }

    }

    @Test
    public void testSwipeArticle() {


        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3,
                "Appium"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' topic searching by 'Appium'",
                15
        );

        waitForElementPresent(
                By.xpath("(//android.view.View[@content-desc=\"Appium\"])[1]"),
                "Cannot find article title",
                15
        );


        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                //By.id("pcs-footer-container-legal"),
                "Cannot find the end of the article",
                5
        );

    }

    @Test
    public void testSaveFirstArticleToMyList() {

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

        waitForElementPresent(
                By.xpath("//*[@text=\"Java (programming language)\"]"),
                "Cannot find article subtitle",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find hamburger menu button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[3]"),
                "'Add to list' button not found",
                7
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/onboarding_button\"]"),
                "'Got it' button not found",
                7
        );

        String name_of_folder = "Learning programming";

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field for entering the folder name",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field for entering the folder name",
                5,
                name_of_folder
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot tap 'OK' button on the pop-up window",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find X button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@content-desc=\"My lists\"]"),
                "My lists are not found in navigation bar",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Saved articles folder not found",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }


    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        String search_line = "Linkin park discography";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3,
                search_line
        );

        String search_result_locator = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/*[@resource-id=\"org.wikipedia:id/page_list_item_container\"]";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anythyng by the request" + search_line,
                15
        );

        int amount_of_search_elements = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_elements > 0
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );

    }


    public WebElement waitForElementDisplayed(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }


    private WebElement waitForElementAndClick(By by, String errr_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errr_message, timeoutInSeconds);
        element.click();
        return element;

    }


    private WebElement waitForElementAndSendKeys(By by, String errr_message, long timeoutInSeconds, String value) {
        WebElement element = waitForElementPresent(by, errr_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }


    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected text",
                expected_text,
                actual_text

        );
    }

    private void assertElementContainsText(WebElement element, String expected_text, String error_message) {
        String search_response = element.getAttribute("name").toLowerCase();
        if (search_response.contains(expected_text.toLowerCase())) {
            Assert.assertTrue(true);
        } else {
            Assert.fail(error_message);
        }

    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();


    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }


    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;


        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            } else {
                swipeUpQuick();
                ++already_swiped;
                System.out.println("already_swiped = " + already_swiped);
            }


        }

    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);

        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by)
    {

        List elements = driver.findElements(by);
        return elements.size();
    }
}

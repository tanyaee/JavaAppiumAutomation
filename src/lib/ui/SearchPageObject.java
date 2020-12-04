package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[@text='Search Wikipedia']",
            SEARCH_INPUT = "//*[@text='Search…']",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id=\"org.wikipedia:id/search_results_list\"]/*[@resource-id=\"org.wikipedia:id/page_list_item_container\"]",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_PREVIOUS_SEARCH_REQUEST_TPL = "//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_CONTAINER = "//*[@resource-id='org.wikipedia:id/page_list_item_title']";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchPreviousSearchRequest(String search_line)
    {
        return SEARCH_PREVIOUS_SEARCH_REQUEST_TPL.replace("{SUBSTRING}", search_line);
    }

    /* TEMPLATE METHODS */

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_title_xpath = getResultSearchElement(title);
        this.waitForElementPresent(By.xpath(search_result_title_xpath), "Cannot find search result with title " + title,5);
        String search_result_description_xpath = getResultSearchElement(description);
        this.waitForElementPresent(By.xpath(search_result_description_xpath), "Cannot find search result with description " + description,5);
    }

    public void initSearchInput()
    {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find and click search init element",5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find search input after clicking search init element",5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot type into search input",5);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring,5);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring,10);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button",
                5
        );

    }
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still present",
                5
        );

    }
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find X to cancel search",5);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anythyng by the request",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForSearchFieldHint(String expected_hint)
    {
        this.assertElementHasText(
                By.xpath(SEARCH_INIT_ELEMENT),
                expected_hint,
                "'Search' field contains unexpected hint"
        );
    }
    public void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty label by the request ",
                15
        );
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }

    public void waitForPreviousSearchesButtonAndClick(String search_line)
    {
        String previous_searches_xpath = getSearchPreviousSearchRequest(search_line);
        this.waitForElementAndClick(
                By.xpath(previous_searches_xpath),
                "Cannot find recent searches button",
                4
        );
    }

    public void checkSearchResultsByText(String article_title)
    {
        String article_title_xpath = getResultSearchElement(article_title);
        this.assertElementHasText(
                By.xpath(article_title_xpath),
                article_title,
                "Article with '" + article_title + "' title not found"
        );
    }

    public void checkSearchResultContainsSearchRequest(String search_query)
    {

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_CONTAINER),
                "Search results elements are not found",
                5
        );

        List<WebElement> search_result = driver.findElements(By.xpath(SEARCH_RESULT_CONTAINER));

        for (int i = 0; i < search_result.size(); i++) {
            this.assertElementContainsText(
                    search_result.get(i),
                    search_query,
                    "Search response doesn't contain search query"
            );
        }
    }
}

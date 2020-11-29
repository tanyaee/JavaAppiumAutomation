package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {
    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testSearchFieldHint() {

        MainPageObject.assertElementHasText(
                By.xpath("//*[@text='Search Wikipedia']"),
                "Search Wikipedia",
                "'Search' field contains unexpected hint"
        );
    }

    @Test
    public void testCheckSearchResultAndCancelSearch() {

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                "OOP",
                10
        );

        MainPageObject.assertElementHasText(
                By.xpath("//*[@text='OOP']"),
                "OOP",
                "Article with provided title not found"
        );

        MainPageObject.assertElementHasText(
                By.xpath("//*[@text='Oops!... I Did It Again (album)']"),
                "Oops!... I Did It Again (album)",
                "Article with provided title not found"
        );

        MainPageObject.assertElementHasText(
                By.xpath("//*[@text='Oopiri']"),
                "Oopiri",
                "Article with provided title not found"
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find close icon",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='OOP']"),
                "Search result is still shown",
                5
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Oopiri']"),
                "Search result is still shown",
                5
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Oops!... I Did It Again (album)']"),
                "Search result is still shown",
                5
        );

    }

    @Test
    public void testCheckSearchWordInSearchResponse() {

        String search_query = "OOP";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                search_query,
                10
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search results elements are not found",
                5
        );

        List<WebElement> search_result = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));

        for (int i = 0; i < search_result.size(); i++) {
            MainPageObject.assertElementContainsText(
                    search_result.get(i),
                    search_query,
                    "Search response doesn't contain search query"
            );
        }

    }




    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin park discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "fegefdewffd";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }


    @Test
    public void testAssertTitlePresent(){
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        String search_line = "Appium";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                search_line,
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/page_list_item_title\"][@text='Appium']"),
                "Cannot find 'Appium' topic searching by "+ search_line,
                15
        );


        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title is missed"
        );

    }

}

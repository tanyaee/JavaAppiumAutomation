package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testSearchFieldHint() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.waitForSearchFieldHint("Search Wikipedia");
    }

    @Test
    public void testCheckSearchResultAndCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("OOP");
        SearchPageObject.checkSearchResultsByText("OOP");
        SearchPageObject.checkSearchResultsByText("Oops!... I Did It Again (album)");
        SearchPageObject.checkSearchResultsByText("Oopiri");
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCheckSearchWordInSearchResponse() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_query = "OOP";
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.checkSearchResultContainsSearchRequest(search_query);

    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "fegefdewffd";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCheckSearchResultsByTitleAnsDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("OOP");
        if(Platform.getInstance().isIOS()){
            SearchPageObject.waitForElementByTitleAndDescription("OOP", "Disambiguation page providing links to topics that could be referred to by the same search term");

        }
        else {
            SearchPageObject.waitForElementByTitleAndDescription("OOP", "Wikimedia disambiguation page");

        }
        SearchPageObject.waitForElementByTitleAndDescription("Oops!... I Did It Again (album)", "2000 studio album by Britney Spears");
        SearchPageObject.waitForElementByTitleAndDescription("Oopiri", "2016 film by Vamsi Paidipally");

    }


}

package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {
    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }
    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject =  new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUi NavigationUi = new NavigationUi(driver);

        NavigationUi.cLickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        MyListsPageObject.openFolderName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }


    @Test
    public void testSaveTwoArticlesToOneFolder()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                3
        );

        String search_line = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                search_line,
                10
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' topic searching by 'Java'",
                15
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text=\"Java (programming language)\"]"),
                "Cannot find article subtitle",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find hamburger menu button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[3]"),
                "'Add to list' button not found",
                7
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id=\"org.wikipedia:id/onboarding_button\"]"),
                "'Got it' button not found",
                7
        );

        String name_of_folder = "Learning programming";

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field for entering the folder name",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field for entering the folder name",
                name_of_folder,
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot tap 'OK' button on the pop-up window",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find X button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search' field",
                4
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ search_line +"']"),
                "Cannot find recent searches button",
                4
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='JavaScript']"),
                "Cannot find 'JavaScript' topic searching by 'Java'",
                15
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text=\"JavaScript\"]"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find hamburger menu button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "'Add to list' button not found",
                7
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ name_of_folder +"']"),
                "Folder with '"+ name_of_folder + "'name wasn't found",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find X button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc=\"My lists\"]"),
                "My lists are not found in navigation bar",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Saved articles folder not found",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text=\"JavaScript\"]"),
                "Cannot find article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='JavaScript']"),
                "'JavaScript' article not found",
                3
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text=\"JavaScript\"]"),
                "Cannot find article title",
                15
        );
    }

}

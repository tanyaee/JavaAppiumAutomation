package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        SWIPE_ACTION_TO_DELETE,
        CLOSE_SYNC_YOUR_SAVED_ARTICLES;

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {

        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public void openFolderName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                10
        );
    }
    public void waitToArticleToAppear(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }
    public void waitToArticleToDisappear(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitToArticleToAppear(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );
        if (Platform.getInstance().isIOS()) {
             this.waitForElementAndClick(SWIPE_ACTION_TO_DELETE, "Cannot find delete button",4);
             //this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find element to delete");
        }
        this.waitToArticleToDisappear(article_title);

    }


    public void openArticle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementAndClick(
                article_xpath,
                "'" + article_title + "' article not found",
                3
        );
    }

    public void closeSyncYourSavedArticles()
    {
        this.waitForElementAndClick(CLOSE_SYNC_YOUR_SAVED_ARTICLES, "Cannot find and click close icon by element" + CLOSE_SYNC_YOUR_SAVED_ARTICLES, 3);
    }

    public void assertArticleContainsText(String article_title)
    {
        String article_element_xpath = getSavedArticleXpathByTitle(article_title);

        WebElement element = this.waitForElementPresent(article_element_xpath, "Cannot find saved article eleemt by locator + " + article_element_xpath,2);
        this.assertElementContainsText(element, article_title, "Cannot find elemant with title " + article_title);
    }
}

package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.SplittableRandom;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title;

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
            article_title = ArticlePageObject.getArticleTitle();

        }else {
            ArticlePageObject.waitForTitleElementIOS("Java (programming language)");
            article_title = ArticlePageObject.getArticleTitleIOS("Java (programming language)");

        }

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);

        }else{
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUi NavigationUi = NavigationUIFactory.get(driver);

        NavigationUi.cLickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderName(name_of_folder);
        }else{
            MyListsPageObject.closeSyncYourSavedArticles();
        }
        MyListsPageObject.swipeByArticleToDelete(article_title);

    }


    @Test
    public void testSaveTwoArticlesToOneFolder()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Java";
        String first_article = "Java (programming language)";
        String second_article = "Java";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(first_article);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String first_article_title;
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
            first_article_title = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToMyList(name_of_folder);

        }else {
            ArticlePageObject.waitForTitleElementIOS(first_article);
            first_article_title = ArticlePageObject.getArticleTitleIOS(first_article);
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();
        if(Platform.getInstance().isAndroid()){
                    SearchPageObject.initSearchInput();
        SearchPageObject.waitForPreviousSearchesButtonAndClick(search_line);
        }

        SearchPageObject.clickByArticleWithSubstring(second_article);

        String second_article_title;
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
            second_article_title = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addSecondArticleToMyList(name_of_folder);
            ArticlePageObject.closeArticle();
        }else {
            ArticlePageObject.waitForTitleElementIOS(second_article);
            second_article_title = second_article;
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeArticle();
            SearchPageObject.clickCancelSearch();
        }

        NavigationUi NavigationUi = NavigationUIFactory.get(driver);

        NavigationUi.cLickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderName(name_of_folder);
        }else{
            MyListsPageObject.closeSyncYourSavedArticles();
        }

        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        MyListsPageObject.waitToArticleToDisappear(first_article_title);
        MyListsPageObject.assertArticleContainsText(second_article_title);

    }

}

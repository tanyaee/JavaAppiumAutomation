package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static final String
        TITLE = "id:org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
        OPTIONS_BUTTON = "xpath://*[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://android.widget.LinearLayout[3]",
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "xpath://*[@content-desc='Navigate up']",
        MY_LISTS_ITEM_TPL = "xpath://*[@text='{NAME_OF_FOLDER}']",
        ARTICLE_TITLE_TPL = "xpath://*[@text='{ARTICLE_TITLE}']";


    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getFolderNameElement(String name_of_folder)
    {
        return MY_LISTS_ITEM_TPL.replace("{NAME_OF_FOLDER}", name_of_folder);
    }

    private static String getArticleNameElement(String article_title)
    {
        return ARTICLE_TITLE_TPL.replace("{ARTICLE_TITLE}", article_title);
    }

    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on Page", 5);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder)
    {

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find hamburger menu button",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "'Add to list' button not found",
                7
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "'Got it' button not found",
                7
        );


        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input field for entering the folder name",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot find input field for entering the folder name",
                10
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot tap 'OK' button on the pop-up window",
                5
        );

    }

    public void addSecondArticleToMyList(String name_of_folder)
    {

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find hamburger menu button",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "'Add to list' button not found",
                7
        );
        String name_of_folder_xpath = getFolderNameElement(name_of_folder);

        this.waitForElementAndClick(
                name_of_folder_xpath,
                "Folder with '"+ name_of_folder + "'name wasn't found",
                5
        );

    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find X button",
                5
        );
    }

    public void checkArticleTitlePresent(String article_title)
    {
        String article_title_xpath = getArticleNameElement(article_title);
        this.waitForElementPresent(
                article_title_xpath,
                "Cannot find article title",
                15
        );
    }
}

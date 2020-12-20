package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

abstract public class NavigationUi extends MainPageObject {
    protected static String
        MY_LISTS_LINK;


    public NavigationUi(AppiumDriver driver)
    {
        super(driver);
    }

    public void cLickMyLists(){
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "My lists are not found in navigation bar",
                5
        );
    }
}

package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUi extends MainPageObject {
    private static final String
        MY_LISTS_LINK = "xpath://*[@content-desc=\"My lists\"]";


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

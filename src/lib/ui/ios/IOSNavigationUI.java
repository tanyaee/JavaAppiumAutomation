package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUi;

public class IOSNavigationUI extends NavigationUi {
    static {
        MY_LISTS_LINK = "id:Saved";

    }

    public IOSNavigationUI(AppiumDriver driver)
    {
        super(driver);
    }
}

package BasePage;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import sun.jvm.hotspot.debugger.Page;

public class BaseTest {
    private SoftAssert softAssert;

    @BeforeMethod
    public void initSoftAssert() {
        softAssert = new SoftAssert(); // Initialize before each test method
    }

    public SoftAssert getSoftAssert() {
        return softAssert; // Return existing instance, not reinitializing every time
    }

}

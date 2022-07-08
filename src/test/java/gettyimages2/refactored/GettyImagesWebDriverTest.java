package gettyimages2.refactored;

import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GettyImagesWebDriverTest {

    private GettyImagesWebDriver gettyImagesWebDriver;
    private GettyImagesPage gettyImagesPage;

    @BeforeAll
    public void setUp() {
        gettyImagesWebDriver = new GettyImagesWebDriver();
        gettyImagesPage = new GettyImagesPage(gettyImagesWebDriver.getDriver());
    }

    @AfterEach
    public void tearDown() {
        LogEntries browserLogs = gettyImagesWebDriver.getDriver().manage().logs().get(LogType.BROWSER);
        List<LogEntry> allLogRows = browserLogs.getAll();
        if (allLogRows.size() > 0) {
        }
    }

    @Test
    @Owner("Viktoryia Chyhrynets")
    @Order(1)
    @Feature("Registration")
    @Link(name = "Authorization to the site", url = "https://www.gettyimages.com/")
    @Description("Authorization test is ran. Username and password are required")
    @Step("Authorization")
    @Story("Grouping")
    @Severity(value = SeverityLevel.CRITICAL)
    public void authorizationTest() throws InterruptedException {
        log.info("Authorization test has been started...");
        String result = gettyImagesPage.closeCookies()
                .openAuthorizationPanel()
                .signIn()
                .userNameFill("vika.dolgikh@mail.ru")
                .passwordFill("19072014Vika")
                .signInButtonGetText();
        assertEquals("SIGN IN", result);
        gettyImagesPage.signInButtonClick();
        log.info("Authorization test is successfully completed.");
    }

    @Test
    @Order(2)
    @Feature("Photos selection")
    @DisplayName("Test 2: Add photos to the cart")
    @Description("User chooses photos, price and then adds those photos to cart")
    @Step("Add to cart")
    @Severity(value = SeverityLevel.NORMAL)
    @Flaky
    public void addPhotosToCart() throws InterruptedException {
        log.info("Add photos to cart test has been started...");
        String creativeButton = gettyImagesPage.closeVisualGPSInsightsButton()
                .creativePhotosSelectionGetText();
        assertEquals("CREATIVE", creativeButton);
        String addToCartButton1 = gettyImagesPage.creativePhotosSelectionClick()
                .selectPicture1()
                .selectPrice()
                .addToCartGetText();
        assertEquals("ADD TO CART", addToCartButton1);
        String addToCartButton2 = gettyImagesPage.addToCartClick()
                .backToMenuClick()
                .backToCreativeButtonClick()
                .selectPicture2()
                .selectPrice()
                .addToCartGetText();
        assertEquals("ADD TO CART", addToCartButton2);
        gettyImagesPage.addToCartClick()
                .viewCartClick();
        log.info("Add photos to cart test is successfully completed.");
    }

    @Test
    @Order(3)
    @Feature("Photos selection")
    @DisplayName("Test 3: Remove photos from cart")
    @Description("User can remove photos from cart")
    @Step("Removal from cart")
    @Severity(value = SeverityLevel.NORMAL)
    @Flaky
    public void removePhotos() throws InterruptedException {
        log.info("Remove photos from the cart test has been started...");
        gettyImagesPage.clearCartClick();
        log.info("Remove photos from the cart test is successfully completed.");
    }

    @Test
    @Order(4)
    @Feature("Photos search")
    @DisplayName("Test 4: Find pictures with cats and then add to cart/remove")
    @Description("User can type any word into search and find photos on this topic")
    @Step("Cats search")
    @Severity(value = SeverityLevel.MINOR)
    @Flaky
    public void catsSearch() throws InterruptedException {
        log.info("Cats search test has been started...");
        String allButton = gettyImagesPage.searchFill("Cats")
                .searchClick()
                .allButtonGetText();
        assertEquals("All", allButton);
        gettyImagesPage.picture3NewWindow()
                .viewCartClick()
                .removePicture3();
        log.info("Cats search test is successfully completed.");
    }

    @Test
    @Order(5)
    @Feature("Registration")
    @DisplayName("Test 5: Sign out")
    @Description("Signing out process is checked")
    @Step("Sign out")
    @Severity(value = SeverityLevel.CRITICAL)
    @Flaky
    public void signOut() {
        log.info("Sign out test has been started...");
        String singOutButton = gettyImagesPage.goToAuthorizationPanel()
                .signOutButtonGetText();
        assertEquals("SIGN OUT", singOutButton);
        gettyImagesPage.signOutButtonClick();
        log.info("Sign out test is completed.");
    }

    @AfterAll
    public void closeChromeTab() {
        gettyImagesWebDriver.getDriver().quit();
    }
}

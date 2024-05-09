import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class TestDiscoveryFeature {

    AndroidDriver driver;
    String email = "test@test.com";
    String password = "Password";

    @BeforeMethod(alwaysRun=true)
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new UiAutomator2Options();
        capabilities.setCapability("browserstack.gpsLocation", "53.3441835,-6.2472137");
        capabilities.setCapability("os_version", "13.0");
        capabilities.setCapability("device", "Google Pixel 7");
        capabilities.setCapability("app", System.getProperty("user.dir") +"/SquidApp.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test() throws InterruptedException {
        // Given the Squid app is opened

        // Allow the app to send the user notifications
        WebElement allowNotificationsButton = getElementByUiSelector("new UiSelector().text(\"Allow\")");
        allowNotificationsButton.click();

        // Dismiss the NFC prompt if it's displayed (otherwise, ignore)
        dismissNfcPrompt();

        // Skip the sign in process for now
        WebElement skipForNowButton = getElementByUiSelector("new UiSelector().text(\"Skip for Now\")");
        skipForNowButton.click();

        // Dismiss the NFC prompt if it's displayed (otherwise, ignore)
        dismissNfcPrompt();

        // Click the "discovery" button
        try{
            WebElement discoveryButton = getElementByUiSelector("new UiSelector().className(\"android.widget.ImageView\").instance(1)");
            discoveryButton.click();
        }
        catch (TimeoutException timeoutException){
            tap(driver, 305, 2248);
        }

        // Allow the device location to be shared with the app
        WebElement allowLocationButton = getElementByUiSelector("new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\")");
        allowLocationButton.click();

        // Adding a sleep to show the businesses listed in the Browserstack Video
        Thread.sleep(2000);
    }

    @Test
    public void testWithLogin() throws InterruptedException {
        // Given the Squid app is opened

        // Allow the app to send the user notifications
        WebElement allowNotificationsButton = getElementByUiSelector("new UiSelector().text(\"Allow\")");
        allowNotificationsButton.click();

        // Dismiss the NFC prompt if it's displayed (otherwise, ignore)
        dismissNfcPrompt();

        // Click the "Continue with Email" button to login
        WebElement continueWithEmailButton = getElementByUiSelector("new UiSelector().text(\"Continue with Email\")");
        continueWithEmailButton.click();

        // Dismiss the NFC prompt if it's displayed (otherwise, ignore)
        dismissNfcPrompt();

        // Enter a valid email address
        WebElement emailTextBox = getElementByUiSelector("new UiSelector().className(\"android.widget.EditText\")");
        emailTextBox.sendKeys(email);

        // Click the "Continue" button
        WebElement continueButton = getElementByUiSelector("new UiSelector().text(\"Continue\")");
        continueButton.click();

        // Enter a valid email password
        WebElement passwordTextBox = getElementByUiSelector("new UiSelector().className(\"android.widget.EditText\").instance(1)");
        passwordTextBox.sendKeys(password);

        // Click the login button
        WebElement logInButton = getElementByUiSelector("new UiSelector().text(\"Log In\")");
        logInButton.click();

        // Dismiss the NFC prompt if it's displayed (otherwise, ignore)
        dismissNfcPrompt();

        // Click the "discovery" button
        try{
            WebElement discoveryButton = getElementByUiSelector("new UiSelector().className(\"android.widget.ImageView\").instance(1)");
            discoveryButton.click();
        }
        catch (TimeoutException timeoutException){
            tap(driver, 305, 2248);
        }

        // Allow the device location to be shared with the app
        WebElement allowLocationButton = getElementByUiSelector("new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\")");
        allowLocationButton.click();

        // Adding a sleep to show the businesses listed in the Browserstack Video
        Thread.sleep(2000);
    }

    public WebElement getElementByUiSelector(String uiSelector){
        return new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator(uiSelector)));
    }

    public void dismissNfcPrompt(){
        try {
            WebElement nfcPromptCancelButton = getElementByUiSelector("new UiSelector().resourceId(\"android:id/button2\")");
            nfcPromptCancelButton.click();
        }
        catch (TimeoutException ignored){
            // NFC Prompt not displayed
        }
    }

    public static void tap(AppiumDriver driver, int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(150)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));
        System.out.println("Tap with Coordinates");
    }
}

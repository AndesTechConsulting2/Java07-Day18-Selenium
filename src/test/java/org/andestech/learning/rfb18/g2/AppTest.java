package org.andestech.learning.rfb18.g2;


import org.openqa.selenium.*;
//import org.openqa.selenium.;
//import org.openqa.selenium.
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class AppTest
{
    private WebDriver wd = null;
    private ChromeOptions option = null;

    @BeforeClass
    public void initData(){
    System.setProperty("webdriver.chrome.driver",
            "E:\\drivers\\selenium\\chromedriver.exe");
    System.out.println("+++ Class: " + this);

    String myProfile = "C:\\Users\\and\\AppData\\Local\\Google\\Chrome\\User Data";
        myProfile =    "C:\\Users\\and\\AppData\\Local\\Chromium\\User Data";
     //   myProfile =    "C:\\Users\\and\\AppData\\Local\\Chromium\\User Data";
        option = new ChromeOptions();
        option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        option.setBinary("E:\\progs\\chrome-win\\chrome.exe");

        option.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        // option.setPageLoadStrategy(PageLoadStrategy.NONE);
        option.addArguments("user-data-dir=" + myProfile);

        wd = new ChromeDriver(option);
        wd.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wd.get(LoginPage.basicURL);
    }

    private void savePicture(){

        File screen1 = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
        Path data1 = Paths.get("E:\\datas\\logs\\screen_login_" + System.currentTimeMillis() + ".png");
        try{
        Files.copy(new FileInputStream(screen1), data1);}
        catch (IOException ex){ex.printStackTrace();}
    }

    @Test
    public void windowTest() throws InterruptedException {

       WebDriver.Window w = wd.manage().window();
       w.setSize(new Dimension(1200, 800));

       Set<String> windows = wd.getWindowHandles();

       ((JavascriptExecutor) wd).executeScript("window.open()");

        Set<String> windows2 = wd.getWindowHandles();

        windows2.removeAll(windows);

        String w1 = (String) windows2.toArray()[0];


       wd.switchTo().window(w1);

       wd.get("http://lenta.ru");

       Thread.sleep(5000);



    }


    @Test
    public void testActions() throws InterruptedException {

        WebElement slider = wd.findElement(By.cssSelector("#price"));

        Actions actions = new Actions(wd);

        actions.moveToElement(slider,0,0).click().build().perform();


        for(int i =0 ; i<10; i++){
                actions.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).
                build().perform();
        Thread.sleep(100);}


    }

    @Test
    public void testLoginLogout() throws InterruptedException, IOException {

       // Thread.sleep(2000);

        savePicture();

        wd.findElement(By.linkText("Login")).click();//wd.findElement(By.cssSelector("#sub > footer > nav > div > a:nth-child(3)")).click();

        savePicture();



       // wd.get(LoginPage.basicURL + "login.html");

       // Thread.sleep(1000);

       // wd.findElement(By.name("reset")).click();

        WebElement login = wd.findElement(By.cssSelector("#login"));
      //  Thread.sleep(1000);
        login.clear();
        login.sendKeys("ppetrov");

        WebElement pass =  wd.findElement(By.cssSelector("#pass"));
        pass.clear();
      //  Thread.sleep(1000);
        pass.sendKeys("Ppetrov22");

      //  Thread.sleep(1000);

        //wd.findElement(By.cssSelector("input#lgn")).click();
        login.submit();
      //  Thread.sleep(2000);

//       WebElement lout =  wd.findElement(By.cssSelector("#sub > footer > nav > div > a:nth-child(5)"));
//       WebElement lout2 =  wd.findElement(By.xpath("//*[@id=\"sub\"]/footer/nav/div/a[5]"));
//
//        lout2.click();

        savePicture();

        wd.findElement(By.linkText("Logout")).click();

        Alert alert = wd.switchTo().alert();

      //  savePicture();

      //  Thread.sleep(2000);

        if(alert.getText().contains("успешно")) { alert.accept();}
        else {alert.accept();
            Assert.assertTrue(false, "Unexpected alert!!!");}

      // Thread.sleep(5000);
        savePicture();
    }

    @Test(groups = "positive")
    public void loginLogoutPageTest()
    {

        Login login = new Login("ppetrov", "Ppetrov22");
        LoginPage loginpage = new LoginPage(login,wd);

        Assert.assertTrue(loginpage.testLogin(), "Login failed!..." + login);
        //.. действия авторизованого пользователя на странице\\

        Assert.assertTrue(loginpage.testLogout(),"Logout failed!..." + login);

    }


    @Test(groups = "positive")
    public void registrationTest(){


    }


    @Test
    public void selectorTest() throws InterruptedException
    {
        wd.get("http://andestech.org/learning/rfb18/newcustomer.html");

        WebElement webElement =
                wd.findElement(By.id("group_selector"));

        Select select = new Select(webElement);

       // select.selectByValue("free");
        select.selectByVisibleText("безработный");
        Thread.sleep(2000);
        select.selectByVisibleText("студент");
        Thread.sleep(2000);

    }


    @AfterClass
    public void tearDown()
    {
      if(wd != null) wd.quit();
      System.out.println("--- Class: " + this);
    }

}

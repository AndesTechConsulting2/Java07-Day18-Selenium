package org.andestech.learning.rfb18.g2;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertTrue;

public class AppTest2
{
    private WebDriver wd = null;
    private FirefoxOptions options = null;

    @BeforeClass
    public void initData(){

    System.setProperty("webdriver.gecko.driver",
            "E:\\drivers\\selenium\\geckodriver.exe");
        System.out.println("+++ Class: " + this);

        File f1 = new File("C:\\Users\\and\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\nywxnvk0.default");
        FirefoxProfile profile = new FirefoxProfile(f1);

        options = new FirefoxOptions();
        options.setProfile(profile);



    }

    @Test
    public void testCaseFFox01()
    {
        wd = new FirefoxDriver(options);
        wd.get("http://lenta.ru");
        assertTrue( true );
    }


    @AfterClass
    public void tearDown()
    {
        if(wd != null) wd.quit();
        System.out.println("--- Class: " + this);
    }

}

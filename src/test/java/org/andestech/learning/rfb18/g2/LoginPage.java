package org.andestech.learning.rfb18.g2;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  public final static String basicURL =
  "http://andestech.org/learning/rfb18/";

  public final static String loginURL =
          "http://andestech.org/learning/rfb18/login.html";


  public LoginPage(Login login, WebDriver wd) {

    PageFactory.initElements(wd, this);
    this.login = login;
    this.wd = wd;
  }

  private Login login;
  private WebDriver wd;

  @FindBy(id = "login")
  private WebElement loginText;


  @FindBy(id = "pass")
  private WebElement passText;

  @FindBy(id = "lgn")
  private WebElement loginSubmitBtn;

  @FindBy(linkText = "Logout")
  private WebElement logoutLink;

  @FindBy(name = "reset")
  private WebElement resetBtn;


  private boolean isUserLoggedIn()
  {
    Cookie cookie= wd.manage().getCookieNamed("loginOk");
    if(cookie != null && cookie.getValue().equals(login.getLogin())) return true;
    return false;

  }

  public boolean testLogin()
  {
    if(isUserLoggedIn()) return true;
    if(!wd.getCurrentUrl().equals(loginURL)) wd.get(loginURL);
    resetBtn.click();
    loginText.sendKeys(login.getLogin());
    passText.sendKeys(login.getPassword());
    loginSubmitBtn.click();
    /// проверка что логин успешен
    if(isUserLoggedIn())
    {return true;}

    return false;
  }

  public boolean testLogout()
  {
    if(!isUserLoggedIn()) testLogin();
    logoutLink.click();

    wd.switchTo().alert().accept();

    if(!isUserLoggedIn())
      return true;

    return false;
  }


}

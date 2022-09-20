package com.testing.class6.pageFactoryPO.pageShop;


import com.testing.web.WebKeyword;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage  {
	//把webkeyword当成一个工具对象来进行使用。
	public WebKeyword kw;
	public String Url = "http://www.testingedu.com.cn:8000/Home/user/login.html";

	@FindBy(xpath = "//input[@id='username']")
	public WebElement user;

	@FindBy(xpath = "//input[@id='password']")
	public WebElement password;

	@FindBy(xpath = "//input[@placeholder='验证码']")
	public WebElement verifyCode;

	@FindBy(xpath = "//a[@name='sbtbutton']")
	public WebElement submitBtn;

	public LoginPage(WebKeyword keyword) {
		kw=keyword;
		kw.visitWeb(Url);
		PageFactory.initElements(kw.getDriver(), this);
	}

	public void login() {
		try {
			user.sendKeys("13800138006");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			password.sendKeys("123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verifyCode.sendKeys("1");
		submitBtn.click();
		kw.halt("2");
	}

}

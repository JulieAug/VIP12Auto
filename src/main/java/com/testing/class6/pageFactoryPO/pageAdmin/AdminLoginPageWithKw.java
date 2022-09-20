package com.testing.class6.pageFactoryPO.pageAdmin;

import com.testing.web.WebKeyword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class AdminLoginPageWithKw extends WebKeyword {
	public String Url="http://www.testingedu.com.cn:8000/Admin/Admin/login";
//	@FindBy(xpath = "//input[@name='username']")
	@FindBy(name="username")
	public WebElement user;

	@FindBy(xpath = "//input[@name='password']")
	public WebElement password;

	@FindBy(xpath = "//*[@id='vertify']")
	public WebElement verifyCode;

	@FindBy(xpath = "//input[@name='submit']")
	public WebElement submitBtn;
	
	
	public AdminLoginPageWithKw(WebDriver driver) {
		setDriver(driver);
		visitWeb(Url);
		PageFactory.initElements(getDriver(), this);
	}

	public void login() {
		user.sendKeys("admin");
		password.sendKeys("123456");
		verifyCode.sendKeys("1");
		submitBtn.click();
		halt("2");
	}
	
	public void login(String username,String pwd,String vCode) {
		user.sendKeys(username);
		password.sendKeys(pwd);
		verifyCode.sendKeys(vCode);
		submitBtn.click();
		halt("2");
	}
}

package com.testing.class6.pageFactoryPO.pageAdmin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminLoginPage {
	//成员变量
	public WebDriver admin;
	public String Url="http://www.testingedu.com.cn:8000/Admin/Admin/login";
	//表示使用byname方法进行定位，定位到的元素传递给 成员变量user
//	@FindBy(xpath = "//input[@name='username']")
	@FindBy(name="username")
	public WebElement user;

	@FindBy(xpath = "//input[@name='password']")
	public WebElement password;

	@FindBy(xpath = "//*[@id='vertify']")
	public WebElement verifyCode;

	@FindBy(xpath = "//input[@name='submit']")
	public WebElement submitBtn;
	
	
	public AdminLoginPage(WebDriver driver) {
		//为成员变量admin赋值，使用同一个浏览器。
		admin=driver;
		admin.get(Url);
		PageFactory.initElements(admin, this);
	}

	/**
	 * login方法，将需要用的数据也写在了方法中，管理维护的时候，到方法里修改数据
	 */
	public void login() {
		user.sendKeys("admin");
		password.sendKeys("123456");
		verifyCode.sendKeys("1");
		submitBtn.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 数据封装到参数列表里，调用的时候进行传递。维护的时候，就在调用的时候进行数据管理。
	 * @param username
	 * @param pwd
	 * @param vCode
	 */
	public void login(String username,String pwd,String vCode) {
		user.sendKeys(username);
		password.sendKeys(pwd);
		verifyCode.sendKeys(vCode);
		submitBtn.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

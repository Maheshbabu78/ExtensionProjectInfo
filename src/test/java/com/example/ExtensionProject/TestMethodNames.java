package com.example.ExtensionProject;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestMethodNames {
	
	@AfterSuite
	   public void name(ITestContext context) {
	      ITestNGMethod[] a = context.getAllTestMethods();
	      for (ITestNGMethod b : a) {
	         System.out.println("Method Name "+b.getConstructorOrMethod().getName()+ " executed");
	      }
	   }

}

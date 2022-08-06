package com.example.ExtensionProject;

public class MethodsInDb {
	
	private String key;
	private String value;
	private String testCase;
	
	
	
	
	
	public MethodsInDb(String key, String value, String testCase) {
		super();
		this.key = key;
		this.value = value;
		this.testCase = testCase;
	}


	public MethodsInDb() {
		super();
	}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


	public String getTestCase() {
		return testCase;
	}


	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}


	@Override
	public String toString() {
		return "MethodsInDb [key=" + key + ", value=" + value + ", testCase=" + testCase + "]";
	}


	
	

}

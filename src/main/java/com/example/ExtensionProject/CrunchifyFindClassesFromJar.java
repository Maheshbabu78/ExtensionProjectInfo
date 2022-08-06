package com.example.ExtensionProject;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
 
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
 
/**
 * @author Crunchify.com
 * 
 */
 @Component
public class CrunchifyFindClassesFromJar {
 
	@SuppressWarnings("resource")
	public JSONObject getCrunchifyClassNamesFromJar(String crunchifyJarName) {
		JSONArray listofClasses = new JSONArray();
		JSONObject crunchifyObject = new JSONObject();
		try {
			JarInputStream crunchifyJarFile = new JarInputStream(new FileInputStream(crunchifyJarName));
			JarEntry crunchifyJar;
 
			while (true) {
				crunchifyJar = crunchifyJarFile.getNextJarEntry();
				if (crunchifyJar == null) {
					break;
				}
				if ((crunchifyJar.getName().endsWith(".class"))) {
					String className = crunchifyJar.getName().replaceAll("/", "\\.");
					String myClass = className.substring(0, className.lastIndexOf('.'));
					listofClasses.put(myClass);
				}
			}
			
			crunchifyObject.put("Jar File Name", crunchifyJarName);
			crunchifyObject.put("List of Class", listofClasses);
		} catch (Exception e) {
			System.out.println("Oops.. Encounter an issue while parsing jar" + e.toString());
		}
		return crunchifyObject;
	}
}
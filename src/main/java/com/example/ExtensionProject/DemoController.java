package com.example.ExtensionProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.internal.Path;

@RestController
public class DemoController {

	@Autowired
	private CrunchifyFindClassesFromJar jar;

	@Autowired
	private HomeController controller;

	@PostMapping("/api/data")
	public Object saveData(@RequestBody JsonNode jsonNode) throws Exception {
		JSONObject obj = jar.getCrunchifyClassNamesFromJar("C:\\Users\\NMAHESHBABU\\ebanking-0.0.1-SNAPSHOT.jar");
		JSONArray list = (JSONArray) obj.get("List of Class");
		list.forEach(className -> {
			if (className.toString().contains("BOOT-INF.classes")) {
				String classNameCon = className.toString().replace("BOOT-INF.classes.", "");
				try {
					String url = jsonNode.get("url").toString();
					if (jsonNode.get("method").toString().equals("\"POST\"")) {

						StringBuffer postUrl = new StringBuffer(url);
						String replaced = postUrl.toString().replace("http://localhost:8080", "");

						loadJar(classNameCon, "POST", replaced);
					} else if (jsonNode.get("method").toString().equals("\"GET\"")) {

						StringBuffer postUrl = new StringBuffer(url);
						String replaced = postUrl.toString().replace("http://localhost:8080", "");
						loadJar(classNameCon, "GET", replaced);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		loadJar();
		return list;
	}

	public void loadJar(String className, String type, String value) throws Exception {
		File file = new File("C:\\Users\\NMAHESHBABU\\ebanking-0.0.1-SNAPSHOT.jar");
		URLClassLoader child = new URLClassLoader(new URL[] { file.toURI().toURL() }, this.getClass().getClassLoader());
		Class<?> classToLoad = Class.forName(className, true, child);
		boolean status = classToLoad.isAnnotationPresent(RequestMapping.class);
		StringBuffer data = new StringBuffer();

		StringBuffer data2 = new StringBuffer();
		if (status == true) {
			String[] data1 = ((RequestMapping) classToLoad.getAnnotation(RequestMapping.class)).value();
			data.append(data1[0].toString());
		}

		Method[] metho = classToLoad.getMethods();
		InsertMethods m = new InsertMethods();
		Arrays.asList(metho).forEach(met -> {
			boolean check = met.isAnnotationPresent(PostMapping.class);
			boolean check1 = met.isAnnotationPresent(GetMapping.class);
			boolean check2 = met.isAnnotationPresent(PutMapping.class);
			// boolean check3 = met.isAnnotationPresent(DeleteMapping.class);
			// boolean check4 = met.isAnnotationPresent(PatchMapping.class);
			if (check) {
				String[] name = met.getAnnotation(PostMapping.class).path();
				String[] sell = met.getAnnotation(PostMapping.class).value();

				Arrays.asList(sell).forEach(sel2 -> {
					String replacedVal = value.replace("\"", "");
					StringBuffer buff1 = new StringBuffer();
					buff1.append(data);
					String name2 = buff1.append(sel2.toString()).toString();
					int mid = replacedVal.lastIndexOf('/') + 1;
					String remin= replacedVal.substring(0, mid);
					String caseName = "";					
					if (name2.equals(replacedVal)) {
						try {
							caseName = controller.getTestCaseName();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						System.err.println("invoked post method name:- " + met.getName() + " "
								+ met.getDeclaringClass().getSimpleName());
						try {
							PreparedStatement st = m.insertData();
							st.setString(1, met.getName());
							st.setString(2, met.getDeclaringClass().getSimpleName());
							st.setString(3, caseName);
							st.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} else if (check1) {
				GetMapping name = met.getAnnotation(GetMapping.class);
				
				String[] sel = met.getAnnotation(GetMapping.class).value();
				
				Arrays.asList(sel).forEach(sel1 -> {
					String replacedVal = value.replace("\"", "");
					StringBuffer buff = new StringBuffer();
					buff.append(data);
					String name1 = buff.append(sel1.toString()).toString();
					String caseName = "";
					if (name1.equals(replacedVal)) {
						try {
							caseName = controller.getTestCaseName();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						try {
							caseName = controller.getTestCaseName();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						try {
							PreparedStatement st = m.insertData();
							st.setString(1, met.getName());
							st.setString(2, met.getDeclaringClass().getSimpleName());
							st.setString(3, caseName);
							st.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.err.println("invoked get method name:- " + met.getName() + " className:- "
								+ met.getDeclaringClass().getSimpleName());

					}

				});
			} else if (check2) {
				String[] name = met.getAnnotation(PutMapping.class).path();
				String[] sel = met.getAnnotation(PutMapping.class).value();
				Arrays.asList(sel).forEach(sel1 -> {
					String replacedVal = value.replace("\"", "");
					StringBuffer buff3 = new StringBuffer();
					buff3.append(data); // admin/
					String name3 = buff3.append(sel1.toString()).toString();
					String caseName = "";
					if (name3.contains(replacedVal)) {
						try {
							caseName = controller.getTestCaseName();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						try {
							PreparedStatement st = m.insertData();
							st.setString(1, met.getName());
							st.setString(2, met.getDeclaringClass().getSimpleName());
							st.setString(3, caseName);
							st.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.err.println("invoked put method name:- " + met.getName() + " className:- "
								+ met.getDeclaringClass().getSimpleName());
						buff3.setLength(0);
						data.setLength(0);

					}

				});
			}

		});
	}

}

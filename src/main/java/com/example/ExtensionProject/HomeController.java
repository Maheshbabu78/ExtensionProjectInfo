package com.example.ExtensionProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.CodeSource;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.JavaAgent;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.VirtualMachine;

@RestController
public class HomeController {
	
	

	HashMap<String, String> map = new HashMap<String, String>();

	@GetMapping("/api/saveQuery")
	public HashMap<String, String> data() throws Exception {
		
		StringBuilder html = new StringBuilder();
		FileReader fr = new FileReader("src/main/resources/templates/index.html");
		try {
			BufferedReader br = new BufferedReader(fr);
			boolean status = false;
			String val;
			while ((val = br.readLine()) != null) {
				if (val.contains("<span class='test-name'>")) {
					val.replaceAll("\\<(/?[^\\>]+)\\>", "\\ ").replaceAll("\\s+", " ").trim();
					status = true;
				}
				if (status) {
					html.append(val);
					if (val.contains("<span class='test-name'>")) {
						val.replaceAll("\\<(/?[^\\>]+)\\>", "\\ ").replaceAll("\\s+", " ").trim();
						status = false;
					}
				}
			}
			html.append(val);
			String result = html.toString();
			org.jsoup.nodes.Document document = Jsoup.parse(result);
			Elements divs = document.select("span");
			for (org.jsoup.nodes.Element div : divs) {
				result = div.ownText();
			}
			br.close();
			map.put("value", result);
			
			//System.out.println("test case is "+result);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("value", String.valueOf(html));
		return map;
	}
	
	public String getTestCaseName() throws FileNotFoundException {
		StringBuilder html = new StringBuilder();
		FileReader fr = new FileReader("src/main/resources/templates/index.html");
		String result="";
		try {
			BufferedReader br = new BufferedReader(fr);
			boolean status = false;
			String val;
			while ((val = br.readLine()) != null) {
				if (val.contains("<span class='test-name'>")) {
					val.replaceAll("\\<(/?[^\\>]+)\\>", "\\ ").replaceAll("\\s+", " ").trim();
					status = true;
				}
				if (status) {
					html.append(val);
					if (val.contains("<span class='test-name'>")) {
						val.replaceAll("\\<(/?[^\\>]+)\\>", "\\ ").replaceAll("\\s+", " ").trim();
						status = false;
					}
				}
			}
			html.append(val);
			result = html.toString();
			org.jsoup.nodes.Document document = Jsoup.parse(result);
			Elements divs = document.select("span");
			for (org.jsoup.nodes.Element div : divs) {
				result = div.ownText();
			}
			br.close();
			map.put("value", result);
			System.out.println("test case is "+result);
	}catch(Exception e) {
		e.printStackTrace();
	}
		return result;

}
}

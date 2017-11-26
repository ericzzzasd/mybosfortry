package com.itcast.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class TestF{
	
	@Test
	public void test() throws Exception{
		Configuration configuration=new Configuration(Configuration.VERSION_2_3_22);
		configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/template"));
		Template template = configuration.getTemplate("hellp.ftl");
		
		Map<String,String> map=new HashMap<>();
		map.put("title", "test");
		map.put("content", "这里是内容");
	
		template.process(map,new PrintWriter(System.out));
	}
	public static void main(String[] args) {
		String s="<img src=\"/bos_management/upload/eccaf186133f454e9d2843b1c72d539a.jpg\" width=\"200\" height=\"271\" alt=\"\" />";
		System.out.println(s.contains("<img src=\"/bos_management"));
		String replace = s.replace("<img src=\"/bos_management", "<img src=\"/http://localhost:8080/bos_management");
		System.out.println(replace);
		System.out.println(replace.contains("<img src=\"/bos_management"));
	}
	
	


}

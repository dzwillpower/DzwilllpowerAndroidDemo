package com.wits.dzwillpower.android.xmlparse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxService {
	public SaxService() {
		// TODO Auto-generated constructor stub
	}

	public static List<HashMap<String, String>> readXML(InputStream inputstream, String nodeName) {
		try {
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();// 解析xml
			MyHandler handler = new MyHandler(nodeName);
			parser.parse(inputstream, handler);
			inputstream.close();
			return handler.getList();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

}

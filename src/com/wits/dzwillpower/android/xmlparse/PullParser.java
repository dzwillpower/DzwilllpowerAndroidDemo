package com.wits.dzwillpower.android.xmlparse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * pull 解析的类
 * 
 * @author Administrator
 * 
 */
public class PullParser {
	public static List<Book> getBooks(InputStream inputStream, String encoding) throws Exception {
		List<Book> bookList = null;
		Book book = null;
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xmlParser = factory.newPullParser();
		xmlParser.setInput(inputStream, encoding);
		int eventType = xmlParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				bookList = new ArrayList<Book>();
				break;
			case XmlPullParser.START_TAG:
				if ("book".equals(xmlParser.getName())) {
					book = new Book();
					book.setId(Integer.parseInt(xmlParser.getAttributeValue(0)));
				} else if ("name".equals(xmlParser.getName())) {
					book.setName(xmlParser.nextText());
				} else if ("price".equals(xmlParser.getName())) {
					book.setPrice(Float.parseFloat(xmlParser.nextText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if ("book".equals(xmlParser.getName())) {
					bookList.add(book);
					book = null;
				}
				break;
			}
			eventType = xmlParser.next();
		}
		return bookList;
	}
}

/*
 * XmlPullParser.START_DOCUMENT（开始解析）；
 * 
 * XmlPullParser.START_TAG（开始元素）；
 * 
 * XmlPullParser.TEXT（解析文本）；
 * 
 * XmlPullParser.END_TAG（结束元素）；
 * 
 * XmlPullParser.END_DOCUMENT（结束解析）。
 */
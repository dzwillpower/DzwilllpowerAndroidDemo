package com.wits.dzwillpower.android.xmlparse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wits.dzwillpower.android.R;
import com.wits.dzwillpower.android.utilites.MyLog;

public class DomXmlParseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmlparse);
		Button buttonDomParse = (Button) findViewById(R.id.btnDomParse);
		Button buttonPullParse = (Button) findViewById(R.id.btnPullParse);
		Button buttonSAXParse = (Button) findViewById(R.id.btnSaxParse);
		buttonDomParse.setOnClickListener(new domParseListener());
		buttonPullParse.setOnClickListener(new PullParseListener());
		buttonSAXParse.setOnClickListener(new SaxParseListener());

	}

	public class domParseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			InputStream inputstream = DomXmlParseActivity.class.getClassLoader().getResourceAsStream("book.xml");
			try {
				List<Book> list = getBooks(inputstream);
				for (Book book : list) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public class PullParseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			InputStream inputstream = DomXmlParseActivity.class.getClassLoader().getResourceAsStream("book.xml");
			try {
				List<Book> listBooks = PullParser.getBooks(inputstream, "UTF-8");
				System.out.println(listBooks.size());
				for (Book book : listBooks) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public class SaxParseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			InputStream inputstream = DomXmlParseActivity.class.getClassLoader().getResourceAsStream("book.xml");
			try {
				List<HashMap<String, String>> list = SaxService.readXML(inputstream, "book");
				for (HashMap<String, String> map : list) {
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public List<Book> getBooks(InputStream inputstream) throws Exception {
		List<Book> list = new ArrayList<Book>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputstream);
		Element element = document.getDocumentElement();

		NodeList bookNodes = element.getElementsByTagName("book");
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Element bookElement = (Element) bookNodes.item(i);
			Book book = new Book();
			book.setId(Integer.parseInt(bookElement.getAttribute("id")));

			NodeList childNodes = bookElement.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {

				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if ("name".equals(childNodes.item(j).getNodeName())) {
						book.setName(childNodes.item(j).getFirstChild().getNodeValue());
					} else if ("price".equals(childNodes.item(j).getNodeName())) {
						book.setPrice(Float.parseFloat(childNodes.item(j).getFirstChild().getNodeValue()));
					}
				}
			}
			list.add(book);
		}

		return list;

	}

}

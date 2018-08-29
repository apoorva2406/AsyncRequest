package metadataextraction;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ning.http.client.Response;

public class MetadataStudy {

	public List<String> MetaStudyName(Response res){
		List<String>subjectArray = new ArrayList<String>();
		//File xmlFile = new File("/home/neeraj/mapreduce/Studies.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		String stringToParse;
		try {
			stringToParse = res.getResponseBody();
			int length = stringToParse.length();
			String stringCopy=stringToParse.substring(3, length);
			try {
				builder = factory.newDocumentBuilder();
				ErrorHandler handler = null ;
			     builder.setErrorHandler(handler);
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(stringCopy));
			    Document doc;
			    try {
					doc = builder.parse(is);
					NodeList nl = doc.getElementsByTagName("ProtocolName");
					for(int i=0;i<nl.getLength();i++){
						Node nameStudy = nl.item(i);
						System.out.println(nameStudy.getTextContent());
						subjectArray.add(nameStudy.getTextContent());
						
					}
					
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return subjectArray;
		
		
	}
	
	
}

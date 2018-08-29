package metadataextraction;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ning.http.client.Response;

public class MetadataVersionParse {

	public List<String> ParseMetadata (Response m){
		//File xmlFile = new File("/home/neeraj/mapreduce/Versions.xml");
		
		List<String>numberMetadataVersion = new ArrayList<String>();
		try {
			
			String xmlData = m.getResponseBody();
			int length = xmlData.length();
			String stringCopy=xmlData.substring(39, length);
			//System.out.println(stringCopy);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		
		DocumentBuilder builder;
	
		try {
			builder = factory.newDocumentBuilder();
			ErrorHandler handler = null ;
		      builder.setErrorHandler(handler);
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(stringCopy));
		      Document doc;
			try {
				doc = builder.parse(is);
				NodeList nl = doc.getElementsByTagName("MetaDataVersion");
		        
				for(int i=0;i<nl.getLength();i++){
					Node meta = nl.item(i);
					NamedNodeMap nmp = meta.getAttributes();
					//System.out.println(nmp.item(1).getNodeValue());
					numberMetadataVersion.add(nmp.item(1).getNodeValue());
				}
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
				
		      
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return numberMetadataVersion;
		
	}

	
}


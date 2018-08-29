package com.request.get;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ning.http.client.Response;


/*parsing the study info response used the XML InputFActory and returning the list of studies exists in Rave Web Service*/
public class StudyParse {
	
	public List<String> StudyName(Response res){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		List<String>subjectArray = new ArrayList<String>();
		try {
			XMLStreamReader reader;
			try {
				String stringToParse = res.getResponseBody();
				int length = stringToParse.length();
				String stringCopy=stringToParse.substring(3, length);
				//System.out.println(stringCopy);
				reader = factory.createXMLStreamReader(new ByteArrayInputStream(stringCopy.getBytes()));
				//reader = factory.createXMLStreamReader(new FileInputStream( "/home/neeraj/mapreduce/Studies.xml"));
				
				while(reader.hasNext()){
					int event = reader.next();
					switch(event){
					case XMLStreamConstants.START_ELEMENT:
						if ("Study".equals(reader.getLocalName())){
							subjectArray.add(reader.getAttributeValue(0));
							}

					}
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return subjectArray;
		
		
	}

}

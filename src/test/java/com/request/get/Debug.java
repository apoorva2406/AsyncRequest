package com.request.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kafka.javaapi.producer.Producer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class Debug {
	public static void main(String []args) {
		Configuration configuration = new Configuration();
		  //2. URI of the file to be read
		  URI uri;
		try {
			uri = new URI("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121");
			
			 //3. Get the instance of the HDFS 
			  FileSystem hdfs = FileSystem.get(uri, configuration);
			  //FileSystem fileSystem = FileSystem.get(location.toUri(), configuration);
			  FileStatus[] status = hdfs.listStatus(new Path("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121/"));
			  System.out.println(status.length);
			  
			  for (int i=0;i<status.length;i++){
                  BufferedReader br=new BufferedReader(new InputStreamReader(hdfs.open(status[i].getPath())));
                  //String line;
                  //System.out.println(status[i].getPath());
                  //System.out.println(i);
               String xml=br.readLine();
       		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		factory.setValidating(true);
    		factory.setNamespaceAware(true);
    		SimpleDateFormat formatter = new SimpleDateFormat(
    				"yyyy-MM-dd'T'HH:mm:ss");
    		SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");
    
    		//collector.ack(input);
    		//System.out.println("AfterInput");
    	
    		try {
    			DocumentBuilder builder = factory.newDocumentBuilder();
    			ErrorHandler handler = null;
    			builder.setErrorHandler(handler);
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(xml));
    			try {
    				Document doc = builder.parse(is);
    				String fileOID=((Element)((NodeList)doc.getElementsByTagName("ODM")).item(0)).getAttribute("FileOID");
    				//if(fileOID.equals("86a011e6-1fe9-45a7-8fbf-0ac18f8ceea8"))
    				//	{System.out.println(status[i].getPath());}
    				
    				NodeList clinicalData = doc
    						.getElementsByTagName("ClinicalData");
    				//System.out.println(clinicalData.getLength());
    				for (int j = 0; j < clinicalData.getLength(); j++) {
    					Element clinical = (Element) clinicalData.item(j);
    				    if(((Element)clinical.getElementsByTagName("SiteRef").item(0)).getAttribute("LocationOID").equals(""))
    				    	System.out.println(clinical.getElementsByTagName("SourceID").item(0).getTextContent());
    				}
    				//System.out.println(fileOID);
    			} catch (SAXException | IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				//collector.fail(input);
    			}
    			
    		} catch (ParserConfigurationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			//collector.fail(input);
    		}
             // System.out.println(xml);    
       	   ProducerSpeedLayer pl = new ProducerSpeedLayer();
			Producer<String,String>p;
			p=pl.SetProducerProperties();
			String newLineRemoveResponse = xml;
			//pl.AddData(p, newLineRemoveResponse);
			  }

					
		  	} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
}
	

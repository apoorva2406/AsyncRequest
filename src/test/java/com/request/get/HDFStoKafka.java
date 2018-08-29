package com.request.get;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kafka.common.FailedToSendMessageException;
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

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class HDFStoKafka {
	  private static  Document parseXmlFile(String in) {
	    	
	    	//in = removeUTF8BOM(in);
	    	//logger.info("xml contents after removing BOM "+in);
	        try {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            InputSource is = new InputSource(new StringReader(in));
	            return db.parse(is);
	        } catch (ParserConfigurationException e) {
	            throw new RuntimeException(e);
	        } catch (SAXException e) {
	            throw new RuntimeException(e);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	
	public static void main(String []args) throws InterruptedException {
		Configuration configuration = new Configuration();
		  //2. URI of the file to be read
		  URI uri;
		try {
			//uri = new URI("hdfs://172.16.1.64:9000/gilead/GS-US-312-0118(DEV)/" + "Audit_012-26-2014-02-50-40-PM.xml");
			uri = new URI("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121");
			 //3. Get the instance of the HDFS 
			  FileSystem hdfs = FileSystem.get(uri, configuration);
			  //FileSystem fileSystem = FileSystem.get(location.toUri(), configuration);
			  FileStatus[] status = hdfs.listStatus(new Path("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121"));
			 
			  System.out.println(status.length);
			  
			  for (int i=0;i<status.length;i++){
				 
				  String iS= String.valueOf(i);
				 System.out.println(i);
				  BufferedReader br=new BufferedReader(new InputStreamReader(hdfs.open(status[i].getPath())));
				  String abc1="";
				  String add="";
				   abc1=br.readLine();
				  String []clinicalElement = abc1.split("</ClinicalData>");
				  String []abc = abc1.split("xmlns:mdsol=\"http");
				   add=abc[0].concat("xmlns:mdsol=\"http://www.mdsol.com/ns/odm/metadata\">\"");
				 // System.out.println(add);
				   
					
				   
				   if(clinicalElement.length<1000)
				   {
					   ProducerSpeedLayer pl = new ProducerSpeedLayer();
						Producer<String,String>p;
						p=pl.SetProducerProperties();
						pl.AddData(p, abc1 );
					
						File file = new File("/home/anmol/Documents/oncologyStudy/"+i+".xml");
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(abc1);
						bw.close();
				   }
				   else
				   {int k=0;
				    int d=0;
					while( k<clinicalElement.length-1 )	
					{
						if(k+1000<clinicalElement.length ) d = k+1000;
						else d = clinicalElement.length;
					
						
							int j=k;
							int t=j;
							if(j>0)
							clinicalElement[j]=add+clinicalElement[j];
							
							clinicalElement[t]=clinicalElement[t].concat("</ClinicalData>");
						for(j=j+1; j< d-1; j++)
							clinicalElement[t]=clinicalElement[t].concat(clinicalElement[j]).concat("</ClinicalData>");
						  
						
						  
					if(j==clinicalElement.length-1)
					{	
						System.out.println(j);
						System.out.println(clinicalElement.length);
						clinicalElement[t]=clinicalElement[t].concat(clinicalElement[j]);
						j++;
					}
					else  
						{
						clinicalElement[t]=clinicalElement[t].concat(clinicalElement[j]).concat("</ClinicalData>");
						clinicalElement[t]=clinicalElement[t].concat("</ODM>");
						System.out.println(clinicalElement.length);
						j++;
						}
							  
							k=j;
							File file = new File("/home/anmol/Documents/oncologyStudy/"+i+"-"+t+".xml");
							FileWriter fw = new FileWriter(file.getAbsoluteFile());
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(clinicalElement[t]);
							bw.close();
							 ProducerSpeedLayer pl = new ProducerSpeedLayer();
							Producer<String,String>p;
							p=pl.SetProducerProperties();
							pl.AddData(p, clinicalElement[t] );
					}
			  }
				   
				   
				
		/*				
				  for(int j=0;j<clinicalElement.length;j++){
					
					 // System.out.println(clinicalElement.length);
					 // System.out.print(j+" ");
					
					  if(j>0){
						  clinicalElement[j]=add+clinicalElement[j];
					  }
					  clinicalElement[j]=clinicalElement[j].concat("</ClinicalData>");
					  clinicalElement[j]=clinicalElement[j].concat("</ODM>");
					 // System.out.println(clinicalElement[j]);
					  String newLineRemoveResponse = clinicalElement[j];
					  try{
					  ProducerSpeedLayer pl = new ProducerSpeedLayer();
						Producer<String,String>p;
						p=pl.SetProducerProperties();
						pl.AddData(p, newLineRemoveResponse );
						 }
				  catch(FailedToSendMessageException e){
					  e.printStackTrace(); 
					  Thread.sleep(5000);
					  j=j-1;
				  }
						//p.close();
					  
				  }
					 */
				 // Thread.sleep(10000);
				  
				  
				 
				  
				  
                /*  BufferedReader br=new BufferedReader(new InputStreamReader(hdfs.open(status[i].getPath())));
                  //String line;
                  //System.out.println(status[i].getPath());
                  //System.out.println(i);
                  System.out.println(i +" "+ status[i].getPath());
                String xml = br.readLine();
                String xml1;
               while(((xml1= br.readLine()) !=(null)))
            		   {
            	   xml = xml.concat(xml1);
            		   }
            		//System.out.println(xml);   
           
           
              // System.out.println(xml2);
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
    				//String fileOID=((Element)((NodeList)doc.getElementsByTagName("ODM")).item(0)).getAttribute("FileOID");
    				//if(fileOID.equals("c7229-ba7c-4e17-a930-1aa7d428dfb1"))
    				//	{System.out.println(status[i].getPath());}
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
			pl.AddData(p, newLineRemoveResponse);
			*/
			  
			  }	
		  	} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
		}
	

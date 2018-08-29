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

public class CopyOfHDFStoKafka {

	
	public static void main(String []args) throws InterruptedException {
		Configuration configuration = new Configuration();
		  //2. URI of the file to be read
		  URI uri;
		try {
		
			uri = new URI("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121");
		    FileSystem hdfs = FileSystem.get(uri, configuration);
		    FileStatus[] status = hdfs.listStatus(new Path("hdfs://172.16.1.64:9000/gileadSandBox/GS-US-337-0121"));
			System.out.println(status.length);
			  
			  for (int i=0;i<1;i++){
				 System.out.println(i);
				 BufferedReader br=new BufferedReader(new InputStreamReader(hdfs.open(status[i].getPath())));
				 String abc1="";
				 abc1=br.readLine();
				 System.out.print(abc1);
			  }	
		  	} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
}
	

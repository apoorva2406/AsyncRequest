package com.request.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.client.AuthenticationException;

public class HttpRequest1 {
	
public static String fileLocationHDFS(String url,String directory){
		
		String [] urlSplit = url.split("/");
		String [] directoryName=urlSplit[2].split("\\.");
		
		String [] fileNameSplit = urlSplit[5].split("=");
		
		String [] fileName = fileNameSplit[1].split("&");
		String study = fileName[0].replaceAll("\\(", "-");
		String studies = study.replaceAll("\\)","");
		
		String study1 = studies.replaceAll("%28", "-");
		String studies1 = study1.replaceAll("%29","");
		
		return directory+"/"+studies1+"/";
		
		
	}
	
	public static String studyName(String url){
		
		String [] urlSplit = url.split("/");
		
		
		String [] fileNameSplit = urlSplit[5].split("=");
		
		String [] fileName = fileNameSplit[1].split("&");
		String study = fileName[0].replaceAll("\\(", "-");
		String studies = study.replaceAll("\\)","");
		
		String study1 = studies.replaceAll("%28", "-");
		String studies1 = study1.replaceAll("%29","");
		return studies1;
		
		
	}
	public static String getStartId(String url){
		
		String startId="";
		String [] abc1 = url.split("&");
		
		if(abc1.length==3) {
			String []abc2 = abc1[2].split("=");
			startId=abc2[1];
			//System.out.println(abc2[1]);
		}
		
		return startId;
	}

	public static int DataIngest(String multipleResponse,
			String fileLocation,String startId) {
		AuthenticatedURL authenticatedURL = new AuthenticatedURL();
		AuthenticatedURL.Token token = new AuthenticatedURL.Token();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss-a");
		//System.out.println("Neerajjjjjjjjjjjjj");
		try {
			String formattedDate = sdf.format(date);

			String AuditLog = "Audit_";
		
			// String url = (String)parameters.get(7) + AuditLog + dummy +
			// formattedDate.toString() + ".xml" +
			// "?user.name=hduser&op=CREATE";

			// HttpURLConnection conn = authenticatedURL.openConnection(new
			// URL(url), token);
			String dataNodeUrl = "http://172.16.1.64:50075/webhdfs/v1/"+fileLocation
					+ AuditLog+"_"
					+ startId
					+ ".xml"
					+ "?op=CREATE&user.name=hduser&namenoderpcaddress=172.16.1.64:9000&overwrite=false";
			HttpURLConnection conn = authenticatedURL.openConnection(new URL(
					dataNodeUrl), token);
			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.connect();

			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			String newLineRemoveResponse = multipleResponse
					.replaceAll("\\n", "");
			out.write(newLineRemoveResponse);

			out.close();
			
			conn.disconnect();
			return conn.getResponseCode();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public static void main(String []args){
		String url ="https://gileadsandbox.mdsol.com/RaveWebServices/datasets/ClinicalAuditRecords.odm?studyoid=GS-US-337-0121&per_page=100000&startid=701104367";
		do{
		
		URL obj;
		try {
			String userPassword = "vsrivastava" + ":" + "vinay@kumar8";
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			obj = new URL(url);
			try {
				System.out.println("\nSending 'GET' request to URL : " + url);
				HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
				con.setRequestProperty("Authorization", "Basic " + encoding);
				con.setConnectTimeout(60000000);
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
		 
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
		 
				//print result
				//System.out.println(response.toString());
				//System.out.println(con.getHeaderField("Link"));
				String fileLoca=fileLocationHDFS(url,"gileadSandBox");
				String sourceId = getStartId(url);
				int resCode =DataIngest(response.toString(),fileLoca,sourceId);
				System.out.println(resCode);
				
				String link = con.getHeaderField("Link");
				String [] linkArray = link.split(";");
				String finalLink = linkArray[0].substring(1,
						linkArray[0].length() - 1);
				System.out.println(finalLink);
				url =finalLink;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}while(true);
	}
}
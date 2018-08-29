package com.request.get;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL.Token;

public class WebHdfsGetRequest {
	public static void main(String []args) throws AuthenticationException{
	AuthenticatedURL authenticatedURL = new AuthenticatedURL();
	//url = new URL("http://172.16.1.151:50070/webhdfs/v1/tmp/webhdfs2?user.name=hduser&op=MKDIRS");
	//HttpURLConnection httpCon;
	Token token = new AuthenticatedURL.Token();
	try {
		HttpURLConnection conn;
		try {
			//conn = authenticatedURL.openConnection(new URL("http://172.16.1.218:50070/webhdfs/v1/webhdfs57?user.name=hduser&op=MKDIRS"), token);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss-a");
			String formattedDate = sdf.format(date);
			//System.out.println(i);
			String AuditLog= "File_Audit_Log";
			int i=0;
			String dummy = Integer.toString(i);
			
			
			conn = authenticatedURL.openConnection(new URL("http://172.16.1.64:50070/webhdfs/v1/wincere/Counter?op=OPEN&user.name=hduser"), token);
			
			conn = authenticatedURL.openConnection(new URL("http://172.16.1.64:50075/webhdfs/v1/wincere/Counter?op=OPEN&user.name=hduser&namenoderpcaddress=172.16.1.64:9000&offset=0"), token);
			conn = authenticatedURL.openConnection(new URL("http://172.16.1.64:50070/explorer.html#/wincere/RAVE SDBE(DEV)/Audit_112-24-2014-07-05-37-PM.xml"), token);
			
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.connect();
			
			
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = reader.readLine();
			while (!line.equals(null) ) {
				
				 System.out.println(line);
				 line = reader.readLine();
				 if(line.equals(null)) break;
			}
			reader.close();
			is.close();
			System.out.println(line);
			 System.out.println(conn.getResponseCode());
			conn.disconnect();
		} catch (NullPointerException  e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		//resp = result(conn, true);
		
		//httpCon.connect();
		//OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		//out.write("string=" );
		//out.close();
		
		//httpCon.setRequestMethod("DELETE");
		//httpCon.connect();	
			
	} catch (IOException e ) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("--111---------------");
	}
	}
}

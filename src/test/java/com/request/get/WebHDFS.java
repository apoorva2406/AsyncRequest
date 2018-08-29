package com.request.get;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL.Token;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.apache.hadoop.security.authentication.client.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebHDFS {
	
	public static void  result(HttpURLConnection conn, boolean input)
			throws IOException {
		 StringBuffer sb = new StringBuffer();
		if (input) {
			//InputStream  is = new FileInputStream("/home/neeraj/mapreduce/input.log");
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
		
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
			}
			reader.close();
			is.close();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", conn.getResponseCode());
		result.put("mesg", conn.getResponseMessage());
		result.put("type", conn.getContentType());
		result.put("data", sb);
		//
		// Convert a Map into JSON string.
		//
		

		//
		// Convert JSON string back to Map.
		//
		// Type type = new TypeToken<Map<String, Object>>(){}.getType();
		// Map<String, Object> map = gson.fromJson(json, type);
		// for (String key : map.keySet()) {
		// System.out.println("map.get = " + map.get(key));
		// }
		
		
	}
	
	protected static long copy(InputStream input, OutputStream result)
			throws IOException {
		byte[] buffer = new byte[12288]; // 8K=8192 12K=12288 64K=
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			result.write(buffer, 0, n);
			count += n;
			result.flush();
		}
		result.flush();
		return count;
	}
		public static void main (String []args){
			
		
			AuthenticatedURL authenticatedURL = new AuthenticatedURL();
			//url = new URL("http://172.16.1.151:50070/webhdfs/v1/tmp/webhdfs2?user.name=hduser&op=MKDIRS");
			//HttpURLConnection httpCon;
			Token token = new AuthenticatedURL.Token();
			try {
				HttpURLConnection conn;
				try {
					conn = authenticatedURL.openConnection(new URL("http://172.16.1.151:50070/webhdfs/v1/webhdfs23/webhdfs.txt?user.name=hduser&op=CREATE"), token);
					conn.setRequestMethod("PUT");
					conn.setDoOutput(true);
					conn.connect();
					//InputStream  is = new FileInputStream("/home/neeraj/mapreduce/input.log");
					//OutputStream os = conn.getOutputStream();
					//copy(is, os);
					//is.close();
					
					//os.close();
					
					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
					out.write("abc");
					out.close();
					//result(conn, true);
					 System.out.println(conn.getResponseCode());
					conn.disconnect();
				} catch (AuthenticationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//resp = result(conn, true);
				
				//httpCon.connect();
				//OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
				//out.write("string=" );
				//out.close();
				
				//httpCon.setRequestMethod("DELETE");
				//httpCon.connect();	
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("--111---------------");
			}
			
		}
}

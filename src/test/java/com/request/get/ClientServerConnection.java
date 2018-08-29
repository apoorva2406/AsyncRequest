package com.request.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class ClientServerConnection {
	public static void main(String []args){
		
		InetAddress host;
		int port = 1234;
		try {
			host= InetAddress.getByName("anmol-Dell");
			try {
				Socket client = new Socket(host, port);
				String userInput;
				  BufferedReader stdIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
				  System.out.println("Response from server:");
				  while ((userInput = stdIn.readLine()) != null) {
			            System.out.println(userInput);
			        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

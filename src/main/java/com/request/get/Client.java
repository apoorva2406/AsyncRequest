package com.request.get;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {
	public static void main(String []args){
		InetAddress host;
		int port = 1236;
		try {
			host= InetAddress.getByName("neeraj-Vostro-2520");
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
				System.out.println("Server started at port 1234");
				int portNumber = 1234;
				try {
					ServerSocket socketServer = new ServerSocket(portNumber);
					Socket client = socketServer.accept();
					//Socket client = new Socket(host, portNumber);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

}

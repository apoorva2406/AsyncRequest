package com.request.get;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ServerSocketConnection {
	
	public static void start(int portNumber){
		System.out.println("Starting the socket server at port:" + portNumber);
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("Waiting for clients...");
			
			Socket client = serverSocket.accept();
		
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			writer.write("Hello. You are connected to a Socket Server. What is your name?");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String []args){
		int portNumber = 1234;
		start(portNumber);
		
		


	}
}

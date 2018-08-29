package com.request.get;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Multithreading {
	private static ServerSocket serverSocket;
	private static final int PORT = 1234;
	
	public static void main(String []args){
		
		try {
			InetAddress host = InetAddress.getLocalHost();
			serverSocket = new ServerSocket(PORT,1,host);
			do
			{
			//Wait for client...
			Socket client = serverSocket.accept();
			System.out.println("\nNew client accepted.\n");
	
			ClientHandler handler =new ClientHandler(client);
			handler.start();
			}while (true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

}

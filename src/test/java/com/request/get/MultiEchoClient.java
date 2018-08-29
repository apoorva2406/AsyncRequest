package com.request.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MultiEchoClient {
	private static InetAddress host;
	private static final int PORT = 1234;
	
	private static void sendMessages(){
		Socket socket = null;
		try {
			socket = new Socket(host,PORT);
			Scanner networkInput =new Scanner(socket.getInputStream());
			PrintWriter networkOutput =new PrintWriter(socket.getOutputStream(),true);
			
			Scanner userEntry = new Scanner(System.in);
			String message, response;
			
			do
			{
			System.out.print(
			"Enter message ('QUIT' to exit): ");
			message = userEntry.nextLine();
	
			networkOutput.println(message);
			response = networkInput.nextLine();
			
			System.out.println(
			"\nSERVER> " + response);
			}while (!message.equals("QUIT"));

		} catch (IOException e) {
		
			e.printStackTrace();
		}
		finally{
			try{
				System.out.println("\nClosing connection...");
				socket.close();
			}catch(IOException ioEx){
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}


	}

	public static void main(String[] args){
		try{
			host = InetAddress.getLocalHost();
		}catch(UnknownHostException uhEx){
			System.out.println("\nHost ID not found!\n");
			System.exit(1);
		}
		sendMessages();
	}

}

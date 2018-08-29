package com.request.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	public ClientHandler(Socket socket){
		client = socket;
		try {
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(),true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public void run(){
		String received;

		do
		{
		//Accept message from client on
		//the socket's input stream...
		received = input.nextLine();
		//Echo message back to client on
		//the socket's output stream...
		output.println("ECHO: " + received);
		//Repeat above until 'QUIT' sent by client...
		}while (!received.equals("QUIT"));
		try{
			if (client!=null){
				System.out.println("Closing down connection...");
				client.close();
			}
		}catch(IOException ioEx){
			System.out.println("Unable to disconnect!");
		}

	}
}

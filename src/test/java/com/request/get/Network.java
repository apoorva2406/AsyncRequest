package com.request.get;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Network {

	public static void main(String []args){
		
		try {
			Scanner stdin = new Scanner(System.in);
			InetAddress address;
			String host;
			host = stdin.next();
			
			System.out.println(InetAddress.getByName(host));
			System.out.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

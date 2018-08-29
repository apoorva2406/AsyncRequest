package com.request.get;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
	private static final String HOST = "172.16.1.151";
	
	public static void main(String []args){
	
		 try {
			Registry registry = LocateRegistry.getRegistry("172.16.1.171",2000);
			 try {
				Hello stub = (Hello)registry.lookup("HELLO");
				String response = stub.getGreetings();
				System.out.println(response);
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	}
}

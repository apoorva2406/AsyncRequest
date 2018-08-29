package com.request.get;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
	private static final String host ="172.16.1.151";
	
	public static void main(String []args){
		//System.setProperty("java.rmi.server.hostname", "172.16.1.151");
		 try {
			HelloImp1 obj = new HelloImp1();
			
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("HELLO", obj);
			System.out.println("Server Ready");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}

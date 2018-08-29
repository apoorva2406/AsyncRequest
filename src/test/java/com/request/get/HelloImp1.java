package com.request.get;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImp1 extends UnicastRemoteObject implements Hello{

	protected HelloImp1() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getGreetings() throws RemoteException {
		
		return ("Hello There");
	}

}

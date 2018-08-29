package com.request.get;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote{
	public String getGreetings() throws RemoteException;
}

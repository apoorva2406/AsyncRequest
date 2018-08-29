package com.request.get;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class ServerSocketChannelConnection {
	public static  void main (String []args){
		try {
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

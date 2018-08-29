package com.request.get;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientNonBlocking {
	
	public static void main(String []args){
		try {
			SocketChannel client = SocketChannel.open();
			client.configureBlocking(false);
			client.connect(new java.net.InetSocketAddress("172.16.1.151",8000));
			Selector selector = Selector.open();
			SelectionKey clientKey = client.register(selector, SelectionKey.OP_CONNECT);
			
			while(selector.select(500)>0){
				Set keys = selector.selectedKeys();
				System.out.println(keys.size());
				Iterator i = keys.iterator();
				while (i.hasNext()) {
				    SelectionKey key = (SelectionKey)i.next();
				    i.remove();
				    SocketChannel channel = (SocketChannel)key.channel();
				    if(key.isConnectable()){
				    	System.out.println("Server Found");
				    	
				    	if(channel.isConnectionPending()){
				    		System.out.println("---------------->");
				    		channel.finishConnect();
				    		 ByteBuffer buffer = null;
				    		 //for (;;) {
				    		        buffer = 
				    		          ByteBuffer.wrap(
				    		            new String(" Client " + "89" + " ").getBytes());
				    		        channel.write(buffer);
				    		        buffer.clear();
				    		   //   }
				    	}
				    }
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

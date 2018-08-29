package com.request.get;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class ServerNonBlocking {

	public static void main (String []args){
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);   // for non blocking
			server.socket().bind(new java.net.InetSocketAddress("172.16.1.151",8000));
			System.out.println("Server active at port 8000");
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			
			while (true){
				selector.select();
				Set keys =selector.selectedKeys();
				Iterator i = keys.iterator();
				while(i.hasNext()){
					SelectionKey key = (SelectionKey) i.next();
					i.remove();
					if(key.isAcceptable()){
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						client.register(selector,SelectionKey.OP_READ );
						System.out.println("*************");
						continue;
					}
					if(key.isReadable()){
						SocketChannel client = (SocketChannel) key.channel();
						int BUFFER_SIZE = 32;
						ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
						 client.read(buffer);
						 buffer.flip();
						 Charset charset=Charset.forName("ISO-8859-1");
						 CharsetDecoder decoder = charset.newDecoder();
						 CharBuffer charBuffer = decoder.decode(buffer);
						 System.out.print(charBuffer.toString());
						 continue;
						 
					}
					
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.request.get;

import org.apache.commons.math3.random.*;


public class Random {
	//RandomDataImpl random = new RandomDataImpl();
	public static void main(String []args){
		RandomData random = new RandomDataImpl();
		for(int i=0;i<10;i++){
		System.out.println(	random.nextInt(0, 10));
		}
	}
}

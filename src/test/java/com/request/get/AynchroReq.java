package com.request.get;

import com.ning.http.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
public class AynchroReq {
	
	
		public static void main(String []args){
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			String [] urls= new String[30];
			urls[0]="https://wincere.mdsol.com/RaveWebServices/studies";
			urls[1]="https://wincere.mdsol.com/RaveWebServices/metadata/studies";
			urls[2]="https://wincere.mdsol.com/RaveWebServices/metadata/studies/RAVE%20SDBE/versions";
			Realm realm = new Realm.RealmBuilder()
            .setPrincipal("RWS_IO1")
            .setPassword("Wincere100")
            .build();
			//Future<Response> f;
			List<Future<Response>> f = new ArrayList<Future<Response>>();
			Future<Response> abc;
			Future<Response> r;
			int i=0;
				try {
					for (i=0;i<3;i++){
					abc=asyncHttpClient.prepareGet(urls[i]).setRealm(realm).execute();
					f.add(i, abc);
					}
					for(i=0;i<3;i++){
						
						r= f.get(i);
						Response d;
						try {
							d=r.get();
							System.out.println(d.getResponseBody());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				
				asyncHttpClient.closeAsynchronously();
		}
	
}

package com.request.get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kafka.javaapi.producer.Producer;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;

public class MultipleAssynRequests {

	/*This code uses asysnchronous HTTP client on each studies existed in Rave web service
	 *  and sending request simultaneously and after getting the response we are calling a class(webHdfs.DataIngest(finalResponse,j);) 
	 *  that sends data directly to the hdfs and if the data cant be pulled in 6000 ms from any webservice 
	 *  it will start pulling the data from the same url next time and so we dont have any loss of 
	 *  information*/
	
	public NextUrl AssynchronousRequest(List<String>studyUrl,String username,String password){
		NextUrl nextUrlInfo = new NextUrl();
		WebServiceToHDFS webHdfs = new WebServiceToHDFS();
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		Realm realm = new Realm.RealmBuilder()
        .setPrincipal(username)
        .setPassword(password)
        .build();
		List<Future<Response>> multipleFuture = new ArrayList<Future<Response>>();
		Future<Response> singleFuture;
		Response finalResponse = null;
		Future<Response> r;
		String []linkArray = new String [10];

		
		int flag=0;
			try {
				for(int i=0;i<studyUrl.size();i++){
					System.out.println(studyUrl.get(i));
				singleFuture=asyncHttpClient.prepareGet(studyUrl.get(i)).setRealm(realm).execute();
				multipleFuture.add(i, singleFuture);
				//System.out.println("");
				}
			
				for(int j=0;j<multipleFuture.size();j++){
					System.out.println(multipleFuture.size());
					flag=0;
						r=multipleFuture.get(j);
						try {
							
							finalResponse=r.get();
							
							
						} catch (InterruptedException | ExecutionException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							//System.out.println(".............");
							System.out.println("cannot fetch the data from study at index"+j+" will fetch the data in next iteration");
							nextUrlInfo.nextUrl.add(j, studyUrl.get(j));
							//System.out.println(nextUrlInfo.nextUrl.get(j));
							 flag=1;
						}
						
						
						
						try {
							String link=finalResponse.getHeader("Link");
							linkArray = link.split(";");
							String finalLink = linkArray[0].substring(1,linkArray[0].length()-1);
							//System.out.println(finalLink);
							if(flag==0){
							nextUrlInfo.nextUrl.add(j, finalLink);
							//webHdfs.DataIngest(finalResponse,j);
							System.out.println(finalResponse.getStatusCode());
							ProducerSpeedLayer pl = new ProducerSpeedLayer();
							Producer<String,String>p;
							p=pl.SetProducerProperties();
							String newLineRemoveResponse = finalResponse.getResponseBody().replaceAll("\\n", "");
							pl.AddData(p, newLineRemoveResponse);
							}
							
						} catch (NullPointerException npe) {
							if(flag==0){
							nextUrlInfo.nextUrl.add(j, studyUrl.get(j));
							//System.out.println(studyUrl.get(j));
						    nextUrlInfo.reachedend.add(j, 1);
							}
						}
						
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		asyncHttpClient.closeAsynchronously();
		return nextUrlInfo;
		
	}


}

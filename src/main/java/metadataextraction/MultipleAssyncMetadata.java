package metadataextraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kafka.javaapi.producer.Producer;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;
import com.request.get.Ingestor;
import com.request.get.ProducerSpeedLayer;

public class MultipleAssyncMetadata {
	
	public void FetchMetadata(List<String>metadataUrl){
		WebHdfsMetadata webToHdfs = new WebHdfsMetadata();
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		Realm realm = new Realm.RealmBuilder()
        .setPrincipal(Ingestor.username)
        .setPassword(Ingestor.password)
        .build();
		List<Future<Response>> multipleFuture = new ArrayList<Future<Response>>();
		Future<Response> singleFuture;
		Response finalResponse = null;
		Future<Response> r;
		for(int i=0;i<metadataUrl.size();i++){
			System.out.println(metadataUrl.get(i));
		try {
			singleFuture=asyncHttpClient.prepareGet(metadataUrl.get(i)).setRealm(realm).execute();
			multipleFuture.add(i, singleFuture);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//System.out.println("");
		}
		for(int j=0;j<multipleFuture.size();j++){
			System.out.println(multipleFuture.size());
				r=multipleFuture.get(j);
					
					try {
						finalResponse=r.get();
						//webToHdfs.DataIngest(finalResponse, j);
						ProducerMetadata pl = new ProducerMetadata();
						Producer<String,String>p;
						p=pl.SetProducerProperties();
						
						try {
							String newLineRemoveResponse = finalResponse.getResponseBody().replaceAll("\\n", "");
							pl.AddData(p, newLineRemoveResponse);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
	}

}

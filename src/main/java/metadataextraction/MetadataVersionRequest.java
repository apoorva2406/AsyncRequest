package metadataextraction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;
import com.request.get.Ingestor;

public class MetadataVersionRequest {

	public Response MetadataRequest(String ArrayName){
		String url = null;
		//System.out.println(ArrayName);
		if(ArrayName.equals("RAVE SDBE"))
			url = Ingestor.protocol+Ingestor.domain+Ingestor.WebService+"/metadata"+"/studies/"+"RAVE%20SDBE"+"/versions";
		
		else url = Ingestor.protocol+Ingestor.domain+Ingestor.WebService+"/metadata"+"/studies/"+ArrayName+"/versions";
		
		System.out.println(url);
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		Realm realm = new Realm.RealmBuilder()
		.setPrincipal(Ingestor.username)
		.setPassword(Ingestor.password)
		.build();
		Response MetadataResponse=null;
		Future<Response> f;
		try {
			f=asyncHttpClient.prepareGet(url).setRealm(realm).execute();
			try {
				MetadataResponse=f.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		asyncHttpClient.closeAsynchronously();
	return MetadataResponse;
		
		
	}
}

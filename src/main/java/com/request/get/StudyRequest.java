package com.request.get;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Realm;
import com.ning.http.client.Response;
/*getting the response from study info url after hitting the Rave web Service*/
public class StudyRequest {
	
	public Response StudyData(String username,String password){
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		String url ="https://wincere.mdsol.com/RaveWebServices/studies";
		Realm realm = new Realm.RealmBuilder()
		.setPrincipal(username)
		.setPassword(password)
		.build();
		Response StudyResponse=null;
		Future<Response> f;
		try {
			f=asyncHttpClient.prepareGet(url).setRealm(realm).execute();
			try {
				StudyResponse=f.get();
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
	return StudyResponse;
	}

}

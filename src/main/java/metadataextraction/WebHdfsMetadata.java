package metadataextraction;






import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL.Token;

import com.ning.http.client.Response;
import com.request.get.Ingestor;

public class WebHdfsMetadata {
	public void DataIngest(Response multipleResponse, int i){
		AuthenticatedURL authenticatedURL = new AuthenticatedURL();
		Token token = new AuthenticatedURL.Token();
		HttpURLConnection conn;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss-a");
		
		try {
			
				String formattedDate = sdf.format(date);
				//System.out.println(i);
				String AuditLog= "File_Metadata";
				String dummy = Integer.toString(i);
				String url = Ingestor.HDFSpath1+AuditLog+dummy+formattedDate.toString()+".xml"+"?user.name=hduser&op=CREATE";
				//System.out.println(url);
				conn = authenticatedURL.openConnection(new URL(url), token);
				conn.setRequestMethod("PUT");
				conn.setDoOutput(true);
				conn.connect();

				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				String newLineRemoveResponse = multipleResponse.getResponseBody().replaceAll("\\n", "");
				out.write(newLineRemoveResponse );
				//out.write("abc");
				out.close();
				System.out.println(conn.getResponseCode());
				conn.disconnect();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*public static void main(String []args){
		WebServiceToHDFS webHdfs= new WebServiceToHDFS();
		webHdfs.DataIngest(null);
	}*/

}

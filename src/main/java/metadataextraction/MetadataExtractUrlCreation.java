package metadataextraction;

import java.util.ArrayList;
import java.util.List;

import com.request.get.Ingestor;

public class MetadataExtractUrlCreation {
	
	public List<String> MetadataUrl (List<String>Versions,String StudyName){
		
		List<String>metadataVersionUrl = new ArrayList<String>();
		String url= null;
		for(int i=0;i<Versions.size();i++){
			if(StudyName.equals("RAVE SDBE")){
				url = Ingestor.protocol+Ingestor.domain+Ingestor.WebService+"/metadata"+"/studies/"+"RAVE%20SDBE"+"/versions/"+Versions.get(i);
				metadataVersionUrl.add(url);
				System.out.println(url);
			}
			else{
				 url = Ingestor.protocol+Ingestor.domain+Ingestor.WebService+"/metadata"+"/studies/"+StudyName+"/versions/"+Versions.get(i);
				 metadataVersionUrl.add(url);
				 System.out.println(url);
			}
		}
		return metadataVersionUrl;
		
	}

}

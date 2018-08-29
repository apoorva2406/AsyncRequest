package metadataextraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ning.http.client.Response;
import com.request.get.Ingestor;
import com.request.get.StudyParse;
import com.request.get.StudyRequest;
import com.request.get.studyUrl;

public class Testing {
	public static void main(String []args){
		
		String user=Ingestor.username;
		String pass= Ingestor.password;
		
		StudyRequest St= new StudyRequest();
		Response r = St.StudyData(user, pass);     //sending study request  and getting the response
		List<String> studyArray = new ArrayList<String>();  
		MetadataStudy stp = new 	MetadataStudy();     
		
		
		studyArray=stp.MetaStudyName(r); 
		MetadataVersionRequest mt = new MetadataVersionRequest();
		MetadataVersionParse metaParse = new MetadataVersionParse();
		
		Map<String,List<String>> previousUrlInfo = new HashMap<String,List<String>>();
		for(int i=0;i<studyArray.size();i++){
			
			Response metResponse = mt.MetadataRequest(studyArray.get(i));
			List<String>metaArray=metaParse.ParseMetadata(metResponse); // array of all the versions in single study
			
			if(metaArray.size()>0){
				System.out.println(i);
				
				
				MetadataExtractUrlCreation metaUrl = new MetadataExtractUrlCreation();
				List<String>metadataUrl=metaUrl.MetadataUrl(metaArray, studyArray.get(i));
				
				previousUrlInfo.put( studyArray.get(i), metaArray);
				
				MultipleAssyncMetadata  mulRequest = new MultipleAssyncMetadata();
				mulRequest.FetchMetadata(metadataUrl);
			}
			
		}
		while(true){
			NextIteration nextIter = new NextIteration();
			previousUrlInfo=nextIter.nextInfo(previousUrlInfo);
		}
	}

}

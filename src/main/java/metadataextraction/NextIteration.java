package metadataextraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ning.http.client.Response;
import com.request.get.Ingestor;
import com.request.get.StudyRequest;

public class NextIteration {
	public Map<String,List<String>> nextInfo(Map<String,List<String>>previousUrl){
		Map<String,List<String>>nextUrl= new HashMap<String,List<String>>();
		String user=Ingestor.username;
		String pass= Ingestor.password;
		
		StudyRequest St= new StudyRequest();
		Response r = St.StudyData(user, pass);     //sending study request  and getting the response
		List<String> studyArray = new ArrayList<String>();  
		MetadataStudy stp = new 	MetadataStudy();     
		
		
		studyArray=stp.MetaStudyName(r); 
		MetadataVersionRequest mt = new MetadataVersionRequest();
		MetadataVersionParse metaParse = new MetadataVersionParse();
		
		for(int i=0;i<studyArray.size();i++){
			
			Response metResponse = mt.MetadataRequest(studyArray.get(i));
			List<String>metaArray=metaParse.ParseMetadata(metResponse); // array of all the versions in single study
			
			nextUrl.put(studyArray.get(i) ,metaArray);
			List<String>versions=previousUrl.get(studyArray.get(i));
			
			List<String>newVersions = new ArrayList<String>();
			
			for(int j=0;j<metaArray.size();j++){
				int flag = 0;
				for(int k=0;k<versions.size();k++){
					if(metaArray.get(j).equals(versions.get(k))){
						flag = 1;
						break;
					}
					
				}
				if(flag==0){
					newVersions.add(metaArray.get(j));
				}
			}
			if(newVersions.size()>0){
				System.out.println(i);
				
				
				MetadataExtractUrlCreation metaUrl = new MetadataExtractUrlCreation();
				List<String>metadataUrl=metaUrl.MetadataUrl(newVersions, studyArray.get(i));
				
				
				MultipleAssyncMetadata  mulRequest = new MultipleAssyncMetadata();
				mulRequest.FetchMetadata(metadataUrl);
			}
			
		}
		return nextUrl;
	}

}

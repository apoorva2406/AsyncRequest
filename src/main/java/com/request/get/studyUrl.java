package com.request.get;

import java.util.ArrayList;
import java.util.List;
/*initial study url making
 * */
public class studyUrl {
	public List<String> initialStudiesUrl(List<String>Study){
		
		List<String> StudyUrlArray= new ArrayList<String>();
		String studyUrlName="";
		for(int i=0;i<Study.size();i++){
			
			if(Study.get(i).equals("RAVE SDBE(DEV)")){
				 studyUrlName=Ingestor.protocol+Ingestor.domain+Ingestor.WebService+Ingestor.auditurl+"RAVE%20SDBE(DEV)"+Ingestor.pagelimit;
				
			}
			else{
			 studyUrlName=Ingestor.protocol+Ingestor.domain+Ingestor.WebService+Ingestor.auditurl+Study.get(i)+Ingestor.pagelimit;
			
			}
			StudyUrlArray.add(studyUrlName);
		}
		return StudyUrlArray;
		
		
	}
}

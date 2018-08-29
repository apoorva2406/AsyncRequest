package com.request.get;

import java.util.ArrayList;
import java.util.List;

public class MiddleStart {
	public List<String> StartMiddle(List<String>studyName,List<String>startId){
		
		String studyUrlName = null;
		List<String>StudyUrlArray = new ArrayList<String>();
		
		for(int i=0;i<studyName.size();i++){
			if(studyName.get(i).equals("Fixitol(Prod)")){
				studyUrlName=Ingestor.protocol+Ingestor.domain+Ingestor.WebService+Ingestor.auditurl+studyName.get(i)+Ingestor.pagelimit;
			}
			else if(studyName.get(i).equals("RAVE SDBE(DEV)")){
				 studyUrlName=Ingestor.protocol+Ingestor.domain+Ingestor.WebService+Ingestor.auditurl+"RAVE%20SDBE(DEV)"+Ingestor.pagelimit+"&startid="+startId.get(i);
				
			}
			else{
			 studyUrlName=Ingestor.protocol+Ingestor.domain+Ingestor.WebService+Ingestor.auditurl+studyName.get(i)+Ingestor.pagelimit+"&startid="+startId.get(i);
			
			}
			
			StudyUrlArray.add(studyUrlName);
		}
		return StudyUrlArray;
		
	}

}

package com.request.get;

import java.util.ArrayList;
import java.util.List;

import com.ning.http.client.Response;
/*This code is written so that we can search for new studies and if it exist we can add it to the list of studies*/
public class NextStudyTrial {
		public List<String> NextTrail(List<String>studyArray,List<String>nextUrlInfo){
			List<String>previousStudyArray= new ArrayList<String>();
			previousStudyArray= studyArray;
			StudyRequest St = new StudyRequest();
			
			Response r = St.StudyData(Ingestor.username, Ingestor.password);
			StudyParse stp = new StudyParse();
			studyArray=stp.StudyName(r);
			
			studyUrl nst = new studyUrl();
			
			List<String> urlInfo= new ArrayList<String>();
			int urlSetFLag=0;
			int k=0;
			for(int i=0;i<studyArray.size();i++){
				for(int j=0;j<previousStudyArray.size();j++){
					if(studyArray.get(i).equals(previousStudyArray.get(j))){
						urlInfo.add(k,nextUrlInfo.get(j));
						urlSetFLag=1;
						k++;
					}
					
				}
				if(urlSetFLag==0){
					List<String>newStudy= new ArrayList<String>();
					newStudy.add(0, studyArray.get(i));
					List<String>nextStudiesUrl= new ArrayList<String>();
					nextStudiesUrl=nst.initialStudiesUrl(newStudy);
					urlInfo.add(k, nextStudiesUrl.get(0));
					k++;
				}
				urlSetFLag=0;
			}
			return urlInfo;
			
		}
}

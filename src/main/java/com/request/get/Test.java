package com.request.get;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ning.http.client.Response;

/*Main Program from where we are running the code*/
public class Test {
	public static void main(String []args){
		
		
		String user=Ingestor.username;
		String pass= Ingestor.password;
		StudyRequest St= new StudyRequest();
		Response r = St.StudyData(user, pass);     //sending study request  and getting the response
		List<String> studyArray = new ArrayList<String>();  
		StudyParse stp = new StudyParse();     
		studyUrl stUrl= new studyUrl();
	
		List<String>studyUrlName = new ArrayList<String>();	
		studyArray=stp.StudyName(r);       // sending the study Response for parsing and getting the list of studies
		studyUrlName=stUrl.initialStudiesUrl(studyArray);   // getting the initial studyUrl
		List<Response> mulRes = new ArrayList<Response>();
	
		// If we start form the beginning
		
		MultipleAssynRequests mulReq = new MultipleAssynRequests(); 
		NextUrl nextUrlInfo = new NextUrl();
		nextUrlInfo=mulReq.AssynchronousRequest(studyUrlName, user, pass);  // sending the list of url studies and getting the response from the Rave web service
		
		// not to start from the beginning
		
		/* List<String>startId = new ArrayList<String>();
		startId.add(null);
		startId.add("464944");
		startId.add("430596");    // updated on 14-Nov
		startId.add("462885");
		startId.add("458524");
		MiddleStart ms = new MiddleStart();
		List<String>middleUrlName = new ArrayList<String>();
		middleUrlName = ms.StartMiddle(studyArray, startId);
		nextUrlInfo=mulReq.AssynchronousRequest(middleUrlName, user, pass);
		*/
		
		//the below logic is that when the Study id gets constant we will then check whether a study has
		//been created and include that and pull the data from all the existing studies else we will pull the data until the study id  gets constant.
		/*we can here schedule the work of getting data from existing studies and check
		  whether new study has been created or not.If scheduling is fine we can go with that.
		  So we can schedule when to hit the to url (study info url and data info url) accordingly
		  */
		
		
		while(true){
			EndTrial endTrial = new EndTrial();
			int endTrailFlag= endTrial.CheckForEndtrail(nextUrlInfo);
			if(endTrailFlag==0){
				
				nextUrlInfo=mulReq.AssynchronousRequest(nextUrlInfo.nextUrl, user, pass);
			}
			else{
				
				NextStudyTrial nextStudiesTrial = new NextStudyTrial();
				List<String> urlInfo= new ArrayList<String>();
				urlInfo=nextStudiesTrial.NextTrail(studyArray, nextUrlInfo.nextUrl);
				nextUrlInfo=mulReq.AssynchronousRequest(urlInfo, user, pass);
				
			}
		}
	}
}

package com.request.get;
/*This checks whether batch process have complemented for the existing studies ended so that 
 * we can check for the studies newly added to the rave web service*/

public class EndTrial {
		public int CheckForEndtrail(NextUrl nextUrlInfo){
			int count =0;
			int flag = 0;;
			for(int i=0;i<nextUrlInfo.nextUrl.size();i++){
				if(nextUrlInfo.reachedend.get(i)==1){
					count++;
				}
			}
			System.out.println(count);
			if(count==nextUrlInfo.nextUrl.size()) flag=1;
			return flag;
		}
}

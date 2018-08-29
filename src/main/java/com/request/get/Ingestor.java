package com.request.get;
/*This is just hard coded for the time being but in the final code it will all be pluggable */
public class Ingestor {
	
	public static final String domain ="wincere.mdsol.com";
	public static final String WebService="/RaveWebServices";
	public static final String username="RWS_IO1";
	public static final String password="Wincere#100";
	public static final String auditurl="/datasets/ClinicalAuditRecords.odm?studyoid=";
	public static final String pagelimit="&per_page=1000";
	public static final String protocol="https://";
	public static final String HDFSpath = "http://172.16.1.161:50070/webhdfs/v1/WincereStudiesTest1/";
	//public static final String HDFSpath = "http://172.16.1.161:50070/webhdfs/v1/webhdfsAuditLog10/";
	public static final String topicName = "yoyo";
	public static final String kafkaAddress = "172.16.1.162:9092,172.16.1.83:9092";
	//public static final String kafkaAddress = "172.16.1.175:9092";
	public static final String HDFSpath1= "http://172.16.1.161:50070/webhdfs/v1/webhdfsMatadata/";
	public static final String topicName1 = "metadata";
	public static final String kafkaAddressMetadata = "172.16.1.219:9092";
}

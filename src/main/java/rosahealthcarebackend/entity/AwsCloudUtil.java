package rosahealthcarebackend.entity;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;


import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

@ComponentScan
public class AwsCloudUtil {

	@Value("${aws.access.key}")
	private String AWS_ACCESS_KEY;

	@Value("${aws.secret.key}")
	private String AWS_SECRET_KEY;
	
	@Value("${aws.s3.bucket}")
	private String AWS_BUCKET;

	
	public AwsCloudUtil() {
		// TODO Auto-generated constructor stub
	}

  
  public AwsCloudUtil(String aWS_ACCESS_KEY, String aWS_SECRET_KEY, String aWS_BUCKET) {
		super();
		AWS_ACCESS_KEY = aWS_ACCESS_KEY;
		AWS_SECRET_KEY = aWS_SECRET_KEY;

     	AWS_BUCKET = aWS_BUCKET;
	}
  

	
	public String getAWS_ACCESS_KEY() {
		return AWS_ACCESS_KEY;
	}
	public void setAWS_ACCESS_KEY(String aWS_ACCESS_KEY) {
		AWS_ACCESS_KEY = aWS_ACCESS_KEY;
	}
	public String getAWS_SECRET_KEY() {
		return AWS_SECRET_KEY;
	}
	public void setAWS_SECRET_KEY(String aWS_SECRET_KEY) {
		AWS_SECRET_KEY = aWS_SECRET_KEY;
	}
	public String getAWS_BUCKET() {
		return AWS_BUCKET;
	}
	public void setAWS_BUCKET(String aWS_BUCKET) {
		AWS_BUCKET = aWS_BUCKET;
	}
	
	private AWSCredentials  awsCredentials(String accesskey,String secretkey) {
	
		AWSCredentials credentials=new BasicAWSCredentials(accesskey, secretkey);
		return credentials;
		
	}
    private AmazonS3 awsS3ClentBuilder(String accesskey,String secretkey) {
    
    	AmazonS3 s3client=AmazonS3ClientBuilder.standard()
    			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accesskey, secretkey)))
    			.withRegion(Regions.US_EAST_1)
    			.build();
    	return s3client;
    }
    public void uploadFileTos3(String filename,byte[] fileBytes,String accesskey,String secretkey,String bucket) throws IOException {
    	AmazonS3 s3client=awsS3ClentBuilder(accesskey, secretkey);
   
    	//File file=new File(filename);
    	
    	//try(OutputStream os=new FileOutputStream(file)){
    	try(ResetByteArray os=new ResetByteArray(fileBytes)){
    		
    		//os.write(fileBytes);
    		
    		ObjectMetadata metadata =new ObjectMetadata();
    		metadata.setContentLength(fileBytes.length);
    		s3client.putObject(bucket,filename,os,metadata);
    	}catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	//s3client.putObject(bucket,filename,file);
    	
    }
    public S3ObjectInputStream downloadFileFromS3(String filename,String accesskey,String secretkey,String bucket) {
    	
    	AmazonS3 s3client=awsS3ClentBuilder(accesskey, secretkey);
    	
    	
    	
        S3Object s3object=s3client.getObject(bucket,filename);
    	
    
    	S3ObjectInputStream inputstream=s3object.getObjectContent();
    	
    
    	return inputstream;
    }
    
    
}


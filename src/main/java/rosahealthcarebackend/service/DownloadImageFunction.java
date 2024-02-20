package rosahealthcarebackend.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
@Component
public class DownloadImageFunction {
	


	@Value("${aws.access.key}")
	private String AWS_ACCESS_KEY;

	@Value("${aws.secret.key}")
	private String AWS_SECRET_KEY;
	
	@Value("${aws.s3.bucket}")
	private String AWS_BUCKET;	
	
	
	
	public String imageBase64( String filename)throws IOException{
		InputStream in=downloadFileFromS3(filename,AWS_ACCESS_KEY ,AWS_SECRET_KEY ,AWS_BUCKET);
		
		byte[] imagebyte=in.readAllBytes();
		String base64=Base64.getEncoder().encodeToString(imagebyte);
		return base64;
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
    
    public S3ObjectInputStream downloadFileFromS3(String filename,String accesskey,String  secretkey,String bucket) {
    	
    	AmazonS3 s3client=awsS3ClentBuilder(accesskey, secretkey);
   
   
    	
        S3Object s3object=s3client.getObject(bucket,filename);
  
    	
    	S3ObjectInputStream inputstream=s3object.getObjectContent();
    	
    	
    	return inputstream;
    }
    
}
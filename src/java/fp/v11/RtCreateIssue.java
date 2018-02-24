package fp.v11;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RtCreateIssue {
	
	// Constants will be retrieved from the Config file further into the project
	static final String HOSTNAME = ""; // Required RT Rest host name from server
	static final String BASE_URI = "http://"+HOSTNAME+"/REST/1.0";
	
	public static void main(String[] args) throws Exception {
		        
	    HttpClient httpclient = HttpClientBuilder.create().build();
			   
	    try {
	    // POST request with the given URI
	    HttpPost httppost = new HttpPost(BASE_URI+"/ticket/new?user=username&pass=password");
	    // Request parameters and other properties
        StringBody content = new StringBody("Subject: Test\nQueue: General",ContentType.TEXT_PLAIN); // Content variable with key value pairs
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("content", content);
        httppost.setEntity(builder.build());
        System.out.println("Request: " + httppost.getRequestLine()+"\n");
        
        // Execute and get the response
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        // Checking the status of the response
        System.out.println(response.getStatusLine()+"\n");
        if (resEntity != null) {
            System.out.println(resEntity.getContentType()+"\nContent-Length: "+resEntity.getContentLength());
        }
        // Releases all resources held by HttpEntity
        EntityUtils.consume(resEntity);
        }  
	    catch(IOException e) {
	    	System.out.println(e.getMessage());
        }
	}	    
}

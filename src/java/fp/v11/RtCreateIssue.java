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
	
	// Constants will be retrieved/merged from the Config/Constants file further into the project
	static final String HOSTNAME = ""; // Required RT Rest host name from server
	static final String BASE_URI = "http://"+HOSTNAME+"/REST/1.0";
	
	public static void main(String[] args) throws Exception {
		        
	    String url = BASE_URI +"/ticket/new?user=username&pass=password";
			   
	    // POST request with the given URI
	    HttpPost httppost = sendPost(url);
     
        // Execute and get the response
        HttpEntity resEntity = getResponse(httppost);
        
        // Obtain the content details from response
        getResponseDetails(resEntity); 

	}	
	/**
	 * This method sends a HTTP POST request to the given url
	 * @param url
	 */
	public static HttpPost sendPost(String url){
	   
		HttpPost httppost = new HttpPost(url);
		// Request parameters and other properties
		try {
		StringBody content = new StringBody("Subject: Test\nQueue: General",ContentType.TEXT_PLAIN); // Content variable with key value pairs
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("content", content);
        httppost.setEntity(builder.build());
        System.out.println("Request: " + httppost.getRequestLine()+"\n");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return httppost;
		
	}
	/**
	 * This method retrieves the response from the HTTP POST request
	 * @param httppost
	 */
	public static HttpEntity getResponse(HttpPost httppost){
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpEntity responseEntity = null;
		HttpResponse response = null;
		
		try {
		response = httpclient.execute(httppost);
		responseEntity = response.getEntity();
		}
		catch(IOException e) {
	    	System.out.println(e.getMessage());
        }
		// Checking the status of the response
		if (response !=null){
		System.out.println(response.getStatusLine()+"\n");
		}
		return responseEntity;
		
	}
	/**
	 * This method outputs the content details of the response
	 * @param responseEntity
	 */
	public static void getResponseDetails(HttpEntity responseEntity){
		
		if (responseEntity != null) {
            System.out.println(responseEntity.getContentType()+"\nContent-Length: "+responseEntity.getContentLength());
        }	
	}
}

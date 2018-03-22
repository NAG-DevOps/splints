package fp.v11.splints.bitbucketcloud;

import java.io.IOException;
import java.util.Base64;

import javax.xml.soap.SOAPElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.NodeList;

import fp.v11.splints.ISplints;
import fp.v11.splints.bitbucketcloud.Config;

/**
 * Refactoring of class BitbucketCloud
 * @author Daniil Karpov from soen487-w18-team03
 */
public class BitbucketCloud implements ISplints{

	public String createIssue() {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues";

		HttpPost httppost = new HttpPost(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httppost.setHeader("Authorization", "Basic " + encoding);
		httppost.setHeader("Content-Type", "application/json");

		String jsonString = new JSONObject()
                .put("status", Constants.STATUS)
                .put("priority", Constants.PRIORITY)
                .put("title", Constants.TITLE)
                .put("content", new JSONObject()
            		.put("raw" , Constants.CONTENT))
                .put("reported_by", new JSONObject()
                     .put("username", Constants.USERNAME)
                     .put("first_name", Constants.FIRST_NAME)
                     .put("last_name", Constants.LAST_NAME)
                     .put("is_team", Constants.IS_TEAM)).toString();

		httppost.setEntity(new StringEntity(jsonString, ContentType.TEXT_PLAIN));
		
		HttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = httpclient.execute(httppost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (response != null) {
			System.out.println(response.getStatusLine());
			responseEntity = response.getEntity();
			
			if (responseEntity != null) {
				try {
					System.out.println("Response received: " + EntityUtils.toString(responseEntity));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
		
		return null;
	}


	public void editIssue() {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/" + Constants.ISSUE_NUMBER;

		HttpPut httpput = new HttpPut(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httpput.setHeader("Authorization", "Basic " + encoding);
		httpput.setHeader("Content-Type", "application/json");

		String jsonString = new JSONObject()
                .put("priority", Constants.NEW_PRIORITY)
                .put("kind", Constants.KIND)
                .put("assignee", new JSONObject()
            		.put("username" , Constants.ASSIGNEE_USERNAME))
                .toString();

		httpput.setEntity(new StringEntity(jsonString, ContentType.TEXT_PLAIN));
		
		HttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = httpclient.execute(httpput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (response != null) {
			System.out.println(response.getStatusLine());
			responseEntity = response.getEntity();
			
			if (responseEntity != null) {
				try {
					System.out.println("Response received: " + EntityUtils.toString(responseEntity));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	@Override
	public NodeList getIssueDetails() {
		return null;
	}

	@Override
	public NodeList linkIssues() {
		return null;
	}


	@Override
	public String getIndent(int num) {
		return null;
	}

	@Override
	public void dumpSOAPElement(SOAPElement el, int indent) throws Exception {
	}

	@Override
	public void queryIssues() {
	}

}

package bitbucket;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import javax.xml.soap.SOAPElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.NodeList;

import fp.v11.splints.ISplints;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Refactored BitbucketCloud Class
 * @author Daniil Karpov from soen487-w18-team03
 */
public class BitbucketCloud implements ISplints{

    public static void main(String args[]) {
        BitbucketCloud bitCloud = new BitbucketCloud();
        Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
        contentMap.put("issueNumber", "4");
        //contentMap.put("state", "open");
        //contentMap.put("priority", "minor");
        //contentMap.put("title", "Changed title");
        //contentMap.put("assignee", "d_karpo");
        //contentMap.put("content", "This is a test issue.");
        //contentMap.put("kind", "proposal");
        //bitCloud.getIssueDetails(contentMap);
        bitCloud.search("bug");
        //System.out.println(bitCloud.createIssue(contentMap));
    }
        
    /**
	 * Create new Bitbucket issue given a map of issue details
	 *
	 * @param contentMap Map containing issue details
	 * @return null
	 */
    @Override
	public String createIssue(Map<String, Serializable> contentMap) {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues";

		HttpPost httppost = new HttpPost(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httppost.setHeader("Authorization", "Basic " + encoding);
		httppost.setHeader("Content-Type", "application/json");
		
		JSONObject jsonRequestBody = new JSONObject();
		if (contentMap.get("state") != null) {
			jsonRequestBody.put("state", (String)contentMap.get("state"));
		}
		if (contentMap.get("priority") != null) {
			jsonRequestBody.put("priority", (String)contentMap.get("priority"));
		}
		if (contentMap.get("title") != null) {
			jsonRequestBody.put("title", (String)contentMap.get("title"));
		}
		if (contentMap.get("content") != null) {
			jsonRequestBody.put("content", new JSONObject()
				.put("raw" , (String)contentMap.get("content")));
		}
		if (contentMap.get("kind") != null) {
			jsonRequestBody.put("kind", (String)contentMap.get("kind"));
		}

		httppost.setEntity(new StringEntity(jsonRequestBody.toString(), ContentType.TEXT_PLAIN));
		
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

    /**
   	 * Edit existing Bitbucket issue given a map of issue details
   	 *
   	 * @param contentMap Map containing issue details
   	 */
    @Override
	public void editIssue(Map<String, Serializable> contentMap) {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/" + (String)contentMap.get("issueNumber");

		HttpPut httpput = new HttpPut(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httpput.setHeader("Authorization", "Basic " + encoding);
		httpput.setHeader("Content-Type", "application/json");
		
		JSONObject jsonRequestBody = new JSONObject();
		if (contentMap.get("state") != null) {
			jsonRequestBody.put("state", (String)contentMap.get("state"));
		}
		if (contentMap.get("priority") != null) {
			jsonRequestBody.put("priority", (String)contentMap.get("priority"));
		}
		if (contentMap.get("title") != null) {
			jsonRequestBody.put("title", (String)contentMap.get("title"));
		}
		if (contentMap.get("assignee") != null) {
			jsonRequestBody.put("assignee", new JSONObject()
            		.put("username" , (String)contentMap.get("assignee")));
		}

		httpput.setEntity(new StringEntity(jsonRequestBody.toString(), ContentType.TEXT_PLAIN));
		
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
	
    /**
   	 * Get details of existing Bitbucket issue given a map containing the issue number
   	 *
   	 * @param contentMap Map containing issue number
   	 */
	@Override
	public NodeList getIssueDetails(Map<String, Serializable> contentMap) {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/" + (String)contentMap.get("issueNumber");

		HttpGet httpget = new HttpGet(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httpget.setHeader("Authorization", "Basic " + encoding);
		httpget.setHeader("Content-Type", "application/json");
		
		HttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = httpclient.execute(httpget);
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
	
	 /**
	 * Close existing Bitbucket issue given a map containing the issue number
	 *
	 * @param contentMap Map contains issue number to close
	 */
	public void closeIssue(Map<String, Serializable> contentMap) {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/" + (String)contentMap.get("issueNumber");

		HttpPut httpput = new HttpPut(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httpput.setHeader("Authorization", "Basic " + encoding);
		httpput.setHeader("Content-Type", "application/json");
		
		// setting state to closed
		JSONObject jsonRequestBody = new JSONObject()
			.put("state", "closed");

		httpput.setEntity(new StringEntity(jsonRequestBody.toString(), ContentType.TEXT_PLAIN));
		
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
	
	/**
	 * Search all existing Bitbucket issues given a query string, all matching issues will be printed out
	 *
	 * @param query Query string that will be used for the search
	 */
	public void search(String query) {
		String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/";

		HttpGet httpget = new HttpGet(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		// adding bitbucket credentials for authentication
		String encoding = Base64.getEncoder().encodeToString((Config.AGENT_USERNAME + ":" + Config.AGENT_PASSWORD).getBytes());
		httpget.setHeader("Authorization", "Basic " + encoding);
		httpget.setHeader("Content-Type", "application/json");
		
		HttpResponse response = null;
		HttpEntity responseEntity = null;
		try {
			response = httpclient.execute(httpget);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (response != null) {
			System.out.println(response.getStatusLine());
			responseEntity = response.getEntity();
			
			if (responseEntity != null) {
				try {
					// searching for the query in each issue that is returned
					JSONObject jsonObj = new JSONObject(EntityUtils.toString(responseEntity));
					JSONArray issues = jsonObj.getJSONArray("values");
					for (int i=0; i < issues.length(); i++) {
						JSONObject issue = issues.getJSONObject(i);
						for (Object key : issue.keySet()) {
							String keyStr = (String)key;
							Object keyvalue = issue.get(keyStr);
							if (keyvalue.toString().contains(query)) {
								System.out.println(issue.toString()); // printing out the issue details if query is found
							}
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
}

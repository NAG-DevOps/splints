package rt;

import javax.xml.soap.SOAPElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.NodeList;

import bitbucket.Config;
import fp.v11.splints.ISplints;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.Map;

public class RT implements ISplints {
    

	/**
	 * Create new issue for RT given a map of issue details
	 * @author Daniil Karpov from soen487-w18-team08
	 * @param contentMap Map containing issue details
	 * @return reply String
	 */
	@Override
	public String createIssue(Map<String,Serializable> contentMap) {
		
		String uri = Config.BASE_URI+"/ticket/new?user="+Config.AGENT_USERNAME+"&pass="+Config.AGENT_PASSWORD;
		
		HttpPost httppost = new HttpPost(uri);	
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpEntity responseEntity = null;
		HttpResponse response = null;
		
		try {
			JSONObject jsonRequestBody = new JSONObject();
			if (contentMap.get("id") != null) {
				jsonRequestBody.put("id", (String)contentMap.get("id"));
			}
			if (contentMap.get("Queue") != null) {
				jsonRequestBody.put("Queue", (String)contentMap.get("Queue"));
			}
			if (contentMap.get("Requestor") != null) {
				jsonRequestBody.put("Requestor", (String)contentMap.get("Requestor"));
			}
			if (contentMap.get("Subject") != null) {
				jsonRequestBody.put("Subject", (String)contentMap.get("Subject"));
			}
			if (contentMap.get("CC") != null) {
				jsonRequestBody.put("CC", (String)contentMap.get("CC"));
			}
			if (contentMap.get("AdminCc") != null) {
				jsonRequestBody.put("AdminCc", (String)contentMap.get("AdminCc"));
			}
			if (contentMap.get("Owner") != null) {
				jsonRequestBody.put("Owner", (String)contentMap.get("Owner"));
			}
			if (contentMap.get("Status") != null) {
				jsonRequestBody.put("Status", (String)contentMap.get("Status"));
			}
			if (contentMap.get("Priority") != null) {
				jsonRequestBody.put("Priority", (String)contentMap.get("Priority"));
			}
			if (contentMap.get("InitialPriority") != null) {
				jsonRequestBody.put("InitialPriority", (String)contentMap.get("InitialPriority"));
			}
			if (contentMap.get("FinalPriority") != null) {
				jsonRequestBody.put("FinalPriority", (String)contentMap.get("FinalPriority"));
			}
			if (contentMap.get("TimeEstimated") != null) {
				jsonRequestBody.put("TimeEstimated", (String)contentMap.get("TimeEstimated"));
			}
			if (contentMap.get("Starts") != null) {
				jsonRequestBody.put("Starts", (String)contentMap.get("Starts"));
			}
			if (contentMap.get("Due") != null) {
				jsonRequestBody.put("Due", (String)contentMap.get("Due"));
			}
			if (contentMap.get("Text") != null) {
				jsonRequestBody.put("Text", (String)contentMap.get("Text"));
			}
			if (contentMap.get("CF-CustomField") != null) {
				jsonRequestBody.put("CF-CustomField", (String)contentMap.get("CF-CustomField"));
			}

			httppost.setEntity(new StringEntity(jsonRequestBody.toString(), ContentType.TEXT_PLAIN));
			
			System.out.println("Request: " + httppost.getRequestLine()+"\n");
			
			response = httpclient.execute(httppost);
			responseEntity = response.getEntity();
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if (response !=null){
			System.out.println(response.getStatusLine()+"\n");
			}
		else {
			return null;
		}
		
		if (responseEntity != null) {
            System.out.println(responseEntity.getContentType()+"\nContent-Length: "+responseEntity.getContentLength());
            return responseEntity.toString();
        }
		else {
			return null;
		}
		
	}
	/**
	 * ISplints does not have an issue closure method and the documentation for RT does not provide anything regarding
	 * issue closure therefore it is assumed that closing an issue is done by using the Resolved parameter along with
	 * id to identify the issue.
	 */
	public void closeIssue(){
		
		String uri = Config.BASE_URI+"/ticket/new?user="+Config.AGENT_USERNAME+"&pass="+Config.AGENT_PASSWORD;
		
		HttpPost httppost = new HttpPost(uri);	
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpEntity responseEntity = null;
		HttpResponse response = null;

		try {
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("id: " + Constants.ID);
			stringBuilder.append("Resolved: "+Constants.RESOLVED);

			
			StringBody content = new StringBody(stringBuilder.toString(),ContentType.TEXT_PLAIN);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addPart("content", content);
			httppost.setEntity(builder.build());
			System.out.println("Request: " + httppost.getRequestLine()+"\n");
			
			response = httpclient.execute(httppost);
			responseEntity = response.getEntity();
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if (response !=null){
			System.out.println(response.getStatusLine()+"\n");
			}
		else {
			System.out.println("An error occurred");
		}
		
		if (responseEntity != null) {
            System.out.println(responseEntity.getContentType()+"\nContent-Length: "+responseEntity.getContentLength());
            System.out.println("Response for issue closure received: " + responseEntity.toString());
        }
		else {
			System.out.println("An error occurred");
		}

		
	}

	@Override
	public Map<String, Serializable> getIssueDetails(Map<String,Serializable> contentMap) {
		return null;
	}

	@Override
	public NodeList linkIssues() {
		return null;
	}

	/**
	 * Edit existing RT issue given a map of issue details
	 * @author Daniil Karpov from soen487-w18-team08
	 * @param contentMap Map containing issue details
	 */
	@Override
	public void editIssue(Map<String,Serializable> contentMap) {
		String uri = Config.BASE_URI + "/ticket/" + contentMap.get("id") + "/edit?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;
		HttpPost httppost = new HttpPost(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
			JSONObject jsonRequestBody = new JSONObject();
			if (contentMap.get("Queue") != null) {
				jsonRequestBody.put("Queue", (String)contentMap.get("Queue"));
			}
			if (contentMap.get("Requestor") != null) {
				jsonRequestBody.put("Requestor", (String)contentMap.get("Requestor"));
			}
			if (contentMap.get("Subject") != null) {
				jsonRequestBody.put("Subject", (String)contentMap.get("Subject"));
			}
			if (contentMap.get("CC") != null) {
				jsonRequestBody.put("CC", (String)contentMap.get("CC"));
			}
			if (contentMap.get("AdminCc") != null) {
				jsonRequestBody.put("AdminCc", (String)contentMap.get("AdminCc"));
			}
			if (contentMap.get("Owner") != null) {
				jsonRequestBody.put("Owner", (String)contentMap.get("Owner"));
			}
			if (contentMap.get("Status") != null) {
				jsonRequestBody.put("Status", (String)contentMap.get("Status"));
			}
			if (contentMap.get("Priority") != null) {
				jsonRequestBody.put("Priority", (String)contentMap.get("Priority"));
			}
			if (contentMap.get("InitialPriority") != null) {
				jsonRequestBody.put("InitialPriority", (String)contentMap.get("InitialPriority"));
			}
			if (contentMap.get("FinalPriority") != null) {
				jsonRequestBody.put("FinalPriority", (String)contentMap.get("FinalPriority"));
			}
			if (contentMap.get("TimeEstimated") != null) {
				jsonRequestBody.put("TimeEstimated", (String)contentMap.get("TimeEstimated"));
			}
			if (contentMap.get("Starts") != null) {
				jsonRequestBody.put("Starts", (String)contentMap.get("Starts"));
			}
			if (contentMap.get("Due") != null) {
				jsonRequestBody.put("Due", (String)contentMap.get("Due"));
			}
			if (contentMap.get("Text") != null) {
				jsonRequestBody.put("Text", (String)contentMap.get("Text"));
			}
			if (contentMap.get("CF-CustomField") != null) {
				jsonRequestBody.put("CF-CustomField", (String)contentMap.get("CF-CustomField"));
			}

			httppost.setEntity(new StringEntity(jsonRequestBody.toString(), ContentType.TEXT_PLAIN));
			
			
			System.out.println("Request: " + httppost.getRequestLine()+"\n");
			
			response = httpclient.execute(httppost);
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}

		if (response != null){
			System.out.println(response.getStatusLine());
		}
		
		HttpEntity responseEntity = response.getEntity();
		if (responseEntity != null) {
			System.out.println("Response received: " + responseEntity.toString());
		} 
	}
	
	/**
	 * Search all existing RT issues given a query string, all matching issues will be printed out
	 * 
	 * @author Daniil Karpov from soen487-w18-team08
	 * @param query Query string that will be used for the search
	 */
	public void search(String query) {
		String uri = Config.BASE_URI + "/ticket?query=" + query + "&user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;

		HttpGet httpget = new HttpGet(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		
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
				System.out.println("Response received: " + responseEntity.toString());
			} 
		}
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

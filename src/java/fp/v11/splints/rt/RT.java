package fp.v11.splints.rt;

import javax.xml.soap.SOAPElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.NodeList;

import fp.v11.splints.ISplints;

/**
 * Refactored class RT
 * @author Daniil Karpov from soen487-w18-team08
 *
 */
public class RT implements ISplints {

	public String createIssue() {
		String uri = Config.BASE_URI + "/ticket/new?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;
		HttpPost httppost = new HttpPost(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("id: " + Constants.ID);
			sb.append("Queue: " + Constants.QUEUE);
			sb.append("Owner: " + Constants.OWNER);
			sb.append("Creator: " + Constants.CREATOR);
			sb.append("Requestor: " + Constants.REQUESTOR);
			sb.append("CC: " + Constants.CC);
			sb.append("AdminCc: " + Constants.ADMIN_CC);
			sb.append("Subject: " + Constants.SUBJECT);
			sb.append("Status: " + Constants.STATUS);
			sb.append("Priority: " + Constants.PRIORITY);
			sb.append("InitialPriority: " + Constants.INITIAL_PRIORITY);
			sb.append("FinalPriority: " + Constants.FINAL_PRIORITY);
			sb.append("TimeEstimated: " + Constants.TIME_ESTIMATED);
			sb.append("Starts: " + Constants.STARTS);
			sb.append("Due: " + Constants.DUE);
			sb.append("Text: " + Constants.TEXT);
			
			StringBody content = new StringBody(sb.toString(), ContentType.TEXT_PLAIN);
			MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
			reqEntity.addPart("content", content);
			httppost.setEntity(reqEntity.build());
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
			return responseEntity.toString();
		} else {
			return null;
		}
	}

	public void editIssue() {
		String uri = Config.BASE_URI + "/ticket/" + Constants.TICKET_ID + "/edit?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;
		HttpPost httppost = new HttpPost(uri);
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpResponse response = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("Started: " + Constants.STARTED);
			sb.append("TimeEstimated: " + Constants.TIME_ESTIMATED);
			sb.append("TimeWorked: " + Constants.TIME_WORKED);
			
			StringBody content = new StringBody(sb.toString(), ContentType.TEXT_PLAIN);
			MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
			reqEntity.addPart("content", content);
			httppost.setEntity(reqEntity.build());
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

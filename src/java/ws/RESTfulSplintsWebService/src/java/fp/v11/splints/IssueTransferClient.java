package fp.v11.splints;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import javax.json.*;

import bitbucket.BitbucketIssuesClient;
import github.GitHub;

/**
 * 
 * @author Ziad Yarbouh from soen487-team08
 *
 */

public class IssueTransferClient {


	public void transferFromBitbucket (int issueNumber, String repository) throws Exception {

		String api = "https://api.bitbucket.org/1.0/repositories/";
		String urlprefix = api + repository + "issues/";
		String inumber = Integer.toString(issueNumber);
		JSONObject json;

		FootPrints11 fp = new FootPrints11();
		Map<String, String> fpdetails = new HashMap<String, String>();

		fpdetails.put(Constants.WORKSPACE, repository);

		try {
			URL base = new URL(urlprefix);
			URL url = new URL (base,inumber);
			json = new JSONObject(BitbucketIssuesClient.getText(url));
			//System.out.println(response);

			String[] names = JSONObject.getNames(json);

			for(String str : names){

				if(str.equals("priority")){
					fpdetails.put(Constants.PRIORITY_NUMBER, (String) json.get(str));
				}
				if(str.equals("status")){
					fpdetails.put(Constants.ISSUE_STATUS, (String) json.get(str));
				}
				if(str.equals("content")){
					fpdetails.put(Constants.DESCRIPTION, (String) json.get(str));
				}
				if(str.equals("responsible")){
					fpdetails.put(Constants.ASSIGNEES, (String) json.get(str));
				}
				if(str.equals("username")){
					fpdetails.put(Constants.CONTACT_EMAIL, (String) json.get(str));
				}
				if(str.equals("title")){
					fpdetails.put(Constants.ISSUE_SUBJECT, (String) json.get(str));
				}


			}

			fp.createIssue(fpdetails);

		} catch (MalformedURLException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

	}

	public void transferFromGithub (int issueNumber, String repository) throws Exception {

		String api = "https://api.github.com/repos/";
		String urlprefix = api + repository + "issues/";
		String inumber = Integer.toString(issueNumber);

		FootPrints11 fp = new FootPrints11();
		Map<String, String> fpdetails = new HashMap<String, String>();
		fpdetails.put(Constants.WORKSPACE, repository);
		
		try {
			URL base = new URL(urlprefix);
			URL url = new URL(base, inumber);
			String response = GitHub.sendGet(url);
			System.out.println(response);
			JsonObject object = GitHub.getJsonObject(response);
			JsonString title = (JsonString) object.get("title");
			JsonString body = (JsonString) object.get("body");
			JsonString state = (JsonString) object.get("state");
			JsonString assignees = (JsonString) object.get("assignees");
			JsonString user = (JsonString) object.get("user");
			JsonString labels = (JsonString) object.get("labels");
			
			
			fpdetails.put(Constants.PRIORITY_NUMBER, labels.getString());
			fpdetails.put(Constants.ISSUE_STATUS, state.getString());
			fpdetails.put(Constants.DESCRIPTION, body.getString());
			fpdetails.put(Constants.ASSIGNEES, assignees.getString());
			fpdetails.put(Constants.CONTACT_EMAIL, user.getString());
			fpdetails.put(Constants.ISSUE_SUBJECT, title.getString());
			
			fp.createIssue(fpdetails);

			System.out.println("Issue title : "+title.getString());
		} catch (MalformedURLException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

	}

	public void transferFromRT (int issueNumber, String repository) throws Exception {
		

	}

}

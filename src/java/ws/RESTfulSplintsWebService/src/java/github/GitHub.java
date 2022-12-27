   /**
    * Simple JSON reader parser client (GET) for GitHub
    * Usage: https://bitbucket.org/soen487-w18-03/soen487-w18-team03/issues/8/github-json-parser
    */

package github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.*;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.xml.soap.SOAPElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.NodeList;

import fp.v11.splints.ISplints;

public class GitHub implements ISplints {
	
	public static void main (String []args){
		
	}

    @Override
	public String createIssue(Map<String, Serializable> content) {
		URL base;
		URL url = null;
		try {
			base = new URL(Config.API);
	        url = new URL(base, Config.WORKSPACE + "issues/");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    		
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url.toString());

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(6);
        params.add(new BasicNameValuePair("title", (String) content.get("title")));
        params.add(new BasicNameValuePair("body", (String) content.get("body")));
        params.add(new BasicNameValuePair("assignee", (String) content.get("assignee")));
        params.add(new BasicNameValuePair("milestone", (String) content.get("milestone")));
        params.add(new BasicNameValuePair("labels", (String) content.get("labels")));
        params.add(new BasicNameValuePair("assignees", (String) content.get("assignees")));
        try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //Execute and get the response.
        try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;
	}

	@Override
	public NodeList getIssueDetails(Map<String, Serializable> content) {
		try {
            URL base = new URL(Config.API);
            URL url = new URL(base, Config.WORKSPACE + "issues/" + content.get("issueNumber"));
            String response = sendGet(url);
            System.out.println(response);
            JsonObject object = getJsonObject(response);
            JsonString title = (JsonString) object.get("title");
            System.out.println("Issue title : "+title.getString());
        } catch (MalformedURLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
		return null;
	}

	@Override
	public NodeList linkIssues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editIssue(Map<String, Serializable> content) {
		URL base;
		URL url = null;
		try {
			base = new URL(Config.API);
	        url = new URL(base, Config.WORKSPACE + "issues/"+Constants.ISSUE+"?_HttpMethod=PATCH");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    		
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url.toString());

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(6);
        params.add(new BasicNameValuePair("title", (String) content.get("title")));
        params.add(new BasicNameValuePair("body", (String) content.get("body")));
        params.add(new BasicNameValuePair("assignee", (String) content.get("assignee")));
        params.add(new BasicNameValuePair("milestone", (String) content.get("milestone")));
        params.add(new BasicNameValuePair("labels", (String) content.get("labels")));
        params.add(new BasicNameValuePair("assignees", (String) content.get("assignees")));
        try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


      //Execute and get the response.
        try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getIndent(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dumpSOAPElement(SOAPElement el, int indent) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queryIssues() {
		try {
            URL base = new URL(Config.API);
            URL url = new URL(base, Config.WORKSPACE + "issues");
            StringBuilder result = new StringBuilder();
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            String response = result.toString();
            System.out.println(response);
            
            JsonObject object;
            try {
                JsonReader jsonReader = Json.createReader(new StringReader(response));
                object = jsonReader.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            // TODO: query search, in order to perform query search, queryIssues() method should
            // take a parameter
        } catch (MalformedURLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
	}



    /**
    * This method convert JSon String into JSon Object
    * @param response
    */
    public JsonObject getJsonObject(String response) {
        JsonObject object = null;
        try {
            JsonReader jsonReader = Json.createReader(new StringReader(response));
            object = jsonReader.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return object;
    }

    /**
    * This method should later be merged into an utility package containing all HTTP requests
    * This method send a HTTP GET request to the given URL
    * @param url
    */
    public String sendGet(URL url) {
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }
}

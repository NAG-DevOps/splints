   /**
    * Simple JSON reader parser client (GET) for GitHub
    * Usage: https://bitbucket.org/soen487-w18-03/soen487-w18-team03/issues/8/github-json-parser
    */

package github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.*;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import fp.v11.splints.ISplints;

public class GitHub implements ISplints {

    // To be merged with Constant file
    static final int issue = 1;
    String issue_subject = "";
    String issue_status = "";
    String submitter = "";
    String assignees = "";
    String priority_number = "";
    String description = "";

    @Override
    public String createIssue(String title, String body, String assignee, 
    		int milestone, String [] labels, String [] assignees){
    		
    		URL base = new URL(Config.API);
        URL url = new URL(base, Config.WORKSPACE + "issues/");
    		
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(6);
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("body", body));
        params.add(new BasicNameValuePair("assignee", assignee));
        params.add(new BasicNameValuePair("milestone", milestone));
        params.add(new BasicNameValuePair("labels", labels));
        params.add(new BasicNameValuePair("assignees", assignees));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                return instream;
            } finally {
                instream.close();
            }
        }
        return null;
    }

    public void editIssue(){
    		
    }

    public void getIssueDetails(int piIssueNumber) {
        try {
            URL base = new URL(api);
            URL url = new URL(base, workspace + "issues/" + piIssueNumber);
            String response = sendGet(url);
            System.out.println(response);
            JsonObject object = getJsonObject(response);
            JsonString title = (JsonString) object.get("title");
            System.out.println("Issue title : "+title.getString());
        } catch (MalformedURLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    /**
    * This method convert JSon String into JSon Object
    * @param response
    */
    public JsonObject getJsonObject(String response) {
        JsonObject object;
        try (JsonReader jsonReader = Json.createReader(new StringReader(response))) {
            object = jsonReader.readObject();
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

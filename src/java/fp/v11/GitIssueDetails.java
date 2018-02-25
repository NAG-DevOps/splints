   /**
    * Simple JSON reader parser client (GET) for GitHub
    * Usage: https://bitbucket.org/soen487-w18-03/soen487-w18-team03/issues/8/github-json-parser
    */

package fp.v11;

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


public class GitIssueDetails {

    // To be merged with Constant file
    static final String api = "https://api.github.com/repos/";
    static final String workspace = "NAG-DevOps/splints/";
    static final int issue = 1;
    String issue_subject = "";
    String issue_status = "";
    String submitter = "";
    String assignees = "";
    String priority_number = "";
    String description = "";

    public static void main(String args[]) {
        getIssueDetails(issue);
    }

    public static void getIssueDetails(int piIssueNumber) {
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
    public static JsonObject getJsonObject(String response) {
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
    public static String sendGet(URL url) {
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

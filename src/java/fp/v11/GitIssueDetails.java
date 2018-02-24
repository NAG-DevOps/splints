package fp.v11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GitIssueDetails {

    // To be merged with Constant file
    static String api = "https://api.github.com/repos/";
    static String workspace = "NAG-DevOps/splints/";
    static int issue = 1;
    String issue_subject = "";
    String issue_status = "";
    String submitter = "";
    String assignees = "";
    String priority_number = "";
    String description = "";

    public static void main(String args[]) {
        getIssueDetails(issue);
    }
    
    public static void getIssueDetails(int piIssueNumber){
        try {
            URL base = new URL(api);
            URL url = new URL (base, workspace+"issues/"+piIssueNumber);
            String response = sendGet(url);
            System.out.println(response);
        } catch (MalformedURLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

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

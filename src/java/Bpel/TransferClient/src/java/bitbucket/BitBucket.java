package bitbucket;

import fp.ISplints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.soap.SOAPElement;
import org.json.*;
import org.w3c.dom.NodeList;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import utils.ContentMap;

/**
 *
 * @author soen487-w18-team03
 * @author soen487-w18-team08
 */
@WebService(serviceName = "BitBucket")
public class BitBucket implements ISplints {

    private String urlprefix = "https://api.bitbucket.org/1.0/repositories/soen487-w18-08/json-parser-issue-number/issues/";

    /**
     * Get Issue Details given IssueNumber
     *
     * @param content
     * @param issueNumber
     * @throws Exception
     */
    @WebMethod(operationName = "getIssueDetails")
    public ContentMap getIssueDetails(@WebParam(name = "content") ContentMap params) {
        JSONObject content = new JSONObject(params.getMap());
        if(content.has("issueId"))
        {
            System.out.println("Got Issue details from BitBucket:"+content.getString("issueId"));
            return params;
        }
        String inumber = Integer.toString((Integer)content.get("issueNumber"));
        JSONObject json;
        JSONObject json1;
        try {
            URL base = new URL(urlprefix);
            URL url = new URL(base, inumber);
            json = new JSONObject(getText(url));
			//System.out.println(response);

            String[] names = JSONObject.getNames(json);

            for (String str : names) {

                if (str.equals("reported_by")) {

                    System.out.println(str + ":");

                    json1 = new JSONObject("" + json.get(str));
                    String[] names1 = JSONObject.getNames(json1);

                    for (String str1 : names1) {
                        System.out.println(str1 + ":" + json1.get(str1));
                    }

                }
                if (!str.equals("reported_by")) {
                    System.out.println(str + ":" + json.get(str));
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get Text given URL
     *
     * @param url
     * @return response
     */
    private static String getText(URL url) {
        StringBuilder response = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return response.toString();
    }

    @Override
    @WebMethod(operationName = "createIssue")
    public String createIssue(@WebParam(name = "content") ContentMap params) {
        JSONObject content = new JSONObject(params.getMap());
        if(content.has("issueId"))
        {
            return "New BitBucket Issue:"+(String)content.get("issueId");
        }
        return null;
    }

    @Override
    public ContentMap linkIssues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editIssue(ContentMap content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void queryIssues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

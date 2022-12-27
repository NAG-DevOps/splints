package bitbucket;

import fp.ISplints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import javax.xml.soap.SOAPElement;
import org.json.*;
import org.w3c.dom.NodeList;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import utils.ContentMap;
import utils.ContentMapConverter;

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
        ContentMapConverter converter = new ContentMapConverter();
        JSONObject jsonContentMap = converter.getJsonObjectFromContentMap(params);
        String uri = Config.BASE_URI + Config.ACCOUNT_NAME + "/" + Config.REPO_SLUG + "/issues/" + (String)jsonContentMap.get("issueId");

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

        ContentMap responseContentMap = null;
        if (response != null) {
            System.out.println(response.getStatusLine());
            responseEntity = response.getEntity();

            if (responseEntity != null) {
                try {
                    //System.out.println("Response received: " + EntityUtils.toString(responseEntity));
                    responseContentMap = new ContentMap(EntityUtils.toString(responseEntity));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
        }
        return responseContentMap;
//        JSONObject content = new JSONObject(params.getMap());
//
//        String inumber = Integer.toString((Integer)content.get("issueNumber"));
//        JSONObject json;
//        JSONObject json1;
//        try {
//            URL base = new URL(urlprefix);
//            URL url = new URL(base, inumber);
//            json = new JSONObject(getText(url));
//			//System.out.println(response);
//
//            String[] names = JSONObject.getNames(json);
//
//            for (String str : names) {
//
//                if (str.equals("reported_by")) {
//
//                    System.out.println(str + ":");
//
//                    json1 = new JSONObject("" + json.get(str));
//                    String[] names1 = JSONObject.getNames(json1);
//
//                    for (String str1 : names1) {
//                        System.out.println(str1 + ":" + json1.get(str1));
//                    }
//
//                }
//                if (!str.equals("reported_by")) {
//                    System.out.println(str + ":" + json.get(str));
//                }
//            }
//
//        } catch (MalformedURLException e) {
//            System.err.println("ERROR: " + e.getMessage());
//        }
//        return null;
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
    public String createIssue(@WebParam(name = "content") ContentMap content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

package rt;

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

import fp.ISplints;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import stubs.RTStub;
import utils.ContentMap;

/**
 *
 * @author soen487-w18-team03
 */
@WebService(serviceName = "RT")
public class RT implements ISplints {

    @Override
    @WebMethod(operationName = "createIssue")
    public String createIssue(@WebParam(name = "content") ContentMap params) {
        JSONObject content = new JSONObject(params.getMap());
        String uri = Config.BASE_URI + "/ticket/new?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;

        HttpPost httppost = new HttpPost(uri);
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpEntity responseEntity = null;
        HttpResponse response = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("id: " + content.get("id")/*Constants.ID*/);
            stringBuilder.append("Queue: " + Constants.QUEUE);
            stringBuilder.append("Requestor: " + Constants.REQUESTOR);
            stringBuilder.append("Subject: " + content.get(Constants.SUBJECT));
            stringBuilder.append("CC: " + Constants.CC);
            stringBuilder.append("AdminCc: " + Constants.ADMIN_CC);
            stringBuilder.append("Owner: " + Constants.OWNER);
            stringBuilder.append("Status: " + Constants.STATUS);
            stringBuilder.append("Priority: " + Constants.PRIORITY);
            stringBuilder.append("InitialPriority: " + Constants.INITIAL_PRIORITY);
            stringBuilder.append("FinalPriority: " + Constants.FINAL_PRIORITY);
            stringBuilder.append("TimeEstimated: " + Constants.TIME_ESTIMATED);
            stringBuilder.append("Starts: " + Constants.STARTS);
            stringBuilder.append("Due: " + Constants.DUE);
            stringBuilder.append("Text: " + content.get(Constants.TEXT));
            stringBuilder.append("CF-CustomField: " + Constants.CUSTOM_FIELD);

            StringBody contentString = new StringBody(stringBuilder.toString(), ContentType.TEXT_PLAIN);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("content", contentString);
            httppost.setEntity(builder.build());
            System.out.println("Request: " + httppost.getRequestLine() + "\n");
            //TODO: switch to real system, stubbing for testing
            response = RTStub.createIssue(httppost, content.getString("id"));//httpclient.execute(httppost);
            responseEntity = response.getEntity();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (response != null) {
            System.out.println(response.getStatusLine() + "\n");
        } else {
            return null;
        }

        if (responseEntity != null) {
            System.out.println(responseEntity.getContentType() + "\nContent-Length: " + responseEntity.getContentLength());
            try {
                // InputStream inputStream = responseEntity.getContent();
                return EntityUtils.toString(responseEntity);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * ISplints does not have an issue closure method and the documentation for
     * RT does not provide anything regarding issue closure therefore it is
     * assumed that closing an issue is done by using the Resolved parameter
     * along with id to identify the issue.
     */
    public void closeIssue() {

        String uri = Config.BASE_URI + "/ticket/new?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;

        HttpPost httppost = new HttpPost(uri);
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpEntity responseEntity = null;
        HttpResponse response = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("id: " + Constants.ID);
            stringBuilder.append("Resolved: " + Constants.RESOLVED);

            StringBody content = new StringBody(stringBuilder.toString(), ContentType.TEXT_PLAIN);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("content", content);
            httppost.setEntity(builder.build());
            System.out.println("Request: " + httppost.getRequestLine() + "\n");

            response = httpclient.execute(httppost);
            responseEntity = response.getEntity();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (response != null) {
            System.out.println(response.getStatusLine() + "\n");
        } else {
            System.out.println("An error occurred");
        }

        if (responseEntity != null) {
            System.out.println(responseEntity.getContentType() + "\nContent-Length: " + responseEntity.getContentLength());
            System.out.println("Response for issue closure received: " + responseEntity.toString());
        } else {
            System.out.println("An error occurred");
        }

    }

    @Override
    @WebMethod(operationName = "getIssueDetails")
    public ContentMap getIssueDetails(@WebParam(name = "content") ContentMap params) {
        JSONObject content = new JSONObject(params.getMap());
        JSONObject json;
        String query = content.get("id").toString();
        String urlprefix = "http://" + "/REST/1.0/search/ticket?query=" + query;

        try {
            //URL base = new URL(urlprefix);
            URL url = new URL(urlprefix);
            //TODO: switch to real system, stubbing for testing
            json = RTStub.getIssueDetails(query);//new JSONObject(getText(url));

            String[] names = JSONObject.getNames(json);

            for (String str : names) {
                if (str.equals(query)) {
                    System.out.println(str + ":" + json.get(str));
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ContentMap linkIssues() {
        return null;
    }

    @Override
    public void editIssue(ContentMap params) {
        JSONObject content = new JSONObject(params.getMap());
        String url = "http://" + content.get("host") + "/REST/1.0/ticket/" + content.get("issueId") + "/edit?user=" + content.get("username") + "&pass=" + content.get("password") + "";
        String postData = "AdminCc: userX\nText: This is a REST test edit ticket\n";
        try {
            sendPost(url, postData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void queryIssues() {
    }

    /**
     * Get Text given URL
     *
     * @param url
     * @return response
     */
    private String getText(URL url) {
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

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return response.toString();
    }

    /**
     * Send Post HTTP Request
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    private static void sendPost(String url, String params) throws Exception {

        // open URL connection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setDoOutput(true);

        // add content
        DataOutputStream w = new DataOutputStream(con.getOutputStream());
        w.writeBytes(params);
        w.flush();
        w.close();

        // get response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());
    }

}

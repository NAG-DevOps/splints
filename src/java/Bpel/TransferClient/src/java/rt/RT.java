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
import java.io.Serializable;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONObject;
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

        if(content.has("issueId"))
        {
            return (String)content.get("issueId");
        }
        String uri = Config.BASE_URI + "/ticket/new?user=" + Config.AGENT_USERNAME + "&pass=" + Config.AGENT_PASSWORD;

        HttpPost httppost = new HttpPost(uri);
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpEntity responseEntity = null;
        HttpResponse response = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("id: " + Constants.ID);
            stringBuilder.append("Queue: " + Constants.QUEUE);
            stringBuilder.append("Requestor: " + Constants.REQUESTOR);
            stringBuilder.append("Subject: " + Constants.SUBJECT);
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
            stringBuilder.append("Text: " + Constants.TEXT);
            stringBuilder.append("CF-CustomField: " + Constants.CUSTOM_FIELD);

            StringBody contentString = new StringBody(stringBuilder.toString(), ContentType.TEXT_PLAIN);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("content", contentString);
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
            return null;
        }

        if (responseEntity != null) {
            System.out.println(responseEntity.getContentType() + "\nContent-Length: " + responseEntity.getContentLength());
            return responseEntity.toString();
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
    public ContentMap getIssueDetails(@WebParam(name = "content") ContentMap content) {
        return null;
    }

    @Override
    public ContentMap linkIssues() {
        return null;
    }

    @Override
    public void editIssue(ContentMap content) {

    }

    @Override
    public void queryIssues() {
    }

}

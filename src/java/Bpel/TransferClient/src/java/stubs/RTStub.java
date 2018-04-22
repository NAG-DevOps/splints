package stubs;

import fp.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONObject;

/**
 * Class with mocks RT API responses
 * @author soen487-w18-team03
 */
public class RTStub {

    public static HttpResponse createIssue(HttpPost post, String id) {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setText("Created new RT issue: " + id);
        response.setEntity(entityBuilder.build());
        return response;
    }

    public static JSONObject getIssueDetails(String id) {
        JSONObject response = new JSONObject();
        response.put(fp.Constants.PRIORITY_NUMBER, "1");
        response.put(fp.Constants.ISSUE_STATUS, "review");
        response.put(fp.Constants.DESCRIPTION, "buggy");
        response.put(fp.Constants.ASSIGNEES, "people");
        response.put(fp.Constants.CONTACT_EMAIL, "contact@email.com");
        response.put(fp.Constants.WORKSPACE, "23");
        response.put(fp.Constants.ISSUE_SUBJECT, "bug fix");
        response.put(fp.Constants.ISSUE, id);
        return response;
    }
}

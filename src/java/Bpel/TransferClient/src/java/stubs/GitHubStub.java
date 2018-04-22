package stubs;

import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONObject;

/**
 * Class with mocks GitHub API responses
 * @author soen487-w18-team03
 */
public class GitHubStub {
    
    public static HttpResponse createIssue(HttpPost post, String id)
    {
        HttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse response = factory.newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setText("Created new GitHub issue: "+id);
        response.setEntity(entityBuilder.build());
        return response;
    }

    public static String getIssueDetails(URL url, String id)
    {
        JSONObject response = new JSONObject();
        response.put("id", id);
        response.put("title", "Stub Issue");
        return response.toString();
    }
}

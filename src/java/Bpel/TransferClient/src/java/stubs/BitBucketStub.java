package stubs;

import java.net.URL;
import org.json.JSONObject;

/**
 * Class with mocks BitBucket API responses
 * @author soen487-w18-team03
 */
public class BitBucketStub {

    public static String getIssueDetails(URL url, String id) {
        JSONObject response = new JSONObject();
        response.put("id", id);
        response.put("title", "Stub Issue");
        response.put("body","bitbucket body");
        response.put("assignee","person");
        response.put("milestone","1");
        response.put("labels","Bug");
        response.put("assignees","persons");
        return response.toString();
    }
}

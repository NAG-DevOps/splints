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
        response.put(fp.Constants.PRIORITY_NUMBER, "1");
        response.put(fp.Constants.ISSUE_STATUS, "review");
        response.put(fp.Constants.DESCRIPTION, "buggy");
        response.put(fp.Constants.ASSIGNEES, "people");
        response.put(fp.Constants.CONTACT_EMAIL, "contact@email.com");
        response.put(fp.Constants.WORKSPACE, "23");
        response.put(fp.Constants.ISSUE_SUBJECT, "bug fix");
        response.put(fp.Constants.ISSUE, id);
        response.put("id",id);
        response.put("title", "Bug");
        response.put("body", "fix bug");
        response.put("assignee", "person");
        response.put("milestone", "2");
        response.put("labels", "Bug");
        response.put("assignees", "people");
        response.put(rt.Constants.SUBJECT, "Test");
        response.put(rt.Constants.TEXT, "Description test");
        return response.toString();
    }
}

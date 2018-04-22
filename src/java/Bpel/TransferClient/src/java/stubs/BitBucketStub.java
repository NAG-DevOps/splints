/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;

import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author chris
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

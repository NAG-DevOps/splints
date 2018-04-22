/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONObject;

/**
 *
 * @author chris
 */
@WebService(serviceName = "ContentMapConverter")
public class ContentMapConverter {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getCreateIssueInputContent")
    public ContentMap getCreateIssueInputContent(@WebParam(name = "issueId") String issueId,@WebParam(name = "projectId") String projectId) {
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("issueId", issueId);
//        map.put("projectId", projectId);
        Map<String,String> map = new HashMap<String,String>() {};
        map.put(fp.Constants.ISSUE, issueId);
        map.put(fp.Constants.WORKSPACE, projectId);
        map.put("id", issueId);
        JSONObject response = new JSONObject(map);
        System.out.println("resp:"+response.toString());
        return new ContentMap(response.toString());
    }
}

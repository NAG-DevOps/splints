/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import bitbucket.BitBucket;
import fp.FootPrints11;
import fp.FootPrints12;
import fp.ISplints;
import github.GitHub;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import org.json.JSONObject;
import rt.RT;
import utils.ContentMap;
import utils.ContentMapConverter;
import utils.PATCH;

/**
 * REST Web Service
 *
 * @author soen487-w18-team03
 */
@Path("splints")
public class RESTSplintsService {

    private ISplints service;
    private final String FP11 = "fp11";
    private final String FP12 = "fp12";
    private final String RT = "rt";
    private final String GITHUB = "github";
    private final String BITBUCKET = "bitbucket";
    ContentMapConverter contentMapConverter = new ContentMapConverter();

    @GET
    @Produces("application/json")
    @Path("issue/{serviceType}/{projectNumber}/{issueNumber}")
    public String getIssue(@PathParam("serviceType") String serviceType, @PathParam("projectNumber") String projectNumber, @PathParam("issueNumber") String issueNumber) {
        JSONObject content = new JSONObject();
        switch (serviceType) {
            case FP11:
                content.put(fp.Constants.ISSUE, issueNumber);
                content.put(fp.Constants.WORKSPACE, projectNumber);

                service = new FootPrints11();
                break;
            case FP12:
                content.put(fp.Constants.ISSUE, issueNumber);
                content.put(fp.Constants.WORKSPACE, projectNumber);

                service = new FootPrints12();
                break;
            case RT:
                content.put("id", issueNumber);
                service = new RT();
                break;
            case GITHUB:
                content.put("id", issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
                content.put("id", issueNumber);
                service = new BitBucket();
                break;
            default:
                content.put(fp.Constants.ISSUE, issueNumber);
                content.put(fp.Constants.WORKSPACE, projectNumber);

                service = new FootPrints11();
                break;
        }
        ContentMap requestInfo = new ContentMap(content.toString());
        ContentMap issueDetails = service.getIssueDetails(requestInfo);
        return issueDetails.getMap();
    }

    @GET
    @Produces("application/json")
    @Path("issues/{serviceType}")
    public String getIssues(@PathParam("serviceType") String serviceType) {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Consumes("application/json")
    @Path("issue/{serviceType}/{issueNumber}")
    public void putIssue(@PathParam("serviceType") String serviceType, @PathParam("issueNumber") int issueNumber, String issueDetails) {
        JSONObject request = new JSONObject(issueDetails);
        switch (serviceType) {
            case FP11:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
            case FP12:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints12();
                break;
            case RT:
                request.put("id", issueNumber);
                service = new RT();
                break;
            case GITHUB:
                request.put("id", issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
                request.put("id", issueNumber);
                service = new BitBucket();
                break;
            default:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
        }
        ContentMap issueChanges = new ContentMap(request.toString());
        service.editIssue(issueChanges);
    }

    @DELETE
    @Produces("application/json")
    @Path("issue/{serviceType}/{issueNumber}")
    public String deleteIssue(@PathParam("serviceType") String serviceType, @PathParam("issueNumber") int issueNumber) {
        throw new UnsupportedOperationException();
    }

    @PATCH
    @Consumes("application/json")
    @Path("issue/{serviceType}/{issueNumber}")
    public void patchIssue(@PathParam("serviceType") String serviceType, @PathParam("issueNumber") int issueNumber, String issueDetails) {
        JSONObject request = new JSONObject(issueDetails);
        switch (serviceType) {
            case FP11:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
            case FP12:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints12();
                break;
            case RT:
                request.put("id", issueNumber);
                service = new RT();
                break;
            case GITHUB:
                request.put("id", issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
                request.put("id", issueNumber);
                service = new BitBucket();
                break;
            default:
                request.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
        }
        ContentMap issueChanges = new ContentMap(request.toString());
        service.editIssue(issueChanges);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("issue/{serviceType}")
    public String postIssue(@PathParam("serviceType") String serviceType, String issueDetails) {
        ContentMap newIssueDetails = new ContentMap(issueDetails);
        switch (serviceType) {
            case FP11:
                service = new FootPrints11();
                break;
            case FP12:
                service = new FootPrints12();
                break;
            case RT:
                service = new RT();
                break;
            case GITHUB:
                service = new GitHub();
                break;
            case BITBUCKET:
                service = new BitBucket();
                break;
            default:
                service = new FootPrints11();
                break;
        }
        JSONObject response = new JSONObject();
        response.put("new id", service.createIssue(newIssueDetails));
        return response.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

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

    @GET
    @Produces("application/json")
    @Path("issue/{serviceType}/{issueNumber}")
    public String getIssue(@PathParam("serviceType") String serviceType, @PathParam("issueNumber") int issueNumber) {
        Map<String, Serializable> content = new HashMap<String, Serializable>();
        switch (serviceType) {
            case FP11:
                content.add(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
            case FP12:
                content.add(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints12();
                break;
            case RT:
                content.add(RT.Constants.ISSUE, issueNumber);
                service = new RT();
                break;
            case GITHUB:
                content.add(GitHub.Constants.ISSUE, issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
                content.add(BitBucket.Constants.ISSUE, issueNumber);
                service = new BitbucketCloud();
                break;
            default:
                content.add(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
        }
        Map<String, Serializable> issueDetails = service.getIssueDetails(content);
        JsonObjectBuilder response = Json.createObjectBuilder();
        for (Map.Entry<String, Serializable> detail : issueDetails.entrySet()) {
            response.add(detail.getKey(), String.valueOf(detail.getValue()));
        }
        return response.build().toString();
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
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        switch (serviceType) {
            case FP11:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
            case FP12:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints12();
                break;
            case RT:
        issueChanges.put(RT.Constants.ISSUE, issueNumber);
                service = new RT();
                break;
            case GITHUB:
        issueChanges.put(GitHub.Constants.ISSUE, issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
        issueChanges.put(BitBucket.Constants.ISSUE, issueNumber);
                service = new BitbucketCloud();
                break;
            default:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
        }
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
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        switch (serviceType) {
            case FP11:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
            case FP12:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints12();
                break;
            case RT:
        issueChanges.put(RT.Constants.ISSUE, issueNumber);
                service = new RT();
                break;
            case GITHUB:
        issueChanges.put(GitHub.Constants.ISSUE, issueNumber);
                service = new GitHub();
                break;
            case BITBUCKET:
        issueChanges.put(BitBucket.Constants.ISSUE, issueNumber);
                service = new BitbucketCloud();
                break;
            default:
        issueChanges.put(fp.Constants.ISSUE, issueNumber);
                service = new FootPrints11();
                break;
        }
        service.editIssue(issueChanges);
    }

    @POST
    @Consumes("application/json")
    @Path("issue/{serviceType}")
    public void postIssue(@PathParam("serviceType") String serviceType, String issueDetails) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
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
                service = new BitbucketCloud();
                break;
            default:
                service = new FootPrints11();
                break;
        }
        service.createIssue(issueChanges);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import bitbucket.BitbucketCloud;
import fp.v11.splints.FootPrints11;
import fp.v11.splints.ISplints;
import fp.v12.FootPrints12;
import rt.RT;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * RESTful Web Service for Splints
 * 
 * @author Daniil Karpov
 */
@Path("splints")
public class RestfulSplintsWebService {
    
    private ISplints issueService;
    
    @GET
    @Path("{serviceProvider}/issue/{issueNumber}")
    @Produces("application/json")
    public String getIssue(@PathParam("serviceProvider") String serviceProvider, @PathParam("issueNumber") int issueNumber) {
        Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
        
        switch(serviceProvider) {
            case ServiceProviderConstants.FP11_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, Integer.toString(issueNumber));
                issueService = new FootPrints11();
                break;
            case ServiceProviderConstants.FP12_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, Integer.toString(issueNumber));
                issueService = new FootPrints12();
                break;
            case ServiceProviderConstants.RT_SERVICE:
                contentMap.put(rt.Constants.ID, Integer.toString(issueNumber));
                issueService = new RT();
                break;
            case ServiceProviderConstants.GITHUB_SERVICE:
                contentMap.put(github.Constants.ISSUE, Integer.toString(issueNumber));
                issueService = new GitHub();
                break;    
            case ServiceProviderConstants.BITBUCKET_SERVICE:
                contentMap.put(bitbucket.Constants.ISSUE, Integer.toString(issueNumber));
                issueService = new BitbucketCloud();
                break;    
            default:
                contentMap.put(bitbucket.Constants.ISSUE, Integer.toString(issueNumber));
                issueService = new BitbucketCloud();
                break;
        }
        
        Map<String, Serializable> issueDetails = issueService.getIssueDetails(contentMap);
        JsonObjectBuilder response = Json.createObjectBuilder();
        for (Map.Entry<String, Serializable> detail : issueDetails.entrySet()) {
            response.add(detail.getKey(), String.valueOf(detail.getValue()));
        }
        return response.build().toString();
    }
    
    @GET
    @Path("{serviceProvider}/issues")
    @Produces("application/json")
    public String getIssues(@PathParam("serviceProvider") String serviceProvider) {
        System.out.println("Currently unsupported");
        throw new UnsupportedOperationException();
    }
    
    @DELETE
    @Path("{serviceProvider}/issue/{issueNumber}")
    @Produces("application/json")
    public String deleteIssue(@PathParam("serviceProvider") String serviceType, @PathParam("issueNumber") int issueNumber) {
        System.out.println("Currently unsupported");
        throw new UnsupportedOperationException();
    }
    
    @PUT
    @Path("{serviceProvider}/issue/{issueNumber}")
    @Consumes("application/json")
    public void putIssue(@PathParam("serviceType") String serviceProvider, @PathParam("issueNumber") int issueNumber, String issueParams) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueParams));
        JsonObject issueParamsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueParamsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            contentMap.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        switch (serviceProvider) {
            case ServiceProviderConstants.FP11_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, issueNumber);
                issueService = new FootPrints11();
                break;
            case ServiceProviderConstants.FP12_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, issueNumber);
                issueService = new FootPrints12();
                break;
            case ServiceProviderConstants.RT_SERVICE:
                contentMap.put(rt.Constants.ID, issueNumber);
                issueService = new RT();
                break;
            case ServiceProviderConstants.GITHUB_SERVICE:
                contentMap.put(github.Constants.ISSUE, issueNumber);
                issueService = new GitHub();
                break;
            case ServiceProviderConstants.BITBUCKET_SERVICE:
                contentMap.put(bitbucket.Constants.ISSUE, issueNumber);
                issueService = new BitbucketCloud();
                break;
            default:
                contentMap.put(bitbucket.Constants.ISSUE, issueNumber);
                issueService = new BitbucketCloud();
                break;
        }
        issueService.editIssue(contentMap);
    }

    @POST
    @Path("{serviceProvider}/issue")
    @Consumes("application/json")
    public void postIssue(@PathParam("serviceProvider") String serviceProvider, String issueParams) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueParams));
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : json.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            contentMap.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        switch (serviceProvider) {
            case ServiceProviderConstants.FP11_SERVICE:
                issueService = new FootPrints11();
                break;
            case ServiceProviderConstants.FP12_SERVICE:
                issueService = new FootPrints12();
                break;
            case ServiceProviderConstants.RT_SERVICE:
                issueService = new RT();
                break;
            case ServiceProviderConstants.GITHUB_SERVICE:
                issueService = new GitHub();
                break;
            case ServiceProviderConstants.BITBUCKET_SERVICE:
                issueService = new BitbucketCloud();
                break;
            default:
                issueService = new BitbucketCloud();
                break;
        }
        issueService.createIssue(contentMap);
    }
    
    @PATCH
    @Path("{serviceProvider}/issue/{issueNumber}")
    @Consumes("application/json")
    public void patchIssue(@PathParam("serviceProvider") String serviceProvider, @PathParam("issueNumber") int issueNumber, String issueParams) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueParams));
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : json.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            contentMap.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        switch (serviceProvider) {
            case ServiceProviderConstants.FP11_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, issueNumber);
                issueService = new FootPrints11();
                break;
            case ServiceProviderConstants.FP12_SERVICE:
                contentMap.put(fp.v11.splints.Constants.ISSUE, issueNumber);
                issueService = new FootPrints12();
                break;
            case ServiceProviderConstants.RT_SERVICE:
                contentMap.put(rt.Constants.ID, issueNumber);
                issueService = new RT();
                break;
            case ServiceProviderConstants.GITHUB_SERVICE:
                contentMap.put(github.Constants.ISSUE, issueNumber);
                issueService = new GitHub();
                break;
            case ServiceProviderConstants.BITBUCKET_SERVICE:
                contentMap.put(bitbucket.Constants.ISSUE, issueNumber);
                issueService = new BitbucketCloud();
                break;
            default:
                contentMap.put(bitbucket.Constants.ISSUE, issueNumber);
                issueService = new BitbucketCloud();
                break;
        }
        issueService.editIssue(contentMap);
    }
}

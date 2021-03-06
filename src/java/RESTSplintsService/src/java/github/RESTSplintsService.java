/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package github;

import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
 * @author chris
 */
@Path("github")
public class RESTSplintsService {

    private ISplints github;
            /**
             * Creates a new instance of RESTSplintsService
             */

    public RESTSplintsService() {
        github = new GitHub();
    }

    @GET
    @Produces("application/json")
    @Path("issue/{issueNumber}")
    public String getIssue(@PathParam("issueNumber") int issueNumber) {
        Map<String, Serializable> content = new HashMap<String, Serializable>();
        content.add(GitHub.Constants.ISSUE, issueNumber);
        Map<String, Serializable> issueDetails = github.getIssueDetails(content);
        JsonObjectBuilder response = Json.createObjectBuilder();
        for (Map.Entry<String, Serializable> detail : issueDetails.entrySet()) {
            response.add(detail.getKey(), String.valueOf(detail.getValue()));
        }
        return response.build().toString();
    }

    @GET
    @Produces("application/json")
    @Path("issues")
    public String getIssues() {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Consumes("application/json")
    @Path("issue/{issueNumber}")
    public void putIssue(@PathParam("issueNumber") int issueNumber, String issueDetails) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        issueChanges.put(GitHub.Constants.ISSUE, issueNumber);
        github.editIssue(issueChanges);
    }

    @DELETE
    @Produces("application/json")
    @Path("issue/{issueNumber}")
    public String deleteIssue(@PathParam("issueNumber") int issueNumber) {
        throw new UnsupportedOperationException();
    }

    @PATCH
    @Consumes("application/json")
    @Path("issue/{issueNumber}")
    public void patchIssue(@PathParam("issueNumber") int issueNumber, String issueDetails) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        issueChanges.put(GitHub.Constants.ISSUE, issueNumber);
        github.editIssue(issueChanges);
    }

    @POST
    @Consumes("application/json")
    @Path("issue")
    public void postIssue(String issueDetails) {
        JsonReader jsonReader = Json.createReader(new StringReader(issueDetails));
        JsonObject issueDetailsJson = jsonReader.readObject();
        jsonReader.close();
        Map<String, Serializable> issueChanges = new HashMap<String, Serializable>();
        for (Map.Entry<String, JsonValue> detail : issueDetailsJson.entrySet()) {
            Class<?> valueClass = detail.getValue().getClass();
            issueChanges.put(detail.getKey(), (Serializable) valueClass.cast(detail.getValue()));
        }
        github.createIssue(issueChanges);
    }
}

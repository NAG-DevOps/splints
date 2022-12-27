package fp.v11.splints;

import rt.RT;
import rt.Constants;
import github.GitHub;
import bitbucket.BitbucketCloud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;

/**
 * Client for Transferring between ISplints inheritors
 *
 * @author soen487-w18-team03
 */
public class FPIssueTransferClient {

    private ISplints fp11;
    
    public FPIssueTransferClient() {
        fp11 = new FootPrints11();
    }

    /**
     * Create issue to RT from FP11
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToRT(int issueId, int projectId) {
        RT rt = new RT();
        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
        fpIssueParams.put(Constants.WORKSPACE, projectId);
        fpIssueParams.put(Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.add(rt.Constants.SUBJECT,context.item(Constants.ISSUE_SUBJECT).getNodeValue());
        details.add(rt.Constants.STATUS,context.item(Constants.ISSUE_STATUS).getNodeValue());
        details.add(rt.Constants.CREATOR,context.item(Constants.SUBMITTER).getNodeValue());
        details.add(rt.Constants.TOLD,context.item(Constants.ASSIGNEES).getNodeValue());
        details.add(rt.Constants.PRIORITY ,context.item(Constants.PRIORITY_NUMBER).getNodeValue());
        details.add(rt.Constants.TEXT ,context.item(Constants.DESCRIPTION).getNodeValue());
        details.add(rt.Constants.REQUESTOR ,context.item(Constants.DESCRIPTION).getNodeValue());
        rt.createIssue(details);
    }

    /**
     * Create issue to Github from FP11
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToGitHub(int issueId, int projectId) {
        GitHub github = new GitHub();
        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
        fpIssueParams.put(Constants.WORKSPACE, projectId);
        fpIssueParams.put(Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.add(github.Constants.SUBJECT,context.item(Constants.ISSUE_SUBJECT).getNodeValue());
        details.add(github.Constants.BODY ,context.item(Constants.DESCRIPTION).getNodeValue());
        details.add(github.Constants.ASSIGNEES,context.item(Constants.ASSIGNEES).getNodeValue());
        github.createIssue(details);    
    }

    /**
     * Create issue to BitBucket from FP11
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToBitbucket(int issueId, int projectId) {
        BitbucketCloud bitbucket = new BitbucketCloud();
        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
        fpIssueParams.put(Constants.WORKSPACE, projectId);
        fpIssueParams.put(Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.add(bitbucket.Constants.STATUS ,context.item(Constants.ISSUE_STATUS).getNodeValue());
        details.add(bitbucket.Constants.PRIORITY ,context.item(Constants.PRIORITY_NUMBER).getNodeValue());
        details.add(bitbucket.Constants.SUBJECT ,context.item(Constants.ISSUE_SUBJECT).getNodeValue());
        details.add(bitbucket.Constants.RESPONSIBLE ,context.item(Constants.ASSIGNEES).getNodeValue());
        details.add(bitbucket.Constants.CONTENT ,context.item(Constants.DESCRIPTION).getNodeValue());
        details.add(bitbucket.Constants.KIND ,context.item(Constants.TICKET_TYPE).getNodeValue());
        bitbucket.createIssue(details);
    }

}

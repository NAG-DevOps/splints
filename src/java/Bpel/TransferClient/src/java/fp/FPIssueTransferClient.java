package fp;

import github.GitHub;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;
import rt.RT;
import rt.Constants;

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
        fpIssueParams.put(fp.Constants.WORKSPACE, projectId);
        fpIssueParams.put(fp.Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.put(Constants.SUBJECT,context.item(Integer.valueOf(fp.Constants.ISSUE_SUBJECT)).getNodeValue());
        details.put(Constants.STATUS,context.item(Integer.valueOf(fp.Constants.ISSUE_STATUS)).getNodeValue());
        //details.put(Constants.CREATOR,context.item(Constants.SUBMITTER).getNodeValue());
        //details.put(Constants.TOLD,context.item(Constants.ASSIGNEES).getNodeValue());
        details.put(Constants.PRIORITY ,context.item(Integer.valueOf(fp.Constants.PRIORITY_NUMBER)).getNodeValue());
        details.put(Constants.TEXT ,context.item(Integer.valueOf(fp.Constants.DESCRIPTION)).getNodeValue());
        details.put(Constants.REQUESTOR ,context.item(Integer.valueOf(fp.Constants.SUBMITTER)).getNodeValue());
        rt.createIssue(details);
    }

    /**
     * Create issue to GitHub from FP11
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToGitHub(int issueId, int projectId) {
        GitHub github = new GitHub();
        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
        fpIssueParams.put(fp.Constants.WORKSPACE, projectId);
        fpIssueParams.put(fp.Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        //TODO: Implement
        //details.put(github.Constants.SUBJECT,context.item(Integer.valueOf(fp.Constants.ISSUE_SUBJECT)).getNodeValue());
        //details.put(github.Constants.BODY ,context.item(Integer.valueOf(fp.Constants.DESCRIPTION)).getNodeValue());
        //details.put(github.Constants.ASSIGNEES,context.item(Integer.valueOf(fp.Constants.ASSIGNEES)).getNodeValue());
        github.createIssue(details);
        throw new UnsupportedOperationException();
    }

    /**
     * Create issue to BitBucket from FP11
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToBitbucket(int issueId, int projectId) {
        //TODO: Implement
//        BitBucket bitbucket = new BitBucket();
//        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
//        fpIssueParams.put(Constants.WORKSPACE, projectId);
//        fpIssueParams.put(Constants.ISSUE, issueId);
//        NodeList context = fp11.getIssueDetails(fpIssueParams);
//        Map<String, Serializable> details = new HashMap<String, Serializable>();
//        details.put(bitbucket.Constants.STATUS ,context.item(Integer.valueOf(Constants.ISSUE_STATUS)).getNodeValue());
//        details.put(bitbucket.Constants.PRIORITY ,context.item(Integer.valueOf(Constants.PRIORITY_NUMBER)).getNodeValue());
//        details.put(bitbucket.Constants.SUBJECT ,context.item(Integer.valueOf(Constants.ISSUE_SUBJECT)).getNodeValue());
//        details.put(bitbucket.Constants.RESPONSIBLE ,context.item(Integer.valueOf(Constants.ASSIGNEES)).getNodeValue());
//        details.put(bitbucket.Constants.CONTENT ,context.item(Integer.valueOf(Constants.DESCRIPTION)).getNodeValue());
//        details.put(bitbucket.Constants.KIND ,context.item(Integer.valueOf(Constants.TICKET_TYPE)).getNodeValue());
//        bitbucket.createIssue(details);
        throw new UnsupportedOperationException();
    }

}

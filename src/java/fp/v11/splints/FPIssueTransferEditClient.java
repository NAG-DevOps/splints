package fp.v11.splints;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;

/**
 * Class that takes a pair of tickets, source and destination, and will
 * update the destination if it exists
 */
public class FPIssueTransferEditClient {

    private ISplints fp11;

    public FPIssueTransferEditClient() {
        fp11 = new FootPrints11();
    }

    /**
     * Create issue to RT from FP11
     *
     * @param issueId
     * @param projectId
     */
    public void transferEditToRT(int issueId, int projectId) {
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
        try {
            rt.editIssue(details);
        } catch(Exception e) {
            System.out.println("The issue does not exist");
        }
    }

    /**
     * Create issue to Github from FP11
     *
     * @param issueId
     * @param projectId
     */
    public void transferEditToGitHub(int issueId, int projectId) {
        GitHub github = new GitHub();
        Map<String, Serializable> fpIssueParams = new HashMap<String, Serializable>();
        fpIssueParams.put(Constants.WORKSPACE, projectId);
        fpIssueParams.put(Constants.ISSUE, issueId);
        NodeList context = fp11.getIssueDetails(fpIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.add(github.Constants.SUBJECT,context.item(Constants.ISSUE_SUBJECT).getNodeValue());
        details.add(github.Constants.BODY ,context.item(Constants.DESCRIPTION).getNodeValue());
        details.add(github.Constants.ASSIGNEES,context.item(Constants.ASSIGNEES).getNodeValue());
        try {
            github.editIssue(details);
        } catch(Exception e) {
            System.out.println("The issue does not exist");
        }
    }

    /**
     * Create issue to BitBucket from FP11
     *
     * @param issueId
     * @param projectId
     */
    public void transferEditToBitbucket(int issueId, int projectId) {
        BitBucket bitbucket = new BitBucket();
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
        try {
            bitbucket.editIssue(details);
        } catch(Exception e) {
            System.out.println("The issue does not exist");
        }
    }
}

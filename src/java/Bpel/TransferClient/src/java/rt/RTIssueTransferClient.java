package rt;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.NodeList;
import fp.ISplints;
import fp.FootPrints11;

/**
 * Client for Transferring between ISplints inheritors
 *
 * @author soen487-w18-team03
 */
public class RTIssueTransferClient {

    private ISplints rt;
    
    public RTIssueTransferClient() {
        rt = new RT();
    }

    /**
     * Create issue to FP11 from RT
     * 
     * @param issueId
     * @param projectId 
     */
    public void transferToFP11(int issueId, int projectId) {
        FootPrints11 fp11 = new FootPrints11();
        Map<String, Serializable> rtIssueParams = new HashMap<String, Serializable>();
        //TODO: Set issueParams to getDetails
        NodeList context = rt.getIssueDetails(rtIssueParams);
        Map<String, Serializable> details = new HashMap<String, Serializable>();
        details.put(fp.Constants.ISSUE_SUBJECT,context.item(Integer.valueOf(Constants.SUBJECT)).getNodeValue());
        details.put(fp.Constants.ISSUE_STATUS,context.item(Integer.valueOf(Constants.STATUS)).getNodeValue());
        //details.put(fp.Constants.SUBMITTER,context.item(Constants.CREATOR).getNodeValue());
        //details.put(fp.Constants.ASSIGNEES,context.item(Constants.TOLD).getNodeValue());
        details.put(fp.Constants.PRIORITY_NUMBER ,context.item(Integer.valueOf(Constants.PRIORITY)).getNodeValue());
        details.put(fp.Constants.DESCRIPTION ,context.item(Integer.valueOf(Constants.TEXT)).getNodeValue());
        details.put(fp.Constants.SUBMITTER ,context.item(Integer.valueOf(Constants.REQUESTOR)).getNodeValue());
        fp11.createIssue(details);
    }
}

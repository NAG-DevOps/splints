package fp;

import java.io.Serializable;
import javax.xml.soap.SOAPElement;

import org.w3c.dom.NodeList;

import java.util.Map;
import utils.ContentMap;

/**
 * ISplints Interface Interface for interacting with Splints
 *
 * @author soen487-w18-team03
 * @author Vincent Fugnitto from soen487-team08
 */
public interface ISplints {

    /**
     * Creates an issue
     *
     * @return Ticket Number
     */
    public String createIssue(ContentMap content);
    //public String createIssue(Map<String,String> idetails);

    /**
     * Gets issue details
     *
     */
    public ContentMap getIssueDetails(ContentMap content);

    /**
     * Links two issues
     *
     */
    public ContentMap linkIssues();

    /**
     * Edit Issue
     */
    void editIssue(ContentMap content);

    /**
     * Query Issues
     */
    void queryIssues();
}

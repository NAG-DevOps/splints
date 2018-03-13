package fp.v11.splints;

import org.w3c.dom.NodeList;

/**
 * Interface for interacting with Splints
 * @author soen487-w18-team03
 */
public interface ISplints {
    
    /**
     * Creates an issue
     * @return Ticket Number
     */
    public String createIssue();
    
    /**
     * Gets issue details
     * 
     */
    public NodeList getIssueDetails();
    
    /**
     * Links two issues
     * 
     */
    public NodeList linkIssues();
}

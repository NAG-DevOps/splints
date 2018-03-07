package fp.v11.splints;

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
    public void getIssueDetails();
    
    /**
     * Links two issues
     * 
     */
    public void linkIssues();
}

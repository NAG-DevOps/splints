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
     * @return ReplyBody
     */
    public Object getIssueDetails();
    
    /**
     * Links two issues
     * @return 
     */
    public Object linkIssues();
}

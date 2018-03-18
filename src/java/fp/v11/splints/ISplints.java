package fp.v11.splints;

import javax.xml.soap.SOAPElement;
import org.w3c.dom.NodeList;

/**
 * ISplints Interface
 * Interface for interacting with Splints
 *
 * @author soen487-w18-team03
 * @author Vincent Fugnitto from soen487-team08
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

	/**
	 * Edit Issue
	 */
	void editIssue();

	/**
	 * Get Indent
	 * @param num
	 * @return indent
	 */
	String getIndent(int num);

	/**
	 * Dump SOAP Element
	 * @param el
	 * @param indent
	 * @throws Exception
	 */
	void dumpSOAPElement(SOAPElement el, int indent) throws Exception;

	/**
	 * Query Issues
	 */
	void queryIssues();
}


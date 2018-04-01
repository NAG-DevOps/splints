package fp;

import java.io.Serializable;
import javax.xml.soap.SOAPElement;

import org.w3c.dom.NodeList;

import java.util.Map;

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
    public String createIssue(Map<String,Serializable> content);
    //public String createIssue(Map<String,String> idetails);

    /**
     * Gets issue details
     *
     */
    public NodeList getIssueDetails(Map<String,Serializable> content);

    /**
     * Links two issues
     *
     */
    public NodeList linkIssues();

    /**
     * Edit Issue
     */
    void editIssue(Map<String,Serializable> content);

    /**
     * Get Indent
     *
     * @param num
     * @return indent
     */
    String getIndent(int num);

    /**
     * Dump SOAP Element
     *
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

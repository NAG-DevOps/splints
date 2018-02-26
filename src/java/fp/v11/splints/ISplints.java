package fp.v11.splints;

import javax.xml.soap.SOAPElement;

/**
 * ISplints Interface
 * 
 * @author Vincent Fugnitto from soen487-team08
 *
 */
public interface ISplints {

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
package fp.v11.splints;

import javax.xml.soap.SOAPElement;

import org.w3c.dom.NodeList;

public class BitbucketCloud implements ISplints{

	@Override
	public String createIssue() {
		// TODO Need to implement this method as a REST service
		return null;
	}

	@Override
	public void editIssue() {
		// TODO Need to implement this method as a REST service
	}
	
	@Override
	public NodeList getIssueDetails() {
		return null;
	}

	@Override
	public NodeList linkIssues() {
		return null;
	}


	@Override
	public String getIndent(int num) {
		return null;
	}

	@Override
	public void dumpSOAPElement(SOAPElement el, int indent) throws Exception {
	}

	@Override
	public void queryIssues() {
	}

}

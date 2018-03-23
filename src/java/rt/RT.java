package rt;

import javax.xml.soap.SOAPElement;

import org.w3c.dom.NodeList;

import fp.v11.splints.ISplints;

public class RT implements ISplints {

	@Override
	public String createIssue() {
		
		return null;
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
	public void editIssue() {
	
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

package fp.v11.splints;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.xml.soap.SOAPElement;

import org.w3c.dom.NodeList;

@Path("/RT")
public class RT implements ISplints {

	@Override
	@POST
	@Path("/createIssue")
	public String createIssue() {
		// TODO Need to implement this method as a REST service
		return null;
	}

	@Override
	@PUT
	@Path("/editIssue")
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

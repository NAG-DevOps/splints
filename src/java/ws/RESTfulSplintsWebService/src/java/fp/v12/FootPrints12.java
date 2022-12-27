package fp.v12;

import fp.v11.splints.ISplints;
import java.io.Serializable;
import java.util.Map;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.NodeList;

public class FootPrints12 implements FootPrints12Interface, ISplints {


    @Override
    public void createItem() {
        //To implement
    }

    @Override
    public int getItemId() {
        //To implement
        return 0;
    }

    @Override
    public void editItem() {
        //To implement
    }

    @Override
    public void linkItems() {
        //To implement
    }

    @Override
    public void createTicket() {
        //To implement
    }

    @Override
    public void createTicketAndLinkAssets() {
        //To implement
    }

    @Override
    public String getTicketDetails() {
        //To implement
        return null;
    }

    @Override
    public void editTicket() {
        //To implement
    }

    @Override
    public void linkTickets() {
        //To implement
    }

    @Override
    public void createContact() {
        //To implement
    }

    @Override
    public void createOrEditContact() {
        //To implement
    }

    @Override
    public String getContactAssociatedTickets() {
        //To implement
        return null;
    }

    @Override
    public void editContact() {
        //To implement
    }

    @Override
    public void createCI() {
        //To implement
    }

    @Override
    public void editCI() {
        //To implement
    }

    @Override
    public void listContainerDefinitions() {
        //To implement
    }

    @Override
    public void listItemDefinitions() {
        //To implement
    }

    @Override
    public void listFieldDefinitions() {
        //To implement
    }

    @Override
    public void listQuickTemplates() {
        //To implement
    }

    @Override
    public void listSearches() {
        //To implement
    }

    @Override
    public void runSearch() {
        //To implement
    }

    @Override
    public String createIssue(Map<String, Serializable> content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NodeList linkIssues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editIssue(Map<String, Serializable> content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getIndent(int num) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dumpSOAPElement(SOAPElement el, int indent) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void queryIssues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Serializable> getIssueDetails(Map<String, Serializable> content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

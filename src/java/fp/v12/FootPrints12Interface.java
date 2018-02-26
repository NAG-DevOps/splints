package fp.v12;

/**
 *  Describes the services offered by
 *  FootPrint version 12
 */
public interface FootPrints12Interface {

    /**
     *  ITEM OPERATIONS
     */
    void createItem();

    int getItemId();

    void editItem();

    void linkItems();

    /**
     *  TICKET OPERATIONS
     */

    void createTicket();

    void createTicketAndLinkAssets();

    String getTicketDetails();

    void editTicket();

    void linkTickets();

    /**
     *  CONTACT OPERATIONS
     */
    void createContract();

    void createOrEditContract();

    String getContactAssociatedTickets();

    void editContract();

    /**
     *  CI OPERATIONS
     */
    void createCI();

    void editCI();

    /**
     *  LIST OPERATIONS
     */

    void listContainerDefinitions();

    void listItemDefinitions();

    void listFieldDefinitions();

    void listQuickTemplates();

    void listSearches();

    /**
     *  SEARCH OPERATION
     */

    void runSearch();



}

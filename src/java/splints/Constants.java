package splints;

/**
 * 
 * @author soen487-w18-team08
 *
 */
public class Constants {

	/* Fields */

	static final String WORKSPACE = "projectID";
	static final String ISSUE = "mrID";
	static final String ISSUE_SUBJECT = "title";
	static final String ISSUE_STATUS = "status";
	static final String CONTACT_FIRST_NAME = "First__bName";
	static final String CONTACT_LAST_NAME = "Last__bName";
	static final String CONTACT_EMAIL = "Email__bAddress";
	static final String SUBMITTER = "submitter";
	static final String ASSIGNEES = "assignees";
	static final String PRIORITY_NUMBER = "priorityNumber";
	static final String DESCRIPTION = "description";

	/* Custom Fields */

	static String IMPACT = "Impact";
	static String URGENCY = "Urgency";
	static String CONTACT_METHOD = "Contact__bMethod";
	static String TICKET_TYPE = "Type__bof_bTicket";
	static String SERVICE_CATGORY = "Service__bCategory";
	static String SERVICE_NAME = "Service__bName";

	/**
	 * set valid impacts, else reset if needed
	 * 
	 * @param impact
	 */
	public void setValidImpact(String impact) {
		if (impact == "Single User") {
			IMPACT = "Single User";
		} else if (IMPACT != "Impact" || impact == "Impact") {
			IMPACT = "Impact";
		}
	}

	/**
	 * set valid urgency, else reset if needed
	 * 
	 * @param urgency
	 */
	public void setValidUrgency(String urgency) {
		if (urgency == "Working Normally / Inquiry") {
			URGENCY = "Working Normally / Inquiry";
		} else if (URGENCY != "Urgency" || urgency == "Urgency") {
			URGENCY = "Urgency";
		}
	}

	/**
	 * set valid contact, else reset if needed
	 * 
	 * @param contact
	 */
	public void setValidContact(String contact) {
		if (contact == "Email") {
			CONTACT_METHOD = "Email";
		} else if (contact == "Other") {
			CONTACT_METHOD = "Other";
		} else if (CONTACT_METHOD != "Contact__bMethod" || contact == "Contact__bMethod") {
			CONTACT_METHOD = "Contact__bMethod";
		}
	}

	/**
	 * set valid ticket types, else reset if needed
	 * 
	 * @param type
	 */
	public void setValidTicketTypes(String type) {
		if (type == "Service Request") {
			TICKET_TYPE = "Email";
		} else if (type == "Incident") {
			TICKET_TYPE = "Other";
		} else if (TICKET_TYPE != "Type__bof_bTicket" || type == "Type__bof_bTicket") {
			TICKET_TYPE = "Type__bof_bTicket";
		}
	}

	/**
	 * set valid service category, else reset if needed
	 * 
	 * @param category
	 */
	public void setValidServiceCategory(String category) {
		if (category == "User - IT Support") {
			SERVICE_CATGORY = "User - IT Support";
		} else if (SERVICE_CATGORY != "Service__bCategory" || category == "Service__bCategory") {
			SERVICE_CATGORY = "Service__bCategory";
		}
	}

	/**
	 * set valid service name, else reset if needed
	 * 
	 * @param serviceName
	 */
	public void setValidServiceName(String serviceName) {
		if (serviceName == "ENCS Services") {
			SERVICE_NAME = "ENCS Services";
		} else if (SERVICE_NAME != "Service__bName" || serviceName == "Service__bName") {
			SERVICE_NAME = "Service__bName";
		}

	}

}

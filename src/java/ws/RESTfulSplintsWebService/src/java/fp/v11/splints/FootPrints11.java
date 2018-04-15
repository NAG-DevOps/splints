package fp.v11.splints;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author soen487-w18-team03
 * @author Vincent Fugnitto from soen487-w18-team08
 * @author Ziad Yarbouh from soen487-team08
 */
public class FootPrints11 implements ISplints {

    @Override
    public String createIssue(Map<String,Serializable> content) {
 
        //Optional Parameters
        String customFieldOne = "Value of Custom Field One";
        String customFieldTwo = "Value of Custom Field Two";
        String item = "user2";
        String customABFieldOne = "Value of Custom AB Field One";

        String ticketNumber = "0";
        try {
            
            // Setup SOAP message.
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();
            MessageFactory msgFactory = MessageFactory.newInstance();
            SOAPMessage msg = msgFactory.createMessage();

            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
            env.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
            env.addNamespaceDeclaration("namesp2", "http://xml.apache.org/xml-soap");
            env.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");

            // Compose SOAP body.
            SOAPBody body = env.getBody();
            SOAPElement invoke = body
                    .addChildElement(env.createName("MRWebServices__createIssue", "namesp1", "MRWebServices"));

            SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
            arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg1.addTextNode(Config.SOAP_AGENT_USERNAME);

            SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
            arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg2.addTextNode(Config.SOAP_AGENT_PASSWORD);

            SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
            arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

            SOAPElement arg4 = invoke.addChildElement(env.createName("args"));
            arg4.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            SOAPElement arg4_1 = arg4.addChildElement(env.createName(Constants.PRIORITY_NUMBER));
            arg4_1.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_1.addTextNode(content.get(Constants.PRIORITY_NUMBER));

            SOAPElement arg4_2 = arg4.addChildElement(env.createName(Constants.ISSUE_STATUS));
            arg4_2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_2.addTextNode(content.get(Constants.ISSUE_STATUS));

            SOAPElement arg4_4 = arg4.addChildElement(env.createName(Constants.DESCRIPTION));
            arg4_4.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_4.addTextNode(content.get(Constants.DESCRIPTION));

            SOAPElement arg4_5 = arg4.addChildElement(env.createName(Constants.ASSIGNEES));
            arg4_5.addAttribute(env.createName("type", "xsi", ""), "SOAP-ENC:Array");
            arg4_5.addAttribute(env.createName("arrayType", "SOAP-ENC", ""), "xsd:string[2]");

            SOAPElement arg4_5_1 = arg4_5.addChildElement(env.createName("item"));
            arg4_5_1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_5_1.addTextNode(content.get(Constants.ASSIGNEES));

            SOAPElement arg4_3 = arg4.addChildElement(env.createName("abfields"));
            arg4_3.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            /*
            SOAPElement arg4_3_1 = arg4_3.addChildElement( env.createName("Custom__bAB__bField__bOne") );
            arg4_3_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_1.addTextNode(customABFieldOne);
            
            SOAPElement arg4_3_2 = arg4_3.addChildElement( env.createName(Constants.CONTACT_LAST_NAME) );
            arg4_3_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_2.addTextNode(content.get(Constants.CONTACT_LAST_NAME));
            
            SOAPElement arg4_3_3 = arg4_3.addChildElement( env.createName(Constants.CONTACT_FIRST_NAME) );
            arg4_3_3.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_3.addTextNode(content.get(Constants.CONTACT_FIRST_NAME));
             */
            SOAPElement arg4_3_4 = arg4_3.addChildElement(env.createName(Constants.CONTACT_EMAIL));
            arg4_3_4.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_3_4.addTextNode(content.get(Constants.CONTACT_EMAIL));

            /*
            SOAPElement arg4_6 = arg4.addChildElement( env.createName("projfields") );
            arg4_6.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );
            
            SOAPElement arg4_6_1 = arg4_6.addChildElement( env.createName("Custom__bField__bOne") );
            arg4_6_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_6_1.addTextNode(customFieldOne);
            
            SOAPElement arg4_6_2 = arg4_6.addChildElement( env.createName("Custom__bField__bTwo") );
            arg4_6_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_6_2.addTextNode(customFieldTwo);
             */
            SOAPElement arg4_7 = arg4.addChildElement(env.createName(Constants.WORKSPACE));
            arg4_7.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_7.addTextNode(content.get(Constants.WORKSPACE));

            SOAPElement arg4_8 = arg4.addChildElement(env.createName(Constants.ISSUE_SUBJECT));
            arg4_8.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_8.addTextNode(content.get(Constants.ISSUE_SUBJECT));

            msg.saveChanges();

            //System.out.println("Request Message ----------\n");
            //msg.writeTo( System.out );
            // Make SOAP call
            SOAPMessage reply = connection.call(msg, Config.BASE_URL + "/MRcgi/MRWebServices.pl");

            connection.close();

            //System.out.println("Reply Message ----------\n");
            //reply.writeTo( System.out );
            // Get result
            SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();

            // Check for error
            if (replybody.hasFault()) {
                throw new Exception(replybody.getFault().getFaultString());

            }

            // Iterate through the result body, extracting information
            java.util.Iterator it = replybody.getChildElements();
            while (it.hasNext()) {
                Object obj = it.next();

                if (obj instanceof SOAPElement) {
                    SOAPElement ele = (SOAPElement) obj;
                    String s = ele.getElementName().getLocalName();

                    if (s.equals("MRWebServices__createIssueResponse")) {
                        java.util.Iterator it2 = ele.getChildElements();

                        while (it2.hasNext()) {
                            Object obj2 = it2.next();
                            if (obj2 instanceof SOAPElement) {
                                SOAPElement ele2 = (SOAPElement) obj2;
                                String s2 = ele2.getElementName().getLocalName();
                                if (s2.equals("return")) {
                                    java.util.Iterator it3 = ele2.getChildElements();

                                    while (it3.hasNext()) {
                                        Object obj3 = it3.next();

                                        if (obj3 instanceof Text) {
                                            Text txt = (Text) obj3;
                                            ticketNumber = txt.getValue();
                                            System.out.println("Issue " + ticketNumber + " has been created.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Done");
        return ticketNumber;
    }

    @Override
    public NodeList getIssueDetails(Map<String,Serializable> content) {
        NodeList result = null;

        try {

            // Setup SOAP message.
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();
            MessageFactory msgFactory = MessageFactory.newInstance();
            SOAPMessage msg = msgFactory.createMessage();

            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
            env.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
            env.addNamespaceDeclaration("namesp2", "http://xml.apache.org/xml-soap");
            env.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");

            // Compose SOAP body.
            SOAPBody body = env.getBody();
            SOAPElement invoke = body
                    .addChildElement(env.createName("MRWebServices__getIssueDetails", "namesp1", "MRWebServices"));

            SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
            arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg1.addTextNode(Config.SOAP_AGENT_USERNAME);

            SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
            arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg2.addTextNode(Config.SOAP_AGENT_PASSWORD);

            SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
            arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

            SOAPElement arg4 = invoke.addChildElement(env.createName(Constants.WORKSPACE));
            arg4.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4.addTextNode(content.get(Constants.WORKSPACE));

            SOAPElement arg5 = invoke.addChildElement(env.createName(Constants.ISSUE));
            arg5.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg5.addTextNode(content.get(Constants.ISSUE));

            msg.saveChanges();

            //System.out.println("Request Message ----------\n");
            //msg.writeTo( System.out );
            // Make SOAP call
            SOAPMessage reply = connection.call(msg, Config.BASE_URL + "/MRcgi/MRWebServices.pl");

            connection.close();

            //System.out.println("Reply Message ----------\n");
            //reply.writeTo( System.out );
            // Get result
            SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();

            // Check for error
            if (replybody.hasFault()) {
                throw new Exception(replybody.getFault().getFaultString());

            }
            DumpSOAPElement(replybody, 0);
            result = replybody.getChildNodes();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        System.out.println("Done");
        return result;
    }

    @Override
    public NodeList linkIssues() {
        String linkType = "LINK_TYPE";
        String projectId1 = "PROJECT_ID1";
        String ticketNumber1 = "TICKET_NUMBER1";
        String projectId2 = "PROJECT_ID2";
        String ticketNumber2 = "TICKET_NUMBER2";
        NodeList result = null;
        
        try {
           
            // Setup SOAP message.
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();
            MessageFactory msgFactory = MessageFactory.newInstance();
            SOAPMessage msg = msgFactory.createMessage();

            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
            env.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
            env.addNamespaceDeclaration("namesp2", "http://xml.apache.org/xml-soap");
            env.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");

            // Compose SOAP body.
            SOAPBody body = env.getBody();
            SOAPElement invoke = body
                    .addChildElement(env.createName("MRWebServices__linkIssues", "namesp1", "MRWebServices"));

            SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
            arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg1.addTextNode(Config.SOAP_AGENT_USERNAME);

            SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
            arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg2.addTextNode(Config.SOAP_AGENT_PASSWORD);

            SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
            arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

            SOAPElement arg4 = invoke.addChildElement(env.createName("args"));
            arg4.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            SOAPElement arg4_1 = arg4.addChildElement(env.createName("linkType"));
            arg4_1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_1.addTextNode(linkType);

            SOAPElement arg4_2 = arg4.addChildElement(env.createName("issue1"));
            arg4_2.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            SOAPElement arg4_2_1 = arg4_2.addChildElement(env.createName("projectID"));
            arg4_2_1.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_2_1.addTextNode(projectId1);

            SOAPElement arg4_2_2 = arg4_2.addChildElement(env.createName("mrID"));
            arg4_2_2.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_2_2.addTextNode(ticketNumber1);

            SOAPElement arg4_3 = arg4.addChildElement(env.createName("issue2"));
            arg4_3.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            SOAPElement arg4_3_1 = arg4_3.addChildElement(env.createName("projectID"));
            arg4_3_1.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_3_1.addTextNode(projectId2);

            SOAPElement arg4_3_2 = arg4_3.addChildElement(env.createName("mrID"));
            arg4_3_2.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_3_2.addTextNode(ticketNumber2);

            msg.saveChanges();

            //System.out.println("Request Message ----------\n");
            //msg.writeTo( System.out );
            // Make SOAP call
            SOAPMessage reply = connection.call(msg, Config.BASE_URL + "/MRcgi/MRWebServices.pl");

            connection.close();

            //System.out.println("Reply Message ----------\n");
            //reply.writeTo( System.out );
            // Get result
            SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();
            result = replybody.getChildNodes();
            // Check for error
            if (replybody.hasFault()) {
                throw new Exception(replybody.getFault().getFaultString());

            }

            if (replybody.getValue() != null) {
                System.out.println(String.format("Link %s %s:$s -> %s:%s is successful.\n", linkType, ticketNumber1,
                        projectId1, ticketNumber2, projectId2));
            } else {
                System.out.println(String.format("Link %s %s:$s -> %s:%s failed.\n", linkType, ticketNumber1,
                        projectId1, ticketNumber2, projectId2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Prints soap element names
     */
    private void DumpSOAPElement(SOAPElement el, int indent) throws Exception {
        java.util.Iterator it = el.getChildElements();

        while (it.hasNext()) {
            String indstr = GetIndent(indent);
            Object obj = it.next();

            if (obj instanceof SOAPElement) {
                SOAPElement ele = (SOAPElement) obj;
                System.out.println(indstr + "-----------------------------");
                System.out.println(indstr + ele.getElementName().getLocalName());
                System.out.println(indstr + "-----------------------------");
                DumpSOAPElement(ele, indent + 4);

            } else if (obj instanceof Text) {
                Text txt = (Text) obj;
                System.out.println(indstr + txt.getValue() + "\n");

            }
        }
    }

    
    /**
     * Gets indent for printing
     */
    private String GetIndent(int num) {
        String s = "";

        for (int i = 0; i < num; i++) {
            s = s + " ";
        }

        return s;
    }


	/* (non-Javadoc)
	 * @see fp.v11.splints.ISplints#editIssue()
	 */
	@Override
	public void editIssue(Map<String,Serializable> content) {

		try {
			java.util.Properties props = System.getProperties();
			props.put("http.proxyHost", Config.PROXY_HOST);
			props.put("http.proxyPort", Config.PROXY_PORT);

			// Setup SOAP message.
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = scf.createConnection();
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage msg = msgFactory.createMessage();
			SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
			env.addNamespaceDeclaration("namesp2", "http://xml.apache.org/xml-soap");
			env.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");

			// Compose SOAP body.
			SOAPBody body = env.getBody();
			SOAPElement invoke = body
					.addChildElement(env.createName("MRWebServices__editIssue", "namesp1", "MRWebServices"));

			SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
			arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
                        arg1.addTextNode(Config.SOAP_AGENT_USERNAME);

			SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
			arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
                        arg2.addTextNode(Config.SOAP_AGENT_PASSWORD);

			SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
			arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

			SOAPElement arg4 = invoke.addChildElement(env.createName("args"));
			arg4.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

			SOAPElement arg4_1 = arg4.addChildElement(env.createName("abfields"));
			arg4_1.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

			SOAPElement arg4_1_1 = arg4_1.addChildElement(env.createName("Custom__bAB__bField__bOne"));
			arg4_1_1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg4_1_1.addTextNode("NEW VALUE FOR Custom AB Field One");

			SOAPElement arg4_2 = arg4.addChildElement(env.createName("projfields"));
			arg4_2.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

			SOAPElement arg4_2_1 = arg4_2.addChildElement(env.createName("Custom__bField__bTwo"));
			arg4_2_1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg4_2_1.addTextNode("NEW VALUE FOR Custom Field Two");

			SOAPElement arg4_3 = arg4.addChildElement(env.createName(Constants.ISSUE_SUBJECT));
			arg4_3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg4_3.addTextNode(content.get(Constants.ISSUE_SUBJECT));

			SOAPElement arg4_4 = arg4.addChildElement(env.createName(Constants.WORKSPACE));
			arg4_4.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
			arg4_4.addTextNode(content.get(Constants.WORKSPACE));

			SOAPElement arg4_5 = arg4.addChildElement(env.createName(Constants.ISSUE));
			arg4_5.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
			arg4_5.addTextNode(content.get(Constants.ISSUE));

			msg.saveChanges();

			// Make SOAP call
			SOAPMessage reply = connection.call(msg, "http://fakeserver/MRcgi/MRWebServices.pl");
			connection.close();

			// Get result
			SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();

			// Check for error
			if (replybody.hasFault()) {
				throw new Exception(replybody.getFault().getFaultString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see fp.v11.splints.ISplints#getIndent(int)
	 */
	@Override
	public String getIndent(int num) {
		String s = "";
		for (int i = 0; i < num; i++) {
			s = s + " ";
		}
		return s;
	}

	/* (non-Javadoc)
	 * @see fp.v11.splints.ISplints#dumpSOAPElement(javax.xml.soap.SOAPElement, int)
	 */
	@Override
	public void dumpSOAPElement(SOAPElement el, int indent) throws Exception {
		java.util.Iterator<?> it = el.getChildElements();
		while (it.hasNext()) {
			String indstr = getIndent(indent);
			Object obj = it.next();

			if (obj instanceof SOAPElement) {
				SOAPElement ele = (SOAPElement) obj;
				System.out.println(indstr + "-----------------------------");
				System.out.println(indstr + ele.getElementName().getLocalName());
				System.out.println(indstr + "-----------------------------");
				dumpSOAPElement(ele, indent + 4);
			}

			else if (obj instanceof Text) {
				Text txt = (Text) obj;
				System.out.println(indstr + txt.getValue() + "\n");
			}
		}
	}

	/* (non-Javadoc)
	 * @see fp.v11.splints.ISplints#queryIssues()
	 */
	@Override
	public void queryIssues() {
		try {
			// Comment this out for NO proxy.
			java.util.Properties props = System.getProperties();
			props.put("http.proxyHost", Config.PROXY_HOST);
			props.put("http.proxyPort", Config.PROXY_PORT);

			// Setup SOAP message.
			SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = scf.createConnection();
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage msg = msgFactory.createMessage();

			SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/1999/XMLSchema");
			env.addNamespaceDeclaration("namesp2", "http://xml.apache.org/xml-soap");
			env.addNamespaceDeclaration("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");

			// Compose SOAP body.
			SOAPBody body = env.getBody();
			SOAPElement invoke = body
					.addChildElement(env.createName("MRWebServices__search", "namesp1", "MRWebServices"));

			SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
			arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg1.addTextNode(Config.SOAP_AGENT_USERNAME);

			SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
			arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg2.addTextNode(Config.SOAP_AGENT_PASSWORD);

			SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
			arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

			SOAPElement arg4 = invoke.addChildElement(env.createName("query"));
			arg4.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg4.addTextNode("select mrID, mrTITLE from MASTER78 WHERE mrTITLE LIKE '%of%'");

			msg.saveChanges();
			// System.out.println("Request Message ----------\n");
			// msg.writeTo( System.out );
			// Make SOAP call
			SOAPMessage reply = connection.call(msg, "http://virgo/MRcgi/MRWebServices.pl");
			connection.close();
			// System.out.println("Reply Message ----------\n");
			// reply.writeTo( System.out );
			// Get result
			SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();
			// Check for error
			if (replybody.hasFault()) {
				throw new Exception(replybody.getFault().getFaultString());
			}
			dumpSOAPElement(replybody, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

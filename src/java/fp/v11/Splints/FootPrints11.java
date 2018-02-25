package fp.v11.splints;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.Text;

/**
 * 
 * @author soen487-w18-team03
 */
public class FootPrints11 implements ISplints {

    @Override
    public String createIssue() {
        String priorityNumber = "5";
        String status = "Open";
        String description = "Place issue description here. From Java code.";
        String assignee = "[YOUR_QUEUE_ASSIGNEE]";
        String emailBAddress = "USER@EMAIL";
        String projectId = "PROJECT_ID";
        String title = "Test Issue Java - Splints";

        String ticketNumber = "0";
        try {
            /*
            // Comment this out for NO proxy.
            java.util.Properties props = System.getProperties();
            props.put( "http.proxyHost", Config.PROXY_HOST );
            props.put( "http.proxyPort", Config.PROXY_PORT );
             */

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
            arg2.addTextNode(Config.SOAP_AGENT_USERNAME);

            SOAPElement arg3 = invoke.addChildElement(env.createName("extrainfo"));
            arg3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");

            SOAPElement arg4 = invoke.addChildElement(env.createName("args"));
            arg4.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            SOAPElement arg4_1 = arg4.addChildElement(env.createName("priorityNumber"));
            arg4_1.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_1.addTextNode(priorityNumber);

            SOAPElement arg4_2 = arg4.addChildElement(env.createName("status"));
            arg4_2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_2.addTextNode(status);

            SOAPElement arg4_4 = arg4.addChildElement(env.createName("description"));
            arg4_4.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_4.addTextNode(description);

            SOAPElement arg4_5 = arg4.addChildElement(env.createName("Assignees"));
            arg4_5.addAttribute(env.createName("type", "xsi", ""), "SOAP-ENC:Array");
            arg4_5.addAttribute(env.createName("arrayType", "SOAP-ENC", ""), "xsd:string[2]");

            SOAPElement arg4_5_1 = arg4_5.addChildElement(env.createName("item"));
            arg4_5_1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_5_1.addTextNode(assignee);

            //SOAPElement arg4_5_2 = arg4_5.addChildElement( env.createName("item") );
            //arg4_5_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            //arg4_5_2.addTextNode("user2");
            SOAPElement arg4_3 = arg4.addChildElement(env.createName("abfields"));
            arg4_3.addAttribute(env.createName("type", "xsi", ""), "namesp2:SOAPStruct");

            /*
            SOAPElement arg4_3_1 = arg4_3.addChildElement( env.createName("Custom__bAB__bField__bOne") );
            arg4_3_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_1.addTextNode("Value of Custom AB Field One");
            
            SOAPElement arg4_3_2 = arg4_3.addChildElement( env.createName("Last__bName") );
            arg4_3_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_2.addTextNode("Doe");
            
            SOAPElement arg4_3_3 = arg4_3.addChildElement( env.createName("First__bName") );
            arg4_3_3.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_3_3.addTextNode("John");
             */
            SOAPElement arg4_3_4 = arg4_3.addChildElement(env.createName("Email__bAddress"));
            arg4_3_4.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_3_4.addTextNode(emailBAddress);

            /*
            SOAPElement arg4_6 = arg4.addChildElement( env.createName("projfields") );
            arg4_6.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );
            
            SOAPElement arg4_6_1 = arg4_6.addChildElement( env.createName("Custom__bField__bOne") );
            arg4_6_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_6_1.addTextNode("Value of Custom Field One");
            
            SOAPElement arg4_6_2 = arg4_6.addChildElement( env.createName("Custom__bField__bTwo") );
            arg4_6_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
            arg4_6_2.addTextNode("Value of Custom Field Two");
             */
            SOAPElement arg4_7 = arg4.addChildElement(env.createName("projectID"));
            arg4_7.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4_7.addTextNode(projectId);

            SOAPElement arg4_8 = arg4.addChildElement(env.createName("title"));
            arg4_8.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
            arg4_8.addTextNode(title);

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
    public Object getIssueDetails() {
        String projectId = "PROJECT_ID";
        String ticketNumber = "TICKET_NUMBER";

        Object result = null;
        try {
            /*
            // Comment this out for NO proxy.
            java.util.Properties props = System.getProperties();
            props.put( "http.proxyHost", Config.PROXY_HOST );
            props.put( "http.proxyPort", Config.PROXY_PORT );
             */

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

            SOAPElement arg4 = invoke.addChildElement(env.createName("projectID"));
            arg4.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg4.addTextNode(projectId);

            SOAPElement arg5 = invoke.addChildElement(env.createName("mrid"));
            arg5.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
            arg5.addTextNode(ticketNumber);

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
            result = replybody;
            DumpSOAPElement(replybody, 0);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        System.out.println("Done");
        return result;
    }

    @Override
    public Object linkIssues() {
        String linkType = "LINK_TYPE";
        String projectId1 = "PROJECT_ID1";
        String ticketNumber1 = "TICKET_NUMBER1";
        String projectId2 = "PROJECT_ID2";
        String ticketNumber2 = "TICKET_NUMBER2";

        Object result = null;
        try {
            /*
            // Comment this out for NO proxy.
            java.util.Properties props = System.getProperties();
            props.put( "http.proxyHost", Config.PROXY_HOST );
            props.put( "http.proxyPort", Config.PROXY_PORT );
             */

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

            // Check for error
            if (replybody.hasFault()) {
                throw new Exception(replybody.getFault().getFaultString());

            }

            // Iterate through the result body, extracting information
            java.util.Iterator it = replybody.getChildElements();
            result = replybody;

            if (result != null) {
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

    private String GetIndent(int num) {

        String s = "";

        for (int i = 0; i < num; i++) {

            s = s + " ";

        }

        return s;

    }

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
}

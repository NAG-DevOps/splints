package splints;

import java.util.ArrayList;

import javax.xml.soap.*;

/**
 * 
 * @author soen487-w18-team08
 *
 */
public class FootPrints11 implements ISplints {

	/* (non-Javadoc)
	 * @see splints.ISplints#editIssue()
	 */
	@Override
	public void editIssue() {

		try {
			java.util.Properties props = System.getProperties();
			props.put("http.proxyHost", "localhost");
			props.put("http.proxyPort", "8888");

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
			arg1.addTextNode("WebServices");

			SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
			arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg2.addTextNode("fakepassword");

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

			SOAPElement arg4_3 = arg4.addChildElement(env.createName("title"));
			arg4_3.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg4_3.addTextNode("NEW title is here.");

			SOAPElement arg4_4 = arg4.addChildElement(env.createName("projectID"));
			arg4_4.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
			arg4_4.addTextNode("78");

			SOAPElement arg4_5 = arg4.addChildElement(env.createName("mrID"));
			arg4_5.addAttribute(env.createName("type", "xsi", ""), "xsd:int");
			arg4_5.addTextNode("40");

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
	 * @see splints.ISplints#queryResults()
	 */
	@Override
	public ArrayList<String> queryResults() {
		return new ArrayList<String>();
	}
	
}

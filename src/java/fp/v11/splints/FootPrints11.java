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
 * @author Vincent Fugnitto from soen487-w18-team08
 *
 */
public class FootPrints11 implements ISplints {

	/* (non-Javadoc)
	 * @see fp.v11.splints.ISplints#editIssue()
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
					.addChildElement(env.createName("MRWebServices__search", "namesp1", "MRWebServices"));

			SOAPElement arg1 = invoke.addChildElement(env.createName("user"));
			arg1.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg1.addTextNode("WebServices");

			SOAPElement arg2 = invoke.addChildElement(env.createName("password"));
			arg2.addAttribute(env.createName("type", "xsi", ""), "xsd:string");
			arg2.addTextNode("root");

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

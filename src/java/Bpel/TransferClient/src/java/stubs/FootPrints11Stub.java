/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author chris
 */
public class FootPrints11Stub {

    public static SOAPMessage createIssue(SOAPMessage msg,String id) {
        String xml = "<?xml version=\"1.0\"?>\n"
                + "\n"
                + "<soap:Envelope\n"
                + "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n"
                + "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n"
                + "\n"
                + "<soap:Body xmlns:m=\"http://www.example.org/stock\">\n"
                + "  <m:MRWebServices__createIssueResponse>\n"
                + "    <m:return>"+id+"</m:return>\n"
                + "  </m:MRWebServices__createIssueResponse>\n"
                + "</soap:Body>\n"
                + "\n"
                + "</soap:Envelope>";
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
            return message;
        } catch (Exception e) {
        }
        return msg;
    }

    public static SOAPMessage getIssueDetails(SOAPMessage msg, String id) {
        String xml = "<?xml version=\"1.0\"?>\n"
                + "\n"
                + "<soap:Envelope\n"
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"\n"
                + "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n"
                + "\n"
                + "<soap:Body xmlns:m=\"http://www.example.org/stock\">\n"
                + "  <m:MRWebServices__createIssueResponse>\n"
                + "    <m:id>" + id + "</m:id>\n"
                + "    <m:" + rt.Constants.SUBJECT + ">Test</m:" + rt.Constants.SUBJECT + ">\n"
                + "    <m:" + rt.Constants.TEXT + ">Description test</m:" + rt.Constants.TEXT + ">\n"
                + "    <m:title>githubTitle</m:title>\n"
                + "    <m:body>githubDescription</m:body>\n"
                + "    <m:assignee>githubPerson</m:assignee>\n"
                + "    <m:milestone>3</m:milestone>\n"
                + "    <m:labels>Bug</m:labels>\n"
                + "    <m:assignees>githubPersons</m:assignees>\n"
                + "  </m:MRWebServices__createIssueResponse>\n"
                + "</soap:Body>\n"
                + "\n"
                + "</soap:Envelope>";
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
            return message;
        } catch (Exception e) {
        }
        return msg;
    }
}

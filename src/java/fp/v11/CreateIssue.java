package fp.v11;

import javax.xml.soap.*;

public class CreateIssue
{
   
   public static void main( String[] args )
   {
      
      try
      {
      	/*
         // Comment this out for NO proxy.
         java.util.Properties props = System.getProperties();
         props.put( "http.proxyHost", "localhost" );
         props.put( "http.proxyPort", "8888" );
         */

      	
         // Setup SOAP message.
         SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
         SOAPConnection connection = scf.createConnection();
         MessageFactory msgFactory = MessageFactory.newInstance();
         SOAPMessage msg           = msgFactory.createMessage();

         SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
         env.addNamespaceDeclaration( "xsi", "http://www.w3.org/1999/XMLSchema-instance" );
         env.addNamespaceDeclaration( "xsd", "http://www.w3.org/1999/XMLSchema" );
         env.addNamespaceDeclaration( "namesp2", "http://xml.apache.org/xml-soap" );
         env.addNamespaceDeclaration( "SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/" );

         
         // Compose SOAP body.
         SOAPBody body = env.getBody();
         SOAPElement invoke = body.addChildElement( env.createName("MRWebServices__createIssue", "namesp1","MRWebServices") );
         

         SOAPElement arg1 = invoke.addChildElement( env.createName("user") );
         arg1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg1.addTextNode("YOUR_AGENT_USERNAME");

         SOAPElement arg2 = invoke.addChildElement( env.createName("password") );
         arg2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg2.addTextNode("YOUR_AGENT_PASSWORD");

         SOAPElement arg3 = invoke.addChildElement( env.createName("extrainfo") );
         arg3.addAttribute( env.createName("type","xsi",""), "xsd:string" );

         SOAPElement arg4 = invoke.addChildElement( env.createName("args") );
         arg4.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );
         
         SOAPElement arg4_1 = arg4.addChildElement( env.createName("priorityNumber") );
         arg4_1.addAttribute( env.createName("type","xsi",""), "xsd:int" );
         arg4_1.addTextNode("5");

         SOAPElement arg4_2 = arg4.addChildElement( env.createName("status") );
         arg4_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_2.addTextNode("Open");
       
         SOAPElement arg4_4 = arg4.addChildElement( env.createName("description") );
         arg4_4.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_4.addTextNode("Place issue description here. From Java code.");
         																			  
         
         SOAPElement arg4_5 = arg4.addChildElement( env.createName("Assignees") );
         arg4_5.addAttribute( env.createName("type","xsi",""), "SOAP-ENC:Array" );
         arg4_5.addAttribute( env.createName("arrayType","SOAP-ENC",""), "xsd:string[2]" );

         SOAPElement arg4_5_1 = arg4_5.addChildElement( env.createName("item") );
         arg4_5_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_5_1.addTextNode("[YOUR_QUEUE_ASSIGNEE]");
			
         //SOAPElement arg4_5_2 = arg4_5.addChildElement( env.createName("item") );
         //arg4_5_2.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         //arg4_5_2.addTextNode("user2");

         
         SOAPElement arg4_3 = arg4.addChildElement( env.createName("abfields") );
         arg4_3.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );
         
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
         
         SOAPElement arg4_3_4 = arg4_3.addChildElement( env.createName("Email__bAddress") );
         arg4_3_4.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_3_4.addTextNode("USER@EMAIL");
         
         
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
         
         SOAPElement arg4_7 = arg4.addChildElement( env.createName("projectID") );
         arg4_7.addAttribute( env.createName("type","xsi",""), "xsd:int" );
         arg4_7.addTextNode("PROJECT_ID");

         SOAPElement arg4_8 = arg4.addChildElement( env.createName("title") );
         arg4_8.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_8.addTextNode("Test Issue Java - Splints");

         
         msg.saveChanges();

         //System.out.println("Request Message ----------\n");
         //msg.writeTo( System.out );

         // Make SOAP call
         SOAPMessage reply = connection.call( msg, "https://localhost/MRcgi/MRWebServices.pl" );

         
         connection.close();

         //System.out.println("Reply Message ----------\n");
         //reply.writeTo( System.out );

         // Get result
         SOAPBody replybody = reply.getSOAPPart().getEnvelope().getBody();

         // Check for error
         if( replybody.hasFault() )
         {                     	
            throw new Exception( replybody.getFault().getFaultString() );

         }
            
         
        
         // Iterate through the result body, extracting information
         java.util.Iterator it = replybody.getChildElements();
         while( it.hasNext() )
         {            
            Object obj = it.next();

            if( obj instanceof SOAPElement )
            {
               SOAPElement ele = (SOAPElement)obj;
               String s        = ele.getElementName().getLocalName();
               
               if( s.equals("MRWebServices__createIssueResponse") )
               {
                  java.util.Iterator it2 = ele.getChildElements();
                  
                  while( it2.hasNext() )
                  {
                     Object obj2 = it2.next();
                     if( obj2 instanceof SOAPElement )
                     {
                        SOAPElement ele2 = (SOAPElement)obj2;
                        String s2        = ele2.getElementName().getLocalName();
                        if( s2.equals("return") )
                        {
                           java.util.Iterator it3 = ele2.getChildElements();

                           while( it3.hasNext() )
                           {
                              Object obj3 = it3.next();

                              if( obj3 instanceof Text )
                              {
                                 Text txt = (Text)obj3;
                                 System.out.println( "Issue " + txt.getValue() + " has been created." );
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      catch( Exception ex )
      {
         ex.printStackTrace();
      }
      
      System.out.println("Done");
   }

}

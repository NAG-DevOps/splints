package fp.v11;

import javax.xml.soap.*;

public class EditIssue
{
   
   public static void main( String[] args )
   {
                     
      try
      {
         /*      	  
         // Comment this out for NO proxy.
         java.util.Properties props = System.getProperties();
         //props.put( "http.proxyHost", "localhost" );
         //props.put( "http.proxyPort", "8888" );
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
         SOAPElement invoke = body.addChildElement( env.createName("MRWebServices__editIssue", "namesp1","MRWebServices") );
         
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
                  
         SOAPElement arg4_3 = arg4.addChildElement( env.createName("projectID") );
         arg4_3.addAttribute( env.createName("type","xsi",""), "xsd:int" ); 
         arg4_3.addTextNode("PROJECT_ID");

         SOAPElement arg4_4 = arg4.addChildElement( env.createName("mrID") );
         arg4_4.addAttribute( env.createName("type","xsi",""), "xsd:int" );
         arg4_4.addTextNode("TICKET_NUMBER");
         
         SOAPElement arg4_5 = arg4.addChildElement( env.createName("title") );
         arg4_5.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_5.addTextNode("Test Issue Java - Splints");
         
         SOAPElement arg4_6 = arg4.addChildElement( env.createName("description") );
         arg4_6.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_6.addTextNode("Place issue description here. From Java code. End test.");
         
         /*
         SOAPElement arg4_1 = arg4.addChildElement( env.createName("abfields") );
         arg4_1.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );

         SOAPElement arg4_1_1 = arg4_1.addChildElement( env.createName("Custom__bAB__bField__bOne") );
         arg4_1_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_1_1.addTextNode("NEW VALUE FOR Custom AB Field One");
         */
         
			/*
         SOAPElement arg4_2 = arg4.addChildElement( env.createName("projfields") );
         arg4_2.addAttribute( env.createName("type","xsi",""), "namesp2:SOAPStruct" );

         SOAPElement arg4_2_1 = arg4_2.addChildElement( env.createName("Custom__bField__bTwo") );
         arg4_2_1.addAttribute( env.createName("type","xsi",""), "xsd:string" );
         arg4_2_1.addTextNode("NEW VALUE FOR Custom Field Two");
         */
				
         

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

         System.out.println( "done." );
            
      }
      catch( Exception ex )
      {
         ex.printStackTrace();

      }
   }

}

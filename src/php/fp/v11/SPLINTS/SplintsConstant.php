<?php

class SplintsConstant
{
	// replace with real url
	protected $urlLocation  = "https://localhost";

	// replace with real ProjectID
	protected $projectID 	= 27;

	// replace with username and password
	protected $soapUser     = "agentusername";
	protected $soapPass 		= "agentpassword";

	public  $client;
	

	public function __construct()
	{
		$this->client = new SoapClient(NULL, array("location" => $this->urlLocation,
													 			 "uri"   	=> "MRWebServices",
		  													    "style"    => SOAP_RPC,
													 		    "use"      => SOAP_ENCODED       
												   			)
												);
	}
}

?>

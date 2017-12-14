<?
require "SPLINTS/FootPrints11.php";

$splints = new FootPrints11();

   $strTitle		 = "Test Issue PHP - Splints";
	$strStatus      = "Open";
	$strDescription = "New ticket test for department.";

	$arrAssignees   = ["Your_queue"];
	$strContact     = "customer_email";

	
	
	// 1. create new issue with default values
	$arrProjFieldsDefault = array();

	$iTicketCreateDefault = $splints->createIssue($strTitle, $arrAssignees, $strStatus, $strDescription, $strContact, $arrProjFieldsDefault);   
	print "<BR><b>FP# " . $iTicketCreateDefault . ":</b> Issue created with default values.<br><hr>\n";



	// 2. create new issue with Customed values - 
	//		passing real value from real dropdown list to $arrProjFieldsCustomed. Leave for empty for template. When empty, the effect is same as case 1 above.
	$arrProjFieldsCustomed = array();
	$arrProjFields["Department"]  		= ""; 
 	$arrProjFields["Impact"]				= "";
	$arrProjFields["Urgency"]				= "";
	$arrProjFields["Contact Method"]		= "";
	$arrProjFields["Type of Ticket"]		= "";
	$arrProjFields["Service Category"] 	= "";
	$arrProjFields["Service Name"] 		= "";

	$iTicketCreateCustomed = $splints->createIssue($strTitle, $arrAssignees, $strStatus, $strDescription, $strContact, $arrProjFieldsCustomed);   
	print "<BR><b>FP# " . $iTicketCreateCustomed . ":</b> Issue created with customed values.<br><hr>\n";


	
  	// 3.edit issue
	$strStatus 		 = "Customer Responded"; 
	$strDescription = "Description with status " . $strStatus ;	
	
	$splints->editIssue($iTicketCreateCustomed, $strTitle, $arrAssignees, $strStatus, $strDescription);
	print "<BR><b>FP# ". $iTicketCreateCustomed . ":<b/> Issue changed: Status=" . $strStatus . " - Description=" . $strDescription . "<hr>\n";
?>

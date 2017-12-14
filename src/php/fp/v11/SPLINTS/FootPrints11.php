<?php
require "SplintsConstant.php";

class SplintsOperations extends SplintsConstant
{	
	public function __construct()
	{
		parent::__construct();
	}
	

	public function editIssue($iIssueNumber, $strTitle, $arrAssignees, $strStatus, $strDescription)
   {
		
		$this->client->MRWebServices__editIssue(
																$this->soapUser, $this->soapPass,'',
													  		  	array(
																		projectID 	=> $this->projectID,																
																		mrID 			=> $iIssueNumber,	
																		title			=> $strTitle,
                                                      assignees   => $arrAssignees,
         			                                    status 		=> $strStatus ,															   
																		description => $strDescription
																	  )
															);
		
   }
	

	public function createIssue($strTitle, $arrAssignees, $strStatus, $strDescription, $strContact, $arrProjFields)
	{		
		$iIssueNumber = $this->client->MRWebServices__createIssue
							 (
								$this->soapUser, $this->soapPass,'',
								array(
										projectID 		=> $this->projectID,
                              priorityNumber => 5,
                              title				=> $strTitle,
										assignees      => $arrAssignees,										
										status			=> $strStatus,										
										description 	=> $strDescription,                               
                              abfields 		=> array(      
									     								Email__bAddress    => $strContact,  
																		Department         => $arrProjFields["Department"]
     										                    ),
										
                              projfields     => array(
																		Impact 				 => $arrProjFields["Impact"], 
                                                      Urgency 				 => $arrProjFields["Urgency"],
																		Contact__bMethod   => $arrProjFields["Contact Method"],
                                                      Type__bof__bTicket => $arrProjFields["Type of Ticket"],
 																		Service__bCategory => $arrProjFields["Service Category"],
																		Service__bName 	 => $arrProjFields["Service Name"]
																	  )
										
                              )
										
							 );
		
		return $iIssueNumber;
	}

}
?>

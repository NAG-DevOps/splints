/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import rt.RTIssueTransferClient;

/**
 *
 * @author soen487-w18-team03
 */
@WebService(serviceName = "TransferClientWS")
public class TransferClientWS {


    /**
     * Web service operation
     */
    @WebMethod(operationName = "transferFromFp11ToRt")
    public String transferFromFp11ToRt(@WebParam(name = "issueId") int issueId, @WebParam(name = "projectId") int projectId) {
        FPIssueTransferClient transferClient = new FPIssueTransferClient();
        transferClient.transferToRT(issueId, projectId);
        return null;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "transferFromFp11ToGithub")
    public String transferFromFp11ToGitHub(@WebParam(name = "issueId") int issueId, @WebParam(name = "projectId") int projectId) {
        FPIssueTransferClient transferClient = new FPIssueTransferClient();
        transferClient.transferToGitHub(issueId, projectId);
        return null;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "transferFromFp11ToBitBucket")
    public String transferFromFp11ToBitBucket(@WebParam(name = "issueId") int issueId, @WebParam(name = "projectId") int projectId) {
        FPIssueTransferClient transferClient = new FPIssueTransferClient();
        transferClient.transferToBitbucket(issueId, projectId);
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "transferFromRtToFp11")
    public String transferFromRtToFp11(@WebParam(name = "issueId") int issueId, @WebParam(name = "projectId") int projectId) {
        RTIssueTransferClient transferClient = new RTIssueTransferClient();
        transferClient.transferToFP11(issueId, projectId);
        return null;
    }
}

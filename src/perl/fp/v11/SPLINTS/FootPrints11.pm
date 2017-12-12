#!/usr/bin/perl

use strict;
use warnings;

use SOAP::Lite;

use SPLINTS::Config;

package SPLINTS::FootPrints11;

##
## API
##

sub createIssue()
{
  my
  (
    $iProjectID,
    $strSubmitter,
    $strSubject,
    $astrAssignees,
    $iPriorityNumber,
    $strStatus,
    $strPriorityWords,
    $strDescription
  ) = @_;

  my $iTicketNumber = 0;

  # TODO: add permanentCCs, oneTimeCCs, mail, selectContact

  my $soapenv = $::soap->MRWebServices__createIssue
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    {
      projectID => $iProjectID,
      submitter => "$strSubmitter",
      title => "$strSubject",
      assignees => $astrAssignees,
      priorityNumber => $iPriorityNumber,
      priorityWords => "$strPriorityWords",
      status => "$strStatus",
      description => "$strDescription\n\n--\n$::VERSION",
      #description => "$strDescription"

      # TODO: acquire a dictionary of custom fields in use

      abfields =>
      {
        # Contact's names and email address
        Last__bName => 'Perl',
        First__bName => 'Splints',
        Email__bAddress => $strSubmitter,
        #Custom__bAB__bField__bOne => 'Value of Custom AB Field One'
      },

      #projfields =>
      #{
      #  Custom__bField__bOne => 'Value of Custom Field One',
      #  Custom__bField__bTwo => 'Value of Custom Field Two'
      #}
    }
  );

  if($::bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }
  else
  {
    $iTicketNumber = $soapenv->result;
    print "<<<{{{$iTicketNumber}}}>>>\n" if $::bDebug;
  }

  print "Issue [$iTicketNumber] has been created.\n";

  return $iTicketNumber;
}

sub editIssue()
{
  my
  (
    $iTicketNumber,
    $iProjectID,
    $strSubmitter,
    $strSubject,
    $astrAssignees,
    $iPriorityNumber,
    $strStatus,
    $strPriorityWords,
    $strDescription
  ) = @_;

  my $soapenv = $::soap->MRWebServices__editIssue
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    {
      projectID => $iProjectID,
      mrID => $iTicketNumber,

      # New subject line of the ticket
      title => "$strSubject",

      assignees => $astrAssignees,

      submitter => "$strSubmitter",
      title => "$strSubject",
      assignees => $astrAssignees,
      priorityNumber => $iPriorityNumber,
      priorityWords => "$strPriorityWords",
      status => "$strStatus",
      description => "$strDescription\n\n--\n$::VERSION",
      #description => "$strDescription"

      # TODO: acquire a dictionary of custom fields in use

      abfields =>
      {
        # Contact's names and email address
        Last__bName => 'Perl',
        First__bName => 'Splints',
        Email__bAddress => $strSubmitter,
      }

      #projfields =>
      #{
      #  Custom__bField__bOne => 'Value of Custom Field One',
      #  Custom__bField__bTwo => 'Value of Custom Field Two'
      #}
    }
  );

  if($::bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }

  print "editIssue: done\n" if $::bDebug;

  return $soapenv->result;
}

sub getIssueDetails()
{
  my ($iTicketNumber, $iProjectID) = @_;

  my $soapenv = $::soap->MRWebServices__getIssueDetails
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    $iProjectID,
    $iTicketNumber
  );

  print "post SOAP request ---\n" if $::bDebug;

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT --- ${$soapenv->fault}{faultstring}";
    exit(1);
  }
  else
  {
    print "SOAP OK ---\n" if $::bDebug;
    $result = $soapenv->result;
  }

  print "<<<{{{$soapenv}}}>>>\n" if $::bDebug;

  if($::bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  print "post result ---\n" if $::bDebug;

  print "<<<{{{$result}}}>>>\n" if $::bDebug;

  my %hash = %{$result};

  foreach my $item ( keys %hash )
  {
    print "---\n";
    print "ITEM IS: [$item]\n";

    print "---\n";
    print $hash{$item};
    print "\n";
    
    print "---\n";
  }

  print "DONE ---\n";

  return $result;
}

sub linkIssues()
{
  # By calling this, you don't need to worry about whether this is a new or edited contact. This method
  # will do all the work.

  # Can link tickets across workspaces $iProjectID1 and $iProjectID2,
  # else they are simply the same
  my ($iTicketNumber1, $iProjectID1, $iTicketNumber2, $iProjectID2, $strLinkType) = @_;

  $strLinkType = 'dynamic' if not defined($strLinkType);

  my $soapenv = $::soap->MRWebServices__linkIssues
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    {
       linkType => $strLinkType,
       issue1   => {projectID => $iProjectID1, mrID => $iTicketNumber1},
       issue2   => {projectID => $iProjectID2, mrID => $iTicketNumber2},
    }
  );

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }
  else
  {
    $result = $soapenv->result;
  }

  if($result)
  {
    print "Link ($strLinkType) $iTicketNumber1:$iProjectID1 -> $iTicketNumber2:$iProjectID2 is successful.\n";
  }
  else
  {
    warn "Link ($strLinkType) $iTicketNumber1:$iProjectID1 -> $iTicketNumber2:$iProjectID2 failed ($result).";
  }

  return $result;
}

sub queryIssues()
{
  my ($iProjectID, $strQuery) = @_;

  #print "(iProjectID, strQuery) = ($iProjectID, $strQuery)\n" if $::bDebug;

  $strQuery = "SELECT mrID, mrTITLE FROM MASTER$iProjectID WHERE mrSTATUS = 'Open'"
    if not defined($strQuery);

  my $soapenv = $::soap->MRWebServices__search
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    $strQuery
  );

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }
  else
  {
    $result = $soapenv->result;
  }

  my @result_list = @{$result};

  for(my $i = 0; $i <= $#result_list; $i++)
  {
    print "RESULT $i\n";
    
    my $hash_ref = $result_list[$i];

    foreach my $item (keys %{$hash_ref})
    {
        my $val = $hash_ref->{$item};
        print "$item = '$val'\n";
    }

    print "---------------------\n";
  }

  return $result;
}

sub linkAsset()
{
  # MR, PROJ, CMDB ID, CI ID
  my ($iTicketNumber, $iProjectID, $iAssedCMDBID, $iAssetID) = @_;

  # TODO: fix hardcoding
  #my $iAssetIDType = 10;
  my $iAssetIDType = 3;

  my $soapenv = $::soap->MRWebServices__createCIIssueLink
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    {
       CMDB_ID    => $iAssedCMDBID,
       CI_ID      => $iAssetID,
       CI_TYPE_ID => $iAssetIDType,
       PROJECTID  => $iProjectID,
       MRID       => $iTicketNumber,
    }
  );

  if($::bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }
  else
  {
    $result = $soapenv->result;
  }

  if($result)
  {
    print "Link ($iAssetIDType) $iTicketNumber:$iProjectID -> $iAssedCMDBID-$iAssetID is successful.\n";
  }
  else
  {
    warn "Link ($iAssetIDType) $iTicketNumber:$iProjectID -> $iAssedCMDBID-$iAssetID failed ($result).";
  }

  return $result;
}

1;

# EOF

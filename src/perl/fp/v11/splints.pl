#!/usr/bin/perl -w

use strict;

use SOAP::Lite;

my $VERSION = "splints-0.0.2";

##
## Config
## TODO: split into a module
##

#my $USE_PROXY_SERVER = 1;
my $USE_PROXY_SERVER = 0;

# Footprints Workspace
my $FP_PROJECT_ID = 72;

my $soapUser = 'agentusername';
my $soapPass = 'agentpassword';

my $baseUrl  = 'https://localhost';

my $proxyUrl  = 'https://localhost:8888';

my $strExtraInfo = '';

my $bDebug = 1;

##
## main()
##

my $soap = new SOAP::Lite;

$soap->uri("$baseUrl/MRWebServices");

print "post URI ---\n";

if($USE_PROXY_SERVER)
{
  $soap->proxy
  (
    "$baseUrl/MRcgi/MRWebServices.pl",
    proxy => ['http' => "$proxyUrl/"]
  );
}
else
{
  $soap->proxy("$baseUrl/MRcgi/MRWebServices.pl");
  print "post non-proxy proxy ---\n";
}

# Status values:
#   Open
#   Assigned
#   Work In Progress
#   On Hold
#   Pending Customer
#   Scheduled
#   Resolved
#   Cancelled

my $iTicket = &createIssue
(
  $FP_PROJECT_ID,

  # Appears have to be an FP user via API
  "$soapUser",

  "$VERSION: testing createIssue()...",

  ['user1', 'user2'],
  
  #4,
  #2,
  #1,
  3,

  #'Open',
  'Assigned',

  #"Not too urgent",
  "HIGH PRIORITY",
  #"Service Down",

  "Testing Perl Splints and here is a description of the initial issue..."
);

if($iTicket > 0)
{
  &getIssueDetails($iTicket, $FP_PROJECT_ID);

  &editIssue
  (
    $iTicket,

    $FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$soapUser",

    "$VERSION: testing editIssue(now)... -- renaming $iTicket",

    [ ],
    #undef,
  
    4,
    #2,
    #1,
    #3,

    #'Open',
    #'Assigned',
    'Work In Progress',

    "LOW PRIORITY",

    "Re-Testing Perl Splints and here is a description of the updated issue..."
  );

  # Re-query the ticket again after editing
  &getIssueDetails($iTicket, $FP_PROJECT_ID);
}
else
{
  warn "Failed to get proper ticket number: $iTicket, createIssue() must have failed";
}

# Test, getting a given "organic" ticket number
#&getIssueDetails(35086, $FP_PROJECT_ID);
#&getIssueDetails(35363, $FP_PROJECT_ID);

exit(0);

##
## Test subs API, to be refactored into a module
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

  my $soapenv = $soap->MRWebServices__createIssue
  (
    $soapUser,
    $soapPass,
    $strExtraInfo,
    {
      projectID => $iProjectID,
      submitter => "$strSubmitter",
      title => "$strSubject",
      assignees => $astrAssignees,
      priorityNumber => $iPriorityNumber,
      priorityWords => "$strPriorityWords",
      status => "$strStatus",
      description => "$strDescription\n\n--\n$VERSION",
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

  use Data::Dumper;
  print Dumper($soapenv);

  if($soapenv->fault)
  {
    warn "SOAP FAULT:" . ${$soapenv->fault}{faultstring} . "\n";
    exit;
  }
  else
  {
    $iTicketNumber = $soapenv->result;
    print "<<<{{{$iTicketNumber}}}>>>\n";
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

  my $soapenv = $soap->MRWebServices__editIssue
  (
    $soapUser,
    $soapPass,
    $strExtraInfo,
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
      description => "$strDescription\n\n--\n$VERSION",
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

  use Data::Dumper;
  print Dumper($soapenv);

  if($soapenv->fault)
  {
    print "SOAP FAULT: " . ${$soapenv->fault}{faultstring} . "\n";
    exit;
  }

  print "editIssue: done\n";

  return $soapenv->result;
}

sub getIssueDetails()
{
  my ($iTicketNumber, $iProjectID) = @_;

  my $soapenv = $soap->MRWebServices__getIssueDetails
  (
    $soapUser,
    $soapPass,
    $strExtraInfo,
    $iProjectID,
    $iTicketNumber
  );

  print "post SOAP request ---\n";

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT ---";
    print ${$soapenv->fault}{faultstring} . "\n";
    exit;
  }
  else
  {
    print "SOAP OK ---\n";
    $result = $soapenv->result;
  }

  print "<<<{{{$soapenv}}}>>>\n";

  use Data::Dumper;
  print Dumper($soapenv);

  print "post result ---\n";

  print "<<<{{{$result}}}>>>\n";

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


# EOF

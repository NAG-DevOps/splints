#!/usr/bin/perl

use strict;
use warnings;

use SOAP::Lite;

my $VERSION = "splints-0.0.5-dev";

use SPLINTS::Config;

#use SPLINTS::HardcodedCredentialsProvider;
use SPLINTS::PromptCredentialsProvider;

my $bDebug = 1;

##
## main()
##

#$SPLINTS::Config::soapUser = SPLINTS::HardcodedCredentialsProvider::getUsername();
#$SPLINTS::Config::soapPass = SPLINTS::HardcodedCredentialsProvider::getPassword();

$SPLINTS::Config::soapUser = SPLINTS::PromptCredentialsProvider::getUsername();
$SPLINTS::Config::soapPass = SPLINTS::PromptCredentialsProvider::getPassword();

#print "[$SPLINTS::Config::soapUser] --- [$SPLINTS::Config::soapPass]\n";
#exit;

my $soap = new SOAP::Lite;

$soap->uri("$SPLINTS::Config::baseUrl/MRWebServices");

print "post URI ---\n" if $bDebug;

if($SPLINTS::Config::USE_PROXY_SERVER)
{
  $soap->proxy
  (
    "$SPLINTS::Config::baseUrl/MRcgi/MRWebServices.pl",
    proxy => ['http' => "$SPLINTS::Config::proxyUrl/"]
  );
}
else
{
  $soap->proxy("$SPLINTS::Config::baseUrl/MRcgi/MRWebServices.pl");
  print "post non-proxy proxy ---\n" if $bDebug;
}

# Get a list of all open tickets
&queryIssues($SPLINTS::Config::FP_PROJECT_ID);

# Get all tickets with a specific subject pattern
#mrstatus = 'Assigned'
#mrstatus = 'Closed'
#mrstatus = '_DELETED_'
#mrstatus = 'Open'
#mrstatus = '_REQUEST_'
#mrstatus = '_SOLVED_'

&queryIssues
(
  $SPLINTS::Config::FP_PROJECT_ID,
  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Open'"
);

&queryIssues
(
  $SPLINTS::Config::FP_PROJECT_ID,
  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Closed'"
);

&queryIssues
(
  $SPLINTS::Config::FP_PROJECT_ID,
  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Assigned'"
);

&queryIssues
(
  $SPLINTS::Config::FP_PROJECT_ID,
  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrPRIORITY=1"
);

exit(0);

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
  $SPLINTS::Config::FP_PROJECT_ID,

  # Appears have to be an FP user via API
  "$SPLINTS::Config::soapUser",

  "$VERSION: testing createIssue()...",
  #"$VERSION: createIssue()'d ticket for user2's mail test",

  #['user1', 'SOME-Queue L3'],
  ['SOME-Queue L3'],
  #['OTHE-Queue L3'],
  #[],
  
  #4,
  3,

  'Open',
  #'Assigned',

  #"Not too urgent",
  "HIGH PRIORITY",
  #"Service Down",

  #"Testing Perl Splints and here is a description of the initial issue..."
  "From Perl Splints and here is a description of the initial issue..."
);

#exit(0);

if($iTicket > 0)
{
  &getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

  &editIssue
  (
    $iTicket,

    $SPLINTS::Config::FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$SPLINTS::Config::soapUser",

    "$VERSION: testing editIssue(now)... -- renaming $iTicket",

    #['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    [ ],
  
    4,
    #3,

    #'Open',
    #'Assigned',
    'Work In Progress',

    "LOW PRIORITY",

    "Re-Testing Perl Splints and here is a description of the updated issue..."
  );

  # Re-query the ticket again after editing
  &getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

  # Link one or more other tickets, assuming $iTicket as a "parent"
  # 'dynamic' link causes edits to one ticket propagate to the others
  # via editIssue() (new subject, description, priority, status, etc.
  # are set for all links if dynaminc if changed)
  #&linkIssues($iTicket, $FP_PROJECT_ID, 5422, $FP_PROJECT_ID);
  #&linkIssues($iTicket, $FP_PROJECT_ID, 5410, $FP_PROJECT_ID);
  #&linkIssues($iTicket, $FP_PROJECT_ID, 5409, $FP_PROJECT_ID);
  #&linkIssues($iTicket, $FP_PROJECT_ID, 5403, $FP_PROJECT_ID);
  #&linkIssues($iTicket, $FP_PROJECT_ID, 5401, $FP_PROJECT_ID);

  # Close $iTicket
  &editIssue
  (
    $iTicket,

    $SPLINTS::Config::FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$SPLINTS::Config::soapUser",

    "$VERSION: testing create/edit/link/getIssue() -- closing $iTicket",

    ['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    #[ ],
  
    #4,
    3,

    'Resolved',

    "MILD PRIORITY",

    "Re-re-testing Perl Splints and here is a description of the updated issue after linking tickets and closing $iTicket..."
  );

  # Re-query the ticket again after editing
  &getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

  my $iSpecificTicket = 5402;

  # Link one or more other tickets, assuming $iTicket as a "parent"
  # 'static' link maintains links but does not propagate the changes
  # to the linked tickets, unlike 'dynamic'
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, $iTicket, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5417, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5416, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5415, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5414, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5413, $SPLINTS::Config::FP_PROJECT_ID, 'static');

  # Close a specific hardcoded ticket
  &editIssue
  (
    $iSpecificTicket,

    $SPLINTS::Config::FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$SPLINTS::Config::soapUser",

    "$VERSION: testing create/edit/static link/getIssue() -- closing $iSpecificTicket",

    ['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    #[ ],
  
    #4,
    3,

    'Resolved',

    "MILD PRIORITY",

    "Re-re-testing Perl Splints and here is a description of the updated issue after statically linking tickets and closing $iSpecificTicket,.."
  );

  # Re-query the ticket again after editing
  &getIssueDetails($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID);
}
else
{
  warn "Failed to get proper ticket number: $iTicket, createIssue() must have failed";
  exit(1);
}

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

  if($bDebug)
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
    print "<<<{{{$iTicketNumber}}}>>>\n" if $bDebug;
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

  if($bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  if($soapenv->fault)
  {
    warn "SOAP FAULT: " . ${$soapenv->fault}{faultstring};
    exit(1);
  }

  print "editIssue: done\n" if $bDebug;

  return $soapenv->result;
}

sub getIssueDetails()
{
  my ($iTicketNumber, $iProjectID) = @_;

  my $soapenv = $soap->MRWebServices__getIssueDetails
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    $iProjectID,
    $iTicketNumber
  );

  print "post SOAP request ---\n" if $bDebug;

  my $result;

  if($soapenv->fault)
  {
    warn "SOAP FAULT --- ${$soapenv->fault}{faultstring}";
    exit(1);
  }
  else
  {
    print "SOAP OK ---\n" if $bDebug;
    $result = $soapenv->result;
  }

  print "<<<{{{$soapenv}}}>>>\n" if $bDebug;

  if($bDebug)
  {
    use Data::Dumper;
    print Dumper($soapenv);
  }

  print "post result ---\n" if $bDebug;

  print "<<<{{{$result}}}>>>\n" if $bDebug;

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

  my $soapenv = $soap->MRWebServices__linkIssues
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    {
       linkType => $strLinkType,
       issue1   => {projectID => $iProjectID1, mrID => $iTicketNumber1},
       issue2   => {projectID => $iProjectID1, mrID => $iTicketNumber2},
    }
  );

  my $result;

  if($soapenv->fault)
  {
    print ${$soapenv->fault}{faultstring} . "\n";
    exit;
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

  $strQuery = "SELECT mrID, mrTITLE FROM MASTER$iProjectID WHERE mrSTATUS = 'Open'"
    if not defined($strQuery);

  my $soapenv = $soap->MRWebServices__search
  (
    $SPLINTS::Config::soapUser,
    $SPLINTS::Config::soapPass,
    $SPLINTS::Config::strExtraInfo,
    $strQuery
  );

  my $result;

  if($soapenv->fault)
  {
    print ${$soapenv->fault}{faultstring} . "\n";
    exit;
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

    foreach my $item ( keys %{$hash_ref} )
    {
        my $val = $hash_ref->{$item};
        print "$item = '$val'\n";
    }

    print "---------------------\n";
  }

  return $result;
}

# EOF

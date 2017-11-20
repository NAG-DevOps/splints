#!/usr/bin/perl -w

use strict;

use SOAP::Lite;

my $VERSION = "splints-0.0.4";

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

print "post URI ---\n" if $bDebug;

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
  print "post non-proxy proxy ---\n" if $bDebug;
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
  #"$VERSION: createIssue()'d ticket for mail test",

  #['user1', 'SOME-Queue L3'],
  ['SOME-Queue L3'],
  #['OTHE-Queue L3'],
  #[],
  
  #4,
  #2,
  #1,
  3,

  'Open',
  #'Assigned',

  #"Not too urgent",
  "HIGH PRIORITY",
  #"Service Down",

  #"Testing Perl Splints and here is a description of the initial issue..."
  "From Perl Splints and here is a description of the initial issue..."
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

    #['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    [ ],
  
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

  # Link one or more other tickets, assuming $iTicket as a "parent"
  # 'dynamic' link causes edits to one ticket propagate to the others
  # via editIssue() (new subject, description, priority, status, etc.
  # are set for all links if dynaminc if changed)
  #&linkIssue($iTicket, $FP_PROJECT_ID, 5422, $FP_PROJECT_ID);
  #&linkIssue($iTicket, $FP_PROJECT_ID, 5410, $FP_PROJECT_ID);
  #&linkIssue($iTicket, $FP_PROJECT_ID, 5409, $FP_PROJECT_ID);
  #&linkIssue($iTicket, $FP_PROJECT_ID, 5403, $FP_PROJECT_ID);
  #&linkIssue($iTicket, $FP_PROJECT_ID, 5401, $FP_PROJECT_ID);

  # Close $iTicket
  &editIssue
  (
    $iTicket,

    $FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$soapUser",

    "$VERSION: testing create/edit/link/getIssue() -- closing $iTicket",

    ['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    #[ ],
  
    #4,
    2,
    #1,
    #3,

    'Resolved',

    "MILD PRIORITY",

    "Re-re-testing Perl Splints and here is a description of the updated issue after linking tickets and closing $iTicket..."
  );

  # Re-query the ticket again after editing
  &getIssueDetails($iTicket, $FP_PROJECT_ID);

  # Link one or more other tickets, assuming $iTicket as a "parent"
  # 'static' link maintains links but does not propagate the changes
  # to the linked tickets, unlike 'dynamic'
  &linkIssue(5402, $FP_PROJECT_ID, $iTicket, $FP_PROJECT_ID, 'static');
  &linkIssue(5402, $FP_PROJECT_ID, 5417, $FP_PROJECT_ID, 'static');
  &linkIssue(5402, $FP_PROJECT_ID, 5416, $FP_PROJECT_ID, 'static');
  &linkIssue(5402, $FP_PROJECT_ID, 5415, $FP_PROJECT_ID, 'static');
  &linkIssue(5402, $FP_PROJECT_ID, 5414, $FP_PROJECT_ID, 'static');
  &linkIssue(5402, $FP_PROJECT_ID, 5413, $FP_PROJECT_ID, 'static');

  # Close a specific ticket
  &editIssue
  (
    5402,

    $FP_PROJECT_ID,

    # Appears have to be an FP user via API
    "$soapUser",

    "$VERSION: testing create/edit/static link/getIssue() -- closing 5402",

    ['user1', 'SOME-Queue L3'],
    #['SOME-Queue L3'],
    #[ ],
  
    #4,
    #2,
    #1,
    3,

    'Resolved',

    "MILD PRIORITY",

    "Re-re-testing Perl Splints and here is a description of the updated issue after statically linking tickets and closing 5402,.."
  );

  # Re-query the ticket again after editing
  &getIssueDetails(5402, $FP_PROJECT_ID);
}
else
{
  warn "Failed to get proper ticket number: $iTicket, createIssue() must have failed";
  exit(1);
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
    $soapUser,
    $soapPass,
    $strExtraInfo,
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

sub linkIssue()
{
  # By calling this, you don't need to worry about whether this is a new or edited contact. This method
  # will do all the work.

  # Can link tickets across workspaces $iProjectID1 and $iProjectID2,
  # else they are simply the same
  my ($iTicketNumber1, $iProjectID1, $iTicketNumber2, $iProjectID2, $strLinkType) = @_;

  $strLinkType = 'dynamic' if not defined($strLinkType);

  my $soapenv = $soap->MRWebServices__linkIssues
  (
    $soapUser,
    $soapPass,
    $strExtraInfo,
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
    warn "Link ($strLinkType) $iTicketNumber1:$iProjectID1 -> $iTicketNumber2:$iProjectID2 failed.";
  }

  return $result;
}

# EOF

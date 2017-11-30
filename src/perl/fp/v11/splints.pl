#!/usr/bin/perl

use strict;
use warnings;

use SOAP::Lite;

use Getopt::Std;
use Getopt::Long;

our $VERSION = "splints-0.0.6-dev";

use SPLINTS::Config;
#use SPLINTS::HardcodedCredentialsProvider;
use SPLINTS::PromptCredentialsProvider;
use SPLINTS::FootPrints11;

our $bDebug = 0;

my %options = ();

##
## main()
##

#$SPLINTS::Config::soapUser = SPLINTS::HardcodedCredentialsProvider::getUsername();
#$SPLINTS::Config::soapPass = SPLINTS::HardcodedCredentialsProvider::getPassword();

$SPLINTS::Config::soapUser = SPLINTS::PromptCredentialsProvider::getUsername();
$SPLINTS::Config::soapPass = SPLINTS::PromptCredentialsProvider::getPassword();

my $sim         = ''; # default false
my $quick       = ''; # defayypult none
my $edit        = ''; # defayypult none
my $description = 'Created / updated.'; # default none
my $subject     = ''; # default none
my $ticket      = ''; # default none

my $debug       = ''; # default none


my $all         = ''; # default false
my $help        = ''; # default false
my $gig         = ''; # default none
my $pc          = ''; # default none

GetOptions
(
  "sim"            => \$sim,    # flag
  "quick"            => \$quick,    # flag
  "debug"            => \$quick,    # flag
  "description=s"    => \$description,    # flag
  "subject=s"    => \$subject,    # flag
  "ticket=i"                  => \$ticket,         # string

  "edit"            => \$edit, # flag
  "all"            => \$all,          # flag
  "help"              => \$help,         # flag
  "g=s"                  => \$gig,          # string
  "p=s"                  => \$pc,           # string
) or die "Can't process command line options: $!";
die print_usage() if $help;

$bDebug = 1 if $debug;

#print "[$SPLINTS::Config::soapUser] --- [$SPLINTS::Config::soapPass]\n";
#exit;

our $soap = new SOAP::Lite;

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

if($quick)
{
  print "--quick\n";
  #die "description: [$description]\n";
  die "Need description: [$description]" if $description eq "";

  my $title = $subject ? $subject : $description;

  my $iTicket = &SPLINTS::FootPrints11::createIssue
  (
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Quick ticket ($VERSION): $title",
    ['SOME-Queue L3'],
    #[],
    5,
    'Assigned',
    "No priority words",
    #"Testing Perl Splints and here is a description of the initial issue..."
    "$description"
  );

  print "Created ticket: [$iTicket]\n";
}
elsif($edit)
{
  my $title = $subject ? $subject : $description;

  &SPLINTS::FootPrints11::editIssue
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Edited ticket ($VERSION): $title",
    ['SOME-Queue L3'],
    #[ ],
    4,
    #'Assigned',
    'Work In Progress',
    "LOW PRIORITY",
    "$description"
  );
  print "Edited ticket: [$ticket]\n";
}
elsif($sim)
{
  # Get a list of all open tickets
  &SPLINTS::FootPrints11::queryIssues($SPLINTS::Config::FP_PROJECT_ID);
#}
#else
#{
#  print "Not --sim\n";
#  exit(0);
#}

#exit(0);

# Get all tickets with a specific subject pattern
#mrstatus = 'Assigned'
#mrstatus = 'Closed'
#mrstatus = '_DELETED_'
#mrstatus = 'Open'
#mrstatus = '_REQUEST_'
#mrstatus = '_SOLVED_'

&SPLINTS::FootPrints11::queryIssues
(
  $SPLINTS::Config::FP_PROJECT_ID,
  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Open'"
);

#&SPLINTS::FootPrints11::queryIssues
#(
#  $SPLINTS::Config::FP_PROJECT_ID,
#  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Closed'"
#);
#
#&SPLINTS::FootPrints11::queryIssues
#(
#  $SPLINTS::Config::FP_PROJECT_ID,
#  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Assigned'"
#);
#
#&SPLINTS::FootPrints11::queryIssues
#(
#  $SPLINTS::Config::FP_PROJECT_ID,
#  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrPRIORITY=1"
#);

#exit(0);

# Status values:
#   Open
#   Assigned
#   Work In Progress
#   On Hold
#   Pending Customer
#   Scheduled
#   Resolved
#   Cancelled

my $iTicket = &SPLINTS::FootPrints11::createIssue
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
  &SPLINTS::FootPrints11::getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

  &SPLINTS::FootPrints11::editIssue
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
  &SPLINTS::FootPrints11::getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

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
  &SPLINTS::FootPrints11::editIssue
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
  &SPLINTS::FootPrints11::getIssueDetails($iTicket, $SPLINTS::Config::FP_PROJECT_ID);

  my $iSpecificTicket = 5402;

  # Link one or more other tickets, assuming $iTicket as a "parent"
  # 'static' link maintains links but does not propagate the changes
  # to the linked tickets, unlike 'dynamic'
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, $iTicket, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5417, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5416, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5415, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5414, $SPLINTS::Config::FP_PROJECT_ID, 'static');
  &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 5413, $SPLINTS::Config::FP_PROJECT_ID, 'static');

  # Close a specific hardcoded ticket
  &SPLINTS::FootPrints11::editIssue
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
  &SPLINTS::FootPrints11::getIssueDetails($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID);
}
else
{
  warn "Failed to get proper ticket number: $iTicket, createIssue() must have failed";
  exit(1);
}

}
else
{
  print "Not --sim\n";
  exit(0);
}
exit(0);

# EOF

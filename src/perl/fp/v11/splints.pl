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
my $quick       = ''; # default false
my $edit        = ''; # default false
my $details     = ''; # default false
my $link        = ''; # default false
my $status      = ''; # default empty
my $resolve     = ''; # default false
my $assign      = '[ ]'; # default empty list
my $unassign    = '[ ]'; # default empty list
my $linkasset   = ''; # default

my $description = 'Created / updated.'; # default
my $stdin       = ''; # default empty
my $static      = ''; # default empty
my $dynamic     = ''; # default empty
my $subject     = ''; # default empty
my $ticket      = ''; # default empty
my $ticket2     = ''; # default empty
my $tix         = ''; # default empty
my $workstation = 3; # default 3
my $format      = 'raw'; # default empty

my $debug       = ''; # default none

my $all         = ''; # default false
my $help        = ''; # default false
my $gig         = ''; # default none
my $pc          = ''; # default none

GetOptions
(
  "sim"              => \$sim,            # flag
  "quick"            => \$quick,          # flag
  "edit"             => \$edit,           # flag
  "details"          => \$details,        # flag
  "link"             => \$link,           # flag
  "resolve"          => \$resolve,        # flag
  "assign=s"         => \$assign,         # string
  "unassign=s"       => \$unassign,       # string
  "status=s"         => \$status,         # string
  "link-asset=s"     => \$linkasset,      # string

  "debug"            => \$debug,          # flag

  "stdin"            => \$stdin,          # flag
  "static"           => \$static,         # flag
  "dynamic"          => \$dynamic,        # flag

  "description=s"    => \$description,    # string
  "descr=s"          => \$description,    # string
  "subject=s"        => \$subject,        # string
  "ticket=i"         => \$ticket,         # int
  "ticket2=i"        => \$ticket2,        # int
  "tix=s"            => \$tix,            # string
  "format=s"         => \$format,         # string

  "all"            => \$all,          # flag
  "help"              => \$help,         # flag
  "g=s"                  => \$gig,          # string
  "p=s"                  => \$pc,           # string
) or die "Can't process command line options: $!";
#die print_usage() if $help;

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

# --quick
if($quick)
{
  print "--quick\n";

  # --stdin
  if($stdin)
  {
    $description .= "\n";
    while(<STDIN>)
    {
      $description .= $_;
    }
  }

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

# --edit
elsif($edit)
{
  my $title = $subject ? $subject : $description;

  # --stdin
  if($stdin)
  {
    $description .= "\n";
    while(<STDIN>)
    {
      $description .= $_;
    }
  }

  # TODO: check if $ticket is specified
  # TODO: remove forcing of extra fields

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

# --resolve
elsif($resolve)
{
  # TODO: check if $ticket is specified
  # TODO: remove forcing of extra fields

  my $result = &SPLINTS::FootPrints11::editIssue
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Resolved ticket ($ticket)",
    [ ],
    4,
    'Resolved',
    "",
    "Resolving..."
  );

  if($result)
  {
    print "Resolved ticket: [$ticket]\n";
  }
  else
  {
    warn "Failed to resolve ticket [$ticket]";
  }
}

# --assign
elsif($assign)
{
  # TODO: check if $ticket is specified
  # TODO: check if $assign is specified
  # TODO: remove forcing of extra fields

  my $result = &SPLINTS::FootPrints11::editIssue
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Assigned ticket $ticket to $assign",
    $assign,
    4,
    'Assigned',
    "",
    "Assigning..."
  );

  if($result)
  {
    print "Assigned ticket: [$ticket] to [$assign]\n";
  }
  else
  {
    warn "Failed to assign ticket [$ticket] to [$assign]";
  }
}

# --unassign
elsif($unassign)
{
  # TODO: check if $ticket is specified
  # TODO: check if $unassign is specified
  #       remove that assignee from the list and keep others
  # TODO: remove forcing of extra fields

  my $result = &SPLINTS::FootPrints11::editIssue
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Unassigned ticket $ticket",
    [],
    4,
    'Open',
    "",
    "Unassigning..."
  );

  if($result)
  {
    print "Unassigned ticket: [$ticket] from [$unassign]\n";
  }
  else
  {
    warn "Failed to assign ticket [$ticket] from [$unassign]";
  }
}

# --status
elsif($status)
{
  # TODO: check if $ticket is specified
  # TODO: check if $status is specified
  # TODO: remove forcing of extra fields

  my $result = &SPLINTS::FootPrints11::editIssue
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    "$SPLINTS::Config::soapUser",
    "Ticket $ticket status set to $status",
    $assign,
    4,
    $status,
    "",
    "Ticket $ticket status set to $status"
  );

  if($result)
  {
    print "Ticket [$ticket] is [$status]\n";
  }
  else
  {
    warn "Failed to set status of ticket [$ticket] to [$status]";
  }
}

# --details
elsif($details)
{
  # TODO: check if $ticket is specified
  if($format eq "raw")
  {
    &SPLINTS::FootPrints11::getIssueDetails($ticket, $SPLINTS::Config::FP_PROJECT_ID);
    print "Ticket [$ticket]'s details\n";
  }
  else
  {
    warn "Ticket dump format [$format] is not supported.";
  }
}

# --link
elsif($link)
{
  # Link two tickets
  # 'static' link maintains links but does not propagate the changes
  # to the linked tickets, unlike 'dynamic'
  my $linkType = (!$static && !$dynamic) ? "static" : ($static ? 'static' : 'dynamic');

  # TODO: check if $ticket and $ticket2 are specified
  # TODO: allow different workspaces
  
  &SPLINTS::FootPrints11::linkIssues
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    $ticket2,
    $SPLINTS::Config::FP_PROJECT_ID,
    $linkType
  );
  
  print "Done linking tickets [$ticket:$ticket2] as $linkType in PROJ=$SPLINTS::Config::FP_PROJECT_ID.\n";
}

# --link-asset
elsif($linkasset)
{
  # TODO: check if $ticket and $ticket2 are specified
  # TODO: allow different workspaces

  my $result = &SPLINTS::FootPrints11::linkAsset
  (
    $ticket,
    $SPLINTS::Config::FP_PROJECT_ID,
    $workstation, # 3
    $linkasset    # CI-ID
  );

  if($result)
  {
    print "Done linking ticket [$ticket:$SPLINTS::Config::FP_PROJECT_ID] to asset [$workstation-$linkasset]\n";
  }
  else
  {
    warn "Could not link ticket [$ticket:$SPLINTS::Config::FP_PROJECT_ID] to asset [$workstation-$linkasset]";
  }
}

# --sim
elsif($sim)
{
  # Get a list of all open tickets
  &SPLINTS::FootPrints11::queryIssues($SPLINTS::Config::FP_PROJECT_ID);

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

  #&SPLINTS::FootPrints11::queryIssues
  #(
  #  $SPLINTS::Config::FP_PROJECT_ID,
  #  "SELECT mrID, mrTITLE, mrSTATUS from MASTER$SPLINTS::Config::FP_PROJECT_ID WHERE mrTITLE LIKE '%Exam Scoring%' AND mrSTATUS='Assigned'"
  #);

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
  #   Closed

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
    &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 30317, $SPLINTS::Config::FP_PROJECT_ID, 'static');
    &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 30316, $SPLINTS::Config::FP_PROJECT_ID, 'static');
    &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 30315, $SPLINTS::Config::FP_PROJECT_ID, 'static');
    &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 30314, $SPLINTS::Config::FP_PROJECT_ID, 'static');
    &SPLINTS::FootPrints11::linkIssues($iSpecificTicket, $SPLINTS::Config::FP_PROJECT_ID, 30313, $SPLINTS::Config::FP_PROJECT_ID, 'static');

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
  print "No main context option found (@ARGV).\n";
  exit(1);
}

exit(0);

# EOF

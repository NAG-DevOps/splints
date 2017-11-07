#!/usr/bin/perl -w

use strict;

use SOAP::Lite;

##
## Config
## TODO: split into a module
##

#my $USE_PROXY_SERVER = 1;
my $USE_PROXY_SERVER = 0;

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

# Test, getting given ticket number
&getIssueDetails(35086, $FP_PROJECT_ID);

exit(0);

##
## Test subs API, to be refactored into a module
##

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
    print "SOAP FAULT ---\n";
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
}


# EOF

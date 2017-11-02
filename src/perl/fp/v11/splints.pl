#!/usr/bin/perl -w

use strict;

use SOAP::Lite;

#my $USE_PROXY_SERVER = 1;
my $USE_PROXY_SERVER = 0;

my $FP_PROJECT_ID = 72;

my $soap = new SOAP::Lite;

$soap->uri( 'https://localhost/MRWebServices' );

print "post URI ---\n";

if( $USE_PROXY_SERVER )
{
    $soap->proxy(
        'https://localhost/MRcgi/MRWebServices.pl',
        proxy => ['http' => 'http://localhost:8888/'] );
}
else
{
    $soap->proxy( 'https://localhost/MRcgi/MRWebServices.pl' );
    print "post non-proxy proxy ---\n";
}

&getIssueDetails(3508, $FP_PROJECT_ID);

exit(0);

##
## Test subs API, to be refactored into a module
##

sub getIssueDetails()
{
  my ($iTicketNumber, $iProjectID) = @_;

  my $soapenv = $soap->MRWebServices__getIssueDetails(
    'fakeuser',
    'fakepassword',
    '',
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

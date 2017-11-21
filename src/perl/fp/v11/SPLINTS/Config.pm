#!/usr/bin/perl

use strict;
use warnings; 

package SPLINTS::Config;

#our $USE_PROXY_SERVER = 1;
our $USE_PROXY_SERVER = 0;

# Footprints Workspace
our $FP_PROJECT_ID = 27;

our $soapUser = 'agentusername';
our $soapPass = 'agentpassword';

our $baseUrl  = 'https://localhost';

our $proxyUrl  = 'https://localhost:8888';

our $strExtraInfo = '';

1;

# EOF

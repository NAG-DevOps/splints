#!/usr/bin/perl

use strict;
use warnings; 

package SPLINTS::HardcodedCredentialsProvider;

# HardcodedCredentialsProvider as the name implies has
# username and password stored in this file.
#
# WARNING: unless you are doing quick tests with a local
#          instance, do NOT use this; use prompt or other
#          providers!

sub getUsername
{
  return 'hardcodeduser';
}

sub getPassword
{
  return 'hardcodedpass';
}

1;

# EOF

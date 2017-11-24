#!/usr/bin/perl

use strict;
use warnings; 

use Term::ReadKey;

package SPLINTS::PromptCredentialsProvider;

# PromptCredentialsProvider asks for username and password
# instead of hardcoding them. A primitive implementation.

sub getUsername
{
  print "Your username: ";
  chomp(my $username = <STDIN>);
  return $username;
}

sub getPassword
{
  print "Your password: ";
  Term::ReadKey::ReadMode('noecho');
  chomp(my $password = <STDIN>);
  Term::ReadKey::ReadMode('restore');
  #Term::ReadKey::ReadMode(0);
  return $password;
}

1;

# EOF

#!/usr/bin/perl

use strict;
use warnings;

#
# Legend and constant values
# @since 0.0.7
#

package SPLINTS::Constants;

# Fields

our $WORKSPACE          = 'projectID';
our $ISSUE              = 'mrID';
our $ISSUE_SUBJECT      = 'title';
our $ISSUE_STATUS       = 'status';
our $CONTACT_FIRST_NAME = 'First__bName';
our $CONTACT_LAST_NAME  = 'Last__bName';
our $CONTACT_EMAIL      = 'Email__bAddress';
our $SUBMITTER          = 'submitter';
our $ASSIGNEES          = 'assignees';
our $PRIORITY_NUMBER    = 'priorityNumber';
our $DESCRIPTION        = 'description';
 
# Custom fields
our $IMPACT             = 'Impact';
our $URGENCY            = 'Urgency';
our $CONTACT_METHOD     = 'Contact__bMethod';
our $TICKET_TYPE        = 'Type__bof__bTicket';
our $SERVICE_CATGORY    = 'Service__bCategory';
our $SERVICE_NAME       = 'Service__bName';

# Common field values

our @aImacts = ('Single User');
our %hValidImpacts = ($IMPACT => @aImacts);

our @aUrgencies = ('Working Normally / Inquiry');
our %hValidUrgencies = ($URGENCY => @aUrgencies);

our @aContactMethods = ('Email', 'Other');
our %hValidContactMethods = ($CONTACT_METHOD => @aContactMethods);

our @aTicketTypes = ('Service Request', 'Incident');
our %hValidTicketTypes = ($TICKET_TYPE => @aTicketTypes);

our @aServiceCateogies = ('User - IT Support');
our %hValidServiceCategories = ($SERVICE_CATGORY => @aServiceCateogies);

our @aServiceNames = ('ENCS Services');
our %hValidServiceNames = ($SERVICE_NAME => @aServiceNames);

# EOF

# splints - team08
**S**pecial-**P**urpose **L**egwork for **IN**tegration of **T**icketing **S**ystems

v0.0.7-dev

Table of Contents
=================

   * [splints](#splints)
   * [Table of Contents](#table-of-contents)
      * [Installation](#installation)
         * [Perl 5](#perl-5)
         * [PHP](#php)
         * [EL7, EL6, EL7](#el7-el6-el7)
            * [EL7](#el7)
            * [EL6 and EL5](#el6-and-el5)
         * [OS X](#os-x)
         * [Windows 7 and 10](#windows-7-and-10)
            * [ActivePerl-5 or Strawberry Perl](#activeperl-5-or-strawberry-perl)
      * [Running](#running)
         * [FootPrints v11.6 and Perl 5](#footprints-v116-and-perl-5)
            * [Linux and OS X](#linux-and-os-x)
               * [Options](#options)
               * [Examples](#examples)
            * [Windows](#windows)
      * [Supported API](#supported-api)
         * [Summary](#summary)
         * [Details](#details)
      * [TODO](#todo)
         * [Tickecting Systems Support](#tickecting-systems-support)
         * [Language Support](#language-support)
      * [Contribution Guidelines](#contribution-guidelines)
      * [References](#references)

## Installation ##

### Perl 5 ###

Current version is in its very beginning with the gradual
support being added to work with Footprints v11 in Perl on EL6.
It requires `SOAP::Lite` Perl module to work and `Term::ReadKey`
for password prompts if the corresponding credentials provider is in use.
`Crypt::SSLeay` is required for HTTPS access.
The PoC implementation being modularized based on the
Perl sample of the Chapter 11 of BMC FootPrints Service Core manual.

### PHP ###

Experimental PHP 5.5.36 development is in progress.
PHP's `SoapClient` is required.
The PoC implementation being modularized based on the
PHP sample of the Chapter 11 of BMC FootPrints Service Core manual.

### EL7, EL6, EL7 ###

- Automated compile tests on CentOS 6.x and 7.x:
  [![Build Status](https://travis-ci.org/NAG-DevOps/splints.svg?branch=master)](https://travis-ci.org/NAG-DevOps/splints)

#### EL7 ####

- `yum install epel-release`
- (follow the steps for EL6 and EL5 below)

#### EL6 and EL5 ####

- `yum install git`
- `yum install perl-SOAP-Lite`
- `yum install perl-Crypt-SSLeay`
- `yum install perl-TermReadKey` (if you use `SPLINTS::PromptCredentialsProvider`, which you should)
- `git clone https://github.com/NAG-DevOps/splints`

### OS X ###

- open Terminal app
- if getting an `xcrun: error` while doing any of the below after updating your OS X, update your XCode command line tools: `xcode-select --install`
- `cpan -i SOAP::Lite`
- `cpan -i Term::ReadKey` (if you use `SPLINTS::PromptCredentialsProvider`, which you should)
- `git clone https://github.com/NAG-DevOps/splints`

### Windows 7 and 10 ###

#### ActivePerl-5 or Strawberry Perl ####

- get and install Perl for Windows, either:
    - [ActivePerl-5.24.2.2403-MSWin32-x64-403863.exe (or latest)](https://www.activestate.com/activeperl), or
    - [strawberry-perl-5.26.1.1-64bit.msi (or latest)](http://Strawberryperl.com/)
- open a command prompt `cmd.exe`
- `cpan -i SOAP::Lite`
- `cpan -i Term::ReadKey` (if you use `SPLINTS::PromptCredentialsProvider`, which you should)
- using your favorite method (GitHub Desktop, SourceTree, GitBash, etc.), clone https://github.com/NAG-DevOps/splints

## Running ##

Current version has limitations below. See supported API.

### FootPrints v11.6 and Perl 5 ###

#### Linux and OS X ####

- navigate to `src/perl/fp/v11`
- run `./splints.pl [options]`
- (customize `splints.pl`'s `## main` section or `SPLINTS/FootPrints11.pm` if needed)

##### Options #####

- `--quick` -- create a "quick ticket" with an optional description, default assignee, and a subject
- `--edit` -- quick editing a ticket by appending a description and a subject
- `--resolve` -- resolves a specified ticket
- `--status=s` -- sets a specified status `s` of a specified ticket
- `--assign=s[,s]` -- assigns a specified ticket to the assignee 's'; `--status=Assigned` is implied
- `--unassign=s[,s]` -- unassigns a specified ticket from the assignee 's'
- `--details` -- dump ticket details in a simple raw list form
- `--link` -- link two tickets
- `--link-asset=s` -- link CI (defaults to a specific asset type 3) to a specified ticket

- `--stdin` -- populate ticket description from STDIN (can be piped from other programs), works with `--quick` and `--edit`; if `--description` is set, it is pre-pended to the STDIN part.
- `--static` -- use static linking of tickets (default); works with `--link`
- `--dynamic` -- use dynamic linking of tickets; works with `--link`

- `--description=s` -- string of the description to append to the ticket
- `--descr=s` -- shorthand for `--description`
- `--subject=s` -- string of the subject line portion (title of the ticket)
- `--ticket=i` -- ticket number to edit, see details of, or link
- `--ticket2=i` -- other ticket number to link to
- `--format=s` -- ticket dump format (`s=raw` is implied)

- `--debug` -- enable verbose debug mode; **NOTE:** as a part of the request dump your credentials are dumped as well
- `--sim` -- run a simulation scenario of creating, examining, linking, and closing a ticket (the original 0.0.5-'s `main`)

##### Examples #####

- `./splints.pl --quick`
- `./splints.pl --quick --subject=foo --description=barbar`
- `./splints.pl --edit --ticket=5461 --subject=foofoo --description=barbarbaz`
- `cal | ./splints.pl --quick --stdin --subject='stdin description body test'`
- `cal | ./splints.pl --edit --ticket=5461 --stdin --subject='optional subject for stdin description body test'`
- `./splints.pl --resolve --ticket=5474`
- `./splints.pl --status='Open' --ticket=5474`
- `./splints.pl --status='Work In Progress' --ticket=5474`
- `./splints.pl --assign=user1 --ticket=5474`
- `./splints.pl --assign=user1,user2 --ticket=5474`
- `./splints.pl --assign='SOME-Queue L3' --ticket=5474`
- `./splints.pl --unassign=user1 --ticket=5474`
- `./splints.pl --link --ticket=5473 --ticket2=5474`
- `./splints.pl --link --ticket=5474 --ticket2=5475 --dynamic`
- `./splints.pl --details --ticket=5474`
- `./splints.pl --link-asset=5504 --ticket=35506`
- `./splints.pl --sim`
- `./splints.pl --sim --debug`

#### Windows ####

- either open terminal from SourceTree or Git Bash or
- in `cmd.exe` cd to `src\perl\fp\v11`
- (in Notepad or Notepad++ customize `splints.pl`'s `## main` section or `SPLINTS/FootPrints11.pm` if needed)
- run `perl splints.pl [options]` -- options are documented above

## Supported API ###

### Summary ###

| API                            | FP11 + Perl | FP11 + PHP | FP11 + Java |
| ------------------------------ |:-----------:|:----------:|:-----------:|
| `Config`                       | *           | *          |             |
| `Constants`                    | +--         |            |             |
| `PromptCredentialsProvider`    | *           |            |             |
| `HardcodedCredentialsProvider` | *           |            |             |
| `FootPrints11`                 | *           | *          |             |
| `createIssue()`                | *           | *          | *           |
| `editIssue()`                  | *           | *          | *           |
| `--resolve`                    | *           |            |             |
| `--status`                     | *           |            |             |
| `--assign`                     | *           |            |             |
| `--unassign`                   | *           |            |             |
| `getIssueDetails()`            | *           |            |             |
| `--format=raw`                 | *           |            |             |
| `--format=email`               |             |            |             |
| `--format=flucid`              |             |            |             |
| `linkIssues()`                 | *           |            |             |
| `queryIssues()`                | *           |            |             |
| `createAsset()`                |             |            |             |
| `linkAsset()`                  | * (CI to ticket)  |      |             |
| `editAsset()`                  |             |            |             |
| `createContact()`              |             |            |             |
| `editContact()`                |             |            |             |

### Details ###

- `SPLINTS::Config` -- FootPrints instance URLs, credentials, etc.
- `SPLINTS::Constants` -- legend and constants for common and custom fields and their values
- `SPLINTS::PromptCredentialsProvider` -- simple credentials prompting modue (default)
    - `getUsername()`
    - `getPassword()`
- `SPLINTS::HardcodedCredentialsProvider` -- simple hardcoded credtianls; DON'T use unless absolutely have to
    - `getUsername()`
    - `getPassword()`
- `SPLINTS::FootPrints11::createIssue()` -- creates a ticket
```perl
$iReturnedTicketNumber = &SPLINTS::FootPrints11::createIssue();

sub createIssue()
(
    $iProjectID,       # -- workspace
    $strSubmitter,     # -- FP agent's username
    $strSubject,       # -- title of the ticket
    $astrAssignees,    # -- an array of assignees, can be empty `[]`
    $iPriorityNumber,  # -- ticket priority
    $strStatus,        # -- status (e.g., `Open`)
    $strPriorityWords, # -- priority keywords set together with status
    $strDescription    # -- ticket description
)
```
- `SPLINTS::FootPrints11::editIssue()` -- edits a ticket
```perl
sub editIssue()
(
    $iTicketNumber,    # -- ticket to edit
    $iProjectID,       # -- workspace
    $strSubmitter,     # -- submitting agent
    $strSubject,       # -- new title of the ticket
    $astrAssignees,    # -- new assignees
    $iPriorityNumber,  # -- new priority
    $strStatus,        # -- new status
    $strPriorityWords, # -- new priority words
    $strDescription    # -- description to append
)

returns SOAP result
```
- `SPLINTS::FootPrints11::getIssueDetails()` -- queries ticket's details
```perl
sub getIssueDetails()
(
    $iTicketNumber,    # -- ticket to get info about
    $iProjectID        # -- workspace
)

returns SOAP result Perl hash with all the ticket fields
```
- `SPLINTS::FootPrints11::linkIssues()` -- links two tickets; `dynamic` linking causes changes to one ticket
to be repeated for the other linked ticket(s); `static` simply refers
```perl
sub linkIssues()
(
    $iTicketNumber1,    # -- ticket1 to link to
    $iProjectID1,       # -- workspace of ticket1
    $iTicketNumber2,    # -- ticket2 to get linked
    $iProjectID2,       # -- workspace of ticket2
    $strLinkType        # -- 'static' or 'dynamic' (default)
)

returns SOAP result
```
- `SPLINTS::FootPrints11::queryIssues()` -- queries all issues per query criteria
```perl
sub queryIssues()
(
    $iProjectID,       # -- workspace
    $strQuery          # -- optional SQL query
)

returns SOAP result Perl hash with all the query results, including
ticket numbers, titles, and status. If query is not specified, returns
all 'Open' tickets.
```
- `SPLINTS::FootPrints11::linkAsset()` -- links a ticket and CI CMDB entry
```perl
sub linkIssues()
(
    $iTicketNumber,     # -- ticket to link to
    $iProjectID,        # -- workspace of ticket1
    $iAssedCMDBID,      # -- CMDB ID type to link to
    $iAssetID           # -- CI-ID to link to
)

returns SOAP result
```

## TODO ##

- See [Issues](https://github.com/NAG-DevOps/splints/issues)
- Add CI and Contact API support (in progress)

### Tickecting Systems Support ###

- add support for BestPractical RT
- add support for BMC Footprints v12
- add support for GitHub Issues
- add support for Bitbucket Cloud Issues

### Language Support ###

- PHP (in progress)
- Java (in progress)
- Python
- C#

## Contribution Guidelines ##

1. Fork the repo (or sync it if already have)
2. Clone your fork (or merge/rebase)
3. Create a topic branch in your fork
4. Make your modifications
5. Commit and push to your fork
6. Submit your pull request (PR) for review from your topic branch to master here
7. Make any required modifications based on the PR reviews
8. PR is merged, go to 1

## References ##

- [`SOAP::Lite`](http://search.cpan.org/perldoc?SOAP%3A%3ALite) Documentation, CPAN
- [`Term::ReadKey`](http://search.cpan.org/perldoc?Term%3A%3AReadKey) Documentation, CPAN
- [BMC FootPrints v11.6 manual](https://docs.bmc.com/docs/display/public/FPSC0/Version+11.6+PDFs)
- BMC FootPrints v11 SOAP Web Services API and sample code
    - [API Reference](https://tracks.usask.ca/help/FootPrintsHelp/content/footprints_apimaintopic.htm)
    - [Perl sample](https://tracks.usask.ca/help/FootPrintsHelp/content/perl_sample.htm)
- [BMC FootPrints v12.x Web Services API](https://docs.bmc.com/docs/display/public/FPSC120/Configuring+Web+Services)
- [BestPractical RT REST API](https://rt-wiki.bestpractical.com/wiki/REST)
- [GitHub Issues REST API](https://developer.github.com/v3/issues/)
- [Bitbucket Issues REST API](https://confluence.atlassian.com/bitbucket/issues-resource-296095191.html)
- TOC created by [gh-md-toc](https://github.com/ekalinin/github-markdown-toc)

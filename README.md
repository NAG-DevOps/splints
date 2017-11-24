# splints
**S**pecial-**P**urpose **L**egwork for **IN**tegration of **T**icketing **S**ystems

v0.0.5-dev

Table of Contents
=================

   * [splints](#splints)
   * [Table of Contents](#table-of-contents)
      * [Installation](#installation)
         * [EL6 and EL7](#el6-and-el7)
            * [EL7](#el7)
            * [EL6](#el6)
         * [OS X](#os-x)
      * [Running](#running)
         * [FootPrints v11.6](#footprints-v116)
      * [Supported API](#supported-api)
      * [TODO](#todo)
         * [Tickecting Systems Support](#tickecting-systems-support)
         * [Language Support](#language-support)
         * [OS Support](#os-support)
      * [Contribution Guidelines](#contribution-guidelines)
      * [References](#references)

## Installation ##

Current version is in its very beginning with the gradual
support being added to work with Footprints v11 in Perl on EL6.
It requires `SOAP::Lite` Perl module to work and `Term::ReadKey`
for password prompts if the corresponding credentials provider is in use.
The PoC implementation being modularized based on the
Perl sample of the Chapter 11 of BMC FootPrints Service Core manual.

### EL6 and EL7 ###

- Automated compile tests on CentOS 6.x and 7.x:
  [![Build Status](https://travis-ci.org/NAG-DevOps/splints.svg?branch=master)](https://travis-ci.org/NAG-DevOps/splints)

#### EL7 ####

- `yum install epel-release`
- (follow the steps for EL6)

#### EL6 ####

- `yum install git`
- `yum install perl-SOAP-Lite`
- `yum install perl-TermReadKey` (is you use `SPLINTS::PromptCredentialsProvider`, which you should)
- `git clone https://github.com/NAG-DevOps/splints`

### OS X ###

- open Terminal app
- `cpan -i SOAP::Lite`
- `cpan -i Term::ReadKey` (is you use `SPLINTS::PromptCredentialsProvider`, which you should)
- `git clone https://github.com/NAG-DevOps/splints`

## Running ##

Current version has limitations below. See supported API.

### FootPrints v11.6 ###

- navigate to `src/perl/fp/v11`
- customize `splints.pl`'s `## main` section to your needs; TODO in progress to use options
- run `./splints.pl`

## Supported API ###

- `SPLINTS::Config` -- FootPrints instance URLs, credentials, etc.
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

## TODO ##

- See [Issues](https://github.com/NAG-DevOps/splints/issues)
- Add `GetOpt` support

### Tickecting Systems Support ###

- add support for BestPractical RT
- add support for BMC Footprints v12
- add support for GitHub Issues
- add support for Bitbucket Cloud Issues

### Language Support ###

- Java
- Python
- PHP
- C#

### OS Support ###

- Windows 7 and Windows 10

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

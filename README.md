# splints
**S**pecial-**P**urpose **L**egwork for **IN**tegration of **T**icketing **S**ystems.

v0.0.3

## Installation ##

Current version is in its very beginning with the gradual
support being added to work with Footprints v11 in Perl on EL6.
It requires `SOAP::Lite` Perl module to work.
The PoC implementation being modularaized based on the
Perl sample of the Chapter 11 of BMC FootPrints Service Core manual.

### EL6 ###

- `yum install git`
- `yum install perl-SOAP-Lite`
- `git clone https://github.com/NAG-DevOps/splints`

## Running ##

Current version has limitations below. See supported API.

### Footprints v11.6 ###

- navigate to `src/perl/fp/v11`
- customize `splints.pl` to your needs
- run `./splints.pl`

## Supported API ###

- `createIssue()` -- creates a ticket
```
#!perl
$iReturnedTicketNumber = createIssue
(
    $iProjectID,       -- workspace
    $strSubmitter,     -- FP agent's username
    $strSubject,       -- title of the ticket
    $astrAssignees,    -- an array of assignees, can be empty `[]`
    $iPriorityNumber,  -- ticket priority
    $strStatus,        -- status (e.g., `Open`)
    $strPriorityWords, -- priority keywords set together with status
    $strDescription    -- ticket description
)
```
- editIssue()` -- edits a ticket
```
#!perl
editIssue()
(
    $iTicketNumber,    -- ticket to edit
    $iProjectID,       -- workspace
    $strSubmitter,     -- submitting agent
    $strSubject,       -- new title of the ticket
    $astrAssignees,    -- new assignees
    $iPriorityNumber,  -- new priority
    $strStatus,        -- new status
    $strPriorityWords, -- new priority words
    $strDescription    -- description to append
)

returns SOAP result
```
- `getIssueDetails()` -- queries ticket's details
```
#!perl
getIssueDetails()
(
    $iTicketNumber,    -- ticket to get info about
    $iProjectID,       -- workspace
)

returns SOAP result Perl hash with all the ticket fields
```
- `linkIssue()` -- links two tickets; `dynamic` linking causes changes to one ticket
to be repeated for the other linked ticket(s); `static` simply refers
```
#!perl
linkIssue()
(
    $iTicketNumber1,    -- ticket1 to link to
    $iProjectID1,       -- workspace of ticket1
    $iTicketNumber2,    -- ticket2 to get linked
    $iProjectID2,       -- workspace of ticket2
    $strLinkType        -- 'static' or 'dynamic' (default)
)

returns SOAP result
```

## TODO ##

- See [Issues](https://github.com/NAG-DevOps/splints/issues)
- Modularize credentials and API
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

- EL7
- OS X
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

- [BMC FootPrints v11.6 manual](https://docs.bmc.com/docs/display/public/FPSC0/Version+11.6+PDFs)
- BMC FootPrints v11 Web Services API and sample
    - https://tracks.usask.ca/help/FootPrintsHelp/content/footprints_apimaintopic.htm
    - https://tracks.usask.ca/help/FootPrintsHelp/content/perl_sample.htm
- [BMC FootPrints v12.x Web Services API](https://docs.bmc.com/docs/display/public/FPSC120/Configuring+Web+Services)
- [BestPractical RT REST API](https://rt-wiki.bestpractical.com/wiki/REST)

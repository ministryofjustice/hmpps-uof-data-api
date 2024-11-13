# HMPPS Use of Force data API
[![repo standards badge](https://img.shields.io/badge/dynamic/json?color=blue&style=flat&logo=github&label=MoJ%20Compliant&query=%24.result&url=https%3A%2F%2Foperations-engineering-reports.cloud-platform.service.justice.gov.uk%2Fapi%2Fv1%2Fcompliant_public_repositories%2Fhmpps-uof-data-api)](https://operations-engineering-reports.cloud-platform.service.justice.gov.uk/public-github-repositories.html#hmpps-uof-data-api "Link to report")
[![CircleCI](https://circleci.com/gh/ministryofjustice/hmpps-uof-data-api/tree/main.svg?style=svg)](https://circleci.com/gh/ministryofjustice/hmpps-uof-data-api)
[![Docker Repository on Quay](https://quay.io/repository/hmpps/hmpps-uof-data-api/status "Docker Repository on Quay")](https://quay.io/repository/hmpps/hmpps-uof-data-api)
[![API docs](https://img.shields.io/badge/API_docs_-view-85EA2D.svg?logo=swagger)](https://hmpps-uof-data-api-dev.hmpps.service.justice.gov.uk/webjars/swagger-ui/index.html?configUrl=/v3/api-docs)

This service exists to expose Use of Force data in a read-only manner.
Any data input/modification is done via the [Use of Force](https://github.com/ministryofjustice/use-of-force) service.

#### How to sync the project with HMPPS-template-kotlin
Find out missing commits https://github.com/ministryofjustice/hmpps-template-kotlin/commits/main/ following `sync commit` above.
Cherry Pick the changes by calling `git cherry-pick <<commit_id>>..<<commit_id>>` if you want to Cherry pick only one commit call `git cherry-pick <<commit_id>>`
INSERT INTO report
(id, form_response, user_id, sequence_no, booking_id, created_date, status, submitted_date, offender_no, reporter_name, incident_date, agency_id, updated_date, deleted, completed_date)
VALUES(680, '{"evidence": {"cctvRecording": "NO", "baggedEvidence": false, "photographsTaken": false}, "involvedStaff": [{"name": "Abc Admin", "email": "abc@justice.gov.uk", "staffId": 485828, "lastName": "Admin", "username": "ABC_ADM", "verified": true, "firstName": "Abc", "activeCaseLoadId": "WRI"}], "incidentDetails": {"plannedUseOfForce": false, "incidentLocationId": "a57e75a8-b355-4088-afed-75a973d9e0b7"}, "useOfForceDetails": {"taserDrawn": false, "guidingHold": false, "escortingHold": false, "bodyWornCamera": "NO", "weaponsObserved": "NO", "handcuffsApplied": false, "bittenByPrisonDog": false, "restraintPositions": "NONE", "positiveCommunication": false, "pavaDrawnAgainstPrisoner": false, "batonDrawnAgainstPrisoner": false, "painInducingTechniquesUsed": "NONE", "personalProtectionTechniques": false}, "reasonsForUseOfForce": {"reasons": ["ASSAULT_ON_ANOTHER_PRISONER"]}, "relocationAndInjuries": {"f213CompletedBy": "Mr Fowler", "prisonerInjuries": false, "healthcareInvolved": false, "prisonerRelocation": "OWN_CELL", "relocationCompliancy": true, "staffMedicalAttention": false, "prisonerHospitalisation": false}}'::jsonb, 'ABC_GEN', 10, 1037656, '2026-02-03 08:56:43.522', 'COMPLETE', '2026-02-13 09:14:51.960', 'G5942UJ', 'Abc Admin', '2025-11-10 01:00:00.000', 'WRI', '2026-02-13 09:17:26.079', NULL, NULL);

INSERT INTO "statement"
(id, report_id, user_id, "name", email, submitted_date, statement_status, last_training_month, last_training_year, job_start_year, "statement", staff_id, created_date, updated_date, next_reminder_date, overdue_date, in_progress, deleted, removal_requested_reason, removal_requested_date)
VALUES(862, 680, 'BCD_GEN', 'BCD Gen', 'bcd@justice.gov.uk', '2026-02-13 09:16:12.017', 'SUBMITTED', 1, 2000, 2000, 'gen', 485828, '2026-02-13 09:14:51.961', '2026-02-13 09:16:12.017', '2026-02-14 09:14:51.960', '2026-02-16 09:14:51.960', true, NULL, NULL, NULL);

INSERT INTO "statement"
(id, report_id, user_id, "name", email, submitted_date, statement_status, last_training_month, last_training_year, job_start_year, "statement", staff_id, created_date, updated_date, next_reminder_date, overdue_date, in_progress, deleted, removal_requested_reason, removal_requested_date)
VALUES(863, 680, 'ABC_ADM', 'Abc adm', 'abc@justice.gov.uk', '2026-02-13 09:17:26.079', 'SUBMITTED', 1, 2000, 2000, 'adm', 485828, '2026-02-13 09:14:51.961', '2026-02-13 09:17:26.079', '2026-02-14 09:14:51.960', '2026-02-16 09:14:51.960', true, NULL, NULL, NULL);

INSERT INTO statement_amendments
(id, statement_id, additional_comment, date_submitted, deleted)
VALUES(51, 863, 'additional comment', '2026-02-13 09:17:53.271', NULL);

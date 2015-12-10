insert into egpgr_complainttype_category (id,name,description,version) values (nextval('seq_egpgr_complainttype_category'),'Chennai Floods','Chennai Floods',0);


INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Safety', NULL, 0, 'A1', true, 'Safety', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Rescue', NULL, 0, 'B1', true, 'Rescue', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Medical', NULL, 0, 'C1', true, 'Medical', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Food', NULL, 0, 'D1', true, 'Food', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Water', NULL, 0, 'E1', true, 'Water', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
INSERT INTO egpgr_complainttype (id, name, department, version, code, isactive, description, createddate, lastmodifieddate, createdby, lastmodifiedby, slahours, hasfinancialimpact, category) VALUES (nextval('seq_egpgr_complainttype'), 'Transport', NULL, 0, 'F1', true, 'Transport', '2015-11-24 00:00:00', '2015-11-24 00:00:00', 1, 1, 24, false, (select id from egpgr_complainttype_category where name='Chennai Floods'));
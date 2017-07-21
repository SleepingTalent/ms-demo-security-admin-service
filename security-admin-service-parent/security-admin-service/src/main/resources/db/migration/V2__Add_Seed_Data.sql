INSERT INTO security_roles (NAME, DESCRIPTION)
VALUES ('admin', 'Admin Role');

INSERT INTO security_roles (NAME, DESCRIPTION)
VALUES ('doNothing', 'This guy can do nothing');

INSERT INTO security_roles (NAME, DESCRIPTION)
VALUES ('drawingViewer', 'Allows drawing viewing only');

INSERT INTO security_permissions(NAME, DESCRIPTION)
VALUES('*', 'All permissions');

INSERT INTO security_permissions(NAME, DESCRIPTION)
VALUES('view:drawing:*', 'All view drawing permissions');

INSERT INTO security_permissions(NAME, DESCRIPTION)
VALUES('view:drawing:info', 'Only view drawing info permissions');

INSERT INTO security_role_permissions(PERMISSION_ID, ROLE_ID)
SELECT P.ID PERMISSION_ID, R.ID ROLE_ID FROM security_roles R, security_permissions P WHERE R.NAME = 'admin' AND P.NAME = '*';

INSERT INTO security_role_permissions(PERMISSION_ID, ROLE_ID)
SELECT P.ID PERMISSION_ID, R.ID ROLE_ID FROM security_roles R, security_permissions P WHERE R.NAME = 'drawingViewer' AND P.NAME = 'view:drawing:*';

INSERT INTO users(PAY_NUMBER, NAME, EMAIL, CREATION_DATE, IS_MANAGER, PHONE_NUMBER, ORGANISATION, JOB_TITLE, DEPARTMENT, ADDRESS, LOCATION, LICENCE)
VALUES('123456', 'Admin User', 'admin@email.com', SYSDATE(), 'N', '01383 412131', 'Babcock', 'Administrator', 'IT', 'Rosyth Dockyard', 'Rosyth', 'Admin licence');

INSERT INTO users(PAY_NUMBER, NAME, EMAIL, CREATION_DATE, IS_MANAGER, PHONE_NUMBER, ORGANISATION, JOB_TITLE, DEPARTMENT, ADDRESS, LOCATION, LICENCE)
VALUES('234567', 'Jill Document', 'jd@email.com', SYSDATE(), 'N', '01383 412131', 'Babcock', 'Document Controller', 'Doc Control', 'Rosyth Dockyard', 'Rosyth', 'DC1 licence');

INSERT INTO users(PAY_NUMBER, NAME, EMAIL, CREATION_DATE, IS_MANAGER, PHONE_NUMBER, ORGANISATION, JOB_TITLE, DEPARTMENT, ADDRESS, LOCATION, LICENCE)
VALUES('876543', 'Bobby D. Nothing', 'bdn@email.com', SYSDATE(), 'N', '01383 412131', 'Babcock', 'FLM', 'Marine', 'Rosyth Dockyard', 'Rosyth', 'No licence');

INSERT INTO security_subjects(USERNAME, PASSWORD, SALT, ENABLED, USER_ID)
SELECT 'admin', 'tiasHslWV7/ieEwGxos3LkNKvZLd/ben0qI3xXJrgjs=', 'pOijUhjnLLpoiyTTSeWq', 1, ID FROM users WHERE NAME = 'Admin User';

INSERT INTO security_subject_roles(SUBJECT_ID, ROLE_ID)
SELECT SS.ID, SR.ID  FROM security_roles SR, security_subjects SS WHERE SR.NAME = 'admin' AND SS.USERNAME = 'admin';

INSERT INTO security_subjects(USERNAME, PASSWORD, SALT, ENABLED, USER_ID)
SELECT 'jilldocument', 'tiasHslWV7/ieEwGxos3LkNKvZLd/ben0qI3xXJrgjs=', 'pOijUhjnLLpoiyTTSeWq', 1, ID FROM users WHERE NAME = 'Jill Document';

INSERT INTO security_subject_roles(SUBJECT_ID, ROLE_ID)
SELECT SS.ID, SR.ID  FROM security_roles SR, security_subjects SS WHERE SR.NAME = 'drawingViewer' AND SS.USERNAME = 'jilldocument';

INSERT INTO security_subjects(USERNAME, PASSWORD, SALT, ENABLED, USER_ID)
SELECT 'bobbyd', 'tiasHslWV7/ieEwGxos3LkNKvZLd/ben0qI3xXJrgjs=', 'pOijUhjnLLpoiyTTSeWq', 1, ID FROM users WHERE NAME =  'Bobby D. Nothing';

INSERT INTO security_subject_roles(SUBJECT_ID, ROLE_ID)
SELECT SS.ID, SR.ID  FROM security_roles SR, security_subjects SS WHERE SR.NAME = 'doNothing' AND SS.USERNAME = 'bobbyd';

COMMIT;
INSERT INTO user_details(ID, BIRTH_DATE, NAME)
VALUES (10001, current_date(), 'Hlony');

INSERT INTO user_details(ID, BIRTH_DATE, NAME)
VALUES (10002, current_date(), 'Reece');

INSERT INTO user_details(ID, BIRTH_DATE, NAME)
VALUES (10003, current_date(), 'Gator');

INSERT INTO post(ID, USER_ID, DESCRIPTION)
VALUES (1, 10001, 'First Post');

INSERT INTO post(ID, USER_ID, DESCRIPTION)
VALUES (2, 10001, 'Second Post');

INSERT INTO post(ID, USER_ID, DESCRIPTION)
VALUES (3, 10002, 'I''m tired');

INSERT INTO post(ID, USER_ID, DESCRIPTION)
VALUES (4, 10003, 'Flippin loadshedding');

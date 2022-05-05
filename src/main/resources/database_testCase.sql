--------------------USER_INFO-----------------------

insert into USER_INFO values ('keith', '{noop}keithpw', 'cskeith', '1234568','address','ROLE_ADMIN');

INSERT INTO USER_INFO VALUES ('john', '{noop}johnpw', 'john', '1234568','address','ROLE_ADMIN');

INSERT INTO USER_INFO VALUES ('t', '{noop}t', 't', '1234568','address','ROLE_ADMIN');

-------------------COURSE_INFO----------------------

INSERT INTO COURSE_INFO (COURSE_CODE, COURSE_NAME)
VALUES ('COMPS380F', 'Web Applications:Design And Development');

INSERT INTO COURSE_INFO (COURSE_CODE, COURSE_NAME)
VALUES ('COMPS313F', 'Mobile Application Programming');

-------------------LECTURE_INFO----------------------

INSERT INTO LECTURE_INFO (COURSE_CODE, LECTURE_NUM, TITLE)
VALUES ('COMPS380F', '1', 'Overview of Web Applications');

INSERT INTO LECTURE_INFO (COURSE_CODE, LECTURE_NUM, TITLE)
VALUES ('COMPS380F', '2', 'Servlet');

INSERT INTO LECTURE_INFO (COURSE_CODE, LECTURE_NUM, TITLE)
VALUES ('COMPS313F', '1', 'Basics');

INSERT INTO LECTURE_INFO (COURSE_CODE, LECTURE_NUM, TITLE)
VALUES ('COMPS313F', '2', 'Android - Fundamentals');

----------------LECTURE_COMMENTS--------------------

INSERT INTO LECTURE_COMMENTS (COMMENTS, LECTURE_ID, USER_NAME)
VALUES ('love it', 4, 't');

INSERT INTO LECTURE_COMMENTS (COMMENTS, LECTURE_ID, USER_NAME)
VALUES ('love it', 4, 'john');

INSERT INTO LECTURE_COMMENTS (COMMENTS, LECTURE_ID, USER_NAME)
VALUES ('idk', 1, 'john');

INSERT INTO LECTURE_COMMENTS (COMMENTS, LECTURE_ID, USER_NAME)
VALUES ('idk', 1, 't');

----------------------POLL-------------------------

INSERT INTO POLL (POLL_QUESTION, COURSE_CODE, ANS_A, ANS_B, ANS_C, ANS_D)
VALUES ('Which date do you prefer for the mid-term test?', 'COMPS380F', '7/5', '8/5', DEFAULT, DEFAULT);

INSERT INTO POLL (POLL_QUESTION, COURSE_CODE, ANS_A, ANS_B, ANS_C, ANS_D)
VALUES ('Which date do you prefer for the mid-term test?', 'COMPS313F', '9/5', '10/5', '11/5', '12/5');

----------------------USER_CHOICE-------------------------

INSERT INTO USER_CHOICE (USER_NAME, USER_CHOICE, POLL_ID)
VALUES ('t', 'a',  1);
INSERT INTO USER_CHOICE (USER_NAME, USER_CHOICE, POLL_ID)
VALUES ('john', 'b',  1);
INSERT INTO USER_CHOICE (USER_NAME, USER_CHOICE, POLL_ID)
VALUES ('john', 'd', 2);

----------------------POLL_COMMENTS-------------------------

INSERT INTO POLL_COMMENTS (POLL_ID, COMMENTS, USER_NAME)
VALUES (1, 'yessssssssss', 't');
INSERT INTO POLL_COMMENTS (POLL_ID, COMMENTS, USER_NAME)
VALUES (2, 'noooo', 't');
INSERT INTO POLL_COMMENTS (POLL_ID, COMMENTS, USER_NAME)
VALUES (2, 'love it', 'john');

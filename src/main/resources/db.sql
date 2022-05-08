CREATE TABLE user_info
(
    user_name     VARCHAR(50)  NOT NULL,
    user_pw       VARCHAR(50)  NOT NULL,
    user_fullname VARCHAR(50)  NOT NULL,
    user_phone    VARCHAR(8)   NOT NULL,
    user_address  VARCHAR(100) NOT NULL,
    user_type     VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_name)
);

CREATE TABLE course_info
(
    course_code VARCHAR(10) NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (course_code)
);

CREATE TABLE lecture_info
(
    lecture_id  INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    course_code VARCHAR(10)  NOT NULL,
    lecture_num VARCHAR(255) NOt NULL,
    title       VARCHAR(255) NOT NULL,
    PRIMARY KEY (lecture_id),
    FOREIGN KEY (course_code) REFERENCES course_info (course_code) ON DELETE CASCADE
);

CREATE TABLE lecture_comments
(
    comments_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    comments    VARCHAR(255) DEFAULT NULL,
    lecture_id  INTEGER NOT NULL,
    user_name   VARCHAR(50),
    PRIMARY KEY (comments_id),
    FOREIGN KEY (lecture_id) REFERENCES lecture_info (lecture_id) ON DELETE CASCADE,
    FOREIGN KEY (user_name) REFERENCES user_info (user_name) ON DELETE CASCADE


);

CREATE TABLE course_material
(
    attachment_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lecture_id    INTEGER      DEFAULT NULL,
    filename      VARCHAR(255) DEFAULT NULL,
    content_type  VARCHAR(255) DEFAULT NULL,
    content       BLOB         DEFAULT NULL,
    PRIMARY KEY (attachment_id),
    FOREIGN KEY (lecture_id) REFERENCES lecture_info (lecture_id) ON DELETE CASCADE
);

CREATE TABLE poll
(
    poll_id       INTEGER     NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poll_question VARCHAR(255) DEFAULT NULL,
    course_code   VARCHAR(10) NOT NULL,
    ans_a         VARCHAR(255) DEFAULT NULL,
    ans_b         VARCHAR(255) DEFAULT NULL,
    ans_c         VARCHAR(255) DEFAULT NULL,
    ans_d         VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (poll_id),
    FOREIGN KEY (course_code) REFERENCES course_info (course_code) ON DELETE CASCADE
);

CREATE TABLE user_choice
(
    choice_id   INTEGER     NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_name   VARCHAR(50) NOT NULL,
    user_choice VARCHAR(1)  NOT NULL,
    poll_id     int         NOT NULL,
    PRIMARY KEY (choice_id),
    FOREIGN KEY (user_name) REFERENCES USER_INFO (user_name) ON DELETE CASCADE,
    FOREIGN KEY (poll_id) REFERENCES poll (poll_id) ON DELETE CASCADE
);

CREATE TABLE poll_comments
(
    poll_cid  INTEGER     NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poll_id   INTEGER     NOT NULL,
    comments  VARCHAR(255) DEFAULT NULL,
    user_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (poll_cid),
    FOREIGN KEY (poll_id) REFERENCES poll (poll_id) ON DELETE CASCADE,
    FOREIGN KEY (user_name) REFERENCES user_info (user_name) ON DELETE CASCADE
);

--------------------USER_INFO-----------------------

insert into USER_INFO values ('keith', '{noop}keithpw', 'cskeith', '12345678','address','ROLE_ADMIN');

INSERT INTO USER_INFO VALUES ('john', '{noop}john', 'john', '12345678','address','ROLE_USER');

INSERT INTO USER_INFO VALUES ('t', '{noop}t', 'terry', '12345678','address','ROLE_ADMIN');

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

INSERT INTO POLL (POLL_QUESTION, COURSE_CODE, ANS_A, ANS_B, ANS_C, ANS_D)
VALUES ('do u love 380?', 'COMPS380F', 'yes', 'no', 'not at all', 'idk');

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


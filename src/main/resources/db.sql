CREATE TABLE user_info(
                          user_name VARCHAR(50) NOT NULL,
                          user_pw VARCHAR(50) NOT NULL,
                          user_fullname VARCHAR(50) NOT NULL,
                          user_phone VARCHAR(8) NOT NULL,
                          user_address VARCHAR(100) NOT NULL,
                          user_type VARCHAR(100) NOT NULL,
                          PRIMARY KEY (user_name)
);

CREATE TABLE course_info(
                            course_code VARCHAR(10) NOT NULL,
                            course_name VARCHAR(10) NOT NULL,
                            PRIMARY KEY (course_code)
);

CREATE TABLE lecture_info (
                              lecture_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                              course_code VARCHAR(10) NOT NULL,
                              lecture_num VARCHAR(255) NOt NULL,
                              title VARCHAR(255) NOT NULL,
                              PRIMARY KEY(lecture_id),
                              FOREIGN KEY(course_code) REFERENCES course_info(course_code)
);
CREATE TABLE lecture_comments(
                                 comments_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                                 comments VARCHAR(255) DEFAULT NULL,
                                 lecture_id INTEGER NOT NULL,
                                 user_name VARCHAR(50),
                                 PRIMARY KEY (comments_id),
                                 FOREIGN KEY(lecture_id) REFERENCES lecture_info(lecture_id),
                                 FOREIGN KEY(user_name) REFERENCES user_info(user_name)
);

CREATE TABLE course_material(
                                attachment_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                                lecture_id INTEGER DEFAULT NULL,
                                filename VARCHAR(255) DEFAULT NULL,
                                content_type VARCHAR(255) DEFAULT NULL,
                                content BLOB DEFAULT NULL,
                                PRIMARY KEY (attachment_id),
                                FOREIGN KEY(lecture_id) REFERENCES lecture_info(lecture_id)
);

CREATE TABLE poll(
                     poll_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                     user_name VARCHAR(50) NOT NULL,
                     user_choice VARCHAR(1) NOT NULL,
                     poll_cid INTEGER DEFAULT NULL,
                     poll_question VARCHAR(255) DEFAULT NULL,
                     course_code VARCHAR(10) NOT NULL,
                     PRIMARY KEY (poll_id),
                     FOREIGN KEY(user_name) REFERENCES user_info(user_name),
                     FOREIGN KEY(course_code) REFERENCES course_info(course_code)
);

CREATE TABLE poll_comments(
                              poll_cid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                              poll_id INTEGER NOT NULL,
                              comments VARCHAR(255) DEFAULT NULL,
                              user_name VARCHAR(50) NOT NULL,
                              PRIMARY KEY(poll_cid),
                              FOREIGN KEY(poll_id) REFERENCES poll(poll_id),
                              FOREIGN KEY(user_name) REFERENCES user_info(user_name)
);
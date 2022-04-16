CREATE TABLE course (
                         id VARCHAR(255) NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id)
);
CREATE TABLE lecture (
                        id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                        lecture_num VARCHAR(255) NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        course_id VARCHAR(255) DEFAULT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (course_id) REFERENCES course(id)
);
CREATE TABLE  course_material (
                        id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                        filename VARCHAR(255) DEFAULT NULL,
                        content_type VARCHAR(255) DEFAULT NULL,
                        content BLOB DEFAULT NULL,
                        lecture_id VARCHAR(255) DEFAULT NULL,
                        PRIMARY KEY (id)
);
CREATE TABLE tab_user (
    usr_id SERIAL PRIMARY KEY,
    usr_name VARCHAR(30) NOT NULL,
    usr_username VARCHAR(30) NOT NULL UNIQUE,
    usr_password VARCHAR(60) NOT NULL,
    usr_role VARCHAR(20) NOT NULL,
    usr_img VARCHAR(30)
);

CREATE TABLE prompt(
    prt_id SERIAL PRIMARY KEY,
    prt_name VARCHAR(50),
    prt_prompt TEXT
);

CREATE TABLE flashcard(
    fls_id SERIAL PRIMARY KEY,
    fls_que VARCHAR(100),
    fls_ans VARCHAR(300) NOT NULL,
    fls_review_date DATE,
    fls_usr_id INT NOT NULL,
    FOREIGN KEY(fls_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE resume(
    res_id SERIAL PRIMARY KEY,
    res_title VARCHAR(50) NOT NULL,
    res_text VARCHAR(500) NOT NULL,
    res_usr_id INT NOT NULL,
    FOREIGN KEY(res_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE tag(
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL UNIQUE,
    tag_color CHAR(9) NOT NULL, 
    tag_usr_id INT NOT NULL,
    FOREIGN KEY(tag_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE chat(
    cht_id SERIAL PRIMARY KEY,
    cht_title VARCHAR(30) NOT NULL,
    cht_usr_id INT NOT NULL,
    FOREIGN KEY(cht_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE flashcard_tag(
    fst_id SERIAL PRIMARY KEY,
    fst_fls_id INT NOT NULL,
    fst_tag_id INT NOT NULL,
    FOREIGN KEY (fst_fls_id) REFERENCES flashcard(fls_id),
    FOREIGN KEY (fst_tag_id) REFERENCES tag(tag_id)
);

CREATE TABLE resume_tag(
    rst_id SERIAL PRIMARY KEY,
    rst_res_id INT NOT NULL,
    rst_tag_id INT NOT NULL,
    FOREIGN KEY (rst_res_id) REFERENCES resume(res_id),
    FOREIGN KEY (rst_tag_id) REFERENCES tag(tag_id)
);

CREATE TABLE messages(
    msg_id SERIAL PRIMARY KEY,
    msg_origin VARCHAR(20) NOT NULL,
    msg_message TEXT NOT NULL,
    msg_cht_id INT NOT NULL,
    msg_time TIMESTAMP,
    FOREIGN KEY(msg_cht_id) REFERENCES chat(cht_id)
);
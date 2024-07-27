CREATE TABLE tab_user (
    usr_id SERIAL PRIMARY KEY,
    usr_name VARCHAR(30) NOT NULL,
    usr_username VARCHAR(30) UNIQUE,
    usr_password VARCHAR(60),
    usr_role VARCHAR(20) NOT NULL,
    usr_img VARCHAR(255),
    usr_github_username VARCHAR(255) UNIQUE
);

CREATE TABLE prompt(
    prt_id SERIAL PRIMARY KEY,
    prt_name VARCHAR(50),
    prt_prompt TEXT
);

CREATE TABLE assistent(
    ast_id SERIAL PRIMARY KEY,
    ast_name VARCHAR(50) NOT NULL,
    ast_description VARCHAR(300),
    ast_prompt TEXT NOT NULL
);

CREATE TABLE flashcard(
    fls_id SERIAL PRIMARY KEY,
    fls_que VARCHAR(100),
    fls_ans VARCHAR(300) NOT NULL,
    fls_priority INT NOT NULL DEFAULT 0,
    fls_revision_date DATE NOT NULL,
    fls_creation_date DATE NOT NULL,
    fls_usr_id INT NOT NULL,
    FOREIGN KEY(fls_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE resume(
    res_id SERIAL PRIMARY KEY,
    res_title VARCHAR(50) NOT NULL,
    res_text TEXT NOT NULL,
    res_usr_id INT NOT NULL,
    FOREIGN KEY(res_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE category(
    cat_id SERIAL PRIMARY KEY,
    cat_name VARCHAR(50) NOT NULL,
    cat_usr_id INT NOT NULL,
    FOREIGN KEY (cat_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE resume_category(
    rsc_id SERIAL PRIMARY KEY,
    rsc_name VARCHAR(50) NOT NULL,
    rsc_res_id INT NOT NULL,
    rsc_usr_id INT NOT NULL,
    FOREIGN KEY(rsc_res_id) REFERENCES resume(res_id),
    FOREIGN KEY(rsc_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE tag(
    tag_id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    tag_color CHAR(9) NOT NULL, 
    tag_usr_id INT NOT NULL,
    FOREIGN KEY(tag_usr_id) REFERENCES tab_user(usr_id)
);

CREATE TABLE chat(
    cht_id SERIAL PRIMARY KEY,
    cht_title VARCHAR(30) NOT NULL,
    cht_usr_id INT NOT NULL,
    cht_res_id INT NOT NULL,
    FOREIGN KEY(cht_res_id) REFERENCES resume(res_id),
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

CREATE TABLE revision_dificulty(
    rvd_id SERIAL PRIMARY KEY,
    rvd_name VARCHAR(20)
);

CREATE TABLE flashcard_Revision(
    flr_id SERIAL PRIMARY KEY,
    flr_min_time TIMESTAMP,
    flr_fls_id INT NOT NULL,
    flr_rvd_id INT NOT NULL,
    FOREIGN KEY(flr_fls_id) REFERENCES flashcard(fls_id),
    FOREIGN KEY(flr_rvd_id) REFERENCES revision_dificulty(rvd_id)
);


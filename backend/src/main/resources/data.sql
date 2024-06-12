CREATE TABLE appuser (
    usr_id SERIAL AUTO_INCREMENT,
    usr_name VARCHAR(30) NOT NULL,
    usr_password VARCHAR(60) NOT NULL,
    usr_img VARCHAR(30)
);

CREATE TABLE assistent(
    ass_id SERIAL AUTO_INCREMENT,
    name VARCHAR(50),
    description VARCHAR(50)
);

CREATE TABLE flashcard(
    fls_id SERIAL AUTO_INCREMENT,
    fls_que VARCHAR(100),
    fls_ans VARCHAR(300) NOT NULL,
    fls_review_date DATE
);

CREATE TABLE resumes(
    res_id SERIAL AUTO_INCREMENT,
    res_title VARCHAR(50) NOT NULL,
    res_text VARCHAR(500) NOT NULL
);

CREATE TABLE tag(
    tag_id SERIAL AUTO_INCREMENT,
    tag_name VARCHAR(50) NOT NULL,
    tag_color CHAR(9) NOT NULL
);

CREATE TABLE flashcard_tag(
    fst_id SERIAL AUTO_INCREMENT,
    fst_fls_id INT NOT NULL,
    fst_tag_id INT NOT NULL,
    FOREIGN KEY (fst_fls_id) REFERENCES flashcard(fls_id),
    FOREIGN KEY (fst_tag_id) REFERENCES tag(tag_id)
);

INSERT INTO assistent (ass_id, name , description) VALUES (1, 'assistente', 'teste para ver se funciona');
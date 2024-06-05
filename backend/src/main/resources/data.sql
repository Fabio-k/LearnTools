CREATE TABLE assistent(
    ass_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description VARCHAR(50)
);

CREATE TABLE flashcard(
    fls_id INT PRIMARY KEY AUTO_INCREMENT,
    fls_que VARCHAR(100),
    fls_ans VARCHAR(300) NOT NULL
);

CREATE TABLE tag(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(50),
    tag_color CHAR(9)
);

CREATE TABLE flashcard_tag(
    fst_id INT PRIMARY KEY AUTO_INCREMENT,
    fst_fls_id INT NOT NULL,
    fst_tag_id INT NOT NULL,
    FOREIGN KEY (fst_fls_id) REFERENCES flashcard(fls_id),
    FOREIGN KEY (fst_tag_id) REFERENCES tag(tag_id)
);

INSERT INTO assistent (ass_id, name , description) VALUES (1, 'assistente', 'teste para ver se funciona');
ALTER TABLE prompt ADD prt_ast_id INTEGER;
ALTER TABLE prompt ADD CONSTRAINT fk_prt_ast_id FOREIGN KEY (prt_ast_id) REFERENCES assistent(ast_id);
INSERT INTO prompt (prt_name, prt_prompt) VALUES ('Lucas', 'faça uma pergunta que um leigo teria sobre o resumo do usuário ou da resposta que ele deu');
INSERT INTO prompt (prt_name, prt_prompt) VALUES ('Luciano', 'faça uma pergunta que explore o nivel de conhecimento do usuário sobre o assunto abordado no resumo');
UPDATE prompt 
SET prt_ast_id = (SELECT ast_id FROM assistent WHERE assistent.ast_name = prompt.prt_name);
ALTER Table assistent DROP COLUMN ast_name,DROP COLUMN ast_prompt;
  
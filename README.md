<p align="center"> <img src="https://github.com/user-attachments/assets/6bcfb89e-c3fa-4ce9-9bbe-458b57e52805" alt="LearnTools Logo"></p>

# LearnTools

![license](https://img.shields.io/github/license/fabio-k/LearnTools) ![status](https://img.shields.io/badge/status-em%20desenvolvimento-orange)

LearnTools é um website que ajuda nos estudos utilizando métodos de estudos eficiêntes integrados com IA. Feito para rodar localmente e completamente offline

## Funcionalidades

- Resumos: Crie resumos que podem posteriormente ser utilizados pela ferramenta `Feyman chat`.

- Feynman chat: uma ferramenta que simula a técnica de ensino de Richard Feynman. O usuário explica um tópico para a IA como se estivesse explicando para alguém leigo no assunto. A IA então faz perguntas para preencher lacunas na explicação, destacando pontos que precisam ser melhorados.

- Revisões: revise assuntos através de perguntas criando/usando um resumo (não é necessário fazer um resumo completo para esta funcionalidade apenas adicionar o tópico como titúlo do resumo)

## Técnologias Utilizadas

- Frontend
  - Javascript
  - React
  - Node
- Backend
  - Java
  - Spring Boot
  - Flyway
  - Postgres

## Instalar e rodar o projeto

### Dependências globais

Dependências necessárias para conseguir rodar a aplicação

- Ollama (LLM runner) [site oficial](https://ollama.com)
- Docker Engine v26.1.4+
- Node.js v16+

### Dependências locais

Depois de instalar as dependências locais utilize os seguintes comandos para baixar as dependêcias locais

frontend:

```bash
npm install
```

backend:

```bash
cd backend
mvn clean package
```

### Rodar o projeto

O comando abaixo roda o projeto localmente. ele automaticamente roda o backend e o banco de dados.

```bash
npm run dev
```

## Licença

LearnTools possui a licença [MIT](./LICENSE)

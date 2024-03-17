import React from "react";
import { useState, useEffect } from "react";
import {
  Button,
  Input,
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from "reactstrap";

const Resumes = () => {
  const [dropdownOpen, setDropdown] = useState(false);
  const toggle = () => setDropdown(!dropdownOpen);
  const resumes = [
    {
      id: 1,
      title: "threads",
      description:
        "Threads são como diferentes tarefas que um programa pode realizar ao mesmo tempo. Imagine que você está cozinhando: enquanto a panela está no fogo, você também está cortando os ingredientes. Essas duas ações podem acontecer ao mesmo tempo, assim como as threads permitem que um programa faça várias coisas simultaneamente. Cada thread é como uma linha de atividade separada dentro do programa, permitindo que ele seja mais eficiente e responsivo. No entanto, é importante coordenar essas atividades para evitar que elas atrapalhem umas às outras. Isso é feito usando técnicas especiais para garantir que as threads não interfiram umas nas outras enquanto compartilham recursos, como dados ou arquivos.",
      topic: ["Computer Science", "OS"],
    },
    {
      id: 2,
      title: "processos",
      description:
        "Processos são programas em execução no computador. Cada processo tem seu próprio espaço de memória, que inclui o código do programa, dados e recursos temporários. Eles funcionam de forma independente, o que significa que podem executar diferentes tarefas simultaneamente sem interferir um no outro. Por exemplo, você pode estar escrevendo um documento enquanto ouve música em um player de mídia, e ambos os aplicativos são processos separados. O sistema operacional é responsável por gerenciar esses processos, atribuindo recursos, como CPU e memória, e garantindo que cada um execute de forma eficiente e segura. Os processos podem se comunicar entre si, geralmente através de mecanismos fornecidos pelo sistema operacional, como sinais, pipes ou sockets, permitindo a cooperação entre diferentes partes de um sistema computacional.",
      topic: ["Computer Science", "OS"],
    },
  ];
  const [data, setData] = useState(resumes);
  const [topics, setTopics] = useState([]);
  useEffect(() => {
    const allTopics = data.reduce((topics, resume) => {
      return topics.concat(resume.topic);
    }, []);
    const uniqueTopics = [...new Set(allTopics)];
    setTopics(uniqueTopics);
  }, [data]);

  return (
    <div>
      <section
        style={{ display: "flex", height: "20%", justifyContent: "center" }}
      >
        <div
          style={{
            display: "flex",
            width: "60%",
            padding: "10px",
            alignItems: "center",
          }}
        >
          <Input placeholder="search for resume" />
          <Dropdown isOpen={dropdownOpen} toggle={toggle} direction="down">
            <DropdownToggle caret>topico</DropdownToggle>
            <DropdownMenu>
              {topics.map((topic, index) => {
                return <DropdownItem key={index}>{topic}</DropdownItem>;
              })}
            </DropdownMenu>
          </Dropdown>
        </div>
      </section>
      <ul
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {data.length > 0
          ? data.map((item) => (
              <li
                key={item.id}
                style={{
                  border: "1px solid black",
                  padding: "10px",
                  borderRadius: "10px",
                  maxWidth: "70%",
                  marginBottom: "50px",
                }}
              >
                <section
                  style={{ display: "flex", justifyContent: "space-between" }}
                >
                  <h1>{item.title}</h1>
                  <div
                    style={{
                      display: "flex",
                      width: "40%",
                      justifyContent: "space-evenly",
                      alignItems: "center",
                    }}
                  >
                    <Button color="danger" outline>
                      delete
                    </Button>
                    <Button color="primary" outline>
                      edit
                    </Button>
                    <Button color="warning" outline>
                      send to ai
                    </Button>
                  </div>
                </section>

                <p>{item.description}</p>
              </li>
            ))
          : "Resumes not found"}
      </ul>
    </div>
  );
};

export default Resumes;

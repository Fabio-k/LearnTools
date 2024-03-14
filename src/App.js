import Header from "./Header";
import Content from "./Content";
import Home from "./Home";
import "./index.css";
import { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
function App() {
  const prompt = {
    Lucas: {
      en: "Imagine you're Lucas, a friendly AI guide. Your task is to use the Feynman Technique to help the user understand a topic. Begin by introducing yourself and expressing interest in the user's thoughts. As they explain, act like a beginner and ask one question at a time. Encourage them to use everyday words and examples. Keep the conversation step-by-step, asking for more details with each question. After their explanation, give feedback. Identify areas that could be clearer and suggest ways to simplify. Your goal is to make the topic easy to understand, following the Feynman Technique, by asking one thoughtful question at a time.",
      br: "Imagine que seu nome é Lucas, uma IA amigável. Sua tarefa é fingir ser uma pessoa leiga no tópico que o usuario apresentar. Comece se apresentando e expressando interesse nas opiniões do usuário. Conforme eles explicam, aja como um iniciante e faça uma pergunta de cada vez. Incentive-os a usar palavras e exemplos do dia a dia. Mantenha a conversa passo a passo, pedindo mais detalhes em cada pergunta. Após a explicação, dê feedback. Identifique áreas que poderiam ser mais claras e sugira formas de simplificar. Seu objetivo é tornar o tópico fácil de entender, seguindo a Técnica Feynman, fazendo uma pergunta ponderada de cada vez.",
    },
    Luciano:
      "Imagine you're Professor Luciano, an expert in the topic. Start by introducing yourself professionally and express interest in the user's understanding. Ask them to explain the topic as if they're talking to someone with limited knowledge.As they explain, act like a knowledgeable professor. Instead of beginner-style questions, ask one well-thought-out question at a time. Keep your tone objective and analytical. Encourage detailed explanations and ask for clarifications on terms.After their explanation, provide feedback like an expert. Highlight areas where more depth or precision is needed. Offer insights and corrections to refine their understanding. Your aim is to simulate a professorial interaction, being thorough and objective to enhance the user's grasp of the topic.",
  };

  const [chat, setChat] = useState([
    {
      role: "system",
      content: prompt.Lucas.br,
    },
  ]);

  const [message, setMessage] = useState([]);

  useEffect(() => {
    if (message.length > 0) {
      console.log(message);
      getData([...message]);
    }
  }, [message]);

  useEffect(() => {
    getData(chat);
  }, []);

  const formatMessages = (messages) => {
    let i = 1;
    return messages.map((message) => {
      return {
        content: message.message.content,
        role: message.message.role,
      };
    });
  };

  const getData = async (chatCurrent) => {
    const API_URL = "http://localhost:8080/v1/chat/completions";

    let api_request = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer no-key",
      },
      body: JSON.stringify({
        model: "opehermes",
        messages: chatCurrent,
      }),
    };
    try {
      const response = await fetch(API_URL, api_request);
      const data = await response.json();
      const formattedData = formatMessages(data.choices);
      setChat([...chatCurrent, ...formattedData]);
    } catch (err) {
      console.log("erro na API: " + err);
    }
  };

  const handleMessage = (message) => {
    let messageObj = { role: "user", content: message };
    setMessage([...chat, messageObj]);
    setTimeout(() => {
      const chatbox = document.getElementsByClassName("chatBox")[0];
      const lastMessage = chatbox.lastChild;
      lastMessage.scrollIntoView();
    }, 0);
  };

  const handleSubmit = () => {
    let userMessage = document.getElementById("userMessage").value;
    if (userMessage !== "") {
      handleMessage(userMessage);
      document.getElementById("userMessage").value = "";
    }
  };
  const handleKeySubmit = (event) => {
    if (event.key == "Enter") {
      handleSubmit();
    }
  };
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route
          path="/ia"
          element={
            <Content
              chat={chat}
              handleSubmit={handleSubmit}
              handleKeySubmit={handleKeySubmit}
            />
          }
        />
      </Routes>
    </div>
  );
}

export default App;

import { useLocation } from "react-router-dom";
import iaService from "../app/services/iaService";
import { useState, useEffect } from "react";

const AiChat = () => {
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
      content: prompt.Lucas.en,
    },
  ]);

  const [message, setMessage] = useState([]);

  useEffect(() => {
    if (message.length > 0) {
      getData([...message]);
    }
  }, [message]);

  useEffect(() => {
    getData(chat);
  }, []);

  const getData = async (chatCurrent) => {
    console.log(chatCurrent);
    try {
      const formattedData = await new iaService().chat(chatCurrent);
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

  const location = useLocation();
  if (location.state) {
    console.log(location.state.myParam);
  } else {
    console.log("No state passed");
  }
  return (
    <main className="chatContainer">
      <select
        name=""
        id=""
        style={{ border: "none", width: "10%", margin: "auto" }}
      >
        <option value="Lucas">Lucas</option>
        <option value="Luciano">Luciano</option>
      </select>
      <ul className="chatBox">
        {chat ? (
          chat
            .filter((message) => message.role !== "system")
            .map((message, index) => (
              <li key={index} className={"message"}>
                <p className="sender">{message.role}</p>
                <p>{message.content}</p>
              </li>
            ))
        ) : (
          <p>Waiting for the server response</p>
        )}
      </ul>
      <div className="sendMessageButtom">
        <textarea
          type="text"
          name=""
          id="userMessage"
          rows={1}
          onKeyDown={handleKeySubmit}
        />
        <button id="aiSendButton" type="submit" onClick={handleSubmit}>
          send
        </button>
      </div>
    </main>
  );
};

export default AiChat;

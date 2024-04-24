import AssistentService from "../app/services/Assistent";
import { useState, useEffect } from "react";

const AiChat = () => {
  const [chat, setChat] = useState([]);
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
    try {
      const assistentResponse = await new AssistentService().postAssistent(
        1,
        chatCurrent
      );
      console.log(assistentResponse);
      setChat([...chatCurrent, ...assistentResponse]);
    } catch (err) {
      console.log("erro na API: " + err);
    }
  };

  const handleMessage = (message) => {
    let messageObj = { role: "user", content: message };
    setMessage([...chat, messageObj]);
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

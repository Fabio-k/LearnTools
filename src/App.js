import Header from "./Header";
import Content from "./Content";
import "./index.css";
import { useState } from "react";

function App() {
  const [chat, setChat] = useState([
    { id: 1, actor: "assistant", message: "hello there" },
    { id: 2, actor: "user", message: "hello there" },
  ]);

  const handleMessage = (message) => {
    let newId = chat[chat.length - 1].id + 1;
    let messageObj = { id: newId, actor: "user", message: message };
    setChat([...chat, messageObj]);
  };
  const handleSunmit = () => {
    let userMessage = document.getElementById("userMessage").value;
    if (userMessage != "") {
      console.log(userMessage);
      handleMessage(userMessage);
      userMessage = "";
    }
  };

  return (
    <div className="App">
      <Header />
      <Content chat={chat} handleSubmit={handleSunmit} />
    </div>
  );
}

export default App;

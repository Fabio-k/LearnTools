import { useEffect, useState, React, useRef } from "react";
import "./AiChat.css";
import AssistentService from "../../app/services/AssistentService";
import ChatService from "../../app/services/ChatService";
import Dropdown from "../../components/Dropdown/Dropdown";
import SendButton from "../../Assets/ui/sendIcon.svg";
import Button from "../../components/Button/Button";

const AiChat = (props) => {
  const [assistents, setAssistents] = useState([]);
  const [chat, setChat] = useState({});
  const [aiTag, setAiTag] = useState([]);
  const [currentAiTag, setCurrentAiTag] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isdropdownOpen, setisDropdownOpen] = useState(false);
  const [isAiTagDropdown, setAiTagDropdown] = useState(false);
  const [isMainDropdown, setIsMainDropdown] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [assistentName, setAssistentName] = useState("");
  const [assistentId, setAssistentId] = useState(0);
  const [message, setMessage] = useState("");
  const [isChatLoading, setisChatLoading] = useState(true);
  const [isNewMessage, setIsNewMessage] = useState(false);
  const messageEndRef = useRef(null);

  const handleAssistentClick = (assistent) => {
    setAssistentName(assistent.name);
    setAssistentId(assistent.id);
    setisDropdownOpen(false);
  };

  const handleAiAssistentClick = (aiTag) => {
    setCurrentAiTag(aiTag.name);
    setAiTagDropdown(false);
  };

  const handleMainAssistentClick = (aiTag) => {
    setCurrentAiTag(aiTag.name);
    setIsMainDropdown(false);
  };

  const handleCreateNewChat = async () => {
    if (props.resumeId === 0) return;
    setIsModalOpen(false);
    const chatResponse = await chatService.getNewChat(
      props.resumeId,
      assistentId,
      currentAiTag
    );
    setChat(chatResponse);
    setisChatLoading(false);
  };
  const handleSearchKeyDown = (e) => {
    if (e.key == "Enter") {
      handleSendMessage();
    }
  };
  const handleSendMessage = async () => {
    setMessage("");
    setisChatLoading(true);
    const updatedWithUserMessage = [
      ...chat.messages,
      { role: "user", message: message },
    ];
    setChat({ ...chat, messages: updatedWithUserMessage });
    setIsNewMessage(true);

    const chatResponse = await chatService.getMessage(
      chat.chatId,
      message,
      currentAiTag
    );
    const updatedWithAiMessage = [
      ...updatedWithUserMessage,
      chatResponse.message,
    ];
    setChat({ ...chat, messages: updatedWithAiMessage });
    setIsNewMessage(true);
    setisChatLoading(false);
  };

  const handleDeleteChat = async () => {
    if (await chatService.deleteChat(chat.chatId)) {
      setIsModalOpen(true);
      setChat({});
    }
  };

  const renderAssistentMenu = (item) => {
    return (
      <>
        <p className="assistentTitle">{item.name}</p>
        <p className="assistentDescription">{item.description}</p>
      </>
    );
  };

  const renderAiTagMenu = (item) => {
    return (
      <>
        <p className="assistentTitle">{item.model}</p>
      </>
    );
  };

  const chatService = new ChatService();

  const assistentService = new AssistentService();

  useEffect(() => {
    const fetchData = async () => {
      const response = await assistentService.getAssistents();
      setAssistentName(response[0].name);
      setAssistentId(response[0].id);
      setAssistents(response);

      const aiTagResponse = await chatService.getTags();
      setAiTag(aiTagResponse.models);
      setCurrentAiTag(aiTagResponse.models[0].name);

      const chatResponse = await chatService.getNewChat(props.resumeId);
      console.log(chatResponse);
      if (chatResponse.isNewChat) {
        setIsModalOpen(true);
      } else {
        setChat(chatResponse);
        setisChatLoading(false);
      }
      setIsLoading(false);
    };
    fetchData();
  }, []);

  useEffect(() => {
    if (isNewMessage) {
      messageEndRef.current?.scrollIntoView({
        behavior: "smooth",
      });
      setIsNewMessage(false);
    } else {
      messageEndRef.current?.scrollIntoView({ behavior: "auto" });
    }
  }, [chat.messages]);

  function adjustHeight(e) {
    e.target.style.height = "inherit"; // Reseta a altura para calcular corretamente
    e.target.style.height = `${e.target.scrollHeight}px`; // Ajusta a altura com base no scrollHeight
  }

  return (
    <>
      <div className="aiResumeMenu">
        <Dropdown
          title={currentAiTag}
          data={aiTag}
          renderItem={renderAiTagMenu}
          isDropdownOpen={isMainDropdown}
          setIsDropdownOpen={setIsMainDropdown}
          handleItemClick={handleMainAssistentClick}
          containerStyle={{ margin: "0px", border: "none" }}
        ></Dropdown>
        <div>
          <Button content="review" onCLick={handleDeleteChat} />
          <Button content="new chat" onCLick={handleDeleteChat} />
        </div>
      </div>
      <div className="chatContent">
        <div id="messagesDiv">
          {Object.keys(chat).length > 0
            ? chat.messages.map((message, index) => {
                return (
                  <div
                    key={index}
                    className={`${
                      message.role === "assistant"
                        ? "assistentMessage"
                        : "userMessage"
                    }`}
                  >
                    {message.role === "assistant" && (
                      <p className="assistentName">{chat.assistentName}</p>
                    )}
                    <p>{message.message}</p>
                    {index === chat.messages.length - 1 && (
                      <div ref={messageEndRef}></div>
                    )}
                  </div>
                );
              })
            : ``}
        </div>
      </div>
      <div className="textContainer">
        <div className="inputContainer">
          <textarea
            type="text"
            name=""
            id="aiTextInput"
            placeholder="Digite sua resposta aqui"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onInput={(e) => adjustHeight(e)}
            disabled={isChatLoading}
            onKeyDown={(e) => handleSearchKeyDown(e)}
            autoComplete="off"
            rows="1"
          />
          <button
            className="sendButton"
            onClick={() => handleSendMessage()}
            disabled={isChatLoading || message === ""}
          >
            <img src={SendButton} alt="send message icon" id="sendIcon" />
          </button>
        </div>
      </div>

      <div className={`modalBackground ${isModalOpen ? "active" : ""}`}></div>
      <div className={`aiModal ${isModalOpen ? "aiModalActive" : ""}`}>
        <h1 className="aiTitle">Escolha o seu Assistente</h1>
        <div className="assistentContainer">
          <Dropdown
            title={assistentName}
            data={assistents}
            renderItem={renderAssistentMenu}
            isDropdownOpen={isdropdownOpen}
            setIsDropdownOpen={setisDropdownOpen}
            handleItemClick={handleAssistentClick}
          ></Dropdown>
          <Dropdown
            title={currentAiTag}
            data={aiTag}
            renderItem={renderAiTagMenu}
            isDropdownOpen={isAiTagDropdown}
            setIsDropdownOpen={setAiTagDropdown}
            handleItemClick={handleAiAssistentClick}
          ></Dropdown>
        </div>

        <button onClick={() => handleCreateNewChat()}>conversar</button>
      </div>
    </>
  );
};

export default AiChat;

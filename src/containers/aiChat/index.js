import { useEffect, useState, React } from "react";
import "./AiChat.css";
import AssistentService from "../../app/services/AssistentService";
import ChatService from "../../app/services/ChatService";
import Dropdown from "../../components/Dropdown/Dropdown";
import SendButton from "../../Assets/ui/sendIcon.svg";

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

  const handleAssistentClick = (assistent) => {
    setAssistentName(assistent.name);
    setAssistentId(assistent.id);
    setisDropdownOpen(false);
  };

  const handleAiAssistentClick = (aiTag) => {
    setCurrentAiTag(aiTag.name);
    setAiTagDropdown(false);
  };

  const handleCreateNewChat = async () => {
    if (props.resumeId == 0) return;
    setIsModalOpen(false);
    const chatResponse = await chatService.getNewChat(
      props.resumeId,
      assistentId,
      currentAiTag
    );
    setChat(chatResponse);
    setisChatLoading(false);
    console.log(chatResponse);
  };

  const handleSendMessage = async () => {
    setMessage("");
    setisChatLoading(true);
    chat.messages.push({ role: "user", message: message });
    const chatResponse = await chatService.getMessage(
      chat.chatId,
      message,
      currentAiTag
    );
    chatResponse.message.message = chatResponse.message.content;
    chat.messages.push(chatResponse.message);
    setisChatLoading(false);
  };

  const handleDeleteChat = async () => {
    if (chatService.deleteChat(chat.chatId)) {
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
      const chatResponse = await chatService.getStatus(props.resumeId);

      const response = await assistentService.getAssistents();
      setAssistentName(response[0].name);
      setAssistentId(response[0].id);
      setAssistents(response);

      const aiTagResponse = await chatService.getTags();
      setAiTag(aiTagResponse.models);
      setCurrentAiTag(aiTagResponse.models[0].name);
      console.log(chatResponse);
      if (!chatResponse.isCreated) {
        setIsModalOpen(true);
      } else {
        await handleCreateNewChat();
      }
      setIsLoading(false);
    };
    fetchData();
  }, []);

  return (
    <>
      <div className="aiResumeMenu">
        <Dropdown
          title={currentAiTag}
          data={aiTag}
          renderItem={renderAiTagMenu}
          isDropdownOpen={isMainDropdown}
          setIsDropdownOpen={setIsMainDropdown}
          handleItemClick={handleAiAssistentClick}
          containerStyle={{ margin: "0px", border: "none" }}
        ></Dropdown>

        <button onClick={() => handleDeleteChat()}>start a new chat</button>
      </div>
      <div className="chatContent">
        <div id="messagesDiv">
          {Object.keys(chat).length > 0
            ? chat.messages.map((message, index) => {
                return (
                  <div
                    key={index}
                    className={`${
                      message.role == "assistant"
                        ? "assistentMessage"
                        : "userMessage"
                    }`}
                  >
                    {message.role == "assistant" && <p>{chat.assistentName}</p>}
                    <p>{message.message}</p>
                  </div>
                );
              })
            : ``}
        </div>
      </div>
      <div className="textContainer">
        <div className="inputContainer">
          <input
            type="text"
            name=""
            id="aiTextInput"
            placeholder="Digite sua resposta aqui"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
          <button className="sendButton" onClick={() => handleSendMessage()}>
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

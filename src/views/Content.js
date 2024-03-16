import { useLocation } from "react-router-dom";
const Content = ({ chat, handleSubmit, handleKeySubmit }) => {
  const location = useLocation();
  if (location.state) {
    console.log(location.state.myParam);
  } else {
    console.log("No state passed");
  }
  return (
    <main className="chatContainer">
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

export default Content;

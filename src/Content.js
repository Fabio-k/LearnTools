const Content = ({ chat, handleSubmit }) => {
  return (
    <main className="chatContainer">
      <ul className="chatBox">
        {chat.map((message) => (
          <li key={message.id} className={"message"}>
            <p className="sender">{message.actor}</p>
            <p>{message.message}</p>
          </li>
        ))}
      </ul>
      <div className="sendMessageButtom">
        <input type="text" name="" id="userMessage" />
        <button type="submit" onClick={handleSubmit}>
          send
        </button>
      </div>
    </main>
  );
};

export default Content;

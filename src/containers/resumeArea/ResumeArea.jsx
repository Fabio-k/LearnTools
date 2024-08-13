import "./ResumeArea.css";
import resumesService from "../../app/services/resumeService";
import chatIcon from "../../Assets/ui/chatIcon.svg";
import AiChat from "../aiChat/Aichat";
import Button from "../../components/Button/Button";

const ResumeArea = ({
  resumes,
  dispatch,
  isFeymanChat,
  resumeDisplay,
  setResumeDisplay,
  setIsFeymanChat,
}) => {
  function handleResumeTitle(title) {
    setResumeDisplay({ ...resumeDisplay, title: title });
  }

  function handleResumeDescription(description) {
    setResumeDisplay({ ...resumeDisplay, description: description });
  }

  async function saveResume() {
    const resumeService = new resumesService();
    let response;
    if (resumeDisplay.id === 0) {
      response = await resumeService.addResume(
        resumeDisplay.title,
        resumeDisplay.description
      );
      resumeDisplay.id = response.id;
    } else {
      response = await resumeService.editResume(
        resumeDisplay.id,
        resumeDisplay.title,
        resumeDisplay.description
      );
    }
    if (response) return response;
  }

  async function handleResumeSave() {
    const result = await saveResume();
    if (result) dispatch({ type: "UPDATE", payload: result });
  }

  return isFeymanChat ? (
    <>
      <AiChat resumeId={resumeDisplay.id} />
    </>
  ) : (
    <>
      <div className="titleContainer">
        <div className="textButtons">
          <Button content="revisar" />
          <Button content="salvar resumo" onCLick={handleResumeSave} />
        </div>

        <input
          type="text"
          className="titleInput"
          value={resumeDisplay.title}
          onChange={(e) => handleResumeTitle(e.target.value)}
          placeholder="titulo"
        />
      </div>

      <textarea
        name=""
        id=""
        className="descriptionInput"
        value={resumeDisplay.description}
        onChange={(e) => handleResumeDescription(e.target.value)}
        placeholder="texto"
      ></textarea>
      {resumeDisplay.id !== 0 ? (
        <button className="chatButton" onClick={() => setIsFeymanChat(true)}>
          <img src={chatIcon} className="chatImg" alt="chat icon" />
        </button>
      ) : (
        <></>
      )}
    </>
  );
};

export default ResumeArea;

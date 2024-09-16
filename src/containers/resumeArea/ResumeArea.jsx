import "./ResumeArea.css";
import resumesService from "../../app/services/resumeService";
import chatIcon from "../../Assets/ui/chatIcon.svg";
import AiChat from "../aiChat/Aichat";
import Button from "../../components/Button/Button";
import Editor from "../../components/Editor/Editor";

const ResumeArea = ({
  dispatch,
  isFeymanChat,
  resumeDisplay,
  setResumeDisplay,
  setIsFeymanChat,
}) => {
  function handleResumeTitle(title) {
    setResumeDisplay({ ...resumeDisplay, title: title });
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
        </div>
      </div>
      <div id="anotationContainer">
        <div>
          <textarea
            class="titleContainer"
            placeholder="titulo"
            value={resumeDisplay.title}
            onChange={(e) => handleResumeTitle(e.target.value)}
            onBlur={(e) => handleResumeSave()}
          ></textarea>
        </div>

        <Editor resumeDisplay={resumeDisplay} dispatch={dispatch} />
      </div>

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

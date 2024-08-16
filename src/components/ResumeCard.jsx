import React from "react";
import menu from "../Assets/ui/menu.svg";
import style from "../resumeCard.module.css";

const ResumeCard = ({
  resumeData,
  selectedResumeId,
  onItemClick,
  resumeSummary,
  handleContextMenu,
  handleButtonContextMenu,
}) => {
  const classes = `${style.resume} ${
    resumeData.id === selectedResumeId ? style.resumeSelected : ""
  }`;

  return (
    <>
      <li
        className={classes}
        onClick={onItemClick}
        onContextMenu={(e) => handleContextMenu(e, resumeData)}
      >
        <section>
          <h1 style={{ margin: 0, fontSize: "20px", fontFamily: "sans-serif" }}>
            {resumeData.title}
          </h1>
          <p style={{ margin: 0, marginTop: "5px", color: "#595959" }}>
            {resumeSummary}
          </p>
        </section>
        <img
          src={menu}
          alt="menu do item"
          className={`${style.menuImage} contextMenuButton`}
          onClick={(e) => handleButtonContextMenu(e, resumeData)}
        />
      </li>
    </>
  );
};

export default ResumeCard;

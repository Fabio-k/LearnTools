import React from "react";
import menu from "../Assets/ui/menu.svg";
import style from "../resumeCard.module.css";

const ResumeCard = ({
  resumeData,
  selectedResumeId,
  onItemClick,
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
            {resumeData.title == "" ? "sem título" : resumeData.title}
          </h1>
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

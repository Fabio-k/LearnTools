import React from "react";
import "./Button.css";
const Button = ({ content, onCLick }) => {
  return (
    <button className="defaultButton" onClick={() => onCLick()}>
      {content}
    </button>
  );
};

export default Button;

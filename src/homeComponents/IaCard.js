import React from "react";
import { Link } from "react-router-dom";

const IaCard = () => {
  return (
    <section className="choiceBlock">
      <div className="AI_Div">
        <h1 id="AI_Title">
          AI <br />
          Chat
        </h1>
      </div>
      <label htmlFor="">choose AI</label>
      <select name="" id="">
        <option value="LucasAI">Lucas</option>
        <option value="LucicanoAI">Luciano</option>
      </select>
      <div className="resumeDiv">
        <button>choose resume</button>
        <Link to={{ pathname: "/ia", state: { myParam: "LucasAI" } }}>
          start conversation
        </Link>
      </div>
    </section>
  );
};

export default IaCard;

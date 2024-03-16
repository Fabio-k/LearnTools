import React from "react";

const ResumesCard = () => {
  return (
    <section className="choiceBlock">
      <div className="resumeCard">
        <div className="resumeDiv">
          <input type="text" />
          <select name="" id="">
            <option value="CS">Computer sciency</option>
            <option value="addTopic">new Topic</option>
          </select>
        </div>
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          placeholder="Resume sketch"
        ></textarea>
        <div className="resumeDiv">
          <button>start ai with this Resume</button>
          <button>save resume</button>
        </div>
      </div>
    </section>
  );
};

export default ResumesCard;

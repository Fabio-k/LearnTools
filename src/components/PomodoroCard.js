import React, { useState, useEffect } from "react";

const PomodoroCard = () => {
  

  return (
    <section className="choiceBlock pomodoroCard">
      <h1 id="pomodoroClock">{`${minutes.toString().padStart(2, "0")}:${seconds
        .toString()
        .padStart(2, "0")}`}</h1>
      <div id="pomodoroDiv">
        <button className="pomodoroButtons" onClick={handleLongPauseButtom}>
          Long Pause
        </button>
        <button className="pomodoroButtons" onClick={handleStartPauseButtom}>
          {!isRunning ? "start" : "stop"}
        </button>
        <button className="pomodoroButtons" onClick={handleShortPauseButtom}>
          Short Pause
        </button>
      </div>
    </section>
  );
};

export default PomodoroCard;

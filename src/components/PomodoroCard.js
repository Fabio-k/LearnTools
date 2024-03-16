import React, { useState, useEffect } from "react";

const PomodoroCard = () => {
  const [count, setCount] = useState(0);
  const [timeLimit, setTimeLimit] = useState(25 * 60);
  const [isRunning, setIsRunning] = useState(false);

  useEffect(() => {
    const updateCount = () => {
      setCount((prevCount) => prevCount + 1);
    };

    let timer = null;
    if (isRunning && count < timeLimit) {
      updateCount();
      timer = setInterval(updateCount, 1000);
    }

    return () => {
      clearInterval(timer);
    };
  }, [isRunning, timeLimit]);

  const minutes = Math.floor(count / 60);
  const seconds = count % 60;

  const handleLongPauseButtom = () => {
    setCount(0);
    setTimeLimit(15 * 60);
    setIsRunning(true);
  };
  const handleShortPauseButtom = () => {
    setCount(0);
    setTimeLimit(5 * 60);
    setIsRunning(true);
  };
  const handleStartPauseButtom = () => {
    setIsRunning(!isRunning);
  };

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

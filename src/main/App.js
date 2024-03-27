import Header from "../components/Header";
import "../index.css";
import { useState, useEffect } from "react";
import Rotas from "./Rotas";
function App() {
  const [timeLimit, setTimeLimit] = useState(25 * 60);
  const [isRunning, setIsRunning] = useState(false);
  const [count, setCount] = useState(0);
  useEffect(() => {
    const updateCount = () => {
      setCount((prevCount) => prevCount + 1);
    };

    let pomodoroTimer = null;
    if (isRunning && count < timeLimit) {
      updateCount();
      pomodoroTimer = setInterval(updateCount, 1000);
    }

    return () => {
      clearInterval(pomodoroTimer);
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
    <div className="App">
      <Header
        time={`${minutes.toString().padStart(2, "0")}:${seconds
          .toString()
          .padStart(2, "0")}`}
        handleLongPause={handleLongPauseButtom}
        handleShortPause={handleShortPauseButtom}
        handleStartPauseButtom={handleStartPauseButtom}
        isRunning={isRunning}
      />
      <Rotas />
    </div>
  );
}

export default App;

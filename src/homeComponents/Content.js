import React from "react";
import IaCard from "./IaCard";
import PomodoroCard from "./PomodoroCard";
import ResumesCard from "./ResumesCard";
import FlashCard from "./FlashCard";

const Content = () => {
  return (
    <main className="homeContainer">
      <FlashCard />
      <IaCard />
      <ResumesCard />
      <PomodoroCard />
    </main>
  );
};

export default Content;

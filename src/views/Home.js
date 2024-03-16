import React from "react";
import IaCard from "../components/IaCard";
import PomodoroCard from "../components/PomodoroCard";
import ResumesCard from "../components/ResumesCard";
import FlashCard from "../components/FlashCard";

export default function Home() {
  return (
    <div className="App">
      <main className="homeContainer">
        <FlashCard />
        <IaCard />
        <ResumesCard />
        <PomodoroCard />
      </main>
    </div>
  );
}

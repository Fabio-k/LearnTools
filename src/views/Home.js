import React, { useEffect } from "react";
import { Doughnut } from "react-chartjs-2";
import { useLayout } from "../components/LayoutContext";
import homeStyle from "../home.module.css";
import { Chart, ArcElement, Tooltip, Legend } from "chart.js";
Chart.register(ArcElement, Tooltip, Legend);

const sideMenu = () => {
  return <aside className={homeStyle.sideMenu}>side menu</aside>;
};

const FlashcardAnalytics = () => {
  const data = {
    labels: ["Red", "Blue", "Yellow"],
    datasets: [
      {
        label: "# of flashcards",
        data: [12, 19, 3],
        backgroundColor: [
          "rgba(255, 99, 132, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
        ],
        borderWidth: 0,
      },
    ],
  };

  const options = {
    plugins: {
      legend: {
        display: false,
      },
    },
    cutout: "75%",
  };

  return (
    <>
      <p>analizes</p>
      <div className={homeStyle.flashcardChart}>
        <Doughnut data={data} style={{ maxHeight: "80%" }} options={options} />
      </div>
    </>
  );
};

const GeneralAnalitics = () => {
  return (
    <>
      <p>general analitics</p>
    </>
  );
};

const ResumeAnalitics = () => {
  return (
    <>
      <p>resume analitics</p>
    </>
  );
};

export default function Home() {
  const { setExtraElement } = useLayout();

  useEffect(() => {
    setExtraElement(sideMenu);

    return () => setExtraElement(null);
  }, [setExtraElement]);

  return (
    <>
      <div className={homeStyle.flashcardAnalyze}>
        <FlashcardAnalytics />
      </div>

      <div className={homeStyle.sideAnaliticsContainer}>
        <div className={homeStyle.generalAnalitics}>
          <GeneralAnalitics />
        </div>
        <div className={homeStyle.resumeAnalitics}>
          <ResumeAnalitics />
        </div>
      </div>
    </>
  );
}

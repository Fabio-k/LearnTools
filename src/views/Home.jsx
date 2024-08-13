import React, { useEffect, useState } from "react";
import { Doughnut } from "react-chartjs-2";
import { useLayout } from "../components/LayoutContext";
import homeStyle from "../home.module.css";
import { Chart, ArcElement, Tooltip, Legend } from "chart.js";
import AnaliticsService from "../app/services/AnaliticsService";
import useUser from "../interfaces/hooks/useUser";
import { useNavigate } from "react-router-dom";
Chart.register(ArcElement, Tooltip, Legend);

const sideMenu = () => {
  return <aside className={homeStyle.sideMenu}>side menu</aside>;
};

const FlashcardAnalytics = ({ analiticsResponse }) => {
  const tags =
    analiticsResponse && analiticsResponse.tagByFlashcard
      ? analiticsResponse.tagByFlashcard
      : {};
  const data = {
    labels: Object.keys(tags),
    datasets: [
      {
        label: "# of flashcards",
        data: Object.values(tags),
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
        {Object.keys(analiticsResponse.tagByFlashcard || {}).length > 0 ? (
          <Doughnut
            data={data}
            style={{ maxHeight: "80%" }}
            options={options}
          />
        ) : (
          <h2>
            Nenhum Flashcard a vista !
            <br /> Crie um flashcard para obter estatísticas
          </h2>
        )}
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

  const [analiticsData, setAnaliticsData] = useState({});

  const { user, isLoading } = useUser();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user && !isLoading) {
      navigate("/login");
    }
  }, [user, isLoading]);

  useEffect(() => {
    setExtraElement(sideMenu);

    return () => setExtraElement(null);
  }, [setExtraElement]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const analiticsService = new AnaliticsService();
        const analiticsResponse = await analiticsService.getAnalitics();
        setAnaliticsData(analiticsResponse);
      } catch (e) {
        console.error("Erro ao buscar dados:", e);
      }
    };
    fetchData();
  }, []);

  return (
    <>
      <div className={homeStyle.flashcardAnalyze}>
        <FlashcardAnalytics analiticsResponse={analiticsData} />
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

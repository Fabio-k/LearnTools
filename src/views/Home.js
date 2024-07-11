import React, { useEffect, useState } from "react";
import { Doughnut } from "react-chartjs-2";
import { useLayout } from "../components/LayoutContext";
import homeStyle from "../home.module.css";
import { Chart, ArcElement, Tooltip, Legend } from "chart.js";
import flashCardService from "../app/services/flashCardService";
import tagService from "../app/services/tagService";
Chart.register(ArcElement, Tooltip, Legend);

const sideMenu = () => {
  return <aside className={homeStyle.sideMenu}>side menu</aside>;
};

const handleFlashcardAnalyze = (response) => {
  console.log(response);
};

const FlashcardAnalytics = ({ flashcardResponse, tagResponse }) => {
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
        {Object.keys(flashcardResponse).length > 0 ? (
          <Doughnut
            data={data}
            style={{ maxHeight: "80%" }}
            options={options}
          />
        ) : (
          <h2>
            Nem um resumo a vista !
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

  const [flashcardData, setFlashcardData] = useState({});
  const [TagData, setTagData] = useState({});
  const tagSer = new tagService();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const flashcardService = new flashCardService();
        const flashcardResponse = await flashcardService.getFlashCards();
        setFlashcardData(flashcardResponse);
        const tamanho = Object.keys(flashcardResponse).length;

        const tagResponse = await tagSer.getTag();
        setTagData(tagResponse);
      } catch (e) {
        console.error("Erro ao buscar dados:", e);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    setExtraElement(sideMenu);

    return () => setExtraElement(null);
  }, [setExtraElement]);

  useEffect(() => {
    handleFlashcardAnalyze(flashcardData);
  }, [flashcardData]);

  return (
    <>
      <div className={homeStyle.flashcardAnalyze}>
        <FlashcardAnalytics
          flashcardResponse={flashcardData}
          tagResponse={TagData}
        />
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

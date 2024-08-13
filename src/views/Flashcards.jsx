import React, { useState, useEffect } from "react";
import FlashCardService from "../app/services/flashCardService";

const Flashcards = () => {
  const [flashCardList, setflashCardList] = useState([]);
  const flashCardController = new FlashCardService();

  useEffect(() => {
    const fetchFlahCards = async () => {
      const response = await flashCardController.getFlashCards();
      if (response.ok) {
        setflashCardList(await response.json());
      }
    };
    fetchFlahCards();
  }, []);

  return (
    <div>
      <section>
        <div className="flip-card">
          <div className="flip-card-inner">
            <div className="flip-card-front">
              <h1>Front</h1>
            </div>
            <div className="flip-card-back">
              <h1>Back</h1>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Flashcards;

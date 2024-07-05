import React from "react";
import { useState, useEffect } from "react";
import resumesService from "../app/services/resumeService";

const Resumes = () => {
  const [dropdownOpen, setDropdown] = useState(false);
  const toggle = () => setDropdown(!dropdownOpen);

  const [modalOpen, setModalState] = useState(false);
  const toggleModal = () => {
    setModalState((prevModalState) => !prevModalState);
    console.log(modalOpen);
  };
  const [ModalContent, setModalContent] = useState(null);

  const [searchWord, setSearchWord] = useState("");

  const [resumes, setResumes] = useState([]);

  const [topics, setTopics] = useState([]);

  const [isLoadingContent, setIsLoadingContent] = useState(false);

  useEffect(() => {
    const fetchResumes = async () => {
      setIsLoadingContent(true);
      const resumeData = await new resumesService().get();

      setIsLoadingContent(false);

      setResumes(resumeData);
    };
    fetchResumes();
  }, []);

  const handleSearchWord = (word) => {
    setSearchWord(word);
  };

  const handleModalContent = (Content) => {
    setModalContent(Content);
    toggleModal();
  };

  return (
    <div
      style={{
        display: "flex",
        flexGrow: 1,
        flexDirection: "column",
      }}
    >
      <section
        style={{ display: "flex", height: "20%", justifyContent: "center" }}
      >
        <div
          style={{
            display: "flex",
            width: "60%",
            padding: "10px",
            alignItems: "center",
          }}
        ></div>
      </section>
    </div>
  );
};

export default Resumes;

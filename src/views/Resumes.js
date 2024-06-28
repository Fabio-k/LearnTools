import React from "react";
import { useState, useEffect } from "react";
import resumesService from "../app/services/resumeService";
import topicService from "../app/services/topicService";
import ResumeCard from "../components/ResumeCard";
import ResmumesFormContent from "../components/ResumesFormContent";
import TopicFormContent from "../components/TopicFormContent";

const Resumes = () => {
  const topicApiService = new topicService();

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
      const topicData = await topicApiService.get();
      setIsLoadingContent(false);

      setTopics(topicData);
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

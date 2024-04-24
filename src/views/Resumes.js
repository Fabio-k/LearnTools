import React from "react";
import { useState, useEffect } from "react";
import {
  Button,
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Input,
  List,
  Modal,
} from "reactstrap";
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
        >
          <Input
            placeholder="search for resume"
            onChange={(e) => {
              handleSearchWord(e.target.value);
            }}
          />
          <Dropdown isOpen={dropdownOpen} toggle={toggle} direction="down">
            <DropdownToggle caret>topico</DropdownToggle>
            <DropdownMenu>
              {topics.map((topicObject) => {
                return (
                  <DropdownItem key={topicObject.id}>
                    {topicObject.name}
                  </DropdownItem>
                );
              })}
              <DropdownItem
                onClick={() => {
                  handleModalContent(
                    <TopicFormContent
                      topics={topics}
                      setTopics={setTopics}
                      topicApiService={topicApiService}
                      toggleModal={toggleModal}
                    />
                  );
                }}
              >
                + add item
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <Button
            onClick={() => {
              handleModalContent(
                <ResmumesFormContent
                  topics={topics}
                  resumes={resumes}
                  setResumes={setResumes}
                  toggleModal={toggleModal}
                />
              );
            }}
          >
            +
          </Button>
        </div>
      </section>

      {resumes.length > 0 ? (
        <List
          type="unstyled"
          style={{
            display: "flex",
            justifyContent: "center",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          {resumes.map((resumesObject) => (
            <ResumeCard
              resumes={resumes}
              setResumes={setResumes}
              resumesObject={resumesObject}
              handleModalContent={handleModalContent}
              toggleModal={toggleModal}
            />
          ))}
        </List>
      ) : (
        <p style={{ textAlign: "center" }}>
          Resumes not found try to reload the page
        </p>
      )}
      <Modal isOpen={modalOpen} toggle={toggleModal}>
        {ModalContent}
      </Modal>
    </div>
  );
};

export default Resumes;

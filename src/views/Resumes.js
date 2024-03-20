import React from "react";
import { useState, useEffect } from "react";
import {
  Button,
  Input,
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from "reactstrap";
import resumesService from "../app/services/resumeService";
import topicService from "../app/services/topicService";

const Resumes = () => {
  const [dropdownOpen, setDropdown] = useState(false);
  const toggle = () => setDropdown(!dropdownOpen);

  const [resumes, setResumes] = useState([]);
  const [topics, setTopics] = useState([]);

  useEffect(() => {
    const fetchResumes = async () => {
      const resumeData = await new resumesService().get();
      const topicData = await new topicService().get();

      setTopics(topicData);
      setResumes(resumeData);
    };
    fetchResumes();
  }, []);

  return (
    <div>
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
          <Input placeholder="search for resume" />
          <Dropdown isOpen={dropdownOpen} toggle={toggle} direction="down">
            <DropdownToggle caret>topico</DropdownToggle>
            <DropdownMenu>
              {topics.map((topic, index) => {
                return <DropdownItem key={index}>{topic.name}</DropdownItem>;
              })}
            </DropdownMenu>
          </Dropdown>
        </div>
      </section>

      {resumes.length > 0 ? (
        <ul
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          {resumes.map((item) => (
            <li
              key={item.id}
              style={{
                border: "1px solid black",
                padding: "10px",
                borderRadius: "10px",
                maxWidth: "70%",
                marginBottom: "50px",
              }}
            >
              <section
                style={{ display: "flex", justifyContent: "space-between" }}
              >
                <h1>{item.title}</h1>
                <div
                  style={{
                    display: "flex",
                    width: "40%",
                    justifyContent: "space-evenly",
                    alignItems: "center",
                  }}
                >
                  <Button color="danger" outline>
                    delete
                  </Button>
                  <Button color="primary" outline>
                    edit
                  </Button>
                  <Button color="warning" outline>
                    send to ai
                  </Button>
                </div>
              </section>

              <p>{item.description}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>Resumes not found</p>
      )}
    </div>
  );
};

export default Resumes;

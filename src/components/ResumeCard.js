import React from "react";
import { useState } from "react";
import resumesService from "../app/services/resumeService";

const DeleteFormContent = ({
  resumes,
  setResumes,
  resumesObject,
  toggleModal,
  resumeApi,
}) => {
  const handleDeleteResume = async () => {
    const response = await resumeApi.deleteResume(resumesObject.id);
    if (response.ok) {
      setResumes(resumes.filter((resume) => resume.id !== resumesObject.id));
      toggleModal();
    }
  };

  return <></>;
};

const EditResumeForm = ({
  resumes,
  setResumes,
  resumesObject,
  resumeApi,
  toggleModal,
}) => {
  const [resumeObj, setResumeObj] = useState(resumesObject);

  const handleSubmitEdit = async () => {
    const resumeObjectEdited = {
      title: document.getElementById("resumeTitle").value,
      description: document.getElementById("resumeDescription").value,
    };
    const response = await resumeApi.editResume(
      resumesObject.id,
      resumeObjectEdited
    );
    if (response.ok) {
      setResumes(
        resumes.map((resume) => {
          if (resume.id == resumeObj.id) {
            return { ...resume, ...resumeObjectEdited };
          }
          return resume;
        })
      );
      toggleModal();
    }
  };

  return <></>;
};

const ResumeCard = ({
  resumes,
  setResumes,
  resumesObject,
  handleModalContent,
  toggleModal,
}) => {
  const resumeApi = new resumesService();
  return (
    <li
      key={resumesObject.id}
      style={{
        border: "1px solid black",
        padding: "10px",
        borderRadius: "10px",
        maxWidth: "70%",
        marginBottom: "50px",
      }}
    >
      <section style={{ display: "flex", justifyContent: "space-between" }}>
        <h1>{resumesObject.title}</h1>
        <div
          style={{
            display: "flex",
            width: "40%",
            justifyContent: "space-evenly",
            alignItems: "center",
          }}
        ></div>
      </section>

      <p>{resumesObject.description}</p>
    </li>
  );
};

export default ResumeCard;

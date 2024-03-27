import React from "react";
import { useState } from "react";
import {
  Button,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Input,
  FormGroup,
} from "reactstrap";
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

  return (
    <>
      <ModalHeader>Tem certeza que deseja deletar este resumo?</ModalHeader>
      <ModalBody>
        <p>{resumesObject.title}</p>
        <p>{resumesObject.description}</p>
      </ModalBody>

      <ModalFooter>
        <Button color="gray" onClick={toggleModal}>
          cancelar
        </Button>
        <Button color="danger" onClick={handleDeleteResume}>
          deletar
        </Button>
      </ModalFooter>
    </>
  );
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

  return (
    <>
      <ModalHeader>Editar</ModalHeader>
      <ModalBody>
        <FormGroup>
          <Input
            type="text"
            id="resumeTitle"
            value={resumeObj.title}
            onChange={(e) =>
              setResumeObj({ ...resumeObj, title: e.target.value })
            }
          ></Input>
        </FormGroup>
        <FormGroup>
          <Input
            type="textarea"
            id="resumeDescription"
            value={resumeObj.description}
            onChange={(e) =>
              setResumeObj({ ...resumeObj, description: e.target.value })
            }
          ></Input>
        </FormGroup>
      </ModalBody>

      <ModalFooter>
        <Button color="gray" onClick={toggleModal}>
          cancelar
        </Button>
        <Button color="primary" onClick={handleSubmitEdit}>
          salvar
        </Button>
      </ModalFooter>
    </>
  );
};

const SendToAiForm = () => {
  return (
    <>
      <ModalHeader>Iniciar conversa</ModalHeader>
      <ModalBody>
        <FormGroup>
          <Input type="select">
            <option value="Lucas">Lucas</option>
            <option value="Luciano">Luciano</option>
          </Input>
        </FormGroup>
      </ModalBody>

      <ModalFooter>
        <Button color="gray">cancelar</Button>
        <Button color="primary">iniciar</Button>
      </ModalFooter>
    </>
  );
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
        >
          <Button
            color="danger"
            outline
            onClick={() => {
              handleModalContent(
                <DeleteFormContent
                  resumes={resumes}
                  setResumes={setResumes}
                  resumesObject={resumesObject}
                  toggleModal={toggleModal}
                  resumeApi={resumeApi}
                />
              );
            }}
          >
            delete
          </Button>
          <Button
            color="primary"
            outline
            onClick={() => {
              handleModalContent(
                <EditResumeForm
                  resumes={resumes}
                  setResumes={setResumes}
                  resumesObject={resumesObject}
                  resumeApi={resumeApi}
                  toggleModal={toggleModal}
                />
              );
            }}
          >
            edit
          </Button>
          <Button
            color="warning"
            onClick={() => {
              handleModalContent(<SendToAiForm />);
            }}
            outline
          >
            send to ai
          </Button>
        </div>
      </section>

      <p>{resumesObject.description}</p>
    </li>
  );
};

export default ResumeCard;

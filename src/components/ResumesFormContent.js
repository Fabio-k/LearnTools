import { FormGroup } from "reactstrap";
import {
  Button,
  Col,
  Input,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import resumesService from "../app/services/resumeService";

const ResmumesFormContent = ({ topics, resumes, setResumes, toggleModal }) => {
  const resumeApi = new resumesService();
  const handleSubmitNewResume = async () => {
    const currentId =
      resumes.length > 0 ? parseInt(resumes[resumes.length - 1].id) : 0;
    const resumeId = currentId + 1;
    const resumeObject = {
      id: toString(resumeId),
      title: document.getElementById("resumeTitle").value,
      description: document.getElementById("resumeDescription").value,
    };
    resumeApi.addResume(resumeObject);

    setResumes([...resumes, resumeObject]);
    toggleModal();
  };

  return (
    <>
      <ModalHeader>Adicionar resumo</ModalHeader>
      <ModalBody>
        <FormGroup row>
          <Col sm={8}>
            <Input placeholder="título do resumo" id="resumeTitle" required />
          </Col>
          <Col sm={4}>
            <Input
              type="select"
              onChange={(e) => {
                console.log(e.target.id);
              }}
            >
              {topics.map((topic) => {
                return <option key={topic.id}>{topic.name}</option>;
              })}
            </Input>
          </Col>
        </FormGroup>
        <Input type="textarea" id="resumeDescription"></Input>
      </ModalBody>

      <ModalFooter>
        <Button onClick={handleSubmitNewResume}>adicionar</Button>
      </ModalFooter>
    </>
  );
};

export default ResmumesFormContent;

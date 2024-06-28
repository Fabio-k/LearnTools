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

  return <></>;
};

export default ResmumesFormContent;

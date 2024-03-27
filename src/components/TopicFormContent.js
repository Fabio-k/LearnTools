import { ModalHeader, ModalBody, ModalFooter, Input, Button } from "reactstrap";

const TopicFormContent = ({
  topics,
  setTopics,
  topicApiService,
  toggleModal,
}) => {
  const handleSubmitNewTopic = async (event) => {
    event.preventDefault();
    const topicId =
      topics.length > 0 ? parseInt(topics[topics.length - 1].id, 10) + 1 : 0;

    const topicObject = {
      name: document.getElementById("topicName").value,
      id: topicId,
    };

    const serverResponse = await topicApiService.addTopic(topicObject);
    setTopics((prevTopics) => [...prevTopics, topicObject]);
    toggleModal();
  };

  return (
    <>
      <ModalHeader>Adicionar Tópico</ModalHeader>
      <ModalBody>
        <Input placeholder="nome do tópico" id="topicName" required />
      </ModalBody>

      <ModalFooter>
        <Button onClick={handleSubmitNewTopic}>adicionar</Button>
      </ModalFooter>
    </>
  );
};

export default TopicFormContent;

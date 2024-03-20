import api from "../api";

class iaService extends api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:8080/v1/chat/completions";
  }

  formatMessages = (messages) => {
    return messages.map((message) => {
      return {
        content: message.message.content,
        role: message.message.role,
      };
    });
  };

  async chat(body) {
    console.log(body);
    const data = await this.post({
      model: "openhermes",
      messages: body,
    });
    return this.formatMessages(data.choices);
  }
}

export default iaService;

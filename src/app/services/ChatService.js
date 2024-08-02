import Api, { methods, routes } from "../api";

export default class ChatService extends Api {
  constructor() {
    super();
    this.setAuthentication();
    this.baseUrl = routes.chat;
  }

  async getNewChat(resumeId, assistentId, model) {
    const body = {
      resumeId: resumeId,
      assistentId: assistentId,
      model: model,
    };
    const response = await this.request(methods.post, body);
    if (response.status == 200) return await response.json();
  }
  async getMessage(chatId, message, model) {
    this.baseUrl += `/${chatId}`;
    const body = {
      model: model,
      message: message,
    };
    console.log(body);
    const response = await this.request(methods.post, body);

    if (response.status == 200) return await response.json();
  }

  async getTags() {
    const response = await this.request(methods.get, undefined, "/tags");
    if (response.status == 200) return await response.json();
  }

  async deleteChat(chatId) {
    const response = await this.request(
      methods.delete,
      undefined,
      `/${chatId}`
    );
    return response.status == 200;
  }
}

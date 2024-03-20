import api from "../api";

export default class topicService extends api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/topicos";
  }

  async getResumes() {
    return await this.get();
  }
}

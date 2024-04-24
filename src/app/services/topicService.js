import Api from "../api";

export default class topicService extends Api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/topicos";
  }

  async getResumes() {
    return await this.get();
  }

  async addTopic(body) {
    return await this.post(body);
  }
}

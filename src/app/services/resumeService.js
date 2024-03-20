import api from "../api";

export default class resumesService extends api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/resumes";
  }
  async getResumes() {
    return await this.get();
  }
}

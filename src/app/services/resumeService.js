import api from "../api";

export default class resumesService extends api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/resumes";
  }
  async getResumes() {
    return await this.get();
  }
  async addResume(body) {
    return await this.post(body);
  }
  async deleteResume(id) {
    this.baseUrl = this.baseUrl + "/" + id;
    return this.delete();
  }
  async editResume(id, body) {
    this.baseUrl = this.baseUrl + "/" + id;
    return this.patch(body);
  }
}

import Api, { routes, methods } from "../api";

export default class resumesService extends Api {
  constructor() {
    super();
    this.baseUrl = routes.resumes;
    this.setAuthentication();
  }
  async getResumes() {
    return await this.get();
  }

  async addResume(title, description) {
    const body = {
      title: title,
      description: description,
    };
    const response = await this.request(methods.post, body);
    if (response.status == 200) return await response.json();
  }

  async deleteResume(id) {
    this.baseUrl = this.baseUrl + "/" + id;
    return this.delete();
  }

  async editResume(id, title, description) {
    this.baseUrl = this.baseUrl + "/" + id;
    const body = {
      title: title,
      description: description,
    };

    const response = await this.request(methods.patch, body);
    if (response.status == 200) return await response.json();
  }
}

import Api from "../api";

export default class tagService extends Api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:8080/tags";
  }

  async getTag() {
    this.setBasicAuth();
    return await this.get();
  }
}

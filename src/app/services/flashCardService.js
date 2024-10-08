import Api from "../api";

export default class flashCardService extends Api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:8080/flashcards";
  }
  async getFlashCards() {
    this.setAuthentication();
    const response = await this.get();
    return response;
  }
}

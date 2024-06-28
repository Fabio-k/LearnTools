import Api from "../api";

export default class flashCardService extends Api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/flashcards";
  }
  async getFlashCards() {
    const response = await this.get();
  }
}

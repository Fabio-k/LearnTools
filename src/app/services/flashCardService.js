import Api from "../api";

export default class FlashCardService extends Api {
  constructor() {
    super();
    this.baseUrl = "http://localhost:3500/flashcards";
  }
  async getFlashCards() {
    const response = await this.get();
  }
}

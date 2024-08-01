import Api, { methods, routes } from "../api";

export default class AssistentService extends Api {
  constructor() {
    super();
    this.setAuthentication();
    this.baseUrl = routes.assistent;
  }

  async getAssistents() {
    const response = await this.request(methods.get);
    if (response.status == 200) return await response.json();
    return undefined;
  }
}

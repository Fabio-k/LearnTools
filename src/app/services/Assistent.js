import Api, { routes } from "../api";

export default class Assistent extends Api {
  constructor() {
    super();
    this.baseUrl = routes.assistent;
  }

  postAssistent = async (id, messages) => {
    const route = this.baseUrl.replace("{id}", id);
    this.baseUrl = route;

    const body = {
      messages: messages,
    };

    const response = await this.post(body);
    return response;
  };
}

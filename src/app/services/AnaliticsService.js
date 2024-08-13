import Api, { routes } from "../api";

export default class AnaliticsService extends Api {
  constructor() {
    super();
    this.baseUrl = routes.analitics;
  }

  getAnalitics = async () => {
    this.setAuthentication();
    const response = await this.get();
    return response;
  };
}

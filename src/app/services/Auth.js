import Api, { routes } from "../api";

export default class Auth extends Api {
  constructor() {
    super();
    this.baseUrl = routes.login;
  }

  async getUserInformation(name, password) {
    this.setBasicAuth(name, password);
    return this.post("");
  }

  async sendGithubCode(code) {
    this.baseUrl = routes.github;
    return this.post(code);
  }
}

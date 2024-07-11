import Api, { routes } from "../api";

export default class Auth extends Api {
  constructor() {
    super();
    this.baseUrl = routes.login;
  }

  async getUserInformation(name, userPassword) {
    const request = { username: name, password: userPassword };
    return this.post(request);
  }

  async sendGithubCode(request) {
    this.baseUrl = routes.github;
    return this.post(request);
  }
}

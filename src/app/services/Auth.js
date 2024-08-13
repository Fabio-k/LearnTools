import Api, { methods, routes } from "../api";

export default class Auth extends Api {
  constructor() {
    super();
    this.baseUrl = routes.login;
  }

  async signUpUser(username, password) {
    this.baseUrl = routes.signUp;
    const body = {
      name: username,
      username: username,
      password: password,
    };
    return this.request(methods.post, body);
  }

  async getUserInformation(name, userPassword) {
    this.baseUrl = routes.login;
    const request = { username: name, password: userPassword };
    return this.post(request);
  }

  async sendGithubCode(request) {
    this.baseUrl = routes.github;
    return this.post(request);
  }
}

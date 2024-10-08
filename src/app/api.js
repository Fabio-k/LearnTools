export const server = "http://localhost:8080";

export const methods = {
  post: "POST",
  patch: "PATCH",
  get: "GET",
  delete: "DELETE",
};

export const routes = {
  assistent: `${server}/assistents`,
  resumes: `${server}/resumes`,
  login: `${server}/signin`,
  signUp: `${server}/signup`,
  github: `${server}/github`,
  analitics: `${server}/analitics`,
  chat: `${server}/chat`,
};

class Api {
  constructor() {
    this.headers = {
      "Content-Type": "application/json",
    };
    this.baseUrl = "";
  }

  setAuthentication() {
    let token = localStorage.getItem("token");
    if (token) {
      this.headers["Authorization"] = `Bearer ${token}`;
    }
  }

  async request(method, body, param = "") {
    const request = {
      headers: this.headers,
      method: method,
      body: JSON.stringify(body),
    };
    console.log(request.body);
    const response = await fetch(this.baseUrl + param, request);
    return response;
  }

  async post(objeto) {
    let response;
    const request = {
      method: "POST",
      headers: this.headers,
      body: JSON.stringify(objeto),
    };
    try {
      response = await fetch(this.baseUrl, request);
    } catch (err) {
      console.log(err);
    }
    return response.json();
  }

  async get() {
    let response;
    try {
      response = await fetch(this.baseUrl, {
        method: "GET",
        headers: this.headers,
      });
    } catch (err) {
      console.log(err);
    }
    return response.json();
  }

  async delete() {
    let response;
    try {
      response = await fetch(this.baseUrl, {
        method: "DELETE",
        headers: this.headers,
      });
    } catch (err) {
      console.log(err);
    }
    return response;
  }

  async patch(body) {
    let response;
    try {
      response = await fetch(this.baseUrl, {
        method: "PATCH",
        headers: this.headers,
        body: JSON.stringify(body),
      });
    } catch (err) {
      console.log(err);
    }
    return response;
  }
}
export default Api;

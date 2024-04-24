export const server = "http://localhost:8080";

export const aiModel = {
  openHermes: "openhermes",
};
export const routes = {
  assistent: `${server}/assistents/{id}/ask`,
  resumes: `${server}/resumes`,
};

class Api {
  constructor() {
    this.headers = {
      "Content-Type": "application/json",
    };
    this.baseUrl = "";
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
        body: JSON.stringify(body),
      });
    } catch (err) {
      console.log(err);
    }
    return response;
  }
}
export default Api;
class api {
  constructor() {
    this.headers = {
      "Content-Type": "application/json",
    };
    this.baseUrl = "";
  }
  async post(objeto) {
    let response;
    try {
      response = await fetch(this.baseUrl, {
        method: "POST",
        headers: this.headers,
        body: JSON.stringify(objeto),
      });
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
export default api;

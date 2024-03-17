class api {
  constructor() {
    this.headers = {
      "Content-Type": "application/json",
      Authorization: "",
    };
  }
  async post(url, objeto) {
    const requestUrl = url;
    return fetch(requestUrl, {
      method: "POST",
      headers: this.headers,
      body: JSON.stringify(objeto),
    }).then((response) => response.json());
  }
}
export default api;

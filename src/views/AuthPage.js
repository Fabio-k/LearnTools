import logo from "../logo.svg";
import githubLogo from "../github-mark-white.svg";
import styles from "../auth.module.css";
import { useEffect, useState } from "react";
import Auth from "../app/services/Auth";
import { useNavigate } from "react-router-dom";
import queryString from "query-string";

const AuthPage = () => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(false);
  const navigate = useNavigate();

  const clientId = "Ov23liEDnGP9988VvztQ";

  useEffect(() => {
    const { code } = queryString.parse(window.location.search);
    const authService = new Auth();
    console.log(code);
    if (code) {
      authService.sendGithubCode(code);
    }
  }, []);

  const handleSubmitForm = async (event) => {
    event.preventDefault();
    const authService = new Auth();
    try {
      const response = await authService.getUserInformation(name, password);
      if (response != undefined) {
        localStorage.setItem("user", name);
        localStorage.setItem("password", password);
        navigate("/home", { userInformation: { response } });
      }
      console.log(response);
    } catch (e) {
      console.log("error no post" + e);
      setLoginError(true);
    }
  };

  return (
    <div className={styles.authBackgroundDiv}>
      <div className={styles.authLogo}>
        <img src={logo} alt="Logo" />
        <h1 style={{ color: "white" }}>Bem vindo de volta!</h1>
      </div>
      <main className={styles.contentContainer}>
        <form action="" method="post" onSubmit={handleSubmitForm}>
          <h1>Fazer Login</h1>
          <input
            type="text"
            placeholder="Digite seu usuário"
            name="username"
            value={name}
            onChange={(e) => setName(e.target.value)}
            onClick={() => setLoginError(false)}
            autoComplete="off"
            className={loginError ? styles.loginError : undefined}
          />
          <input
            type="password"
            placeholder="Digite a Senha"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onClick={() => setLoginError(false)}
            autoComplete="off"
            className={loginError ? styles.loginError : undefined}
          />
          <button className={styles.loginButton}>Login</button>

          <p>ou</p>
          <button
            className={`button ${styles.github}`}
            onClick={() =>
              (window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}`)
            }
          >
            <div className={styles.githubContent}>
              Continue com Github{" "}
              <img style={{ height: "30px" }} src={githubLogo} alt="" />
            </div>
          </button>
        </form>
      </main>
    </div>
  );
};

export default AuthPage;

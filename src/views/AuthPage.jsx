import logo from "../Assets/ui/logo.svg";
import githubLogo from "../Assets/github-mark-white.svg";
import styles from "../auth.module.css";
import { useEffect, useState } from "react";
import Auth from "../app/services/Auth";
import { useNavigate } from "react-router-dom";
import queryString from "query-string";
import useUser from "../interfaces/hooks/useUser";

const AuthPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loginError, setLoginError] = useState(false);
  const [isSignUp, setIsSignUp] = useState(false);
  const navigate = useNavigate();

  const { fetchUser } = useUser();
  const clientId = "Ov23liEDnGP9988VvztQ";

  useEffect(() => {
    const { code } = queryString.parse(window.location.search);
    const authService = new Auth();
    const authentcaGithubUser = async (code) => {
      if (code) {
        const request = {
          code: code,
          clientId: clientId,
        };
        try {
          const response = await authService.sendGithubCode(request);
          if (response) {
            localStorage.setItem("token", response["token"]);
            await fetchUser();
            navigate("/home", { userInformation: { response } });
          }
        } catch (e) {
          console.log(e);
        }
      }
    };
    authentcaGithubUser(code);
  }, []);

  const handleSubmitForm = async (event) => {
    event.preventDefault();
    const authService = new Auth();
    if (isSignUp) {
      try {
        const response = await authService.signUpUser(username, password);
        if (response.status === 200) {
          const responseBody = await response.json();
          localStorage.setItem("token", responseBody["token"]);
          await fetchUser();
          navigate("/home");
        }
      } catch (e) {}
      return;
    }
    try {
      const response = await authService.getUserInformation(username, password);
      if (response) {
        localStorage.setItem("token", response["token"]);
        await fetchUser();
        navigate("/home");
      }
    } catch (e) {
      console.log("error no post" + e);
      setLoginError(true);
    }
  };

  return (
    <div className={styles.authBackgroundDiv}>
      <div className={styles.authLogo}>
        <img src={logo} alt="Logo" />
        <h1 style={{ color: "white", textAlign: "center" }}>
          {isSignUp ? "Bem Vindo" : "Bem vindo de volta"}!
        </h1>
      </div>
      <main className={styles.contentContainer}>
        <form action="" method="post" onSubmit={handleSubmitForm}>
          <div className={styles.authHeader}>
            <h1 style={{ width: "100%" }}>
              {isSignUp ? "Fazer Cadastro" : "Fazer Login"}
            </h1>
            <p>
              {isSignUp ? "Já possui um login" : "Não está cadastrdo"}?{" "}
              <span
                className={styles.signUpLink}
                onClick={() => setIsSignUp(!isSignUp)}
              >
                {isSignUp ? "login" : "cadastrar"}
              </span>
            </p>
          </div>

          <input
            type="text"
            placeholder="Digite seu usuário"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
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

          <button type="submit" className={styles.loginButton}>
            {isSignUp ? "Cadastrar" : "Login"}
          </button>

          <div className={styles.authDivider}>
            <div className={styles.divider}></div>
            <p style={{ padding: "10px" }}>ou</p>
            <div className={styles.divider}></div>
          </div>
          <button
            className={`button ${styles.github}`}
            onClick={(e) => {
              e.preventDefault();
              window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}`;
            }}
          >
            <div className={styles.githubContent}>
              Continue com Github{" "}
              <img
                style={{ height: "30px" }}
                src={githubLogo}
                alt="Github Logo"
              />
            </div>
          </button>
        </form>
      </main>
    </div>
  );
};

export default AuthPage;

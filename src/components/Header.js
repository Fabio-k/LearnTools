import { Link } from "react-router-dom";
import style from "../header.module.css";
import logo from "../logo.svg";
const Header = () => {
  return (
    <header className={style.headerStyle}>
      <Link to={"/home"}>
        <img src={logo} alt="logo image" className={style.logo} />
      </Link>
      <section className={style.links}>
        <Link to="/resumes" className={style.link}>
          Resumos
        </Link>
        <Link to="/flashcards" className={style.link}>
          Flashcards
        </Link>
      </section>
    </header>
  );
};

export default Header;

import Header from "./Header";
import style from "../header.module.css";
const Layout = ({ extraElement, children }) => {
  return (
    <>
      <Header />
      <div className={style.mainContainer}>
        {extraElement}
        <main className={style.contentContainer}>{children}</main>
      </div>
    </>
  );
};

export default Layout;

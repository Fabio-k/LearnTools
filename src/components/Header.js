import { useState } from "react";
import { GiTomato } from "react-icons/gi";
import { FaPlay, FaPause } from "react-icons/fa";
import { Nav, NavLink, Fade, Button } from "reactstrap";
import { Link } from "react-router-dom";
const Header = ({
  time,
  isRunning,
  handleLongPause,
  handleShortPause,
  handleStartPauseButtom,
}) => {
  const [fadeIn, setFadeIn] = useState(false);

  const toggle = () => setFadeIn(!fadeIn);

  return (
    <header style={{ display: "flex", flexDirection: "row", width: "100%" }}>
      <Nav style={{ display: "flex", flexDirection: "column", width: "100%" }}>
        <section style={{ display: "flex", height: "70%" }}>
          <h1
            style={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              fontSize: "50px",
            }}
          >
            StudyTool's
          </h1>
          <div style={{ display: "flex" }}>
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                position: "relative",
              }}
            >
              <div
                style={{
                  position: "relative",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
                onClick={toggle}
              >
                <GiTomato color="red" size="70px" />
                <p
                  style={{
                    color: "black",
                    position: "absolute",
                    top: "40%",
                    right: "25%",
                    margin: "0",
                    padding: "0",
                  }}
                >
                  {time}
                </p>
              </div>
              <Fade in={fadeIn} style={{ position: "absolute", top: "100%" }}>
                {fadeIn && (
                  <div
                    style={{
                      height: "100%",
                      display: "flex",
                      flexDirection: "row",
                      border: "1px solid black",
                      borderRadius: "10px",
                      padding: "5px",
                      justifyContent: "space-evenly",
                    }}
                  >
                    <button
                      style={{ background: "none", border: "none" }}
                      onClick={handleStartPauseButtom}
                    >
                      {isRunning ? <FaPause /> : <FaPlay />}
                    </button>
                    <select name="" id="">
                      <option value="5">5m</option>
                      <option value="15">15m</option>
                      <option value="25">25m</option>
                    </select>
                  </div>
                )}
              </Fade>
            </div>
          </div>
        </section>
        <section id="listParent" style={{ height: "30%" }}>
          <Button
            href="/ia"
            tag="a"
            color="primary"
            outline
            style={{ padding: "5px" }}
          >
            Feyman's assistant
          </Button>
          <Button
            href="/resumes"
            tag="a"
            color="primary"
            outline
            style={{ padding: "5px" }}
          >
            Resumes
          </Button>
          <Button
            href="/pomodoro"
            tag="a"
            color="primary"
            outline
            style={{ padding: "5px" }}
          >
            Pomodoro
          </Button>
          <Button href="/flashcards" tag="a" color="primary" outline>
            Flashcards
          </Button>
        </section>
      </Nav>
    </header>
  );
};

export default Header;

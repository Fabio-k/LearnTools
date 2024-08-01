import React, { useReducer, useState, useEffect } from "react";
import resumesService from "../app/services/resumeService";
import styles from "../resume.module.css";
import Header from "../components/Header";
import ResumeCard from "../components/ResumeCard";
import ContextMenu from "../components/ContextMenu/ContextMenu";
import ResumeArea from "../containers/resumeArea";

const handleResumeSummary = (description) => {
  if (description.length <= 100) return description;

  let trimmedDescription = description.substr(0, 100);
  const lastSpaceIndex = trimmedDescription.lastIndexOf(" ");

  if (lastSpaceIndex > 0) {
    trimmedDescription = trimmedDescription.substr(0, lastSpaceIndex);
  }

  return trimmedDescription + "...";
};

const resumeData = {
  resumeId: 0,
  resumes: [],
  error: undefined,
};

async function deleteResume(resumeId) {
  const resumeService = new resumesService();
  const response = await resumeService.deleteResume(resumeId);
  if (response.status == 200) {
    return true;
  }
  return false;
}

const Resumes = () => {
  const [isLoadingContent, setIsLoadingContent] = useState({});
  const [isFeymanChat, setIsFeymanChat] = useState(false);
  const [search, setSearch] = useState("");

  const [resumeDisplay, setResumeDisplay] = useState({
    id: 0,
    title: "",
    description: "",
  });

  const [contextMenu, setContextMenu] = useState({
    position: {
      x: 0,
      y: 0,
    },
    toggled: false,
    rightClickItem: null,
  });

  function handleContextMenu(e, rightClickItem) {
    e.preventDefault();
    let x = e.clientX;
    let y = e.clientY;

    setContextMenu({
      position: {
        x,
        y,
      },
      toggled: true,
      rightClickItem: rightClickItem,
    });
  }

  function handleButtonContextMenu(e, rightClickItem) {
    //TODO: detect if the item is near the bottom
    const rect = e.target.getBoundingClientRect();
    const elementX = rect.left + window.scrollX;
    const elementY = rect.top + window.scrollY;

    setContextMenu({
      position: {
        x: elementX + 40,
        y: elementY,
      },
      toggled: !contextMenu.toggled,
      rightClickItem: rightClickItem,
    });

    console.log(contextMenu);
  }

  function resetContextMenu() {
    setContextMenu({
      position: {
        x: 0,
        y: 0,
      },
      toggled: false,
      rightClickItem: null,
    });
  }

  function resetResumeDisplay() {
    setResumeDisplay({ id: 0, title: "", description: "" });
    setIsFeymanChat(false);
  }

  const reducer = (state, action) => {
    switch (action.type) {
      case "DELETE":
        const modifiedResumeDate = state.resumes.filter(
          (resume) => resumeDisplay.id !== resume.id
        );
        resetContextMenu();
        resetResumeDisplay();

        return {
          ...state,
          resumes: modifiedResumeDate,
        };

      case "UPDATE":
        let isFound = false;
        let updatedResumes = state.resumes.map((resume) => {
          if (resume.id == resumeDisplay.id) {
            isFound = true;
            return action.payload;
          }
          return resume;
        });
        if (isFound) return { ...state, resumes: updatedResumes };

        return {
          ...state,
          resumes: [...state.resumes, action.payload],
        };

      case "TITLE":
        return { ...state, resumeTitle: action.payload };

      case "DESCRIPTION":
        return { ...state, resumeDescription: action.payload };

      case "SET_RESUMES":
        return { ...state, resumes: action.payload };

      default:
        return state;
    }
  };

  const [resumes, dispatch] = useReducer(reducer, resumeData);

  useEffect(() => {
    console.log(resumes);
  }, [resumes]);

  async function handleResumeDelete() {
    if (await deleteResume(resumeDisplay.id)) dispatch({ type: "DELETE" });
  }

  const handleItemClick = (item) => {
    if (item.id == resumeDisplay.id && isFeymanChat == false) return;
    setResumeDisplay({
      id: item.id,
      title: item.title,
      description: item.description,
    });
    setIsFeymanChat(false);
  };

  useEffect(() => {
    const fetchResumes = async () => {
      setIsLoadingContent(true);
      const resumeService = new resumesService();
      const response = await resumeService.getResumes();
      setIsLoadingContent(false);

      dispatch({ type: "SET_RESUMES", payload: response });
      console.log(response);
    };
    fetchResumes();
  }, []);

  return (
    <>
      <Header />
      <div className={styles.mainContainer}>
        <aside className={styles.menu}>
          <div className={styles.resumeFilters}>
            <input
              type="text"
              name=""
              id=""
              placeholder="procurar por resumo"
              className={styles.searchBar}
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <section className={styles.filterSection}>
              <select name="" id="">
                <option value="all" defaultChecked>
                  All
                </option>
              </select>
              <button>não revisados</button>
              <button>filtros</button>
            </section>
          </div>

          <section className={styles.resumeSection}>
            <section className={styles.resumeActions}>
              <button
                onClick={() => {
                  resetResumeDisplay();
                }}
              >
                + novo resumo
              </button>
            </section>

            {resumes.resumes.length > 0 ? (
              <ul className={styles.resumeList}>
                {resumes.resumes
                  .filter((resume) => {
                    return search === ""
                      ? resume
                      : resume.title.toLowerCase().includes(search);
                  })
                  .map((resume) => {
                    const resumeSumary = handleResumeSummary(
                      resume.description
                    );
                    return (
                      <ResumeCard
                        key={resume.id}
                        resumeData={resume}
                        selectedResumeId={resumeDisplay.id}
                        resumeSummary={resumeSumary}
                        onItemClick={() => {
                          handleItemClick(resume);
                        }}
                        handleContextMenu={handleContextMenu}
                        handleButtonContextMenu={handleButtonContextMenu}
                      />
                    );
                  })}
              </ul>
            ) : (
              <p style={{ textAlign: "center" }}>
                nenhum resumo foi encontrado
              </p>
            )}
          </section>
          <ContextMenu
            rightClickItem={contextMenu.rightClickItem}
            isToggled={contextMenu.toggled}
            positionx={contextMenu.position.x}
            positiony={contextMenu.position.y}
            resetContextMenu={resetContextMenu}
            buttons={[
              {
                text: "delete",
                onClick: (e, rightClickItem) =>
                  handleResumeDelete(rightClickItem.id),
              },
              {
                text: "usar resumo com ia",
                onClick: (e) => {},
              },
            ]}
          />
        </aside>

        <main className={styles.textContainer}>
          <ResumeArea
            resumes={resumes}
            isFeymanChat={isFeymanChat}
            dispatch={dispatch}
            resumeDisplay={resumeDisplay}
            setResumeDisplay={setResumeDisplay}
            setIsFeymanChat={setIsFeymanChat}
          />
        </main>
      </div>
    </>
  );
};

export default Resumes;

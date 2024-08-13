import "./ContextMenu.css";
import { useRef, useEffect } from "react";

const ContextMenu = ({
  rightClickItem,
  positionx,
  positiony,
  isToggled,
  buttons,
  resetContextMenu,
}) => {
  const contextMenuRef = useRef(null);

  useEffect(() => {
    function handler(e) {
      if (e.target.classList.contains("contextMenuButton")) return;

      if (contextMenuRef.current) {
        if (!contextMenuRef.current.contains(e.target)) {
          resetContextMenu();
        }
      }
    }

    document.addEventListener("click", handler);

    return () => {
      document.removeEventListener("click", handler);
    };
  }, [isToggled, resetContextMenu]);
  return (
    <menu
      style={{
        top: positiony - 10 + "px",
        left: positionx + 2 + "px",
      }}
      className={`context-menu ${isToggled ? "active" : ""}`}
      ref={contextMenuRef}
    >
      {buttons.map((button, index) => {
        function handleClick(e) {
          e.stopPropagation();
          button.onClick(e, rightClickItem);
        }

        return (
          <button
            onClick={(e) => handleClick(e)}
            key={index}
            className="context-menu-button"
          >
            <span>{button.text}</span>
          </button>
        );
      })}
    </menu>
  );
};

export default ContextMenu;

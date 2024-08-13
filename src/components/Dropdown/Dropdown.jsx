import { useEffect, useRef, useState } from "react";
import DropdownIcon from "../../Assets/ui/DropdownIcon.svg";
import "./Dropdown.css";

const Dropdown = ({
  title,
  data,
  isDropdownOpen,
  setIsDropdownOpen,
  handleItemClick,
  renderItem,
  containerStyle,
}) => {
  const contextDropdownRef = useRef(null);
  useEffect(() => {
    function handler(e) {
      if (
        ["assistentMenu", "dropdownIcon", "dropdownText"].some((className) =>
          e.target.classList.contains(className)
        )
      )
        return;
      if (contextDropdownRef.current)
        if (!contextDropdownRef.current.contains(e.target)) {
          setIsDropdownOpen(false);
        }
    }

    window.addEventListener("click", handler);

    return () => {
      window.removeEventListener("click", handler);
    };
  }, []);

  return (
    <div className="assistentContainer">
      <div
        className="assistentMenu"
        onClick={() => {
          setIsDropdownOpen(!isDropdownOpen);
        }}
        style={containerStyle}
      >
        <p className="dropdownText">{title}</p>
        <img src={DropdownIcon} alt="" className="dropdownIcon" />
      </div>
      <div
        id="dropdown"
        className={isDropdownOpen ? "dropdownActive" : ""}
        ref={contextDropdownRef}
      >
        {data.map((item, index) => {
          return (
            <div
              className="assistentContent"
              onClick={(e) => handleItemClick(item)}
              key={index}
            >
              {renderItem(item)}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Dropdown;

import "./FloatingMenuButton.css";

const FloatingMenuButton = ({ title, description, img, onToggleHeading }) => {
  return (
    <button className="floatingMenuOption" onClick={onToggleHeading}>
      <img src={img} alt="heading image" className="floatingMenuOptionImage" />
      <div>
        <p className="floatingMenuOptionTitle">{title}</p>
        <p className="floatingMenuOptionContent">{description}</p>
      </div>
    </button>
  );
};

export default FloatingMenuButton;

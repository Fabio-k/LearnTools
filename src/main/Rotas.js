import { BrowserRouter, Routes, Route } from "react-router-dom";
import AiChat from "../views/AiChat";
import Resumes from "../views/Resumes";
import Home from "../views/Home";
import Flashcards from "../views/Flashcards";
import { LayoutProvider } from "../components/LayoutContext";

export default function Rotas() {
  return (
    <BrowserRouter>
      <LayoutProvider>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/ia" element={<AiChat />} />
          <Route path="/resumes" element={<Resumes />} />
          <Route path="/flashcards" element={<Flashcards />} />
        </Routes>
      </LayoutProvider>
    </BrowserRouter>
  );
}

import { BrowserRouter, Routes, Route } from "react-router-dom";
import AiChat from "../views/AiChat";
import Resumes from "../views/Resumes";
import Home from "../views/Home";
import Flashcards from "../views/Flashcards";

export default function Rotas() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/ia" element={<AiChat />} />
        <Route path="/resumes" element={<Resumes />} />
        <Route path="/flashcards" element={<Flashcards />} />
      </Routes>
    </BrowserRouter>
  );
}

import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import AiChat from "../views/AiChat";
import Resumes from "../views/Resumes";
import Home from "../views/Home";
import Flashcards from "../views/Flashcards";
import { LayoutProvider } from "../components/LayoutContext";
import AuthPage from "../views/AuthPage";
import Github from "../views/Github";
import { UserProvider } from "../interfaces/hooks/useUser";

function Layout() {
  return (
    <LayoutProvider>
      <Outlet />
    </LayoutProvider>
  );
}

export default function Rotas() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<UserProvider />}>
          <Route path="/login" element={<AuthPage />} />
          <Route path="/resumes" element={<Resumes />} />

          <Route element={<Layout />}>
            <Route path="/home" element={<Home />} />
            <Route path="/flashcards" element={<Flashcards />} />
            <Route path="/ia" element={<AiChat />} />

            <Route path="/github" element={<Github />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

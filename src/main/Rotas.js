import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import AiChat from "../views/AiChat";
import Resumes from "../views/Resumes";
import Home from "../views/Home";
import Flashcards from "../views/Flashcards";
import { LayoutProvider } from "../components/LayoutContext";
import AuthPage from "../views/AuthPage";
import PrivateRoute from "../components/PrivateRoute";
import Github from "../views/Github";

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
        <Route path="/login" element={<AuthPage />} />
        <Route element={<Layout />}>
          <Route path="/home" element={<PrivateRoute element={<Home />} />} />

          <Route path="/ia" element={<AiChat />} />
          <Route path="/resumes" element={<Resumes />} />
          <Route path="/flashcards" element={<Flashcards />} />
          <Route path="/github" element={<Github />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

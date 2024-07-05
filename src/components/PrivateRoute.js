import React from "react";
import { Route, Navigate } from "react-router-dom";

const verificarCredenciais = () => {
  const usuario = localStorage.getItem("user");
  const password = localStorage.getItem("password");

  return usuario && password;
};

const PrivateRoute = ({ element }) => {
  return verificarCredenciais() ? element : <Navigate to="/login" replace />;
};

export default PrivateRoute;

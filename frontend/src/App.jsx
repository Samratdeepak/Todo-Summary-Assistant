import { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import Dashboard from "./pages/Dashboard";

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
  
  return (
    <BrowserRouter>
      <div className="min-h-screen bg-gray-50">
        <Navbar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route
            path="/login"
            element={
              !isLoggedIn ? (
                <LoginPage setIsLoggedIn={setIsLoggedIn} />
              ) : (
                <Navigate to="/dashboard" />
              )
            }
          />
          <Route
            path="/register"
            element={
              !isLoggedIn ? <RegisterPage /> : <Navigate to="/dashboard" />
            }
          />
          <Route
            path="/forgot"
            element={
              !isLoggedIn ? <ForgotPasswordPage /> : <Navigate to="/dashboard" />
            }
          />
          <Route
            path="/dashboard"
            element={
              isLoggedIn ? <Dashboard /> : <Navigate to="/login" />
            }
          />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
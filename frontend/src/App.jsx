import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import NavBar from "./components/NavBar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Home from "./pages/Home";
import HomeDef from "./pages/HomeDef";
import Footer from "./components/Footer";
import Movies from "./pages/Movies";
import BookTickets from "./pages/BookTickets";
import AddMovie from "./pages/AddMovie";
import DeleteMovie from "./pages/DeleteMovie";

const App = () => {
  return (
    <Router>
      <NavBar />
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path= "/" element={<HomeDef />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/movies" element={<Movies />} />
        <Route path="/movies/:moviename/bookTickets" element={<BookTickets />} />
        <Route path="/bookTickets" element={<BookTickets />} />
        <Route path="/addMovie" element={<AddMovie />} />
        <Route path="/deleteMovie" element={<DeleteMovie />} />
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;

{
}
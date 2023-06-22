import React from "react";
import { Link, useLocation } from "react-router-dom";
import { Box, Typography } from "@mui/material";
import { moviesData } from "./MovieData";
import "./Footer.css";

const Footer = () => {

  const location = useLocation();

  const isLoginPage = location.pathname === "/login";
  const isRegisterPage = location.pathname === "/register";

  if(isLoginPage || isRegisterPage){
    return null;
  }


  const getMovieNames = (releasedOnly) => {
    const now = new Date();
    const movieLinks = moviesData
      .filter((movie) =>
        releasedOnly ? new Date(movie.releaseDate) <= now : new Date(movie.releaseDate) > now
      )
      .map((movie) => (
        <Link key={movie.id} to={`/movies/${movie.id}`} className="footer-link">
          {movie.title}
        </Link>
      ));

    return movieLinks.reduce((prev, curr, index) => [
      prev,
      index !== 0 && " | ",

      curr,
    ]);
  };

  const genres = [
    "Action",
    "Adventure",
    "Animation",
    "Anime",
    "Comedy",
    "Drama",
    "Horror",
    "Romance",
    "Sci-Fi",
  ];

  const getGenres = () => {
    return genres.map((genre, index) => (
      <React.Fragment key={index}>
        <Link to={`/movies/genre/${genre}`} className="footer-link">
          {genre}
        </Link>
        {index !== genres.length - 1 && " | "}
      </React.Fragment>
    ));
  };

  const showReleasedMovies = () => {
    // Filter and show only released movies
    return (
      <React.Fragment>
        <Typography variant="h6" sx={{color: "#bababa",}}>Movies Now Showing:</Typography>
        <Typography variant="body2">{getMovieNames(true)}</Typography>
      </React.Fragment>
    );
  };

  const showUpcomingMovies = () => {
    // Filter and show only upcoming movies
    return (
      <React.Fragment>
        <Typography variant="h6" sx={{mt: 2, color: "#bababa",}}>Movies Upcoming:</Typography>
        <Typography variant="body2">{getMovieNames(false)}</Typography>
      </React.Fragment>
    );
  };

  const showGenres = () => {
    // Filter and show only genres
    return (
      <React.Fragment>
        <Typography variant="h6" sx={{mt: 2, color: "#bababa",}}>Genres:</Typography>
        <Typography variant="body2">{getGenres(true)}</Typography>
      </React.Fragment>
    );
  };

  return (
    <Box
      component="footer"
      sx={{
        backgroundColor: "#1a1818",
        color: "grey",
        padding: "20px",
        textAlign: "left",
        
        bottom: 0,
      }}
    >
      {showReleasedMovies()}
      {showUpcomingMovies()}
      {showGenres()}
      <Box
        display="flex"
        alignItems="center"
        justifyContent="center"
        mt={2}
      >
        <img
          src="https://img.icons8.com/?size=512&id=81967&format=png"
          alt="BookMovies Icon"
          style={{ width: "40px", height: "40px", marginRight: "8px", marginTop: "24px" }}
        />
        <Typography variant="h5" component="span" color="white" sx={{mt: 4}}>
          BookMovies
        </Typography>
      </Box>
    </Box>
  );
};

export default Footer;
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Box, Grid, Typography } from "@mui/material";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";
// import { moviesData } from "../components/MovieData";

const Home = () => {
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [moviesData, setMoviesData] = useState([]);
  const successResponse = localStorage.getItem("successResponse");

  useEffect(() => {
    fetchMoviesData();
  }, []);

  const fetchMoviesData = async () => {
    try{
          const response = await axios.get("http://localhost:9090/api/v1.0/moviebooking/all", {
            headers: {
              Authorization: successResponse,
            },
          });
          setMoviesData(response.data);
          console.log(response.data);
      }catch(error){
            console.error("Error fetching movies: ", error);
          }
  };

  const handleMovieSelection = (movie) => {
    setSelectedMovie(movie);
  };

  return (
    <Box sx={{ p: 2, mt: 8, mb: 8,}}>
      <Carousel
        showThumbs={false}
        autoPlay={true}
        interval={5000}
        infiniteLoop={true}
        showStatus={false}
        showIndicators={true}
        showArrows={false}
      >
        {moviesData
        .filter((movie) => movie.moviename.charAt(0) === "T" || movie.moviename.charAt(0) === "F")
        .slice(0, 3).map((movie) => (
          <div key={movie.moviename} style={{ position: "relative" }}>
            <Link to={`/movies/${movie.id}`}>
              <img
                src={movie.posterURL}
                alt={movie.moviename}
                style={{
                  width: "100%",
                  height: "auto",
                  maxHeight: "500px",
                  objectFit: "cover",
                  borderRadius: "4px",
                  objectPosition: "bottom",
                }}
                onClick={() => handleMovieSelection(movie)}
              />
            </Link>
            <button
              style={{
                position: "absolute",
                bottom: "10px",
                right: "10px",
                transform: "translateX(-50%)",
                padding: "8px 16px",
                backgroundColor: "#cb0d0d",
                borderRadius: "4px",
                fontWeight: "bold",
                cursor: "pointer",
              }}
            >
              Trailer
            </button>
          </div>
        ))}
      </Carousel>

      <Typography variant="h4" gutterBottom mt={8} sx = {{color: "#9d9d9d"}}>
        Recommended Movies
      </Typography>
      <Grid container spacing={2} sx={{ mt: 6 }}>
        {moviesData
        .sort((a, b) => new Date(b.releaseDate) - new Date(a.releaseDate))
        .map((movie) => (
          <Grid item key={movie.moviename} xs={12} sm={6} md={4} lg={3}>
            <Link to={`/movies/${movie.moviename}/movieDetails`}>
              <img
                src={movie.posterURL}
                alt={movie.moviename}
                style={{ width: "100%", height: "auto" }}
                onClick={() => handleMovieSelection(movie)}
              />
            </Link>
            <Typography variant="subtitle1"  sx = {{color: "#9d9d9d"}}>{movie.moviename}</Typography>
            <Typography variant="caption" sx={{ color: "#777" }}>
              {movie.genre}
            </Typography>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default Home;
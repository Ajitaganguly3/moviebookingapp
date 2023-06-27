import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Box, Grid, Typography } from "@mui/material";
import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import { moviesData } from "../components/MovieData";

const Home = () => {
  const [selectedMovie, setSelectedMovie] = useState(null);

  const handleMovieSelection = (movie) => {
    setSelectedMovie(movie);
  };

  return (
    <Box sx={{ p: 2, mt: 8, mb: 8 }}>
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
        .filter((movie) => movie.title.charAt(0) === "T")
        .slice(0, 3).map((movie) => (
          <div key={movie.id} style={{ position: "relative" }}>
            <Link to={`/movies/${movie.id}`}>
              <img
                src={movie.posterUrl}
                alt={movie.title}
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

      <Typography variant="h4" gutterBottom mt={8}>
        Recommended Movies
      </Typography>
      <Grid container spacing={2} sx={{ mt: 4 }}>
        {moviesData.map((movie) => (
          <Grid item key={movie.id} xs={12} sm={6} md={4} lg={3}>
            <Link to={`/movies/${movie.id}/bookTickets`}>
              <img
                src={movie.posterUrl}
                alt={movie.title}
                style={{ width: "100%", height: "auto" }}
                onClick={() => handleMovieSelection(movie)}
              />
            </Link>
            <Typography variant="subtitle1">{movie.title}</Typography>
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
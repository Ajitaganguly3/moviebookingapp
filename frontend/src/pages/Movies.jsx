import React, { useState, useEffect } from "react";
import { Box, Grid, Typography, Chip } from "@mui/material";
// import { moviesData } from "../components/MovieData";
import { Link } from "react-router-dom";

const Movies = () => {
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [isFilterOpen, setIsFilterOpen] = useState(false);
  const [moviesData, setMoviesData] = useState([]);
  const [showUpcomingMovies, setShowUpcomingMovies] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);

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

  const handleGenreClick = (genre) => {
    if (selectedGenres.includes(genre)) {
      setSelectedGenres(selectedGenres.filter((g) => g !== genre));
    } else {
      setSelectedGenres([...selectedGenres, genre]);
    }
  };

  const handleFilterToggle = () => {
    setIsFilterOpen(!isFilterOpen);
  };

  const handleUpcomingMoviesClick = () => {
    setShowUpcomingMovies(true);
  };

  const handleNowShowingClick = () => {
    setShowUpcomingMovies(false);
  };

  const filteredMovies =
    selectedGenres.length === 0
      ? moviesData
      : moviesData.filter((movie) => {
          const movieGenres = movie.genre.split("/");
          return selectedGenres.some((genre) => movieGenres.includes(genre));
        });

  const allGenres = moviesData.reduce((genres, movie) => {
    const movieGenres = movie.genre.split("/");
    return [...genres, ...movieGenres];
  }, []);

  const uniqueGenres = [...new Set(allGenres)];

  const upcomingMovies = moviesData.filter((movie) => {
    const releaseDate = new Date(movie.releaseDate);
    const currentDate = new Date();
    return releaseDate > currentDate;
  });

  return (
    <Box sx={{ p: 2, mt: 8, mb: 8 }}>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={3}>
          <Box
            sx={{
              backgroundColor: "white",
              padding: 2,
              borderRadius: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "flex-start",
            }}
          >
            <Typography
              variant="h5"
              sx={{
                color: "black",
                mb: 2,
                cursor: "pointer",
                position: "inherit",
              }}
              onClick={handleFilterToggle}
            >
              Filters
            </Typography>
            {isFilterOpen && (
              <>
                <Typography variant="subtitle1" sx={{ color: "black", mb: 2 }}>
                  Genre:
                </Typography>
                <Box
                  sx={{
                    display: "flex",
                    flexWrap: "wrap",
                    alignItems: "flex-start",
                  }}
                >
                  {uniqueGenres.map((genre, index) => {
                    const isSelected = selectedGenres.includes(genre);
                    return (
                      <Chip
                        key={index}
                        label={genre}
                        onClick={() => handleGenreClick(genre)}
                        sx={{
                          m: 1,
                          backgroundColor: isSelected ? "red" : "white",
                          color: isSelected ? "white" : "red",
                          border: `1px solid red`,
                          "&:hover": {
                            backgroundColor: isSelected ? "red" : "black",
                            color: isSelected ? "white" : "red",
                          },
                        }}
                      />
                    );
                  })}
                </Box>
              </>
            )}
          </Box>
        </Grid>
        <Grid item xs={12} sm={9}>
          <Typography variant="h4" gutterBottom sx = {{color: "#9d9d9d"}}>
            {showUpcomingMovies ? "Upcoming Movies" : "Movies"}
          </Typography>
          {!showUpcomingMovies && (
            <Box sx={{ display: "flex", alignItems: "center", mb: 2 }}>
              <Box
                sx={{
                  display: "flex",
                  alignItems: "center",
                  cursor: "pointer",
                  backgroundColor: "white",
                  borderRadius: 4,
                  p: 2,
                }}
                onClick={handleUpcomingMoviesClick}
              >
                <Typography
                  variant="h6"
                  sx={{
                    width: "890px",
                    borderRadius: "20px",
                    color: "black",
                    mr: 1,
                  }}
                >
                  {!showUpcomingMovies ? "Upcoming Movies" : "Now Showing"}
                </Typography>
              </Box>
            </Box>
          )}
          <Grid container spacing={2}>
            {showUpcomingMovies
              ? upcomingMovies.map((movie) => (
                  <Grid item key={movie.id} xs={12} sm={6} md={4} lg={3}>
                    <Link to={`/movies/${movie.moviename}/bookTickets`}>
                    <img
                      src={movie.posterURL}
                      alt={movie.moviename}
                      onClick={() => handleMovieSelection(movie)}
                      style={{ width: "100%", height: "auto" }}
                    />
                    </Link>
                    <Typography variant="subtitle1">{movie.moviename}</Typography>
                    <Typography variant="caption">
                      {movie.releaseDate}
                    </Typography>
                    <br />
                    <Typography variant="caption">{movie.genre}</Typography>
                  </Grid>
                ))
              : filteredMovies.map((movie) => (
                  <Grid item key={movie.id} xs={12} sm={6} md={4} lg={3}>
                  <Link to={`/movies/${movie.moviename}/bookTickets`}>
                    <img
                      src={movie.posterURL}
                      alt={movie.moviename}
                      style={{ width: "100%", height: "auto" }}
                      onClick={() => handleMovieSelection(movie)}
                    />
                    </Link>
                    <Typography variant="subtitle1" sx = {{color: "#9d9d9d"}}>{movie.moviename}</Typography>
                    <Typography variant="caption" sx={{ color: "#777" }}>{movie.genre}</Typography>
                  </Grid>
                ))}
          </Grid>
          {showUpcomingMovies && (
            <Box sx={{ display: "flex", alignItems: "center", mt: 2 }}>
              <Typography
                variant="h6"
                sx={{ cursor: "pointer", textDecoration: "underline" }}
                onClick={handleNowShowingClick}
              >
                View Now Showing
              </Typography>
            </Box>
          )}
        </Grid>
      </Grid>
    </Box>
  );
};

export default Movies;
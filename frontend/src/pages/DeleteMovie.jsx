import React, { useState } from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";

// Import the movie data from MovieData.jsx
import { moviesData } from "../components/MovieData";

const theme = createTheme({
  palette: {
    primary: {
      main: "#cb0d0d",
    },
    background: {
      default: "#242424",
    },
  },
});

const DeleteMoviePage = () => {
  const [selectedMovie, setSelectedMovie] = useState("");
  const [movies, setMovies] = useState(moviesData);

  const handleMovieChange = (event) => {
    setSelectedMovie(event.target.value);
  };

  const handleDeleteMovie = () => {
    const updatedMovies = movies.filter((movie) => movie.id !== selectedMovie);

    setMovies(updatedMovies);

    setSelectedMovie("");
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <Typography component="h1" variant="h5" sx={{ color: "white", mt: 5 }}>
          Delete Movie
        </Typography>
        <FormControl fullWidth sx={{ mt: 3, color: "white" }}>
          <Select
            value={selectedMovie}
            onChange={handleMovieChange}
            displayEmpty
            inputProps={{ "aria-label": "Select Movie" }}
            sx={{
              color: "white",
              "& .MuiSelect-root": { color: "#ffffff" },
              "& .MuiInputBase-input": {
                "&:focus": { borderRadius: 4 },
              },
              "& .MuiOutlinedInput-root": {
                "& fieldset": {
                  borderColor: "#cb0d0d",
                },
                "&:hover fieldset": {
                  borderColor: "#ffffff",
                },
              },
            }}
          >
            <MenuItem value="" disabled>
              Select Movie
            </MenuItem>
            {movies.map((movie) => (
              <MenuItem key={movie.id} value={movie.id}>
                {movie.title}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button
          onClick={handleDeleteMovie}
          fullWidth
          color="inherit"
          sx={{ mt: 3, mb: 2, color: "white", bgcolor: "#cb0d0d" }}
          disabled={!selectedMovie}
        >
          Delete Movie
        </Button>
      </Container>
    </ThemeProvider>
  );
};

export default DeleteMoviePage;
import React, { useState, useEffect } from "react";
import axios from "axios";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";

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
  const [movies, setMovies] = useState([]);
  const [moviename, setMoviename] = useState("");
  const [message, setMessage] = useState("");
  const successResponse = localStorage.getItem("successResponse");

  useEffect(() => {
    fetchMovies();
  }, []);

  const fetchMovies = async () => {
    try{
      const response = await axios.get("http://localhost:9090/api/v1.0/moviebooking/all", {
        headers: {
          Authorization: successResponse,
        },
      });
      setMovies(response.data);
      console.log(response.data);
    } catch(error){
      console.error("Error fetching movies: ", error);
    }
  };

  const handleMovieChange = (event) => {
    setMoviename(event.target.value);
    setSelectedMovie(event.target.value);
  };

  const handleDeleteMovie = async () => {
    console.log(successResponse);

    await axios
      .delete(
        `http://localhost:9090/api/v1.0/moviebooking/${moviename}/delete`,
        {
          headers: {
            Authorization: successResponse,
          },
        }
      )
      .then((response) => {
        setMessage(response.data.message);
        console.log(message);
      })
  
      .catch((error) => {
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log("Error", error.message);
        }
        console.error(error);
      });


  };

  

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <Typography component="h1" variant="h5" sx={{ color: "white", mt: 8 }}>
          Delete Movie
        </Typography>
        <Grid container spacing={2}>
            <Grid item xs={12}>
            <FormControl fullWidth sx={{ mt: 3 }}>
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
                "& .Mui-focused": {
                  borderColor: "#cb0d0d",
                },
              },
            }}
          >
            <MenuItem value="" disabled>
              Select Movie
            </MenuItem>
            {movies.map((movie) => (
              <MenuItem key={movie.moviename} value={movie.moviename}>
              {movie.moviename}
            </MenuItem>
            ))}
              
          </Select>
        </FormControl>
            </Grid>
          </Grid>

        <Button
          onClick={handleDeleteMovie}
          fullWidth
          color="inherit"
          sx={{ mt: 3, mb: 2, color: "white", bgcolor: "#cb0d0d" }}
          disabled={!moviename}
        >
          Delete Movie
        </Button>
        {message && (
          <Typography variant="body1" color="error">
            {" "}
            {message}
          </Typography>
        )}
      </Container>
    </ThemeProvider>
  );
};

export default DeleteMoviePage;
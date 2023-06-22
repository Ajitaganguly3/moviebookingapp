import React, { useState } from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import { Box } from "@mui/material";

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

const AddMovieForm = ({ onSubmit }) => {
  const [title, setTitle] = useState("");
  const [genre, setGenre] = useState("");
  const [releaseDate, setReleaseDate] = useState("");
  const [theatreName, setTheatreName] = useState("");
  const [price, setPrice] = useState("");
  const [about, setAbout] = useState("");
  const [posterUrl, setPosterUrl] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    const id = Date.now();

    const newMovie = {
      id,
      title,
      genre,
      releaseDate,
      theatreName,
      price,
      about,
      posterUrl,
    };

    onSubmit(newMovie);

    setTitle("");
    setGenre("");
    setReleaseDate("");
    setTheatreName("");
    setPrice("");
    setAbout("");
    setPosterUrl("");
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <Typography component="h1" variant="h5" sx={{ color: "white", mt: 5 }}>
          Add Movie
        </Typography>
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="title"
                label="Title"
                name="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="genre"
                label="Genre"
                name="genre"
                value={genre}
                onChange={(e) => setGenre(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="releaseDate"
                label="Release Date"
                name="releaseDate"
                value={releaseDate}
                onChange={(e) => setReleaseDate(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="theatreName"
                label="Theatre Name"
                name="theatreName"
                value={theatreName}
                onChange={(e) => setTheatreName(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="price"
                label="Price"
                name="price"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="about"
                label="About"
                name="about"
                value={about}
                onChange={(e) => setAbout(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="posterUrl"
                label="Poster URL"
                name="posterUrl"
                value={posterUrl}
                onChange={(e) => setPosterUrl(e.target.value)}
                InputLabelProps={{ style: { color: "#ffffff" } }}
                InputProps={{
                  style: { color: "#ffffff" },
                  classes: {
                    root: {
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#cb0d0d",
                        },
                        "&:hover fieldset": {
                          borderColor: "#ffffff",
                        },
                      },
                    },
                  },
                }}
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Add Movie
          </Button>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default AddMovieForm;
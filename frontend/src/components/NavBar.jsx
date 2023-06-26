import React, { useState, useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { MenuItem, Menu, TextField, Autocomplete } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { moviesData } from "./MovieData";

export default function NavBar() {
  const [navOpen, setNavOpen] = useState(null);
  const [searchOptions, setSearchOptions] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState("");

  const toggleNav = (event) => {
    setNavOpen(event.currentTarget);
  };

  const closeNav = () => {
    setNavOpen(null);
  };

  useEffect(() => {
    const role = localStorage.getItem("role");
    setUserRole(role);
  }, []);

  const handleSearch = (event, value) => {
    if (value) {
      const results = moviesData.filter((movie) =>
        movie.title.toLowerCase().includes(value.toLowerCase())
      );

      setSearchOptions(results.map((movie) => movie.title));
      setSearchResults(results);
      console.log("Search results:", results);
    } else {
      setSearchOptions([]);
      setSearchResults([]);
    }

    // Redirect to BookTickets page when a movie is selected
    if (event.key === "Enter" && searchOptions.includes(value)) {
      const selectedMovie = searchResults.find(
        (movie) => movie.title.toLowerCase() === value.toLowerCase()
      );
      const movieId = selectedMovie.id;
      navigate(`/movies/${movieId}/bookTickets`);
    }
  };

  // const menuItems = [
  //   {
  //     role: "Admin",
  //     items: [
  //       {
  //         text: "Logout",
  //         to: "/register",
  //       },
  //       {
  //         text: "Add Movie",
  //         to: "/addMovie",
  //       },
  //       {
  //         text: "Delete Movie",
  //         to: "/deleteMovie",
  //       },
  //     ],
  //   },
  //   {
  //     role: "User",
  //     items: [
  //       {
  //         text: "Profile",
  //         to: "/",
  //       },
  //       {
  //         text: "Movies",
  //         to: "/movies",
  //       },
  //     ],
  //   },
  // ];

  // const getMenuItems = (role) => {
  //   const menuItemConfig = menuItems.find((item) => item.role === role);
  //   if(menuItemConfig){
  //     return menuItemConfig.items.map((item, index) => (
  //       <MenuItem 
  //         key={index}
  //         component={Link}
  //         to={item.to}
  //         sx={{
  //           width: "80px",
  //           bgcolor: "white",
  //           ":hover": {color: "#cb0d0d"},
  //         }}
  //       >
  //         {item.text}
  //       </MenuItem>
  //     ));
  //   }
  //   return null;
  // };

  const menuItems = {
    "Admin": [
      {
        text: "Logout",
        to: "/register",
      },
      {
        text: "Movies",
        to: "/movies",
      },
      {
        text: "Add Movie",
        to: "/addMovie",
      },
      {
        text: "Delete Movie",
        to: "/deleteMovie",
      },
      {
        text: "Book Tickets",
        to: "/bookTickets",
      },
    ],
    "User": [
      {
        text: "Logout",
        to: "/register",
      },
      {
        text: "Profile",
        to: "/",
      },
      {
        text: "Movies",
        to: "/movies",
      },
      {
        text: "Book Tickets",
        to: "/bookTickets",
      },
    ],
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar
        style={{
          backgroundColor: "#353333",
          color: "white",
          boxShadow: "0px 0px 0px 0px",
        }}
      >
        <Toolbar sx={{ justifyContent: "space-between" }}>
          <Box sx={{ display: "flex", alignItems: "center" }}>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
              onClick={toggleNav}
            >
              <MenuIcon />
            </IconButton>
            <Link to="/" style={{ textDecoration: "none", color: "inherit" }}>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                BookMovies
              </Typography>
            </Link>
          </Box>
          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              position: "relative",
            }}
          >
            <Autocomplete
              freeSolo
              options={searchOptions}
              renderInput={(params) => (
                <TextField
                  {...params}
                  type="text"
                  variant="outlined"
                  placeholder="Search for Movies"
                  size="small"
                  sx={{
                    width: "800px",
                    borderRadius: "20px",
                    bgcolor: "white",
                    "& .MuiOutlinedInput-root": {
                      "&:hover fieldset": {
                        borderColor: "red",
                      },
                    },
                    "& .MuiOutlinedInput-notchedOutline": {
                      border: "none",
                    },
                    "&:hover .MuiOutlinedInput-notchedOutline": {
                      borderColor: "red",
                    },
                  }}
                />
              )}
              onInputChange={handleSearch}
            />
            <div
              style={{
                position: "absolute",
                top: 0,
                right: 0,
                bottom: 0,
                left: 0,
                pointerEvents: "none",
                borderRadius: "20px",
                border: "1px solid transparent",
                transition: "border-color 0.2s",
              }}
            />
          </Box>
          <Button
            component={Link}
            to="/register"
            color="inherit"
            sx={{
              color: "white",
              marginRight: "50px",
              bgcolor: "#cb0d0d",
              ":hover": { color: "#cb0d0d" },
            }}
          >
            Register
          </Button>
          <Menu
            anchorEl={navOpen}
            open={Boolean(navOpen)}
            onClose={closeNav}
            onClick={closeNav}
          >
            {menuItems[userRole]?.map((item, index) => (
              <MenuItem 
                key={index}
                component={Link}
                to={item.to}
                sx={{
                  width: "120px",
                  bgcolor: "white",
                  ":hover": {color: "#cb0d0d"},
                }}
              >
              {item.text}
              </MenuItem>
            ))}
          </Menu>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

import * as React from "react";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";

function CustomizedTextField(props) {
  return (
    <TextField
      {...props}
      sx={{
        "& .MuiInputBase-root": {
          color: "#ffffff",
        },
        "& .MuiInputLabel-root": {
          color: "#ffffff",
        },
        "& .MuiOutlinedInput-root": {
          "& fieldset": {
            borderColor: "#cb0d0d",
          },
          "&:hover fieldset": {
            borderColor: "#ffffff",
          },
        },
        "& .MuiInputLabel-shrink": {
          color: "#cb0d0d",
        },
      }}
    />
  );
}

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



// const BASE_URL = "http://localhost:9090/api/v1.0/moviebooking/"

export default function SignUp() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [contactNumber, setContactNumber] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    // const formData = new FormData(event.currentTarget);

    const payload = {
      firstName : firstName,
      lastName : lastName,
      username : username,
      email : email,
      contactNumber : contactNumber,
      password : password,
      confirmPassword : confirmPassword,
      roles : [{role: role}],
    };

    axios.post('http://localhost:9090/api/v1.0/moviebooking/register', payload)
    .then((response) => {
       console.log(response.data);
       setUsername('');
       setFirstName('');
       setLastName('');
       setEmail('');
       setContactNumber('');
       setPassword('');
       setConfirmPassword('');
       setRole('');
      //  alert("User registered successfully");

       navigate("/login");
    })
    .catch((error) =>{
      if(error.response){
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      } else if(error.request){
        console.log(error.request);
      } else{
        console.log("Error", error.message);
      }
      console.error(error);
      alert("username already exist or password validation failed");
    });
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5" sx={{ color: "white" }}>
            Register
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <CustomizedTextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  value={ firstName}
                  onChange={(event) => {
                    setFirstName(event.target.value)
                  }}
                  autoFocus
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <CustomizedTextField
                  required
                  fullWidth
                  id="lastName"
                  label="Last Name"
                  name="lastName"
                  value={lastName}
                  onChange={(event) => {
                    setLastName(event.target.value)
                  }}
                  autoComplete="family-name"
                />
              </Grid>

              <Grid item xs={12}>
                <CustomizedTextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  value={email}
                  onChange={(event) => {
                    setEmail(event.target.value)
                  }}
                  autoComplete="email"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomizedTextField
                  required
                  fullWidth
                  id="username"
                  label="Username"
                  value={username}
                  onChange={(event) => {
                    setUsername(event.target.value)
                  }}
                  name="username"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomizedTextField
                  required
                  fullWidth
                  id="contactNumber"
                  label="Contact Number"
                  value={contactNumber}
                  onChange={(event) => {
                    setContactNumber(event.target.value)
                  }}
                  name="contactNumber"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomizedTextField
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  value={password}
                  onChange={(event) => {
                    setPassword(event.target.value)
                  }}
                  autoComplete="new-password"
                />
              </Grid>
              <Grid item xs={12}>
                <CustomizedTextField
                  required
                  fullWidth
                  name="confirmPassword"
                  label="Confirm Password"
                  type="password"
                  id="confirmPassword"
                  value={confirmPassword}
                  onChange={(event) => {
                    setConfirmPassword(event.target.value)
                  }}
                  autoComplete="new-password"
                />
              </Grid>
              <Grid item xs={12}>
                <FormControl fullWidth sx={{
                  "& .MuiInputBase-root": {
                    color: "#ffffff",
                  },
                  "& .MuiInputLabel-root": {
                    color: "#ffffff",
                  },
                  "& .MuiOutlinedInput-root": {
                    "& fieldset": {
                      borderColor: "#cb0d0d",
                    },
                    "&:hover fieldset": {
                      borderColor: "#ffffff",
                    },
                  },
                  "& .MuiInputLabel-shrink": {
                    color: "#cb0d0d",
                  },
                }}>
                <InputLabel id="role-label" sx={{ color: "black" }}>Role</InputLabel>
                <Select labelId="role-label" id="role" value={role} name="role" label="Role" required sx={{ color: "black" }} onChange={(event) => {
                    setRole(event.target.value)
                  }}>
                  <MenuItem value="User" sx={{ color: "black" }}>User</MenuItem>
                  <MenuItem value="Admin" sx={{ color: "black" }}>Admin</MenuItem>
                </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel
                  control={
                    <Checkbox
                      value="allowExtraEmails"
                      color="primary"
                      sx={{ color: "white" }}
                    />
                  }
                  label="I want to receive inspiration, marketing promotions and updates via email."
                  sx={{ color: "white" }}
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Register
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link
                  href="/login"
                  variant="body2"
                  sx={{ color: "white", ":hover": { color: "#cb0d0d" } }}
                >
                  Already have an account? Login
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Typography
          variant="body2"
          color="text.secondary"
          align="center"
          sx={{ mt: 5, color: "white" }}
        >
          {"© "}
          <Link
            color="inherit"
            href="/"
            sx={{ mt: 5, color: "white", ":hover": { color: "#cb0d0d" } }}
          >
            Movie Booking App
          </Link>{" "}
          {new Date().getFullYear()}
          {"."}
        </Typography>
      </Container>
    </ThemeProvider>
  );
}

import * as React from "react";
import {useState} from "react";
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

export default function SignIn() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    const payload = {
      
      username : username,
      password : password,
      
    };

    axios.post('http://localhost:9090/api/v1.0/moviebooking/login', payload)
    .then((response) => {
       console.log(response.data);
       setUsername('');
       setPassword('');
       alert("Login Successful");
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
      alert("Wrong credentials");
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
            Login
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <CustomizedTextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              value={username}
              onChange={(event) => {
                setUsername(event.target.value)
              }}
              autoFocus
            />
            <CustomizedTextField
              margin="normal"
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
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me" sx={{  color: "white" }}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2, bgcolor: "primary.main" }}
            >
              Login
            </Button>
            <Grid container>
              <Grid item xs>
                <Link
                  href="#"
                  variant="body2"
                  sx={{ color: "white", ":hover": { color: "#cb0d0d" } }}
                >
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link
                  href="/register"
                  variant="body2"
                  sx={{ color: "white", ":hover": { color: "#cb0d0d" } }}
                >
                  {"Don't have an account? Register"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
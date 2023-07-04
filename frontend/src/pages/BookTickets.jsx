import axios from "axios";
import { useState, useEffect } from "react";
import {
  Grid,
  Typography,
  TextField,
  Paper,
  Box,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import SeatIcon from "@mui/icons-material/EventSeat";
import SeatSelectedIcon from "@mui/icons-material/EventSeatOutlined";
import { styled } from "@mui/system";
import { useParams } from "react-router-dom";
import { selectClasses } from "@mui/base";
// import { moviesData } from "../components/MovieData";

// const SeatBox = styled(Box)(({ theme }) => ({
//   width: "40px",
//   height: "40px",
//   display: "flex",
//   justifyContent: "center",
//   alignItems: "center",
//   border: "1px solid black",
//   cursor: "pointer",
//   transition: "background-color 0.3s ease",
// }));

// const SelectedSeatBox = styled(Box)(({ theme }) => ({
//   backgroundColor: "#8e0909",
// }));

// const ColumnSeatBox = styled(Box)(({ theme }) => ({
//   width: "50px",
//   height: "50px",
//   display: "flex",
//   justifyContent: "center",
//   alignItems: "center",
//   border: "1px solid black",
//   cursor: "pointer",
//   transition: "background-color 0.3s ease",
// }));

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

function BookTickets() {
  
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [numSeats, setNumSeats] = useState(0);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [success, setSuccess] = useState(false);
  const [bookedSeatNumbers, setBookedSeatNumbers] = useState([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [bookingDetails, setBookingDetails] = useState({});
  const successResponse = localStorage.getItem("successResponse");
  const { moviename } = useParams();

  useEffect(() => {
    axios
      .get(
        `http://localhost:9090/api/v1.0/moviebooking/movies/search/${encodeURIComponent(
          moviename
        )}`,
        {
          headers: {
            Authorization: successResponse,
          },
        }
      )
      .then((response) => {
        setSelectedMovie(response.data);
        console.log(response.data);
        console.log("Selected Movie: ", selectedMovie[0]);
        console.log(selectedMovie[0].moviename);
      })
      .catch((error) => {
        console.error("Error fetching movie data", error);
      });
  }, [moviename]);

  const handleNumSeatsChange = (event) => {
    setNumSeats(event.target.value);
  };

  const handleSeatClick = (row, column) => {
    setSelectedSeats((prevSelectedSeats) => {
      const seat = { row, column };
      if (
        prevSelectedSeats.some(
          (selectedSeat) =>
            selectedSeat.row === row && selectedSeat.column === column
        )
      ) {
        return prevSelectedSeats.filter(
          (selectedSeat) =>
            selectedSeat.row !== row || selectedSeat.column !== column
        );
      } else {
        return [...prevSelectedSeats, seat];
      }
    });
  };
  const isSeatSelected = (row, column) => {
    const seat = `${row}${column}`;
    return selectedSeats.some(
      (selectedSeat) =>
        selectedSeat.row === row && selectedSeat.column === column
    );
  };

  const isSeatBooked = (row, column) => {
    const seatNumber = `${row}${column}`;
    return bookedSeatNumbers.includes(seatNumber);
  };

  const handleOpenDialog = () => {
    setIsDialogOpen(true);
  };

  const handlePayNowClick = (e) => {
    e.preventDefault();
    const seatNumberArray = selectedSeats.map((seat) => seat.row + seat.column);
    const ticketDto = {
      moviename: selectedMovie[0].moviename,
      theatrename: selectedMovie[0].theatrename,
      noOfTickets: numSeats,
      seatnumber: seatNumberArray,
    };

    const bookingSummary = {
      moviename: selectedMovie[0].moviename,
      seatnumbers: ticketDto.seatnumber.join(", "),
      price: selectedSeats.length * selectedMovie[0].price,
    };

    axios
      .post(
        `http://localhost:9090/api/v1.0/moviebooking/${encodeURIComponent(
          selectedMovie[0].moviename
        )}/book`,
        ticketDto,
        {
          headers: {
            Authorization: successResponse,
          },
        }
      )
      .then((response) => {
        setSuccess(true);
        handleOpenDialog();
        setBookingDetails(bookingSummary);

        setBookedSeatNumbers((prevBookedSeatNumbers) => [
          ...prevBookedSeatNumbers,
          ...seatNumberArray,
        ]);
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
      });
  };

  const handleCloseDialog = () => {
    setIsDialogOpen(false);
  };

  const renderSeats = () => {
    const rows = ["A", "B", "C", "D", "E", "F", "G"];
    const columns = [
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
    ];

    return (
      <Grid container spacing={3} justifyContent="center">
        <Grid item xs={12}>
          <Typography variant="h6" align="center">
            {selectedMovie[0].theatrename}
          </Typography>
          <CustomizedTextField
            margin="normal"
            required
            width="100px"
            id="numSeats"
            label="Select Seats"
            name="numSeats"
            value={numSeats}
            onChange={handleNumSeatsChange}
            autoFocus
          />
        </Grid>

        {rows.map((row) => (
          <Grid item xs={12} key={row}>
            <Grid container spacing={3} justifyContent="center">
              <Grid item>
                <Box variant="outlined" className="row-seat">
                  {row}
                </Box>
              </Grid>
              {columns.map((column) => (
                <Grid item key={`${row}${column}`}>
                  <Box
                    component={Paper}
                    variant="outlined"
                    square
                    className={`seat ${
                      isSeatSelected(row, column) ? "selected" : ""
                    } ${isSeatBooked(row, column) ? "booked" : ""}`}
                    onClick={() => handleSeatClick(row, column)}
                    sx={{
                      width: "25px",
                      height: "25px",
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                      border: "1px solid black",
                      cursor: "pointer",
                      transition: "background-color 0.3s ease",
                      fontWeight: "bold",
                      boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.2)",
                      borderRadius: "20%",
                      backgroundColor: isSeatSelected(row, column)
                        ? "red"
                        : isSeatBooked(row, column)
                        ? "#A9A9A9"
                        : "white",
                    }}
                  >
                    <Typography
                      variant="body2"
                      align="center"
                      sx={{
                        width: "100%",
                        height: "100%",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                      }}
                    >
                      {column}
                    </Typography>
                  </Box>
                </Grid>
              ))}
            </Grid>
          </Grid>
        ))}
      </Grid>
    );
  };

  const seatNumber = selectedSeats.map((seat) => seat.row + seat.column);
  const seatNumberString = seatNumber.join(", ");

  return (
    <div>
      <Grid
        container
        justifyContent="center"
        alignItems="center"
        style={{ minHeight: "100vh" }}
      >
        {selectedMovie && (
          <Grid item xs={12} textAlign="center">
            <Typography variant="h4">{selectedMovie[0].moviename}</Typography>
            <Grid
              container
              justifyContent="center"
              style={{ marginTop: "50px" }}
            >
              {selectedMovie && (
                <form>
                  <Grid
                    container
                    spacing={2}
                    justifyContent="center"
                    alignItems="center"
                  >
                    <Grid item xs={12} textAlign="center">
                      <Typography variant="h4">
                        {selectedMovie[0].moviename}
                      </Typography>
                    </Grid>

                    <Grid item xs={20}>
                      {renderSeats()}
                    </Grid>
                    <Grid item xs={12} textAlign="center">
                      <Typography variant="h6">
                        Selected Seats: {seatNumberString}
                      </Typography>
                    </Grid>
                    <Grid item xs={12} textAlign="center">
                      <Button
                        type="button"
                        variant="contained"
                        onClick={handlePayNowClick}
                        sx={{ mt: 1, mb: 8, bgcolor: "#cb0d0d" }}
                      >
                        Pay: {selectedSeats.length * selectedMovie[0].price}
                      </Button>
                    </Grid>
                  </Grid>
                </form>
              )}
            </Grid>
          </Grid>
        )}

        {success && (
          <>
          <Dialog open={isDialogOpen} onClose={handleCloseDialog}>
            <DialogTitle>Booking Summary</DialogTitle>
            <DialogContent>
              {bookingDetails && (
                <>
                  <Typography variant="h6" align="center">
                    {bookingDetails.moviename}
                  </Typography>
                  <Typography variant="body1">
                    {bookingDetails.seatnumbers}
                  </Typography>
                  <Typography variant="body1">
                    Total: Rs. {bookingDetails.price}
                  </Typography>
                </>
              )}
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseDialog} color="primary">
                Confirm Booking
              </Button>
            </DialogActions>
          </Dialog>
          <p>Booking successful!</p>
          </>
        )}
      </Grid>
    </div>
  );
}

export default BookTickets;

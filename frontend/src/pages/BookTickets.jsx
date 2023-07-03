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

function BookTickets() {
  // const classes = useStyles();
  const [selectedMovie, setSelectedMovie] = useState("");
  const [numSeats, setNumSeats] = useState(0);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [seatNumbers, setSeatNumbers] = useState("");
  const [success, setSuccess] = useState(false);
  const [showForm, setShowForm] = useState(false);
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
        console.log(selectedMovie[0]);
        console.log(selectedMovie[0].moviename);
      })
      .catch((error) => {
        console.error("Error fetching movie data", error);
      });
  }, [moviename]);

  const handleNumSeatsChange = (event) => {
    setNumSeats(event.target.value);
  };

  const handleSeatNumbersChange = (event) => {
    setSeatNumbers(event.target.value);
  };

  const handleSeatClick = (row, column) => {
    setSelectedSeats((prevSelectedSeats) => {
      const seat = `${row}${column}`;
      if (prevSelectedSeats.includes(seat)) {
        return prevSelectedSeats.filter(
          (selectedSeats) => selectedSeats !== seat
        );
      } else {
        return [...prevSelectedSeats, seat];
      }
    });
  };
  const isSeatSelected = (row, column) => {
    const seat = `${row}${column}`;
    return selectedSeats.includes(seat);
  };

  // const handleFormSubmit = (event) => {
  //   event.preventDefault();
  //   // Validate form fields
  //   if (selectedMovie && numSeats && selectedSeats.length > 0) {
  //     const ticketDto = {
  //       moviename: selectedMovie[0].moviename,
  //       theatrename: selectedMovie[0].theatrename,
  //       noOfTickets: numSeats,
  //       seatnumber: selectedSeats.map((seat) => seat.number),
  //     };

  //     axios
  //       .post(
  //         `http://localhost:9090/api/v1.0/moviebooking/${encodeURIComponent(
  //           selectedMovie[0].moviename
  //         )}/book`,
  //         ticketDto,
  //         {
  //           headers: {
  //             Authorization: successResponse,
  //           },
  //         }
  //       )
  //       .then((response) => {
  //         setSuccess(true);
  //         handleOpenDialog();
  //       })
  //       .catch((error) => {
  //         console.error("Error booking tickets", error);
  //       });
  //   }
  // };

  const handleOpenDialog = () => {
    setIsDialogOpen(true);
  };

  const handlePayNowClick = () => {
    const seatNumberArray = selectedSeats.map((seat) => seat.number);
    const ticketDto = {
      moviename: selectedMovie[0].moviename,
      theatrename: selectedMovie[0].theatrename,
      noOfTickets: numSeats,
      seatnumber: seatNumberAraay.join(", "),
    };

    const bookingSummary = {
      moviename: selectedMovie[0].moviename,
      seatnumbers: ticketDto.seatnumber,
      price: numSeats * selectedMovie[0].price,
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
    })
    .catch((error) => {
      console.error("Error booking tickets", error);
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
                      selectedSeats.includes(`${rows[0]}${column}`)
                        ? "selected"
                        : ""
                    }`}
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
                      backgroundColor: selectedSeats.includes(`${row}${column}`)
                        ? "red"
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

  const handleBookTicketsClick = () => {
    setShowForm(true);
  };

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
            <Grid container justifyContent="center" style={{ marginTop: "50px" }}>
          {selectedMovie && (
            <form onSubmit={handlePayNowClick}>
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
                    Selected Seats: {selectedSeats.join(",")}
                  </Typography>
                </Grid>
                <Grid item xs={12} textAlign="center">
                  <Button
                    type="submit"
                    variant="contained"
                    onClick={handlePayNowClick}
                    sx={{ mt: 3, mb: 8, bgcolor: "#cb0d0d" }}
                  >
                    Pay: {bookingDetails.total}
                  </Button>
                </Grid>
              </Grid>
            </form>
          )}
        </Grid>
          </Grid>
        )}

        {/* <Grid container justifyContent="center" style={{ marginTop: "50px" }}>
          {showForm && selectedMovie && (
            <form onSubmit={handleFormSubmit}>
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
                    Selected Seats: {selectedSeats.join(",")}
                  </Typography>
                </Grid>
                <Grid item xs={12} textAlign="center">
                  <Button
                    type="submit"
                    variant="contained"
                    onClick={handlePayNowClick}
                    sx={{ mt: 3, mb: 8, bgcolor: "#cb0d0d" }}
                  >
                    Pay: {bookingDetails.total}
                  </Button>
                </Grid>
              </Grid>
            </form>
          )}
        </Grid> */}

        {success && (
          <Dialog open={isDialogOpen} onClose={() => setIsDialogOpen(false)}>
            <DialogTitle>Booking Summary</DialogTitle>
            <DialogContent>
              {bookingDetails && (
                <>
                  <Typography variant="h6" align="center">
                    {bookingDetails.moviename}
                  </Typography>
                  <Typography variant="body1">
                    {bookingDetails.seatnumber.join(", ")}
                  </Typography>
                  <Typography variant="body1">
                    Total: Rs. {bookingDetails.total}
                  </Typography>
                </>
              )}
            </DialogContent>
            <DialogActions>
              <Button onClick={() => setIsDialogOpen(false)} color="primary">
                Close
              </Button>
            </DialogActions>
          </Dialog>
        )}
      </Grid>
    </div>
  );
}

export default BookTickets;

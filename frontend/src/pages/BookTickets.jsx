import axios from "axios";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { moviesData } from "../components/MovieData";

function BookTickets() {
   const [selectedMovie, setSelectedMovie] = useState("");
  const [numSeats, setNumSeats] = useState(0);
  const [seatNumbers, setSeatNumbers] = useState("");
  const [success, setSuccess] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const successResponse = localStorage.getItem("successResponse");
  const {moviename} = useParams();

  useEffect(() => {
    axios
      .get(
        `http://localhost:9090/api/v1.0/moviebooking/movies/search/${encodeURIComponent(moviename)}`,
        {
          headers: {
            Authorization: successResponse,
          },
        }
      )
      .then(response => {
        setSelectedMovie(response.data);
        console.log(response.data);
        console.log(selectedMovie);
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

  const handleFormSubmit = (event) => {
    event.preventDefault();
    // Validate form fields
    if (selectedMovie && numSeats > 0 && seatNumbers.trim() !== "") {
      const ticketDto = {
        moviename: selectedMovie[0].moviename,
        theatrename: selectedMovie[0].theatrename,
        noOfTickets: numSeats,
        seatnumber: seatNumbers.split(",").map((seat) => seat.trim()),
      };

      axios
        .post(
          `http://localhost:9090/api/v1.0/moviebooking/${encodeURIComponent(selectedMovie[0].moviename)}/book`,
          ticketDto,
          {
            headers: {
              Authorization: successResponse,
            },
          }
        )
        .then((response) => {
          setSuccess(true);
        })
        .catch((error) => {
          console.error("Error booking tickets", error);
        });
    }
  };

  const handleBookTicketsClick = () => {
    setShowForm(true);
  };

  return (
    <div>
      <h1 style={{ textAlign: "center", marginTop: "100px" }}>Book Tickets</h1>
      {selectedMovie && (
        <div style={{ display: "flex", marginTop: "50px", justifyContent: "center"}}>
          <div style={{ justifyContent: "center", textAlign: "center" }}>
            <h2>{selectedMovie[0].moviename}</h2>
            {!showForm && (
              <button
                type="button"
                onClick={handleBookTicketsClick}
                style={{
                  backgroundColor: "#cb0d0d",
                  padding: "8px 16px",
                  marginBottom: "50px",
                }}
              >
                Book Tickets
              </button>
            )}
          </div>
        </div>
      )}
      <div
        style={{ display: "flex", justifyContent: "center", marginTop: "50px" }}
      >
        {showForm && selectedMovie && (
          <form onSubmit={handleFormSubmit}>
            <label>
              Number of Seats:
              <input
                type="number"
                value={numSeats}
                onChange={handleNumSeatsChange}
                style={{ marginLeft: "5px" }}
              />
            </label>
            <br />
            <br />
            <label>
              Seat Numbers:
              <input
                type="text"
                value={seatNumbers}
                onChange={handleSeatNumbersChange}
                style={{ marginLeft: "5px" }}
              />
            </label>
            <p>Theartre : {selectedMovie[0].theatrename}</p>
            <p>Total Amount: Rs. {numSeats * selectedMovie[0].price}</p>
            <button
              type="submit"
              style={{
                backgroundColor: "#cb0d0d",
                padding: "8px 16px",
                marginBottom: "50px",
              }}
            >
              Submit
            </button>
          </form>
        )}
      </div>
      {success && <p>Booking successful!</p>}
    </div>
  );
}

export default BookTickets;

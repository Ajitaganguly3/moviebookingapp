import { useState } from "react";
import { useParams } from "react-router-dom";
import { moviesData } from "../components/MovieData";

function BookTickets() {
  const { id } = useParams();
  const selectedMovie = moviesData.find((movie) => movie.id === parseInt(id));

  const [numSeats, setNumSeats] = useState(0);
  const [seatNumbers, setSeatNumbers] = useState("");
  const [success, setSuccess] = useState(false);
  const [showForm, setShowForm] = useState(false); 


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
      // Calculate total amount
      const totalPrice = numSeats * selectedMovie.price;

      setSuccess(true);
    }
  };

  const handleBookTicketsClick = () => {
    setShowForm(true);
  };

  return (
    <div>
      <h1 style={{ textAlign: "center", marginTop: "100px" }}>Book Tickets</h1>
      {selectedMovie && (
        <div style={{ display: "flex", marginTop: "50px" }}>
          <div style={{ marginRight: "100px", display: "flex" }}>
            <img
              src={selectedMovie.posterUrl}
              alt={selectedMovie.title}
              style={{ width: "400px", height: "auto" }}
            />
          </div>
          <div style={{ justifyContent: "center" }}>
            <h2>{selectedMovie.title}</h2>
            <p>{selectedMovie.genre}</p>
            <p>{selectedMovie.releaseDate}</p>
            <h3>About the Movie:</h3>
            <p>{selectedMovie.about}</p>
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
            <p>Theartre : {selectedMovie.theatreName}</p>
            <p>Total Amount: Rs. {numSeats * selectedMovie.price}</p>
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
import axios from "axios";
import { useState, useEffect } from "react";
import {useNavigate} from "react-router-dom";
import { useParams } from "react-router-dom";

function BookTickets() {
  const [selectedMovie, setSelectedMovie] = useState("");
  const [showForm, setShowForm] = useState(false);
  const successResponse = localStorage.getItem("successResponse");
  const { moviename } = useParams();
  const navigate = useNavigate();

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
        console.log(selectedMovie);
        console.log(selectedMovie[0].moviename);
      })
      .catch((error) => {
        if(error.response){
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if(error.request){
          console.log(error.request);
        } else{
          console.log("Error", error.message);
        }
      });
  }, [moviename]);

  const handleNumSeatsChange = (event) => {
    setNumSeats(event.target.value);
  };

  const handleSeatNumbersChange = (event) => {
    setSeatNumbers(event.target.value);
  };

  const handleBookTicketsClick = () => {
    // setShowForm(true);
    navigate(`/movies/${moviename}/bookTickets`);
  };

  return (
    <div>
      <h1 style={{ textAlign: "center", marginTop: "100px" }}>Movie Details</h1>
      {selectedMovie && (
        <div style={{ display: "flex", marginTop: "50px" }}>
          <div style={{ marginRight: "100px", display: "flex" }}>
            <img
              src={selectedMovie[0].posterURL}
              alt={selectedMovie[0].moviename}
              style={{ width: "270px", height: "auto" , marginBottom: "50px",}}
            />
          </div>
          <div style={{ justifyContent: "center" }}>
            <h2>{selectedMovie[0].moviename}</h2>
            <p>{selectedMovie[0].genre}</p>
            <p>{selectedMovie[0].releaseDate}</p>
            <h3>About the Movie:</h3>
            <p>{selectedMovie[0].about}</p>
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
    </div>
  );
}

export default BookTickets;

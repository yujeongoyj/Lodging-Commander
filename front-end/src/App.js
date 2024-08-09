
import './App.css';
import AddressForm from "./AddressForm";
import {BrowserRouter as Router, Route, Routes  } from "react-router-dom";
import CategoryForm from "./CategoryForm";
import FacilityForm from "./FacilityForm";
import HotelForm from "./HotelForm";
import RoomForm from "./RoomForm";



function App() {
  return (
      <div className="App">
          <Router>
              <Routes>
                  <Route path="/" element={<AddressForm />} />
                  <Route path="/AddressForm" element={<AddressForm />} />
                  <Route path="/CategoryForm" element={<CategoryForm />} />
                  <Route path="/FacilityForm" element={<FacilityForm />} />
                  <Route path="/HotelForm" element={<HotelForm />} />
                  <Route path="/RoomForm" element={<RoomForm />} />
              </Routes>
          </Router>
      </div>
  );
}

export default App;

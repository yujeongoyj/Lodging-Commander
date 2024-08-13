import './App.css';
import AddressForm from "./addHotel/AddressForm";
import {Route, Routes} from "react-router-dom";
import CategoryForm from "./addHotel/CategoryForm";
import FacilityForm from "./addHotel/FacilityForm";
import HotelForm from "./addHotel/HotelForm";
import RoomForm from "./addHotel/RoomForm";
import AddHotelSuccess from "./addHotel/AddHotelSuccess";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./Header";


function App() {
    return (
        <div>
            <Header/>
            <Routes>
                <Route path="/AddressForm" element={<AddressForm/>}/>
                <Route path="/CategoryForm" element={<CategoryForm/>}/>
                <Route path="/FacilityForm" element={<FacilityForm/>}/>
                <Route path="/HotelForm" element={<HotelForm/>}/>
                <Route path="/RoomForm" element={<RoomForm/>}/>
                <Route path="/AddHotelSuccess" element={<AddHotelSuccess/>}/>
            </Routes>
        </div>
    );
}

export default App;

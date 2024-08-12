import './App.css';
import {Route, Routes} from "react-router-dom";
import Cart from "./cart/Cart";
import StartPage from "./home/StartPage";
import RoomList from "./room/RoomList";
import List from "./home/component/List";

function App() {
    return (
        <Routes>
            <Route path="/cart" element={<Cart/>}/>
            <Route path="/" element={<StartPage/>}/>
            <Route path="/room" element={<RoomList/>}/>
            <Route path="/showOne/:hotelId" element={<List/>}/>
        </Routes>
    );
}

export default App;

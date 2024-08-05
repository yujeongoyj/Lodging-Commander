import './App.css';
import {Route, Routes} from "react-router-dom";
import Auth from "./user/Auth";
import Register from "./user/Register";

function App() {
    return (
        <div>
            <Routes>
                <Route path="/login" element={<Auth/>}/>
                <Route path="/user/register" element={<Register/>}/>
            </Routes>
        </div>

    );
}

export default App;

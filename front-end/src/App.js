import './App.css';
import {Route, Routes} from "react-router-dom";
import Auth from "./user/Auth";
import Register from "./user/Register";
import AuthFail from "./user/AuthFail";
import Info from "./user/Info";
import Update from "./user/Update";
import Home from "./user/Home";
import Header from "./Header";

function App() {
    return (
        <div>
            <Header/>
            <Routes>
                <Route path="/" element={<Auth/>}/>
                <Route path="/user/register" element={<Register/>}/>
                <Route path="/user/update" element={<Update/>}/>
                <Route path="/user/info" element={<Info/>}/>
                <Route path="/user/authSuccess" element={<Home/>}/>
                <Route path="/user/authFail" element={<AuthFail/>}/>
            </Routes>
        </div>
    );
}

export default App;
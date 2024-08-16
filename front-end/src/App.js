import './App.css';
import {Route, Routes} from "react-router-dom";
import Auth from "./user/Auth";
import Register from "./user/Register";
import AuthFail from "./user/AuthFail";
import Info from "./user/Info";
import Update from "./user/Update";
import Home from "./user/Home";
import Details from "./hotel/Details";
import Cart from "./cart/Cart";
import StartPage from "./home/StartPage";
import Header from "./home/component/Header";
import ServerAuth from "./ServerAuth";

function App() {
    return (
        <div>
            <Header/>
            <Routes>
                <Route path="/" element={<StartPage/>}/>
                <Route path="/user/register" element={<Register/>}/>
                <Route path="/user/update" element={<Update/>}/>
                <Route path="/user/info" element={<Info/>}/>
                <Route path="/user/authSuccess" element={<Home/>}/>
                <Route path="/user/authFail" element={<AuthFail/>}/>
                <Route path="/hotel/details/:id" element={<Details/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/Auth" element={<Auth/>}/>
                <Route path="/serverAuth" element={<ServerAuth/>}/>
            </Routes>
        </div>
    );
}

export default App;
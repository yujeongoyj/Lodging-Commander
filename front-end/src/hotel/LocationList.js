import { Container } from "react-bootstrap";
import Hotels from "./components/Hotels";
import { getNextDate, getTodayDate } from "../js/day";
import { useLocation, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

let LocationList = () => {
    let { hotelLocation } = useParams();
    const currentLocation = decodeURIComponent(hotelLocation);
    const location = useLocation();
    const userInfo = location.state?.userData || null;
    let [hotels, setHotels] = useState([]); // 초기값을 배열로 설정
    const [checkInDate, setCheckInDate] = useState(getTodayDate());
    const [checkOutDate, setCheckOutDate] = useState(getNextDate(getTodayDate()));

    useEffect(() => {
        const selectList = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hotel/${currentLocation}`, { withCredentials: true });
                setHotels(response.data.hotelList || []);
            } catch (error) {
                console.error('Error fetching hotel details:', error);
            }
        };
        selectList();
    }, [hotelLocation]);

    return (
        <Container>
            <Hotels userInfo={userInfo} filteredHotels={hotels} checkInDate={checkInDate} checkOutDate={checkOutDate} />
        </Container>
    );
}

export default LocationList;

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, ListGroup } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';

const HotelList = () => {
    const [hotels, setHotels] = useState([]);
    const location = useLocation();
    const userInfo = location.state?.userData;

    useEffect(() => {
        const fetchHotels = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/properties/ModifyHotel/user/${userInfo.id}`, {
                    withCredentials: true
                });
                setHotels(response.data);
            } catch (error) {
                console.error('Error fetching hotels', error);
            }
        };

        if (userInfo?.id) {
            fetchHotels();
        }
    }, [userInfo]);

    return (
        <Container className="mt-4">
            <h2>{userInfo?.name}님의 호텔 리스트</h2>
            <ListGroup>
                {hotels.map((hotel) => (
                    <ListGroup.Item key={hotel.id}>
                        {hotel.name} - {hotel.grade}성급
                    </ListGroup.Item>
                ))}
            </ListGroup>
        </Container>
    );
};

export default HotelList;

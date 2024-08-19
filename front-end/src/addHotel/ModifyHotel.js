import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Card, Button, ListGroup, Alert } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';

const HotelList = () => {
    const [hotels, setHotels] = useState([]);
    const location = useLocation();
    const navigate = useNavigate();
    const userInfo = location.state?.userData;

    useEffect(() => {
        if (userInfo) {
            axios.get('http://localhost:8080/properties/hotels', {
                params: { userId: userInfo.id },
                withCredentials: true
            })
                .then(response => {
                    setHotels(response.data);
                })
                .catch(error => {
                    console.error('Error fetching hotels', error);
                });
        }
    }, [userInfo]);

    const handleHotelClick = (hotelId) => {
        navigate(`/HotelDetail?hotelId=${hotelId}`, {
            state: { userData: userInfo }
        });
    };

    return (
        <Container className="mt-4">
            <h2>내가 등록한 호텔 목록</h2>
            {hotels.length === 0 ? (
                <Alert variant="info">
                    아직 등록된 숙소가 없습니다.
                </Alert>
            ) : (
                <ListGroup>
                    {hotels.map(hotel => (
                        <ListGroup.Item key={hotel.id}>
                            <Card>
                                <Card.Body>
                                    <Card.Title>{hotel.name}</Card.Title>
                                    <Card.Text>
                                        전화번호: {hotel.tel}<br />
                                        등급: {hotel.grade}<br />
                                        상세정보: {hotel.detail}
                                    </Card.Text>
                                    <Button variant="primary" onClick={() => handleHotelClick(hotel.id)}>자세히 보기</Button>
                                </Card.Body>
                            </Card>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            )}
        </Container>
    );
};

export default HotelList;

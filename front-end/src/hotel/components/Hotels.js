import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import HotelSlice from './HotelSlice';

let Hotels = ({ filteredHotels = [], checkInDate, checkOutDate, userInfo }) => {
    return (
        <Container>
            <Row>
                <Col>
                    {filteredHotels.length > 0 ? (
                        filteredHotels.map(hotel => (
                            <HotelSlice
                                key={hotel.id}
                                hotel={hotel}
                                checkInDate={checkInDate}
                                checkOutDate={checkOutDate}
                                userInfo={userInfo}
                            />
                        ))
                    ) : (
                        <Row className="justify-content-center align-items-center min-vh-100">
                            <Col className="text-center">
                                <h1>검색 결과가 없습니다.</h1>
                                <h3>조건을 다시 설정해 주세요.</h3>
                            </Col>
                        </Row>
                    )}
                </Col>
            </Row>
        </Container>
    );
};

export default Hotels;

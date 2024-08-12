import React, {useEffect, useState} from 'react';
import {Button, Col, Container, FloatingLabel, Form, Row} from 'react-bootstrap';
import {FaSearch} from 'react-icons/fa';

let HotelSearchForm = ({onSearch}) => {
    let [location, setLocation] = useState('');
    let [checkInDate, setCheckInDate] = useState(getTodayDate());
    let [checkOutDate, setCheckOutDate] = useState('');
    let [guests, setGuests] = useState(1);
    let [rooms, setRooms] = useState(1);

    let [minDate, setMinDate] = useState(getTodayDate);

    function getTodayDate() {
        let today = new Date();
        let year = today.getFullYear();
        let month = (today.getMonth() + 1).toString().padStart(2, '0');
        let day = today.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    let handleSubmit = async (e) => {
        e.preventDefault();

        if (!location || !checkInDate || !checkOutDate || !guests || !rooms) {
            alert('모든 조건들을 입력해 주세요.');
            return;
        }

        try {
            await onSearch(location, checkInDate, checkOutDate, guests, rooms);
        } catch (error) {
            console.error('Error occurred while searching:', error);
        }
    };

    let isFormValid = location && checkInDate && checkOutDate && guests && rooms;

    useEffect(() => {
        if (checkInDate) {
            let checkInDateObj = new Date(checkInDate);

            let nextDay = new Date(checkInDateObj);
            nextDay.setDate(checkInDateObj.getDate() + 1);

            let year = nextDay.getFullYear();
            let month = (nextDay.getMonth() + 1).toString().padStart(2, '0');
            let day = nextDay.getDate().toString().padStart(2, '0');
            setMinDate(`${year}-${month}-${day}`);
        }
    }, [checkInDate]);


    return (
        <Container className="d-flex justify-content-center">
            <Form onSubmit={handleSubmit} className="w-100">
                <Row className="mb-3 justify-content-center">
                    <Col xs="auto">
                        <FloatingLabel label="지역" controlId="location" className="mb-3">
                            <Form.Control
                                type="text"
                                placeholder="지역을 입력하세요"
                                value={location}
                                onChange={(e) => setLocation(e.target.value)}
                            />
                        </FloatingLabel>
                    </Col>
                    <Col xs="auto">
                        <FloatingLabel label="체크인 날짜" controlId="checkInDate" className="mb-3">
                            <Form.Control
                                type="date"
                                value={checkInDate}
                                min={getTodayDate()}
                                onChange={(e) => {
                                    setCheckInDate(e.target.value);
                                    if (new Date(e.target.value) > new Date(checkOutDate)) {
                                        setCheckOutDate(e.target.value);
                                    }
                                }}
                            />
                        </FloatingLabel>
                    </Col>
                    <Col xs="auto">
                        <FloatingLabel label="체크아웃 날짜" controlId="checkOutDate" className="mb-3">
                            <Form.Control
                                type="date"
                                value={checkOutDate}
                                min={minDate}
                                onChange={(e) => setCheckOutDate(e.target.value)}
                            />
                        </FloatingLabel>
                    </Col>
                    <Col xs="auto">
                        <FloatingLabel label="총 게스트 수" controlId="guests" className="mb-3">
                            <Form.Control
                                type="number"
                                min="1"
                                value={guests}
                                onChange={(e) => setGuests(e.target.value)}
                            />
                        </FloatingLabel>
                    </Col>
                    <Col xs="auto">
                        <FloatingLabel label="총 룸 수" controlId="rooms" className="mb-3">
                            <Form.Control
                                type="number"
                                min="1"
                                value={rooms}
                                onChange={(e) => setRooms(e.target.value)}
                            />
                        </FloatingLabel>
                    </Col>
                    <Col xs="auto" className="d-flex align-items-center mb-3">
                        <Button variant="primary" type="submit" disabled={!isFormValid}>
                            <FaSearch />
                        </Button>
                    </Col>
                </Row>
            </Form>
        </Container>
    );
};

export default HotelSearchForm;


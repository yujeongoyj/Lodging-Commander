import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Alert, Button, Card, Col, Container, Row, Spinner} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useNavigate} from "react-router-dom";

const NewList = (userInfo) => {
    const [hotels, setHotels] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentIndex, setCurrentIndex] = useState(0);
    let navigate = useNavigate()

    const goHotelDetail = (id) => {
        navigate(`/hotel/details/${id}`, {
            state: {
                userData: userInfo,
            },
        });
    };

        useEffect(() => {
            const selectList = async () => {
                try {
                    const response = await axios.get('http://localhost:8080/hotel/newHotels', {withCredentials: true});
                    setHotels(response.data.hotelList);
                    console.log(response.data.hotelList)
                } catch (error) {
                    console.error('Error fetching hotel details:', error);
                    setError('Failed to load hotel details.');
                } finally {
                    setLoading(false);
                }
            };
            selectList();
        }, []);

        const showNextHotel = () => {
            setCurrentIndex((prevIndex) => (prevIndex + 1) % hotels.length);
        };

        const showPreviousHotel = () => {
            setCurrentIndex((prevIndex) => (prevIndex - 1 + hotels.length) % hotels.length);
        };


        if (loading) {
            return (
                <Container className="d-flex justify-content-center align-items-center" style={{height: '100vh'}}>
                    <Spinner animation="border" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </Spinner>
                </Container>
            );
        }

        if (error) {
            return (
                <Container className="mt-5">
                    <Row>
                        <Col>
                            <Alert variant="danger">{error}</Alert>
                        </Col>
                    </Row>
                </Container>
            );
        }

        return (
            <Container className="mt-3">
                <Row className="justify-content-center">
                    <Col className="justify-content-between align-items-center">
                        <Row>
                            <Col sm={2}>
                                <Button
                                    variant="secondary"
                                    onClick={showPreviousHotel}
                                    disabled={hotels.length <= 1}
                                    className="flex-shrink-0"
                                >
                                    이전
                                </Button>
                            </Col>
                            <Col sm={8}>
                                <Card className="mx-3">
                                    <Card.Body className="justify-content-between">
                                        <Card.Title
                                            onClick={() => goHotelDetail(hotels[currentIndex].id)}>{hotels[currentIndex].name}</Card.Title>
                                        <Card.Text className="mb-0">
                                            <span className="text-muted">전화번호:</span>
                                            <span className="text-primary">0{hotels[currentIndex].tel}</span>
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                            <Col sm={2}>
                                <Button
                                    variant="primary"
                                    onClick={showNextHotel}
                                    disabled={hotels.length <= 1}
                                    className="flex-shrink-0"
                                >
                                    다음
                                </Button>
                            </Col>
                        </Row>
                    </Col>
                </Row>
                {hotels.length === 0 && (
                    <Row>
                        <Col>
                            <Card>
                                <Card.Body className="text-center">
                                    <Card.Title>이용 가능한 호텔이 없습니다</Card.Title>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                )}
            </Container>

        );
    };

    export default NewList;

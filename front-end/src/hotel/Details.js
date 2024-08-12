import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, Container, Col, Row, Card, Carousel} from "react-bootstrap";

let Details = () => {
    const {id} = useParams()
    const [hotel, setHotel] = useState(null)

    useEffect(() => {
        axios.get(`/hotel/details/${id}`)
            .then(response => {
                setHotel(response.data)
            }).catch(error => {
            console.log(error);
        })
    },  [id])

    if (!hotel) return <div>Loading...</div>
    return (
        <Container className="mt-5">
            <Row>
                <Col md={7}>
                    <Carousel>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src="호텔 이미지 1"
                                alt="첫 번째 슬라이드"
                            />
                        </Carousel.Item>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src="호텔 이미지 2"
                                alt="두 번째 슬라이드"
                            />
                        </Carousel.Item>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src="호텔 이미지 3"
                                alt="세 번째 슬라이드"
                            />
                        </Carousel.Item>
                    </Carousel>
                </Col>
                <Col md={5}>
                    <Card>
                        <Card.Body>
                            <Card.Title>호텔 이름</Card.Title>
                            <Card.Text>
                                숙박 일자, 인원 수
                            </Card.Text>
                            <Card.Text>
                                <strong>₩000,000</strong> 1박당
                            </Card.Text>
                            <Button variant="primary" block>예약 버튼</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <Row className="mt-5">
                <Col md={12}>
                    <h3>호텔 설명</h3>
                    <p>
                      호텔 설명 블라블라블라
                    </p>
                </Col>
            </Row>
            <Row className="mt-3">
                <Col md={12}>
                    <h4>호텔 편의 시설</h4>
                    <ul>
                        <li>무료 Wi-Fi</li>
                        <li>수영장</li>
                        <li>스파 및 웰니스 센터</li>
                        <li>피트니스 센터</li>
                        <li>24시간 룸 서비스</li>
                    </ul>
                </Col>
            </Row>
        </Container>
    );
};

export default Details;

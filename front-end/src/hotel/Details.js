import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useLocation, useParams} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Card, Carousel, Col, Container, Row} from "react-bootstrap";
import RoomList from "../room/RoomList";
import HotelFacility from "./components/HotelFacility";

let Details = () => {
    let location = useLocation();
    let userInfo = location.state.userData ||  null;
    let checkOutDate = location.state.checkOutDate; // 중복 선언 제거
    let checkInDate = location.state.checkInDate; // 중복 선언 제거

    let {id} = useParams();
    let [hotel, setHotel] = useState({hotel: {}});

    useEffect(() => {
        const fetchHotelDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hotel/details/${id}`, { withCredentials: true });
                setHotel(response.data);
            } catch (error) {
                console.error('Error fetching hotel details:', error);
                setError('호텔 정보를 가져오는 데 오류가 발생했습니다.');
            }
        };

        const fetchReviews = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/review/hotel/${id}`, { withCredentials: true });
                if (response.status === 200) {
                    setReviews(response.data.reviews || []);
                } else {
                    throw new Error('서버 오류');
                }
            } catch (error) {
                console.error('Error fetching reviews:', error);
                setError('리뷰를 가져오는 데 오류가 발생했습니다.');
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchHotelDetails();
            fetchReviews();
        }
    }, [id]);

    if (loading) return <Spinner animation="border" variant="primary" />;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!hotel) return <div>Loading...</div>;

    return (
        <Container className="mt-5">
            <Row>
                <Col md={7}>
                    <Carousel>
                        {hotel.imgPath && hotel.imgPath.map((url, index) => (
                            <Carousel.Item key={index}>
                                <img
                                    className="d-block w-100"
                                    src={url}
                                    alt={`Slide ${index}`}
                                />
                            </Carousel.Item>
                        ))}
                    </Carousel>
                </Col>
                <Col md={5}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{hotel.hotelName}</Card.Title> {/* 호텔 이름을 호텔 데이터에서 가져오기 */}
                            <Card.Text>
                                체크인: {checkInDate} 체크아웃: {checkOutDate}
                            </Card.Text>
                            <Card.Text>
                                <strong>₩ {new Intl.NumberFormat().format(hotel.minPrice)}</strong> (1박당 최저가격) {/* 1박당 가격을 호텔 데이터에서 가져오기 */}
                            </Card.Text>
                            <Button variant="primary" block>예약하기</Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <Row className="mt-5">
                <Col md={12}>
                    <h3>호텔 설명</h3>
                    <p>{hotel.detail}</p> {/* 호텔 설명 */}
                </Col>
                <Col>
                    <Kakao id={id}/>
                </Col>
            </Row>
            <Row className="mt-3">
                <Col md={12}>
                    <h4>호텔 편의 시설</h4>
                    <ul>
                       <HotelFacility amenities={hotel.facilities}/>
                    </ul>
                </Col>
            </Row>
            <Row>
                <Col sm={9}>
                    <Row>
                        <h4>Room List</h4>
                    </Row>
                    <Row>
                        <RoomList userInfo={userInfo} checkInDate={checkInDate} checkOutDate={checkOutDate} hotelId={hotel.id}/>
                    </Row>
                </Col>
            </Row>
            <Row>
                <Col>
                    <h4>호텔 리뷰</h4>
                    {reviews.length === 0 ? (
                        <Alert variant="info">작성된 리뷰가 없습니다.</Alert>
                    ) : (
                        <ListGroup>
                            {reviews.map((review) => (
                                <ListGroup.Item key={review.id}>
                                    <h5>작성자: {review.userName}</h5>
                                    <p>{review.content}</p>
                                    <p><strong>평점:</strong> {review.rating}</p>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </Col>
            </Row>
        </Container>
    );
};

export default Details;

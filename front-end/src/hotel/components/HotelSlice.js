import React from 'react';
import {Button, Card, Carousel, Col, Container, Row} from 'react-bootstrap';
import StarRating from '../../cart/components/StarRating';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faSignInAlt} from '@fortawesome/free-solid-svg-icons';
import * as calculate from '../../js/calculate';
import HotelFacility from './HotelFacility';
import {useNavigate} from "react-router-dom";

let HotelSlice = ({hotel, checkInDate, checkOutDate, userInfo}) => {
    let originalPrice = calculate.calculatePrice(checkInDate, checkOutDate, hotel.minPrice);
    let discountedPrice = userInfo ? calculate.calculateDiscountedPrice(originalPrice, userInfo.grade) : originalPrice;

    let navigate = useNavigate();

    let handleClick = (pgaeName) => {
        if (pgaeName === 'hotel') {
            navigate(`/hotel/details/${hotel.id}`, {
                state: {
                    checkInDate,
                    checkOutDate
                }
            });
        } else {
            navigate('/Auth')
        }
    };

    return (
        <Container>
            <Card className="mb-4 position-relative">
                <Row>
                    <Col sm={4}>
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
                    <Col sm={8}>
                        <Card.Body>
                            <Card.Title className="mb-3" onClick={() => handleClick('hotel')}>
                                {hotel.hotelName}
                            </Card.Title>
                            <Card.Text>
                                <Row className="mb-2">
                                    <Col>
                                        <StarRating grade={hotel.grade}/>
                                    </Col>
                                    <Col className="text-end">
                                        이용후기 {new Intl.NumberFormat().format(hotel.reviewCount)}건
                                    </Col>
                                </Row>
                                <Row className="mb-3">
                                    <Col>
                                        <HotelFacility amenities={hotel.facilities || {}}/>
                                    </Col>
                                    <Col className="text-end">
                                        <p className="mb-1 text-muted">
                                            <span style={{fontSize: '0.875rem'}}>1박당 가격: </span>
                                            ₩ {new Intl.NumberFormat().format(hotel.minPrice)}
                                        </p>
                                        {userInfo ? (
                                            <>
                                                <p className="mb-1 text-muted">
                                                    <del>₩ {new Intl.NumberFormat().format(originalPrice)}</del>
                                                </p>
                                                <h5 className="mb-0 text-danger">
                                                    ₩ {new Intl.NumberFormat().format(discountedPrice)}
                                                </h5>
                                                <span
                                                    style={{fontSize: '0.75rem'}}>({userInfo.grade} 등급 할인 적용)</span>
                                            </>
                                        ) : (
                                            <>
                                                <p className="mb-1 text-muted">
                                                    <h4>₩ {new Intl.NumberFormat().format(originalPrice)}</h4>
                                                </p>
                                                <Button variant="primary" onClick={() => handleClick('logIn')}>
                                                    <FontAwesomeIcon icon={faSignInAlt}
                                                                     style={{marginRight: '0.5rem'}}/>
                                                    로그인하여 추가 할인 받기
                                                </Button>
                                            </>
                                        )}
                                    </Col>
                                </Row>
                            </Card.Text>
                        </Card.Body>
                    </Col>
                </Row>
            </Card>
        </Container>
    );
};

export default HotelSlice;

import {Button, Card, Col, Container, Row} from "react-bootstrap";
import React from "react";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faSignInAlt} from '@fortawesome/free-solid-svg-icons';
import * as calculate from '../../js/calculate';
import {FaExclamationTriangle} from 'react-icons/fa';

let RoomSlice = ({room, checkInDate, checkOutDate, userInfo}) => {

    let originalPrice = calculate.calculatePrice(checkInDate, checkOutDate, room.price);
    let discountedPrice = userInfo ? calculate.calculateDiscountedPrice(originalPrice, userInfo.userGrade) : originalPrice;

    return (
        <Container>
            <Card style={{width: '18rem'}}>
                <Card.Img variant="top" src={room.imgPath}/>
                <Card.Body>
                    <Card.Title>{room.roomName}</Card.Title>
                    <Card.Text>
                        {!room.reservable ? (
                            <>
                                <Row>
                                    <Col>{room.detail}</Col>
                                </Row>
                                <Row>
                                    <Col>최대 인원: {room.maxPeople}</Col>
                                </Row>
                                <Row>
                                    <Col sm={7}>
                                        <Row>
                                            <p className="mb-0 text-muted">
                                                <span style={{fontSize: '0.75rem'}}>1박당 가격: </span><br/>
                                                ₩ {new Intl.NumberFormat().format(room.price)}
                                            </p>
                                        </Row>
                                        <Row>
                                            {userInfo ? (
                                                <>
                                                    <p className="mb-0 text-muted">
                                                        <del>₩ {new Intl.NumberFormat().format(originalPrice)}</del>
                                                    </p>
                                                    <h5 className="mb-0 text-danger">
                                                        ₩ {new Intl.NumberFormat().format(discountedPrice)}
                                                    </h5>
                                                    <span style={{fontSize: '0.75rem'}}>({userInfo.grade} 등급 할인 적용)</span>
                                                </>
                                            ) : (
                                                <div>
                                                    <span style={{fontSize: '1rem', fontWeight: 'bold'}}>총 가격:</span><br/>
                                                    <h5 style={{margin: '0'}}>₩ {new Intl.NumberFormat().format(originalPrice)}</h5>
                                                </div>
                                            )}
                                        </Row>
                                    </Col>
                                    <Col sm={5} className="d-flex flex-column justify-content-end align-items-end">
                                        {userInfo ? (
                                            <>
                                                {/*상민님 버튼 핸들러 구현하셔야 해요~*/}
                                                <Row className="mb-2">
                                                    <Button variant="primary" size="sm" style={{ backgroundColor: '#007bff', borderColor: '#007bff' }}>
                                                        구매하기
                                                    </Button>
                                                </Row>
                                                <Row>
                                                    <Button variant="success" size="sm" style={{ backgroundColor: '#28a745', borderColor: '#28a745' }}>
                                                        장바구니
                                                    </Button>
                                                </Row>
                                            </>
                                        ) : (
                                            <Button variant="primary" size="sm" style={{ fontSize: '0.85rem' }}>
                                                <FontAwesomeIcon icon={faSignInAlt} style={{ marginRight: '0.5rem' }} />
                                                로그인 하여 추가 할인 받기
                                            </Button>
                                        )}
                                    </Col>
                                </Row>
                            </>
                        ):(
                            <Row>
                                <p style={{color: 'red'}}>
                                    <FaExclamationTriangle style={{marginRight: '8px'}}/>
                                    본 상품은 판매 완료되었습니다. 다른 옵션 또는 날짜로 검색해 보세요.
                                </p>
                            </Row>
                        )}
                    </Card.Text>
                </Card.Body>
            </Card>
        </Container>
    );
}

export default RoomSlice;

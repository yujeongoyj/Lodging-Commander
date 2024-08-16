import React from 'react';
import {Button, Card, Col, Container, Row} from 'react-bootstrap';
import {useNavigate} from "react-router-dom";

// Toss Payments 클라이언트 키

let PaySlice = ({ totalDiscountedPrice, selectedRoom, userInfo }) => {
    let navigate = useNavigate();

    let handlePayment = async () => {
        if (!selectedRoom || totalDiscountedPrice <= 0) return;

        navigate(`/booking/${selectedRoom.roomId}`, {
            state: {
                userData: userInfo,
                formDate: {
                    checkInDate: selectedRoom.checkInDate,
                    checkOutDate: selectedRoom.checkOutDate
                }
            }
        })
    };

    return (
        <Container>
            <Card className="mb-4 position-relative">
                <Card.Body>
                    <Card.Title className="text-center">
                        총 금액
                    </Card.Title>
                    <Card.Text className="text-end">
                        ₩ {new Intl.NumberFormat().format(totalDiscountedPrice)}
                    </Card.Text>
                    <Row>
                        <Col>
                            <Button
                                className='justify-content-lg-center'
                                variant="primary"
                                size="lg"
                                onClick={handlePayment}
                                disabled={totalDiscountedPrice === 0}
                            >
                                결제하기
                            </Button>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default PaySlice;

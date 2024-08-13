import React from 'react';
import {Button, Card, Col, Container, Row} from 'react-bootstrap';

// Toss Payments 클라이언트 키

let PaySlice = ({ totalDiscountedPrice, selectedRoom, userInfo }) => {

    let handlePayment = async () => {
        if (!selectedRoom || totalDiscountedPrice <= 0) return;
        // 상민님 장바구니 추가 로직
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

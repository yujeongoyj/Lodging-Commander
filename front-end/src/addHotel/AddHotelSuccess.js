import React from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import {Container, Button, Row, Col, Card} from 'react-bootstrap';

const AddHotelSuccess = () => {
    const navigate = useNavigate();
    const location = useLocation();
    let userInfo = location.state?.userData

    const handleGoHome = () => {
        navigate('/', {state: {userData: userInfo}});
    };


    return (
        <Container className="d-flex justify-content-center align-items-start mt-5">
            <Row>
                <Col>
                    <Card className="text-center shadow-lg p-4">
                        <Card.Body>
                            <Card.Title className="mb-4">Hotel Successfully Added!</Card.Title>
                            <Card.Text>
                                숙소정보가 데이터베이스에 성공적으로 저장되었습니다
                            </Card.Text>
                            <Button variant="primary" onClick={handleGoHome}>
                                Go to Home
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default AddHotelSuccess;

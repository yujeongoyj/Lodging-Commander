import React from 'react';
import {useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container, Row, Col, Button, Alert} from 'react-bootstrap';

const AuthFail = () => {

    let navigate = useNavigate();

    let goBack = () => {
        navigate(-1);
    }

    return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Row>
                <Col xs={12}>
                    <Alert variant="primary" className="text-center">
                        <h3 className="display-4">로그인 실패</h3>
                        <p>로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.</p>
                    </Alert>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Button variant="primary" onClick={goBack}>뒤로가기</Button>
                </Col>
            </Row>
        </Container>
    );
};

export default AuthFail;

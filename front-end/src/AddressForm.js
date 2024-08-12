import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col, Alert } from 'react-bootstrap';
import Header from "./Header";

const AddressForm = () => {
    const [address, setAddress] = useState('');
    const [addressDetail, setAddressDetail] = useState('');
    const [postCode, setPostCode] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = {
                address,
                addressDetail,
                postCode,
                latitude: parseFloat(latitude),
                longitude: parseFloat(longitude),
            };
            const response = await axios.post('http://localhost:8080/properties/address', data);
            const addressId = response.data.addressId;
            navigate(`/CategoryForm?addressId=${addressId}`);
        } catch (error) {
            setError('Error saving address');
            console.error('Error saving address', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>1/5 단계</h4>
            <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>주소</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            placeholder="주소"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formAddressDetail">
                    <Form.Label column sm={2}>상세 주소</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={addressDetail}
                            onChange={(e) => setAddressDetail(e.target.value)}
                            placeholder="상세 주소"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formPostCode">
                    <Form.Label column sm={2}>우편번호</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={postCode}
                            onChange={(e) => setPostCode(e.target.value)}
                            placeholder="우편번호"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formLatitude">
                    <Form.Label column sm={2}>위도</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="number"
                            step="any"
                            value={latitude}
                            onChange={(e) => setLatitude(e.target.value)}
                            placeholder="위도"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formLongitude">
                    <Form.Label column sm={2}>경도</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="number"
                            step="any"
                            value={longitude}
                            onChange={(e) => setLongitude(e.target.value)}
                            placeholder="경도"
                            required
                        />
                    </Col>
                </Form.Group>
                <Button variant="primary" type="submit">
                    다음
                </Button>
            </Form>
        </Container>
    );
};

export default AddressForm;

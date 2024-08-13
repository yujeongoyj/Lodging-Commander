import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useNavigate, useLocation} from 'react-router-dom';
import {Container, Form, Button, Row, Col, Alert} from 'react-bootstrap';

const HotelForm = () => {
    const [name, setName] = useState('');
    const [tel, setTel] = useState('');
    const [grade, setGrade] = useState(0);
    const [detail, setDetail] = useState('');
    const [addressId, setAddressId] = useState(null);
    const [categoryId, setCategoryId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();


    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        const categoryIdFromParams = queryParams.get('categoryId');

        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
        if (categoryIdFromParams) {
            setCategoryId(categoryIdFromParams);
        }
    }, [location.search]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const hotelDTO = {
                name,
                tel,
                grade,
                detail,
                addressId,
                categoryId
            };

            const response = await axios.post('http://localhost:8080/properties/hotel', hotelDTO);
            console.log(response);


            const hotelId = response.data.hotelId;
            navigate(`/RoomForm?addressId=${addressId}&categoryId=${categoryId}&hotelId=${hotelId}`);

        } catch (error) {
            console.error('Error saving hotel', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>3/5 단계</h4>
            <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
            <Form onSubmit={handleSubmit} style={{'margin-top':'5%'}}>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>숙소 이름</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Hotel Name"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>숙소 번호</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={tel}
                            onChange={(e) => setTel(e.target.value)}
                            placeholder="Telephone"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>등급</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="number"
                            value={grade}
                            onChange={(e) => setGrade(Number(e.target.value))}
                            placeholder="Grade"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>상세 정보</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            value={detail}
                            onChange={(e) => setDetail(e.target.value)}
                            placeholder="Details"
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

export default HotelForm;

import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useNavigate, useLocation} from 'react-router-dom';
import {Container, Form, Button, Row, Col} from 'react-bootstrap';

const CategoryForm = () => {
    const [name, setName] = useState('');
    const [addressId, setAddressId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    let userInfo = location.state?.userData


    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
    }, [location.search]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/properties/category', {name},
                { withCredentials: true});
            console.log(response);
            const categoryId = response.data.categoryId;
            if (addressId) {
                navigate(`/HotelForm?addressId=${addressId}&categoryId=${categoryId}`, {
                    state : {
                        userData: userInfo
                    }
                });
            } else {
                console.error('Address ID is missing');
            }

        } catch (error) {
            console.error('Error saving category', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>2/5 단계</h4>
            <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
            <Form onSubmit={handleSubmit} style={{'margin-top':'5%'}}>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>숙박시설 종류 (ex. 호텔)</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="Category Name"
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

export default CategoryForm;

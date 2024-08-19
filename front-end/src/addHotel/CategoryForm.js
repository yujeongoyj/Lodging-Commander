import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';

const CategoryForm = () => {
    const [categories, setCategories] = useState([]);
    const [selectedCategoryId, setSelectedCategoryId] = useState('');
    const [newCategoryName, setNewCategoryName] = useState('');
    const [addressId, setAddressId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    const userInfo = location.state?.userData || null;

    useEffect(() => {

        axios.get('http://localhost:8080/properties/category', {withCredentials : true})
            .then(response => {
                setCategories(response.data);
            })
            .catch(error => {
                console.error('Error fetching categories', error);
            });

        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
    }, [location.search]);

    const handleCategoryChange = (e) => {
        setSelectedCategoryId(e.target.value);
        setNewCategoryName('');
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let categoryId = selectedCategoryId;


            if (newCategoryName) {
                const response = await axios.post('http://localhost:8080/properties/category', { name: newCategoryName },
                    { withCredentials: true });
                categoryId = response.data.categoryId;
            }

            if (addressId) {
                navigate(`/HotelForm?addressId=${addressId}&categoryId=${categoryId}`, {
                    state: {
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
            {userInfo ? (
                <>
                    <h4>2/5 단계</h4>
                    <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
                    <Form onSubmit={handleSubmit} style={{marginTop: '5%'}}>
                        <Form.Group as={Row} className="mb-3" controlId="formCategory">
                            <Form.Label column sm={2}>숙박시설 종류 선택</Form.Label>
                            <Col sm={10}>
                                <Form.Control
                                    as="select"
                                    value={selectedCategoryId}
                                    onChange={handleCategoryChange}
                                    disabled={newCategoryName !== ''}
                                >
                                    <option value="">카테고리를 선택하세요</option>
                                    {categories.map(category => (
                                        <option key={category.id} value={category.id}>
                                            {category.name}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Col>
                        </Form.Group>


                        <Button variant="primary" type="submit">
                            다음
                        </Button>
                    </Form>
                </>
            ) : (<h1 className='text-center'>LOGIN이 필요합니다.</h1>)}

        </Container>
    );
};

export default CategoryForm;
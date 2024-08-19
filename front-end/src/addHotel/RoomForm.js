import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useLocation, useNavigate} from 'react-router-dom';
import {Button, Col, Container, Form, Row} from 'react-bootstrap';

const RoomForm = () => {
    const [rooms, setRooms] = useState([{name: '', price: 0, detail: '', maxPeople: 1, quantity: 1, imgId: null}]);
    const [hotelId, setHotelId] = useState(null);
    const [addressId, setAddressId] = useState(null);
    const [categoryId, setCategoryId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    const userInfo = location.state?.userData || null;
    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        const categoryIdFromParams = queryParams.get('categoryId');
        const hotelIdFromParams = queryParams.get('hotelId');

        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
        if (categoryIdFromParams) {
            setCategoryId(categoryIdFromParams);
        }
        if (hotelIdFromParams) {
            setHotelId(hotelIdFromParams);
        }
    }, [location.search]);

    const handleRoomChange = (index, field, value) => {
        const newRooms = [...rooms];
        newRooms[index][field] = value;
        setRooms(newRooms);
    };

    const handleImageChange = async (index, e) => {
        const file = e.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await axios.post('http://localhost:8080/properties/uploadImage', formData, {
                    withCredentials: true
                });

                const imgId = response.data.imgId;

                const newRooms = [...rooms];
                newRooms[index].imgId = imgId;
                setRooms(newRooms);

            } catch (error) {
                console.error('Image upload failed', error);
            }
        }
    };

    const addRoomForm = () => {
        setRooms([...rooms, {name: '', price: 0, detail: '', maxPeople: 1, quantity: 1, imgId: null}]);
    };

    const removeRoomForm = (index) => {
        const newRooms = rooms.filter((_, i) => i !== index);
        setRooms(newRooms);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            for (let i = 0; i < rooms.length; i++) {
                const formData = new FormData();
                formData.append('name', rooms[i].name);
                formData.append('price', rooms[i].price);
                formData.append('detail', rooms[i].detail);
                formData.append('maxPeople', rooms[i].maxPeople);
                formData.append('quantity', rooms[i].quantity)
                formData.append('hotelId', hotelId);

                if (rooms[i].imgId) {
                    formData.append('imgId', rooms[i].imgId);
                }

                await axios.post('http://localhost:8080/properties/room', formData, {withCredentials: true});
            }

            navigate(`/FacilityForm?addressId=${addressId}&categoryId=${categoryId}&hotelId=${hotelId}`, {state: {userData: userInfo}});
        } catch (error) {
            console.error('Error saving rooms', error);
        }
    };


    return (
        <Container className="mt-4">
            {userInfo ? (
                <>
                    <h4>4/5 단계</h4>
                    <h3>객실 정보 등록</h3>
                    <Form onSubmit={handleSubmit} style={{'margin-top': '5%'}}>
                        {rooms.map((room, index) => (
                            <div key={index} className="mb-3 p-3 border rounded">
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>객실 이름</Form.Label>
                                    <Col sm={10}>
                                        <Form.Control
                                            type="text"
                                            value={room.name}
                                            onChange={(e) => handleRoomChange(index, 'name', e.target.value)}
                                            placeholder="객실 이름"
                                            required
                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>가격</Form.Label>
                                    <Col sm={10}>
                                        <Form.Control
                                            type="number"
                                            value={room.price}
                                            onChange={(e) => handleRoomChange(index, 'price', Number(e.target.value))}
                                            placeholder="가격"
                                            required
                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>상세정보</Form.Label>
                                    <Col sm={10}>
                                        <Form.Control
                                            as="textarea"
                                            value={room.detail}
                                            onChange={(e) => handleRoomChange(index, 'detail', e.target.value)}
                                            placeholder="상세정보"
                                            required
                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>최대 인원</Form.Label>
                                    <Col sm={10}>
                                        <Form.Control
                                            type="number"
                                            value={room.maxPeople}
                                            onChange={(e) => handleRoomChange(index, 'maxPeople', Number(e.target.value))}
                                            placeholder="최대 인원"
                                            required
                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>방 수량</Form.Label>
                                    <Col sm={10}>
                                        <Form.Control
                                            type="number"
                                            value={room.quantity}
                                            onChange={(e) => handleRoomChange(index, 'quantity', Number(e.target.value))}
                                            placeholder="방 수량"
                                            required

                                        />
                                    </Col>
                                </Form.Group>
                                <Form.Group as={Row} className="mb-3">
                                    <Form.Label column sm={2}>이미지 업로드</Form.Label>
                                    <Col sm={10}>
                                        <input
                                            type="file"
                                            onChange={(e) => handleImageChange(index, e)}
                                            accept="image/*"
                                        />
                                    </Col>
                                </Form.Group>
                                <Button variant="danger" onClick={() => removeRoomForm(index)}>삭제</Button>
                            </div>
                        ))}
                        <div className="d-flex justify-content-between mt-3">
                            <Button variant="secondary" onClick={addRoomForm}>객실 추가</Button>
                            <Button variant="primary" type="submit">다음 단계로</Button>
                        </div>
                    </Form>
                </>
            ) : (<h1 className='text-center'>LOGIN이 필요합니다.</h1>)}
        </Container>
    );
};

export default RoomForm;

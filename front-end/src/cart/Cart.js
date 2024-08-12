import React, { useState, useEffect } from 'react';
import { Card, Col, Container, Row } from "react-bootstrap";
import CartSlice from "./components/CartSlice";
import PaySlice from "./components/PaySlice";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from "react-router-dom";
import Header from "../home/component/Header";
import * as calculate from '../js/calculate';

let Cart = () => {
    let [data, setData] = useState({ cartList: [] });
    let [selectedRoom, setSelectedRoom] = useState(null);
    let [totalDiscountedPrice, setTotalDiscountedPrice] = useState(0);

    useEffect(() => {
        let selectList = async () => {
            let resp = await axios
                .post('http://localhost:8080/cart/1', {
                    withCredentials: true
                })
                .catch((e) => {
                    console.error("There was a problem fetching the cart data:", e);
                });
            if (resp && resp.status === 200) {
                setData(resp.data);
            }
        };
        selectList();
    }, []);

    useEffect(() => {
        if (selectedRoom) {
            let price = calculate.calculateDiscountedPrice(calculate.calculatePrice(selectedRoom.checkInDate, selectedRoom.checkOutDate, selectedRoom.price), selectedRoom.userGrade);
            setTotalDiscountedPrice(price);
        } else {
            setTotalDiscountedPrice(0);
        }
    }, [selectedRoom]);

    let handleCheckboxChange = (room, isChecked) => {
        if (isChecked) {
            setSelectedRoom(room);
        } else {
            setSelectedRoom(null);
        }
    };

    let navigate = useNavigate();

    let moveToSingle = (id) => {
        navigate('/showOne/' + id);
    };

    let onDelete = async (id) => {
        try {
            let resp = await axios.post('http://localhost:8080/cart/delete', { id: id });
            if (resp.status === 200) {
                alert(resp.data.alertMessage);
                console.log('Item deleted successfully');
                navigate('/cart');
            } else {
                console.error('Failed to delete the item');
            }
        } catch (error) {
            console.error('Error occurred while deleting the item:', error);
        }
    };

    return (
        <Container>
            <Row>
                <Header/>
            </Row>
            <Row>
                <Col sm={8}>
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>
                                장바구니 갯수({data.cartList.length})
                            </Card.Title>
                        </Card.Body>
                    </Card>
                    {data.cartList.length === 0 ? (
                        <Card className="mb-4">
                            <Card.Body className={'text-center'}>
                                <p>{data.message}</p>
                                <p>{data.detailMessage}</p>
                            </Card.Body>
                        </Card>
                    ) : (
                        data.cartList.map(cart => (
                            <CartSlice
                                cart={cart}
                                key={cart.id}
                                moveToSingle={moveToSingle}
                                onDelete={onDelete}
                                handleCheckboxChange={handleCheckboxChange}
                                isSelected={selectedRoom?.id === cart.id}
                                isDisabled={selectedRoom && selectedRoom.id !== cart.id}
                            />
                        ))
                    )}
                </Col>
                <Col sm={4}>
                    <PaySlice totalDiscountedPrice={totalDiscountedPrice} selectedRoom={selectedRoom} />
                </Col>
            </Row>
        </Container>
    );
};

export default Cart;

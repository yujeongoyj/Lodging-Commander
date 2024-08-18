import React, {useEffect, useState} from 'react';
import {Card, Col, Container, Row} from 'react-bootstrap';
import CartSlice from './components/CartSlice';
import PaySlice from './components/PaySlice';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useLocation, useNavigate} from 'react-router-dom';
import * as calculate from '../js/calculate';

let Cart = () => {
    let location = useLocation();
    let userInfo = location.state?.userData;
    let [cartList, setCartList] = useState({cartList: []});
    let [selectedRoom, setSelectedRoom] = useState(null);
    let [totalDiscountedPrice, setTotalDiscountedPrice] = useState(0);
    let navigate = useNavigate();

    useEffect(() => {
        let fetchCartData = async () => {
            try {
                let resp = await axios.get(`http://localhost:8080/cart/${userInfo.id}`, {
                    withCredentials: true
                });

                if (resp.status === 200) {
                    setCartList(resp.data);
                } else {
                    setCartList({cartList: [], message: 'Error', detailMessage: 'Could not fetch data'});
                }
            } catch (error) {
                setCartList({cartList: [], message: 'Error', detailMessage: 'Could not fetch data'});
                console.error('Error fetching cart data:', error);
            }
        };

        if (userInfo) {
            fetchCartData();
        }
    }, [userInfo]);

    useEffect(() => {
        if (selectedRoom) {
            let price = calculate.calculateDiscountedPrice(
                calculate.calculatePrice(selectedRoom.checkInDate, selectedRoom.checkOutDate, selectedRoom.price),
                userInfo.grade
            );
            setTotalDiscountedPrice(price);
        } else {
            setTotalDiscountedPrice(0);
        }
    }, [selectedRoom, userInfo]);

    let handleCheckboxChange = (room, isChecked) => {
        setSelectedRoom(isChecked ? room : null);
    };

    let handleDelete = async (id) => {
        try {
            let resp = await axios.post('http://localhost:8080/cart/delete', {id},{
                withCredentials: true
            });
            if (resp.status === 200) {
                alert(resp.data.alertMessage);
                navigate('/cart', {
                    state: {
                        userData: userInfo
                    }
                });
            } else {
                console.error('Failed to delete the item');
            }
        } catch (error) {
            console.error('Error deleting the item:', error);
        }
    };


    return (
        <Container>
            <Row className="my-4">
                {userInfo ? (
                    <>
                        <Col sm={8}>
                            <Card className="mb-4">
                                <Card.Body>
                                    <Card.Title>
                                        장바구니 갯수 ({cartList.cartList ? cartList.cartList.length : 0})
                                    </Card.Title>
                                </Card.Body>
                            </Card>
                            {!cartList.cartList ? (
                                <Card className="mb-4">
                                    <Card.Body className="text-center">
                                        <p>장바구니가 비어 있습니다</p>
                                        <p>숙소를 검색하고 다음 여행 계획을 세워 보세요</p>
                                    </Card.Body>
                                </Card>
                            ) : (
                                cartList.cartList.map((cart) => (
                                    <CartSlice
                                        cart={cart}
                                        key={cart.id}
                                        moveToSingle={() =>  navigate(`/hotel/details/${cart.hotelId}`, {
                                            state: {
                                                userData:userInfo,
                                            }
                                        })}
                                        onDelete={handleDelete}
                                        handleCheckboxChange={handleCheckboxChange}
                                        isSelected={selectedRoom?.id === cart.id}
                                        isDisabled={selectedRoom && selectedRoom.id !== cart.id}
                                        userInfo={userInfo}
                                    />
                                ))
                            )}
                        </Col>
                        <Col sm={4}>
                            <PaySlice totalDiscountedPrice={totalDiscountedPrice} selectedRoom={selectedRoom}
                                      userInfo={userInfo}/>
                        </Col>
                    </>
                ) : (
                    <h1 className='text-center'>LOGIN이 필요합니다.</h1>
                )}
            </Row>
        </Container>
    );
};

export default Cart;

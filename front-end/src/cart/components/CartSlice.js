import React from 'react';
import { Card, Col, FormCheck, Image, Row } from "react-bootstrap";
import StarRating from "./StarRating";
import { FaExclamationTriangle, FaTrash } from 'react-icons/fa';
import dayjs from 'dayjs';
import * as calculate from '../../js/calculate';

let CartSlice = ({ cart, moveToSingle, onDelete, handleCheckboxChange, isSelected, isDisabled }) => {
    let today = dayjs().startOf('day');
    let checkInDate = dayjs(cart.checkInDate);

    let isSoldOut = checkInDate.isBefore(today);

    let originalPrice = calculate.calculatePrice(cart.checkInDate, cart.checkOutDate, cart.price);
    let discountedPrice = calculate.calculateDiscountedPrice(originalPrice, cart.userGrade);

    return (
        <Card className="mb-4 position-relative">
            <FaTrash
                className="position-absolute top-0 end-0 p-2 text-muted"
                style={{ cursor: 'pointer', fontSize: '2.5rem' }}
                onClick={() => onDelete(cart.id)}
            />
            <Card.Body>
                <Card.Title>
                    <Row>
                        <Col sm={4}>
                            <Image
                                src={cart.imgPath}
                                rounded
                                onClick={() => moveToSingle(cart.hotelId)}
                                style={{ cursor: 'pointer' }}
                            />
                        </Col>
                        <Col sm={8}>
                            <table>
                                <tbody>
                                <tr>
                                    <td>
                                        {cart.hotelName}<br /><br />
                                        <StarRating grade={cart.grade} /><br /><br />
                                        이용후기 {new Intl.NumberFormat().format(cart.reviewCount)}건
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </Col>
                    </Row>
                </Card.Title>
                <hr />
                <Card.Text>
                    {isSoldOut || !cart.isAvailable ? (
                        <p style={{ color: 'red' }}>
                            <FaExclamationTriangle style={{ marginRight: '8px' }} />
                            본 상품은 판매 완료되었습니다. 다른 옵션 또는 날짜로 검색해 보세요.
                        </p>
                    ) : (
                        <table className="w-100">
                            <tbody>
                            <tr>
                                <td>
                                    <Row>
                                        <Col sm={8}>
                                            <table>
                                                <tbody>
                                                <tr>
                                                    <td>
                                                        <FormCheck
                                                            id={`room-${cart.id}`}
                                                            aria-label={`Check option for room ${cart.id}`}
                                                            onChange={(e) => handleCheckboxChange(cart, e.target.checked)}
                                                            checked={isSelected}
                                                            disabled={isDisabled}
                                                        />
                                                    </td>
                                                    <td>{cart.roomName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td>{cart.checkInDate} - {cart.checkOutDate}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </Col>
                                        <Col sm={4} className="text-end">
                                            <p className="mb-0 text-muted">
                                                <del>₩ {new Intl.NumberFormat().format(originalPrice)}</del>
                                            </p>
                                            <h4 className="mb-0 text-danger">
                                                ₩ {new Intl.NumberFormat().format(discountedPrice)}
                                            </h4>
                                            <span style={{ fontSize: '0.75rem' }}>({cart.userGrade} 등급 할인 적용)</span>
                                        </Col>
                                    </Row>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    )}
                </Card.Text>
            </Card.Body>
        </Card>
    );
};

export default CartSlice;

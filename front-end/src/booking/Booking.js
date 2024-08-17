import {Button, Card, Col, Container, Image, Row} from "react-bootstrap";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

import {dataFormatter} from "../js/dataFormatter";
import * as calculate from '../js/calculate';
import axios from "axios";

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/booking/PeopleCounter.css'
import HotelFacility from "../hotel/components/HotelFacility";

const Booking = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const userInfo = location.state?.userData;
    const formDate = location.state?.formDate;

    const params = useParams();
    const roomId = parseInt(params.id);

    const [data, setData] = useState({RoomResponseDTO: {}});
    const [number, setNumber] = useState(1)
    const [facility, setFacility] = useState({FacilityList:[]})

    const totalPrice = formDate ? calculate.calculatePrice(formDate.checkInDate, formDate.checkOutDate, data.price) : 0;
    const discountedPrice = userInfo ? calculate.calculateDiscountedPrice(totalPrice, userInfo.grade) : totalPrice;

    const [inputs, setInputs] = useState({
        userId: userInfo?.id || '',
        roomId: roomId,
        checkInDate: formDate?.checkInDate || '',
        checkOutDate: formDate?.checkOutDate || '',
    });

    useEffect(() => {
        const booking = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/booking/${roomId}`, {
                    withCredentials: true
                })
                if (response.status === 200) {
                    setData(response.data.RoomResponseDTO);
                    setFacility(response.data.FacilityList);
                }
            } catch (e) {
                console.error("booking error", e);
            }
        }
        booking()
    }, [roomId]);

    useEffect(() => {
        setInputs(inputs => ({
            ...inputs,
            totalPeople: number,
            totalPrice: discountedPrice
        }));
    }, [number, discountedPrice]);

    const onDecrement = () => {
        if (number > 1) {
            setNumber(prevNumber => prevNumber - 1);
        }
    }
    const onIncrement = () => {
        if (number < data.maxPeople) {
            setNumber(prevNumber => prevNumber + 1);
        }
    }

    const goBookingList = () => {
        navigate('/bookingList', {state: {userData: userInfo}})
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        try {
            let request = await axios.post(`http://localhost:8080/booking/${roomId}`, inputs,{
                withCredentials: true
            });
            if (request.status !== undefined) {
                goBookingList()
            }
        } catch (e) {
            console.error("booking error", e);
        }
    }

    return (
        <Container>
            {userInfo && formDate ? (
                <>
                    <Row>
                        <Col className={"mb-3"}>
                            <Image
                                src={data.imgPath}
                                alt="room image"
                                rounded
                                fluid
                                style={{ objectFit: "cover", width: "100%", height: "300px" }}
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col xs={8}>
                            <Card className={"mb-3"}>
                                <Card.Body>
                                    <Card.Subtitle>{data.hotelName}</Card.Subtitle>
                                    <Card.Title>{data.roomName}</Card.Title>
                                    <Card.Text>{data.detail}</Card.Text>
                                </Card.Body>
                            </Card>
                            <Card>
                                <Card.Body>
                                    <Card.Title>편의시설</Card.Title>
                                    <Card.Text>
                                        <HotelFacility amenities={facility}/>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col xs={4}>
                            <Card>
                                <Card.Body>
                                    <Card.Title>결제 세부 정보</Card.Title>
                                    <form onSubmit={onSubmit}>
                                        <div style={{ display: "flex", justifyContent: "flex-start" }}>
                                            <p>{data.hotelName} - {data.roomName}</p>
                                        </div>
                                        <hr/>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>1박 요금</p>
                                            <p>₩ {dataFormatter.formatPrice(data.price)}</p>
                                        </div>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>최대 인원 수</p>
                                            <p>{data.maxPeople} 인</p>
                                        </div>
                                        <hr/>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>체크인</p>
                                            <p>{dataFormatter.formatDate(inputs.checkInDate)}</p>
                                        </div>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>체크아웃</p>
                                            <p>{dataFormatter.formatDate(inputs.checkOutDate)}</p>
                                        </div>
                                        <div>
                                            <p>예약 인원 수</p>
                                            <div className={"peopleCounter"}>
                                                <button type={"button"} onClick={onDecrement} disabled={number <= 1}>
                                                    <svg role="img" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                                        <path d="M6 12a1 1 0 0 0 1 1h10a1 1 0 1 0 0-2H7a1 1 0 0 0-1 1Z"></path>
                                                    </svg>
                                                </button>
                                                <input type={"number"} min={"1"} max={data.maxPeople} value={number} readOnly/>
                                                <button type={"button"} onClick={onIncrement} disabled={number >= data.maxPeople}>
                                                    <svg role="img" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                                        <path d="M12 7a1 1 0 0 0-1 1v3H8a1 1 0 1 0 0 2h3v3a1 1 0 1 0 2 0v-3h3a1 1 0 1 0 0-2h-3V8a1 1 0 0 0-1-1z"></path>
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <hr/>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>총 결제 금액</p>
                                            <p style={{ textDecoration: "line-through" }}>₩ {dataFormatter.formatPrice(totalPrice)}</p>
                                        </div>
                                        <div style={{ display: "flex", justifyContent: "space-between" }}>
                                            <p>할인된 금액</p>
                                            <p>₩ {dataFormatter.formatPrice(discountedPrice)}</p>
                                        </div>
                                        <div className={"d-grid gap-2"}>
                                            <Button type={"submit"}>결제 완료</Button>
                                        </div>
                                    </form>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </>
            ):(
                <h4 style={{ textAlign: "center" }}>잘못된 접근입니다. 다시 시도해주세요.</h4>
            )}
        </Container>
    )
}

export default Booking
import {useEffect, useState} from "react";
import {Button, Container, ListGroup, Nav} from "react-bootstrap";
import axios from "axios";
import BookingListCardItem from "./components/BookingListCardItem";
import {useLocation, useNavigate} from "react-router-dom";
import booking from "./Booking";

const BookingList = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const userInfo = location.state?.userData;

    const [data, setData] = useState({bookingList: []});

    const onValidBooking = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/validBooking/${userInfo.id}`, {withCredentials: true});
            if (response.status === 200) {
                setData(response.data);
            }
        } catch (e) {
            console.error("valid booking list error", e);
        }
    }
    const onExpiredBooking = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/expiredBooking/${userInfo.id}`, {withCredentials: true});
            if (response.status === 200) {
                setData(response.data);
            }
        } catch (e) {
            console.error("expired booking list error", e);
        }
    }
    const onCancelBooking = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/cancelBooking/${userInfo.id}`, {withCredentials: true});
            if (response.status === 200) {
                setData(response.data);
            }
        } catch (e) {
            console.error("cancel booking list error", e);
        }
    }

    const goLogin = () => {
        navigate("/Auth");
    }

    useEffect(() => {
        onValidBooking()
    }, []);

    return (
        <Container>
            <h2>내 예약</h2>
            {userInfo ? (
                <>
                    <div>
                        <Nav fill variant="tabs" defaultActiveKey="onValidBooking" className={"mb-3"}>
                            <Nav.Item>
                                <Nav.Link onClick={onValidBooking}>다가오는 예약</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link onClick={onExpiredBooking}>만료된 예약</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link onClick={onCancelBooking}>취소된 예약</Nav.Link>
                            </Nav.Item>
                        </Nav>

                        {data.bookingList.length !== 0 ? (
                            <ListGroup>
                                {data.bookingList.map((b) => (
                                    <BookingListCardItem booking={b} key={b.id} onCancelBooking={onCancelBooking} userInfo={userInfo}/>
                                ))}
                            </ListGroup>
                        ) : (
                            <h4 style={{ textAlign: 'center' }}>해당 예약 목록이 없습니다.</h4>
                        )}
                    </div>
                </>
            ) : (
                <div style={{ textAlign: 'center' }}>
                    <h4 className={'mb-3'}>로그인 후 이용해주세요.</h4>
                    <Button onClick={goLogin}>로그인</Button>
                </div>
            )}
        </Container>
    )
}

export default BookingList
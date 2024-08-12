import {Col, Container, Row} from "react-bootstrap";
import RoomSlice from "./components/RoomSlice";
import {useEffect, useState} from "react";
import axios from "axios";

let RoomList = () => {
    let [data, setData] = useState({roomList: []})
    let [hotelId, setHotelId]=useState(1)
    let [checkInDate, setCheckInDate] = useState('2024-08-22')
    let [checkOutDate, setCheckOutDate] = useState('2024-08-25')

    useEffect(() => {
        let selectList = async () => {
            // if (!hotelId || !checkInDate || !checkOutDate) return;

            try {
                let resp = await axios.get('http://localhost:8080/rooms', {
                    params: {
                        hotelId: hotelId,
                        checkInDate: checkInDate,
                        checkOutDate: checkOutDate
                    }
                });
                if (resp.status === 200) {
                    setData(resp.data);
                }
            } catch (error) {
                console.error('방 목록 조회 중 오류 발생:', error);
            }
        };
        selectList();
    }, [hotelId,checkInDate,checkOutDate]);
    return (
        <Container fluid>
            <Row className="custom-row">
                {data.roomList.map((room, index) => (
                    <Col md={4} key={room.id} className="custom-col mb-2">
                        <RoomSlice room={room} checkOutDate={checkOutDate} checkInDate={checkInDate}/>
                    </Col>
                ))}
            </Row>
        </Container>
    )
}

export default RoomList
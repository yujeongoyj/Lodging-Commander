import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { Button, ListGroup, Spinner, Alert, FloatingLabel, Form, Col, Row } from 'react-bootstrap';

const LikeList2 = () => {
    const location = useLocation();
    const userInfo = location.state?.userData?.userInfo || null;

    const [favorites, setFavorites] = useState([]);
    const [hotelNames, setHotelNames] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [checkInDate, setCheckInDate] = useState('');
    const [checkOutDate, setCheckOutDate] = useState('');
    const [minDate, setMinDate] = useState(getTodayDate());

    function getTodayDate() {
        let today = new Date();
        let year = today.getFullYear();
        let month = (today.getMonth() + 1).toString().padStart(2, '0');
        let day = today.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    useEffect(() => {
        if (checkInDate) {
            let checkInDateObj = new Date(checkInDate);
            let nextDay = new Date(checkInDateObj);
            nextDay.setDate(checkInDateObj.getDate() + 1);

            let year = nextDay.getFullYear();
            let month = (nextDay.getMonth() + 1).toString().padStart(2, '0');
            let day = nextDay.getDate().toString().padStart(2, '0');
            setMinDate(`${year}-${month}-${day}`);
        }
    }, [checkInDate]);

    useEffect(() => {
        const fetchFavorites = async () => {
            if (!userInfo?.id) {
                setError('사용자 ID가 없습니다.');
                setLoading(false);
                return;
            }

            try {
                // 사용자의 찜 목록을 가져옵니다.
                const resp = await axios.get(`http://localhost:8080/likelist/user/${userInfo.id}`, {
                    withCredentials: true
                });

                if (resp.status === 200) {
                    setFavorites(resp.data);

                    // 새로운 엔드포인트를 사용하여 호텔 세부 정보를 가져옵니다.
                    const hotelIds = resp.data.map(item => item.hotelId);
                    console.log('호텔 IDs:', hotelIds); // 디버깅: 호텔 ID 확인

                    // 각 호텔 ID에 대해 개별 요청을 보냅니다.
                    const hotelResponses = await Promise.all(
                        hotelIds.map(id => axios.get(`http://localhost:8080/hotel/details/${id}`, { withCredentials: true }))
                    );

                    // 디버깅: 호텔 응답 확인
                    hotelResponses.forEach(response => {
                        console.log('호텔 응답:', response.data);
                    });

                    // 호텔 이름을 매핑하여 상태를 업데이트합니다.
                    const hotelNameMap = hotelResponses.reduce((acc, response) => {
                        if (response.status === 200) {
                            acc[response.data.id] = response.data.hotelName;
                        }
                        return acc;
                    }, {});

                    setHotelNames(hotelNameMap);
                } else {
                    throw new Error('서버 오류');
                }
            } catch (error) {
                console.error('찜 목록 조회 중 오류 발생:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchFavorites();
    }, [userInfo, checkInDate]);

    const handleDelete = async (itemId) => {
        if (!userInfo?.id) {
            setError('사용자 ID가 없습니다.');
            return;
        }

        try {
            const resp = await axios.delete(`http://localhost:8080/likelist/${itemId}`, {
                withCredentials: true
            });
            if (resp.status === 200) {
                // 삭제된 항목을 찜 목록에서 제거합니다.
                setFavorites(favorites.filter(item => item.id !== itemId));
                const updatedHotelNames = { ...hotelNames };
                delete updatedHotelNames[itemId];
                setHotelNames(updatedHotelNames);
            } else {
                throw new Error('서버 오류');
            }
        } catch (error) {
            console.error('찜 목록 삭제 중 오류 발생:', error);
            setError(error.message);
        }
    };

    if (loading) return <Spinner animation="border" variant="primary" />;
    if (error) return <Alert variant="danger">Error: {error}</Alert>;

    return (
        <div className="container mt-4">
            {/*<Row className="mb-4">
                <Col xs="auto">
                    <FloatingLabel label="체크인 날짜" controlId="checkInDate" className="mb-3">
                        <Form.Control
                            type="date"
                            value={checkInDate}
                            min={getTodayDate()}
                            onChange={(e) => {
                                setCheckInDate(e.target.value);
                                if (new Date(e.target.value) > new Date(checkOutDate)) {
                                    setCheckOutDate(e.target.value);
                                }
                            }}
                        />
                    </FloatingLabel>
                </Col>
                <Col xs="auto">
                    <FloatingLabel label="체크아웃 날짜" controlId="checkOutDate" className="mb-3">
                        <Form.Control
                            type="date"
                            value={checkOutDate}
                            min={minDate}
                            onChange={(e) => setCheckOutDate(e.target.value)}
                        />
                    </FloatingLabel>
                </Col>
            </Row>
           */} <h1 className="mb-4">찜 목록</h1>
            {favorites.length === 0 ? (
                <Alert variant="info">찜한 항목이 없습니다.</Alert>
            ) : (
                <ListGroup>
                    {favorites.map((item) => (
                        <ListGroup.Item key={item.id} className="d-flex justify-content-between align-items-center">
                            <div>
                                <div><strong>호텔 ID:</strong> {item.hotelId}</div>
                                <div><strong>호텔 이름:</strong> {hotelNames[item.hotelId] || '정보 없음'}</div>
                            </div>
                            <Button variant="danger" onClick={() => handleDelete(item.id)}>삭제</Button>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            )}
        </div>
    );
};

export default LikeList2;
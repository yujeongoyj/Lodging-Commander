import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, ListGroup, Spinner, Alert } from 'react-bootstrap';
import {useLocation} from "react-router-dom";

const ReviewList = () => {
    const location = useLocation();
    const userInfo = location.state?.userData;

    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                //userid고정형 말고 받아온 데이터 넣도록 userinfo.id
                const resp = await axios.get(`http://localhost:8080/review/user/${userInfo.id}`, {
                    withCredentials: true
                });

                if (resp.status === 200) {
                    // 응답 데이터가 비어있거나 형식이 예상과 다를 수 있으므로 확인
                    if (resp.data && Array.isArray(resp.data.reviews)) {
                            setReviews(resp.data.reviews);
                    } else {
                        throw new Error('유효하지 않은 데이터 형식');
                    }
                } else {
                    throw new Error('서버 오류');
                }
            } catch (error) {
                console.error('리뷰 조회 중 오류 발생:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchReviews();
    }, []);

    const handleDelete = async (itemId) => {
        try {
            const resp = await axios.delete(`http://localhost:8080/review/delete/${itemId}`, {
                withCredentials: true
            });

            if (resp.status === 200) {
                setReviews(reviews.filter(review => review.id !== itemId));
            } else {
                throw new Error('서버 오류');
            }
        } catch (error) {
            console.error('리뷰 삭제 중 오류 발생:', error);
            setError(error.message);
        }
    };

    if (loading) return <Spinner animation="border" variant="primary" />;
    if (error) return <Alert variant="danger">Error: {error}</Alert>;

    return (
        <div className="container mt-4">
            <h1 className="mb-4">내가 작성한 리뷰</h1>
            {reviews.length === 0 ? (
                <Alert variant="info">작성한 리뷰가 없습니다.</Alert>
            ) : (
                <ListGroup>
                    {reviews.map((review) => (
                        <ListGroup.Item key={review.id} className="d-flex justify-content-between align-items-center">
                            <div>
                                <div><strong>호텔 ID:</strong> {review.hotelId}</div>
                                <div><strong>내용:</strong> {review.content}</div>
                                <div><strong>평점:</strong> {review.rating}</div>
                            </div>
                            <Button variant="danger" onClick={() => handleDelete(review.id)}>삭제</Button>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            )}
        </div>
    );
};

export default ReviewList;
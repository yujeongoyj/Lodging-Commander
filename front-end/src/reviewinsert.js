import React, { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const ReviewInsert = () => {
    const { state } = useLocation();
    const { hotelId, userData } = state || {};
    const navigate = useNavigate();

    const [rating, setRating] = useState('');
    const [comment, setComment] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const changePage = (pageName) => {
        navigate('/' + pageName, { state: { userData: userData } });
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!rating || !comment) {
            setErrorMessage('모든 필드를 입력해야 합니다.');
            return;
        }

        try {
            const response = await axios.post(`http://localhost:8080/review/add`, {
                hotelId,
                userId: userData.id, // userId가 필요할 경우 추가
                rating,
                content: comment
            }, {
                withCredentials: true
            });

            if (response.status === 201) { // 서버에서 201 Created로 응답하는 경우
                setSuccessMessage('리뷰가 성공적으로 제출되었습니다!');
                changePage('reviews');
            } else {
                throw new Error('리뷰 제출 실패');
            }
        } catch (error) {
            setErrorMessage('리뷰 제출 중 오류가 발생했습니다.');
            console.error('리뷰 제출 오류:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>리뷰 작성</h2>
            {successMessage && <Alert variant="success">{successMessage}</Alert>}
            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="rating">
                    <Form.Label>평점 (1-5)</Form.Label>
                    <Form.Control
                        type="number"
                        min="1"
                        max="5"
                        value={rating}
                        onChange={(e) => setRating(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="comment">
                    <Form.Label>코멘트</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit">
                    제출
                </Button>
            </Form>
        </div>
    );
};

export default ReviewInsert;
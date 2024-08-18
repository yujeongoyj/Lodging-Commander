import React, { useState } from 'react';
import {Form, Button, Alert, NavDropdown} from 'react-bootstrap';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';

const ReviewInsert = () => {
    const { state } = useLocation();
    const { hotelId, userData } = state || {};

    const [rating, setRating] = useState('');
    const [comment, setComment] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

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
                userId: userData?.id,  // 유저 ID를 서버에 전달
                rating,
                content: comment  // 여기를 수정해서 content로 보내도록 변경
            }, {
                withCredentials: true
            });

            if (response.data.result === 'success') {
                // 성공 시 이전 페이지로 리다이렉트하면서 성공 메시지를 전달
                changePage('reviews')
            } else {
                setErrorMessage('리뷰 제출 실패');
            }
        } catch (error) {
            setErrorMessage('리뷰 제출 중 오류가 발생했습니다.');
            console.error('리뷰 제출 오류:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>리뷰 작성</h2>
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
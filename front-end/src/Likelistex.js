import React, { useState, useEffect } from 'react';
import { FaHeart, FaRegHeart } from 'react-icons/fa'; // react-icons에서 아이콘 가져오기

function LikeList({ hotelId, userId }) {
    const [isLiked, setIsLiked] = useState(false);

    useEffect(() => {
        const checkIfLiked = async () => {
            try {
                const response = await fetch(`http://localhost:8000/likelist/check?hotelId=${hotelId}&userId=${userId}`);
                const data = await response.json();
                setIsLiked(data.isLiked);
            } catch (error) {
                console.error('Error fetching like status:', error);
            }
        };

        checkIfLiked();
    }, [hotelId, userId]);

    const handleButtonClick = async () => {
        try {
            if (isLiked) {
                // 좋아요 취소 (삭제)
                await fetch('http://localhost:8000/likelist/delete', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        hotelId,
                        userId
                    })
                });
            } else {
                // 좋아요 추가
                await fetch('http://localhost:8000/likelist/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        hotelId,
                        userId
                    })
                });
            }
            // 상태 업데이트
            setIsLiked(!isLiked);
        } catch (error) {
            console.error('Error updating like status:', error);
        }
    };

    return (
        <div onClick={handleButtonClick} style={{ cursor: 'pointer' }}>
            {isLiked ? <FaHeart color="#EE636A" size={35} /> : <FaRegHeart color="lightgrey" size={35} />}
        </div>
    );
}

export default LikeList;
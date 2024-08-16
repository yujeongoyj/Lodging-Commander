import React, { useEffect, useState } from 'react';

const LikeButton = ({ userId, hotelId }) => {
    const [isLiked, setIsLiked] = useState(false);

    // 서버에서 좋아요 상태를 가져오는 useEffect
    useEffect(() => {
        const fetchLikeStatus = async () => {
            try {
                const response = await fetch(`http://localhost:8080/likelist/${userId}/${hotelId}`);
                console.log(response);
                if (response.ok) {
                    const data = await response.json();
                    setIsLiked(data.isLiked); // 서버에서 가져온 상태로 업데이트
                    console.log('Fetched isLiked:', data.isLiked); // 상태를 콘솔에 출력
                } else {
                    console.error('Error fetching like status:', await response.text());
                }
            } catch (error) {
                console.error('Error fetching like status:', error);
            }
        };

        fetchLikeStatus();
    }, [userId, hotelId]);

    // 좋아요 상태를 토글하는 함수
    const handleLikeToggle = async () => {
        try {
            const action = isLiked ? 'remove' : 'add';
            const response = await fetch(`http://localhost:8080/likelist/${action}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', // 인증 쿠키를 포함
                body: JSON.stringify({ userId, hotelId }) // 요청 본문에 데이터 추가
            });
            if (response.ok) {
                setIsLiked(!isLiked); // 요청 성공 시 상태를 토글
            } else {
                console.error('Error toggling like status:', await response.text());
            }
        } catch (error) {
            console.error('Error toggling like status:', error);
        }
    };

    return (
        <div>
            <button onClick={handleLikeToggle} style={{ background: 'none', border: 'none', cursor: 'pointer' }}>
                <span
                    style={{
                        color: isLiked ? 'red' : 'grey', // isLiked 상태에 따라 색상 변경
                        fontSize: '24px',
                    }}
                >
                    ♥
                </span>
            </button>
        </div>
    );
};

export default LikeButton;
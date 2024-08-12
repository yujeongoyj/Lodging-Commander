import React, { useState, useEffect } from 'react';

function LikeList2({ userId }) {
    const [likeList, setLikeList] = useState([]);

    useEffect(() => {
        const fetchLikeList = async () => {
            try {
                const response = await fetch(`http://localhost:8000/likelist/user/${userId}`);
                const data = await response.json();
                setLikeList(data);
            } catch (error) {
                console.error('조회 불가 에러', error);
            }
        };

        fetchLikeList();
    }, [userId]);

    const handleDelete = async (hotelId) => {
        try {
            // 삭제 요청
            await fetch('http://localhost:8000/likelist/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userId,
                    hotelId
                })
            });

            // 성공적으로 삭제된 경우, 목록에서 제거
            setLikeList(likeList.filter(like => like.hotelId !== hotelId));
        } catch (error) {
            console.error('삭제 실패 에러', error);
        }
    };

    return (
        <div>
            <h2>호텔 찜 목록 조회</h2>
            {likeList.length > 0 ? (
                <ul>
                    {likeList.map((like) => (
                        <li key={like.hotelId}>
                            Hotel ID: {like.hotelId}
                            <button onClick={() => handleDelete(like.hotelId)} style={{ marginLeft: '10px' }}>
                                삭제
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>조회된 목록이 없습니다.</p>
            )}
        </div>
    );
}

export default LikeList2;
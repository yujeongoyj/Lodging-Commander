import React, { useState } from 'react';
import { FaHeart, FaRegHeart } from 'react-icons/fa'; // react-icons에서 아이콘 가져오기

function LikeList() {
    const [isLiked, setIsLiked] = useState(false);

    const handleButtonClick = () => {
        setIsLiked(!isLiked);
    };

    return (
        <div onClick={handleButtonClick} style={{ cursor: 'pointer' }}>
            {isLiked ? <FaHeart color="#EE636A" size={35} /> : <FaRegHeart color="lightgrey" size={35} />}
        </div>
    );
}

export default LikeList;
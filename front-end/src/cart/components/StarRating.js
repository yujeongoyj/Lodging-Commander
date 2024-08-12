import React from 'react';
import { FaStar } from 'react-icons/fa';

let StarRating = ({ grade }) => {
    let totalStars = 5;
    return (
        <div>
            {Array.from({ length: totalStars }, (_, index) => (
                <FaStar
                    key={index}
                    color={index < grade ? 'gold' : 'gray'}
                    size={24}
                />
            ))}
        </div>
    );
};

export default StarRating;

import React from 'react';
import { useNavigate } from 'react-router-dom';

const AddHotelSuccess = () => {
    const navigate = useNavigate();

    const handleGoHome = () => {
        navigate('/');
    };

    return (
        <div>
            <button onClick={handleGoHome}>
                홈으로 돌아가기
            </button>
        </div>
    );
};


export default AddHotelSuccess;

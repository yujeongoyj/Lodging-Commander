import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const HotelForm = () => {
    const [name, setName] = useState('');
    const [tel, setTel] = useState('');
    const [grade, setGrade] = useState(0);
    const [detail, setDetail] = useState('');
    const [addressId, setAddressId] = useState(null);
    const [categoryId, setCategoryId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();


    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        const categoryIdFromParams = queryParams.get('categoryId');

        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
        if (categoryIdFromParams) {
            setCategoryId(categoryIdFromParams);
        }
    }, [location.search]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const hotelDTO = {
                name,
                tel,
                grade,
                detail,
                addressId,
                categoryId
            };

            const response = await axios.post('http://localhost:8080/properties/hotel', hotelDTO);
            console.log(response);


            const hotelId = response.data.hotelId;
            navigate(`/RoomForm?addressId=${addressId}&categoryId=${categoryId}&hotelId=${hotelId}`);

        } catch (error) {
            console.error('Error saving hotel', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Hotel Name"
                    required
                />
            </div>
            <div>
                <label>Telephone:</label>
                <input
                    type="text"
                    value={tel}
                    onChange={(e) => setTel(e.target.value)}
                    placeholder="Telephone"
                    required
                />
            </div>
            <div>
                <label>Grade:</label>
                <input
                    type="number"
                    value={grade}
                    onChange={(e) => setGrade(Number(e.target.value))}
                    placeholder="Grade"
                    required
                />
            </div>
            <div>
                <label>Details:</label>
                <textarea
                    value={detail}
                    onChange={(e) => setDetail(e.target.value)}
                    placeholder="Details"
                    required
                />
            </div>
            <button type="submit">Save Hotel</button>
        </form>
    );
};

export default HotelForm;

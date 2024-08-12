import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const CategoryForm = () => {
    const [name, setName] = useState('');
    const [addressId, setAddressId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();


    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
    }, [location.search]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/properties/category', { name });
            console.log(response);
            const categoryId = response.data.categoryId;
            if (addressId) {
                navigate(`/HotelForm?addressId=${addressId}&categoryId=${categoryId}`);
            } else {
                console.error('Address ID is missing');
            }

        } catch (error) {
            console.error('Error saving category', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Category Name"
                required
            />
            <button type="submit">Next</button>
        </form>
    );
};

export default CategoryForm;

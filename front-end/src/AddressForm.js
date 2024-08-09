import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const AddressForm = ({ onNext }) => {
    const [address, setAddress] = useState('');
    const [addressDetail, setAddressDetail] = useState('');
    const [postCode, setPostCode] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = {
                address,
                addressDetail,
                postCode,
                latitude: parseFloat(latitude),
                longitude: parseFloat(longitude)
            };
            const response = await axios.post('http://localhost:8080/properties/address', data);
            const addressId = response.data.addressId;

            navigate(`/CategoryForm?addressId=${addressId}`);
        } catch (error) {
            console.error('Error saving address', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                placeholder="Address"
                required
            />
            <input
                type="text"
                value={addressDetail}
                onChange={(e) => setAddressDetail(e.target.value)}
                placeholder="Address Detail"
                required
            />
            <input
                type="text"
                value={postCode}
                onChange={(e) => setPostCode(e.target.value)}
                placeholder="Post Code"
                required
            />
            <input
                type="number"
                step="any"
                value={latitude}
                onChange={(e) => setLatitude(e.target.value)}
                placeholder="Latitude"
                required
            />
            <input
                type="number"
                step="any"
                value={longitude}
                onChange={(e) => setLongitude(e.target.value)}
                placeholder="Longitude"
                required
            />
            <button type="submit">Next</button>
        </form>
    );
};

export default AddressForm;

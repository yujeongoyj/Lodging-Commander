import React, { useState } from 'react';
import axios from 'axios';

function AddressForm() {
    const [addressDTO, setAddressDTO] = useState({
        address: '',
        addressDetail: '',
        postCode: '',
        latitude: '',
        longitude: ''
    });

    // 입력 값 변경 처리
    const handleChange = (e) => {
        const { name, value } = e.target;
        setAddressDTO({
            ...addressDTO,
            [name]: value
        });
    };

    // 폼 제출 처리
    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post('http://localhost:8080/properties/address', addressDTO)
            .then(response => {
                console.log('Address saved', response.data);
                window.location.href = `/properties/category?addressId=${response.data}`;
            })
            .catch(error => {
                console.error('Error saving address', error);
            });
    };

    return (
        <div>
            <h1>주소 입력</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="address">주소:</label>
                <input
                    type="text"
                    id="address"
                    name="address"
                    value={addressDTO.address}
                    onChange={handleChange}
                    required
                /><br/>

                <label htmlFor="addressDetail">상세 주소:</label>
                <input
                    type="text"
                    id="addressDetail"
                    name="addressDetail"
                    value={addressDTO.addressDetail}
                    onChange={handleChange}
                /><br/>

                <label htmlFor="postCode">우편번호:</label>
                <input
                    type="text"
                    id="postCode"
                    name="postCode"
                    value={addressDTO.postCode}
                    onChange={handleChange}
                    required
                /><br/>

                <label htmlFor="latitude">위도:</label>
                <input
                    type="number"
                    step="0.000001"
                    id="latitude"
                    name="latitude"
                    value={addressDTO.latitude}
                    onChange={handleChange}
                    required
                /><br/>

                <label htmlFor="longitude">경도:</label>
                <input
                    type="number"
                    step="0.000001"
                    id="longitude"
                    name="longitude"
                    value={addressDTO.longitude}
                    onChange={handleChange}
                    required
                /><br/>

                <button type="submit">다음</button>
            </form>
        </div>
    );
}

export default AddressForm;

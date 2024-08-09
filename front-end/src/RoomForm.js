import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const RoomForm = () => {
    const [rooms, setRooms] = useState([{ name: '', price: 0, detail: '', maxPeople: 0, imgId: null }]); // 초기 상태에 한 개의 방 폼 포함
    const [hotelId, setHotelId] = useState(null);
    const [addressId, setAddressId] = useState(null);
    const [categoryId, setCategoryId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const addressIdFromParams = queryParams.get('addressId');
        const categoryIdFromParams = queryParams.get('categoryId');
        const hotelIdFromParams = queryParams.get('hotelId');

        if (addressIdFromParams) {
            setAddressId(addressIdFromParams);
        }
        if (categoryIdFromParams) {
            setCategoryId(categoryIdFromParams);
        }
        if (hotelIdFromParams) {
            setHotelId(hotelIdFromParams);
        }
    }, [location.search]);

    const handleRoomChange = (index, field, value) => {
        const newRooms = [...rooms];
        newRooms[index][field] = value;
        setRooms(newRooms);
    };

    const handleImageChange = async (index, e) => {
        const file = e.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await axios.post('http://localhost:8080/properties/uploadImage', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });

                const imgId = response.data.imgId; // 서버에서 반환된 imgId

                const newRooms = [...rooms];
                newRooms[index].imgId = imgId;
                setRooms(newRooms);

            } catch (error) {
                console.error('Image upload failed', error);
            }
        }
    };

    const addRoomForm = () => {
        setRooms([...rooms, { name: '', price: 0, detail: '', maxPeople: 0, imgId: null }]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            for (let i = 0; i < rooms.length; i++) {
                const roomDTO = {
                    name: rooms[i].name,
                    price: rooms[i].price,
                    detail: rooms[i].detail,
                    maxPeople: rooms[i].maxPeople,
                    hotelId: hotelId,
                    imgId: rooms[i].imgId,
                };

                await axios.post('http://localhost:8080/properties/room', roomDTO);
            }

            navigate(`/FacilityForm?addressId=${addressId}&categoryId=${categoryId}&hotelId=${hotelId}`);
        } catch (error) {
            console.error('Error saving rooms', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {rooms.map((room, index) => (
                <div key={index}>
                    <div>
                        <label>객실 이름:</label>
                        <input
                            type="text"
                            value={room.name}
                            onChange={(e) => handleRoomChange(index, 'name', e.target.value)}
                            placeholder="객실 이름"
                            required
                        />
                    </div>
                    <div>
                        <label>가격:</label>
                        <input
                            type="number"
                            value={room.price}
                            onChange={(e) => handleRoomChange(index, 'price', Number(e.target.value))}
                            placeholder="가격"
                            required
                        />
                    </div>
                    <div>
                        <label>상세정보:</label>
                        <textarea
                            value={room.detail}
                            onChange={(e) => handleRoomChange(index, 'detail', e.target.value)}
                            placeholder="상세정보"
                            required
                        />
                    </div>
                    <div>
                        <label>최대 인원:</label>
                        <input
                            type="number"
                            value={room.maxPeople}
                            onChange={(e) => handleRoomChange(index, 'maxPeople', Number(e.target.value))}
                            placeholder="최대 인원"
                            required
                        />
                    </div>
                    <div>
                        <label>이미지 업로드:</label>
                        <input
                            type="file"
                            onChange={(e) => handleImageChange(index, e)}
                            accept="image/*"
                        />
                    </div>
                </div>
            ))}
            <button type="button" onClick={addRoomForm}>객실 추가</button>
            <button type="submit">다음 단계로</button>
        </form>
    );
};

export default RoomForm;

import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {Route, Routes} from 'react-router-dom';
import Info from './Info';

const UserProfile = () => {
    const [userData, setUserData] = useState({
        email: '',
        password: '',
        nickname: '',
        tel: '',
        grade: '',
        role: ''
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user/checkUser', {
                    withCredentials: true
                })
                if (response.status === 200 && response.data) {
                    setUserData({
                        email: response.data.email,
                        nickname: response.data.nickname,
                        password: response.data.password,
                        tel: response.data.tel,
                        grade: response.data.grade,
                        role: response.data.role,
                    });
                }
            } catch (error) {
                console.error('사용자 데이터를 가져오는 중 오류가 발생했습니다:', error);
            }
        };
        fetchData();
    }, []);

    return (
        <Routes>
            <Route path="/" element={<Info userData={userData} setUserData={setUserData}/>}/>
        </Routes>
    );
}

export default UserProfile;

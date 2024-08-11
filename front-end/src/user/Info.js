import React from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from "axios";

const Info = () => {
    const location = useLocation()
    const navigate = useNavigate()
    const {userData, data} = location.state || {};

    const onLogout = async () => {
        try {
            let response = await axios.post('http://localhost:8080/user/logOutSuccess', {
                withCredentials: true,
            });
            if (response.status === 200) {
                navigate('/')
            }
        } catch (error) {
            console.error('로그아웃 에러:', error)
        }
    }

    let goBack = () => {
        navigate(-1)
    }

    let onUpdate = () => {
    navigate('/user/update' , {state: {userData, data}});
    }

    return (
        <div>
            <h1>마이페이지</h1>
            <p>이메일 : {userData?.email}</p>
            <p>닉네임 : {userData?.nickname}</p>
            <p>연락처 : {userData?.tel}</p>
            <p>등급 : {userData?.grade}</p>
            <p>권한 : {userData?.role}</p>
            <p>추가 데이터: {JSON.stringify(data)}</p>
            <button onClick={onUpdate}>회원수정</button>
            <button onClick={goBack}>뒤로가기</button>
            <button onClick={onLogout}>로그아웃</button>
        </div>
    );
};

export default Info;

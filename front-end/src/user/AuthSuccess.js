import React from 'react';
import {useLocation} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const AuthSuccess = () => {
    const location = useLocation();
    const {userData} = location.state; // userData로부터 데이터 추출
    const {id, email, nickname , tel, grade, role} = userData;

    return (
        <div>
            <h1>인증 성공</h1>
            <p>회원번호 : {id}</p>
            <p>이메일 : {email}</p>
            <p>닉네임 : {nickname}</p>
            <p>전화번호 : {tel}</p>
            <p>등급 : {grade}</p>
            <p>권한 : {role}</p>
        </div>
    );
};

export default AuthSuccess;

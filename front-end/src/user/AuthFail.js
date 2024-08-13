import React from 'react';
import {useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';


const AuthFail = () => {

    let navigate = useNavigate()

    let goBack = () => {
        navigate(-1)
    }

    return (
        <div>
            <h1>인증 실패</h1>
            <p>로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.</p>
            <button onClick={goBack}>뒤로가기</button>
        </div>
    );
};

export default AuthFail;
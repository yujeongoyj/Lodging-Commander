import React, {useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';

const Home = () => {
    const location = useLocation()
    const navigate = useNavigate()
    const {userData} = location.state
    const {id, email, password, nickname, tel, grade, role} = userData || {};
    const [data, setData] = useState({userDTO: {}});  // 초기 상태 설정

    React.useEffect(() => {
        if (!userData) {
            navigate('/logIn');
        }
    }, [userData, navigate]);

    const onLogout = async () => {
        try {
            let response = await axios.post('http://localhost:8080/user/logOutSuccess', {
                withCredentials: true,
            });
            if (response.status === 200) {
                navigate('/')
            }
        } catch (error) {
            console.error('로그아웃 에러:', error);
        }
    };

    let goBack = () => {
        navigate(-1)
    }

    const goToUserPage = () => {
        navigate('/user/info', {state: {userData, data}});
    };

    const goToAdminPage = () => {
        window.location.href = '/user/admin';
    };

    if (!userData) {
        return null; // userData가 없으면 아무것도 렌더링하지 않음
    }

    return (
        <div>
            <h1>사용자 정보</h1>
            <p>이메일 : {email}</p>
            <p>닉네임 : {nickname}</p>
            <p>권한: {role}</p>
            <button onClick={goBack}>뒤로가기</button>
            <button onClick={onLogout}>로그아웃</button>
            <button onClick={goToUserPage}>마이 페이지</button>
            <button onClick={goToAdminPage}>어드민 페이지</button>
        </div>
    )
}

export default Home;

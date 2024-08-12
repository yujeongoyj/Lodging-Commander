import React, {useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, ButtonGroup, Col, Container, Row, Table} from "react-bootstrap";

const Home = () => {
    const location = useLocation()
    const navigate = useNavigate()
    const {userData} = location.state
    const {email, nickname, role} = userData || {};
    const [data] = useState({userDTO: {}});  // 초기 상태 설정

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
    }

    const goToAdminPage = () => {
        window.location.href = '/user/admin';
    }

    if (!userData) {
        return null; // userData가 없으면 아무것도 렌더링하지 않음
    }

    return (
        <Container>
            <Row className="justify-content-center">
                <Col xs={13} sm={10} md={6} lg={5}>
                    <Table className={"text-center"} striped hover bordered>
                        <thead>
                        <tr>
                            <td><h3>사용자 정보</h3></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>이메일 : {email}</td>
                        </tr>
                        <tr>
                            <td>닉네임 : {nickname}</td>
                        </tr>
                        <tr>
                            <td>권한: {role}</td>
                        </tr>
                        <tr>
                            <td>
                                <ButtonGroup className="gap-3">
                                    <Button onClick={goBack}>뒤로가기</Button>
                                    <Button onClick={onLogout}>로그아웃</Button>
                                    <Button onClick={goToUserPage}>마이 페이지</Button>
                                    <Button onClick={goToAdminPage}>어드민 페이지</Button>
                                </ButtonGroup>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </Container>
    )
}

export default Home;
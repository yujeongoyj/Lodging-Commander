import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, ButtonGroup, Col, Container, Row, Table} from "react-bootstrap";

const MyPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const userInfo = location.state?.userData;
    const [userData, setUserData] = useState({
        id: '',
        nickname: '',
        email: '',
        tel: '',
        grade: '',
        role: '',
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUserData = async () => {
            if (!userInfo?.id) {
                setError('사용자 정보가 없습니다.');
                setLoading(false);
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/user/checkUser/${userInfo.id}`, {
                    withCredentials: true
                });

                if (response.status === 200) {
                    setUserData(response.data);
                } else {
                    throw new Error('사용자 정보를 가져오는데 실패했습니다.');
                }
            } catch (error) {
                console.error('사용자 정보 조회 중 오류 발생:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        if (!userInfo) {
            navigate('/logIn');
        } else {
            fetchUserData();
        }
    }, [userInfo, navigate]);

    const onLogout = async () => {
        try {
            let response = await axios.post('http://localhost:8080/user/logOutSuccess',
                {withCredentials: true});
            if (response.status === 200) {
                navigate('/');
            }
        } catch (error) {
            console.error('로그아웃 에러:', error);
        }
    };

    const goBack = () => {
        navigate(-1);
    };

    const goToUserPage = () => {
        navigate('/user/info', {state: {userInfo}});
    };

    const goToAdminPage = () => {
        window.location.href = '/user/admin';
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p style={{color: 'red'}}>{error}</p>;
    }

    return (
        <Container>
            <Row className="justify-content-center">
                <Col xs={13} sm={10} md={6} lg={5}>
                    <Table className={"text-center"} striped hover bordered>
                        <thead>
                        <tr>
                            <td colSpan={2}><h3>사용자 정보</h3></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>아이디</td>
                            <td>{userData.id}</td>
                        </tr>
                        <tr>
                            <td>이메일</td>
                            <td>{userData.email}</td>
                        </tr>
                        <tr>
                            <td>닉네임</td>
                            <td>{userData.nickname}</td>
                        </tr>
                        <tr>
                            <td>전화번호</td>
                            <td>{userData.tel}</td>
                        </tr>
                        <tr>
                            <td>등급</td>
                            <td>{userData.grade}</td>
                        </tr>
                        <tr>
                            <td>권한</td>
                            <td>{userData.role}</td>
                        </tr>
                        <tr>
                            <td colSpan={2}>
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
    );
};

export default MyPage;

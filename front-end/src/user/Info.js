import React from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, ButtonGroup, Col, Container, Row, Table} from "react-bootstrap";

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
        navigate('/user/update', {state: {userData, data}});
    }

    return (<Container>
        <Row className="justify-content-center">
            <Col xs={13} sm={10} md={6} lg={5}>
                <Table className={"text-center"} striped hover bordered>
                    <thead>
                    <tr>
                        <th colSpan={2}><h3>마이페이지</h3></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>이메일</td>
                        <td>{userData?.email}</td>
                    </tr>
                    <tr>
                        <td>닉네임</td>
                        <td>{userData?.nickname}</td>
                    </tr>
                    <tr>
                        <td>연락처</td>
                        <td>{userData?.tel}</td>
                    </tr>
                    <tr>
                        <td>등급</td>
                        <td>{userData?.grade}</td>
                    </tr>
                    <tr>
                        <td>권한</td>
                        <td>{userData?.role}</td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <ButtonGroup className="gap-3">
                                <Button onClick={onUpdate}>회원수정</Button>
                                <Button onClick={goBack}>뒤로가기</Button>
                                <Button onClick={onLogout}>로그아웃</Button>
                            </ButtonGroup>
                        </td>
                    </tr>
                    </tbody>
                </Table>
            </Col>
        </Row>
    </Container>);
};

export default Info;

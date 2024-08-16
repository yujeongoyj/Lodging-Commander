import React, {useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, Col, Container, FormControl, Row, Table} from "react-bootstrap";
import '../css/user/Register.css';

function Register() {
    const [user, setUser] = useState({
        email: '',
        password: '',
        nickname: '',
        tel: '',
        grade: 'Silver',
        role: 'USER'
    });

    const [alertVisible, setAlertVisible] = useState(false);

    const handleChange = (e) => {
        const {id, value} = e.target;
        setUser({...user, [id]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/user/register', user);
            setAlertVisible(true);
            setTimeout(() => {
                setAlertVisible(false);
                window.location.href = '/';
            }, 4000);  // 4초 후에 alert가 사라지고 페이지가 이동
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <Container>
            <Row className="justify-content-center">
                <Col xs={13} sm={10} md={6} lg={5}>
                    {alertVisible && (
                        <div className="alert-container">
                            회원가입 완료
                        </div>
                    )}
                    <form onSubmit={handleSubmit}>
                        <Table striped hover bordered>
                            <thead className={"text-center"}>
                            <tr>
                                <td colSpan={2}><h3>회원가입</h3></td>
                            </tr>
                            </thead>
                            <tbody className={"text-center"}>
                            <tr>
                                <td>이메일</td>
                                <td>
                                    <FormControl
                                        type="text" id="email" value={user.email} placeholder="이메일"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>비밀번호</td>
                                <td>
                                    <FormControl
                                        type="password"
                                        id="password"
                                        value={user.password}
                                        placeholder="비밀번호"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>닉네임</td>
                                <td>
                                    <FormControl
                                        type="text"
                                        id="nickname"
                                        value={user.nickname}
                                        placeholder="닉네임"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>연락처</td>
                                <td>
                                    <FormControl
                                        type="text"
                                        id="tel"
                                        value={user.tel}
                                        placeholder="연락처"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr className={"text-center"}>
                                <td colSpan={2}>
                                    <Button type="submit">회원가입</Button>
                                </td>
                            </tr>
                            </tbody>
                        </Table>
                    </form>
                </Col>
            </Row>
        </Container>
    );
}

export default Register;

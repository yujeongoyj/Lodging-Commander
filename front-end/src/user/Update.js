import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Button, ButtonGroup, Col, Container, FormControl, Row, Table} from "react-bootstrap";

function Update() {
    const [user, setUser] = useState({
        password: '',
        nickname: '',
        tel: ''
    });
    const navigate = useNavigate()

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user/checkUser', {
                    withCredentials: true,
                });
                if (response.status === 200 && response.data) {
                    setUser({
                        password: '',
                        nickname: response.data.nickname || '',
                        tel: response.data.tel || ''
                    });
                }
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };
        fetchData();
    }, []);

    const handleChange = (e) => {
        const {id, value} = e.target;
        setUser({...user, [id]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.put('http://localhost:8080/user/update', user, {
                withCredentials: true,
            });
            console.log(response);
            alert('회원 정보가 수정되었습니다.');
        } catch (error) {
            console.error('Error updating user:', error);
        }
    };

    const handleDelete = async () => {
        const confirmation = window.confirm("정말로 탈퇴하시겠습니까?")
        if (confirmation) {
            try {
                await axios.delete(`http://localhost:8080/user/delete`, {
                    withCredentials: true
                })
                alert('회원 탈퇴가 완료되었습니다.')
                navigate('/')
            } catch (error) {
                console.error('Error deleting user:', error)
                alert('회원 탈퇴 중 문제가 발생했습니다. 다시 시도해 주세요.')
            }
        } else {
            alert('탈퇴가 취소되었습니다.')
        }
    };


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

    return (
        <Container>
            <Row className="justify-content-center">
                <Col xs={13} sm={10} md={6} lg={5}>
                    <form onSubmit={handleSubmit}>
                        <Table className={"text-center"} striped hover bordered>
                            <thead>
                            <tr>
                                <td>
                                    <h3>회원 정보 수정</h3>
                                </td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <FormControl
                                        type="password"
                                        id="password"
                                        value={user.password}
                                        placeholder="새 비밀번호"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <FormControl
                                        type="text" id="nickname" value={user.nickname} placeholder="새 닉네임"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <FormControl
                                        type="text"
                                        id="tel"
                                        value={user.tel}
                                        placeholder="새 연락처"
                                        onChange={handleChange}
                                    />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <Button type="submit">수정</Button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <ButtonGroup className={"gap-3"}>
                                        <Button onClick={handleDelete}>회원 탈퇴</Button>
                                        <Button onClick={goBack}>뒤로가기</Button>
                                        <Button onClick={onLogout}>로그아웃</Button>
                                    </ButtonGroup>
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

export default Update;

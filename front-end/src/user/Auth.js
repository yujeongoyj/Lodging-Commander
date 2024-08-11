import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, Container, FormControl, Table, Alert} from "react-bootstrap";
import axios from "axios";

let Auth = () => {
    let [inputs, setInputs] = useState({
        email: '',
        password: ''
    });

    let [error, setError] = useState('');
    let navigate = useNavigate();

    let onChange = (e) => {
        let {name, value} = e.target;
        console.log(name, value)
        setInputs({
            ...inputs, [name]: value
        });
    };

    let onSubmit = async (e) => {
        e.preventDefault();
        try {
            let formData = new FormData()
            formData.append('email', inputs.email)
            formData.append('password', inputs.password)

            let response = await axios({
                url: 'http://localhost:8080/user/auth',
                method: 'POST',
                data: formData,
                withCredentials: true
            })

            if (response.data.result === 'success') {
                navigate('/user/authSuccess', { state: { userData: response.data } });
            } else {
                navigate('/user/authFail');
            }
        } catch (error) {
            setError('서버에 문제가 발생했습니다. 나중에 다시 시도하세요.');
        }
    };

    let onRegister = () => {
        navigate('/user/register');
    };

    return (
        <Container>
            <form onSubmit={onSubmit}>
                <Table striped hover bordered>
                    <thead>
                    <tr>
                        <td colSpan={2}>로그인</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>이메일</td>
                        <td>
                            <FormControl
                                type={'text'}
                                name={'email'}
                                value={inputs.email}
                                onChange={onChange}/>
                        </td>
                    </tr>
                    <tr>
                        <td>비밀번호</td>
                        <td>
                            <FormControl
                                type={'password'}
                                name={'password'}
                                value={inputs.password}
                                onChange={onChange}/>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <Button type={'submit'}>로그인</Button>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <Button onClick={onRegister}>회원가입</Button>
                        </td>
                    </tr>
                    {error && (
                        <tr>
                            <td colSpan={2}>
                                <Alert variant="danger">{error}</Alert>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </form>
        </Container>
    );
};

export default Auth;

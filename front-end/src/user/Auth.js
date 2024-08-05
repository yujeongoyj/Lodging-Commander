import {Button, Container, FormControl, Table} from "react-bootstrap";
import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";


let Auth = () => {

    let [inputs, setInputs] = useState({
        username: '',
        password: ''
    });

    let onChange = (e) => {
        let {name, value} = e.target;
        setInputs({
            ...inputs,
            [name]: value
        })
    }

    let navigate = useNavigate()

    let register = () => {
        navigate('/user/register')
    }

    let onSubmit = async (e) => {
        e.preventDefault()
        let formData = new FormData()
        formData.append('username', inputs.username)
        formData.append('password', inputs.password)

        let response = await axios({
            url: 'http://localhost:8080/user/auth',
            method: 'POST',
            data: formData,
            withCredentials: true
        })

        if (response.status === 200 && response.data.result === 'success') {
            let userInfo = {
                email: response.data.email,
                tel: response.data.tel,
                grade: response.data.grade,
                role: response.data.role
            }
            navigate('/user/logInClear', {state: {userInfo: userInfo}}) // 임시 네비게이트
        }
    }
    return (
        <Container>
            <form onSubmit={onSubmit}>
                <Table striped hover boarded>
                    <thead>
                    <tr>
                        <td colSpan={2}>로그인</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>아이디</td>
                        <td>
                            <FormControl
                                type={'text'}
                                name={'username'}
                                value={inputs.username}
                                onChange={onChange}
                            />
                        </td>
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
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <Button type={'submit'}>로그인</Button>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <Button onClick={register}>회원가입</Button>
                        </td>
                    </tr>
                    </tbody>
                </Table>
            </form>
        </Container>
    )
}

export default Auth
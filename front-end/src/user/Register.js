import React, {useState} from 'react';
import axios from 'axios';

function Register() {
    const [user, setUser] = useState({
        email: '',
        password: '',
        nickname:'',
        tel: '',
        grade: 'Silver',
        role: 'USER'
    });

    const handleChange = (e) => {
        const {id, value} = e.target;
        setUser({...user, [id]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/user/register', user);
            console.log(response)
            alert('회원가입 완료');
            window.location.href = '/';
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div>
            <h3>회원가입</h3>
            <form onSubmit={handleSubmit}>
                <input type="text" id="email" value={user.email} placeholder="이메일" onChange={handleChange}/>
                <input type="password" id="password" value={user.password} placeholder="비밀번호" onChange={handleChange}/>
                <input type="text" id="nickname" value={user.nickname} placeholder="닉네임" onChange={handleChange}/>
                <input type="text" id="tel" value={user.tel} placeholder="연락처" onChange={handleChange}/>
                <button type="submit">회원가입</button>
            </form>
        </div>
    );
}

export default Register;
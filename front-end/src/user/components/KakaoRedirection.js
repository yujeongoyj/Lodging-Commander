import React, {useEffect} from "react";
import {Container} from "react-bootstrap";
import Kakao from "../../Kakao";
import axios, {post} from "axios";
import {useNavigate} from "react-router-dom";

const kakaoRedirection = () => {

    const code = window.location.search
    const navigate = useNavigate()

    useEffect(() => {
        axios.post(`${process.env.REACT_APP_URL}kakaologIn${code}`).then((r) => {
            console.log(r.data)
            localStorage.setItem('name', r.data.username)

            navigate('/user/authSuccess')
        })
    }, [])

    return (
        <Container>

        </Container>
    )


}

export default kakaoRedirection
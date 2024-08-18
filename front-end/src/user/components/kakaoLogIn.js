import {Button} from "react-bootstrap";

const kakaoLogIn = () => {

    const restAPIKey = "5b37fa5721f812f7997d7ce25c0b792b"
    const redirectKey = "http://localhost:3000/KAKAOLogIn"
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${restAPIKey}&redirect_uri=${redirectKey}&response_type=code`

    const HandleLogin = () => {
        window.location.href = kakaoURL
    }

    return (
        <Button onClick={HandleLogin}>
            로그인
        </Button>
    )
}

export default kakaoLogIn

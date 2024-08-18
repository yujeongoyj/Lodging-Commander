import KakaoLogin from "react-kakao-login";
import React from "react";
import {Container} from "react-bootstrap";

function Loading() {
    return null;
}

function kakaoCallBack() {

    const kakaoClientId = "6d2fc462cb416b7bda83569592aeed42"
    const kakaoSuccess = async (data) => {
        console.log(data)
        const idToken = data.response.access_token
    }
    const kakaoOnFailure = (error) => {
        console.log(error)
    }

    return (
        <Container>
            <KakaoLogin
                token={kakaoClientId}
                onSuccess={kakaoSuccess}
                onFail={kakaoOnFailure}
            />
        </Container>
    )


}

export default kakaoCallBack;
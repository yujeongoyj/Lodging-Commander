import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useLocation, useNavigate} from 'react-router-dom';
import {Container, Form, Button, Row, Col, Alert} from 'react-bootstrap';
import {any} from "prop-types";

const AddressForm2 = () => {
    const [address, setAddress] = useState('');
    const [addressDetail, setAddressDetail] = useState('');
    const [postCode, setPostCode] = useState('');
    const [extraAddress, setExtraAddress] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();
    const location = useLocation();
    const userInfo = location.state?.userData;

    useEffect(() => {
        const loadDaumPostcodeScript = () => {
            const postmapScript = document.createElement('script');
            postmapScript.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
            postmapScript.async = true;

            const mapScript = document.createElement('script');
            mapScript.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=2352038d1f2d9450032dd17ae632df20&libraries=services'

            const mapContainer = document.getElementById('map'),
                mapOption = {
                    center: new window.daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
                    level: 5 // 지도의 확대 레벨
                };

            //지도를 미리 생성
            const map = new window.daum.maps.Map(mapContainer, mapOption);

            const geocoder = new window.daum.maps.services.Geocoder();

            const marker = new window.daum.maps.Marker({
                position: new window.daum.maps.LatLng(37.537187, 127.005476),
                map: map
            });


            document.body.appendChild(postmapScript);
            postmapScript.onload = () => {
                if (window.daum && window.daum.Postcode) {
                    window.daum.Postcode({
                        oncomplete: function (data) {
                            const addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                            let extraAddr = '';

                            if (data.userSelectedType === 'R') {
                                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                                    extraAddr += data.bname;
                                }
                                if (data.buildingName !== '' && data.apartment === 'Y') {
                                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                                }
                                if (extraAddr !== '') {
                                    extraAddr = ' (' + extraAddr + ')';
                                }
                            } else {
                                extraAddr = '';
                            }

                            setPostCode(data.zonecode);
                            setAddress(addr);
                            setExtraAddress(extraAddr);
                            setAddressDetail('');
                            document.getElementById('addressDetail').focus();
                        }
                    });
                }
            };
        };

        loadDaumPostcodeScript();
    }, []);

    const handlePostcodeSearch = () => {
        if (window.daum && window.daum.Postcode) {
            new window.daum.Postcode({
                oncomplete: function (data) {
                    const addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                    let extraAddr = '';

                    if (data.userSelectedType === 'R') {
                        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                            extraAddr += data.bname;
                        }
                        if (data.buildingName !== '' && data.apartment === 'Y') {
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        if (extraAddr !== '') {
                            extraAddr = ' (' + extraAddr + ')';
                        }
                    } else {
                        extraAddr = '';
                    }

                    setPostCode(data.zonecode);
                    setAddress(addr);
                    setExtraAddress(extraAddr);
                    setAddressDetail('');
                    document.getElementById('addressDetail').focus();
                }
            }).open();
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = {
                address,
                addressDetail,
                postCode,
                extraAddress,
                latitude: parseFloat(latitude),
                longitude: parseFloat(longitude),
            };
            const response = await axios.post(`http://localhost:8080/properties/address`, data, {
                withCredentials: true
            });
            const addressId = response.data.addressId;
            navigate(`/CategoryForm?addressId=${addressId}`, {
                state: {
                    addressId: addressId,
                    userData: userInfo
                }
            });

        } catch (error) {
            setError('Error saving address');
            console.error('Error saving address', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>1/5 단계</h4>
            <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit} style={{marginTop: '5%'}}>
                <Form.Group as={Row} className="mb-3" controlId="formPostCode">
                    <Form.Label column sm={2}>우편번호</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={postCode}
                            placeholder="우편번호"
                            readOnly
                        />
                        <Button variant="primary" type="button" onClick={handlePostcodeSearch} className="mt-2">
                            우편번호 찾기
                        </Button>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={2}>주소</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={address}
                            placeholder="주소"
                            readOnly
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="addressDetail">
                    <Form.Label column sm={2}>상세 주소</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={addressDetail}
                            onChange={(e) => setAddressDetail(e.target.value)}
                            placeholder="상세 주소"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formExtraAddress">
                    <Form.Label column sm={2}>참고항목</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="text"
                            value={extraAddress}
                            placeholder="참고항목"
                            readOnly
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formLatitude">
                    <Form.Label column sm={2}>위도</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="number"
                            step="any"
                            value={latitude}
                            onChange={(e) => setLatitude(e.target.value)}
                            placeholder="위도"
                            required
                        />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="formLongitude">
                    <Form.Label column sm={2}>경도</Form.Label>
                    <Col sm={10}>
                        <Form.Control
                            type="number"
                            step="any"
                            value={longitude}
                            onChange={(e) => setLongitude(e.target.value)}
                            placeholder="경도"
                            required
                        />
                    </Col>
                </Form.Group>
                <div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
                <Button variant="primary" type="submit">
                    다음
                </Button>
            </Form>
        </Container>
    );
};

export default AddressForm2;

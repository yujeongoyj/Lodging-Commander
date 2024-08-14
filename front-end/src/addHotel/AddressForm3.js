import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col, Alert } from 'react-bootstrap';

const AddressForm3 = () => {
    const [address, setAddress] = useState('');
    const [addressDetail, setAddressDetail] = useState('');
    const [postCode, setPostCode] = useState('');
    const [extraAddress, setExtraAddress] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [error, setError] = useState('');
    const [map, setMap] = useState(null);
    const [marker, setMarker] = useState(null);
    const [geocoder, setGeocoder] = useState(null);

    const navigate = useNavigate();
    const location = useLocation();
    const userInfo = location.state?.userData;

    useEffect(() => {
        // Kakao Maps API와 Daum Postcode API를 비동기적으로 로드합니다.
        const loadScripts = () => {
            // Kakao Maps API 스크립트 로드
            const mapScript = document.createElement('script');
            mapScript.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=2352038d1f2d9450032dd17ae632df20&libraries=services';
            mapScript.async = true;
            document.body.appendChild(mapScript);

            // Daum Postcode API 스크립트 로드
            const postmapScript = document.createElement('script');
            postmapScript.src = 'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
            postmapScript.async = true;
            document.body.appendChild(postmapScript);

            // 두 스크립트가 모두 로드된 후 초기화
            const scriptsLoaded = new Promise((resolve) => {
                mapScript.onload = postmapScript.onload = resolve;
            });

            scriptsLoaded.then(() => {
                if (window.daum && window.daum.maps && window.daum.Postcode) {
                    const mapContainer = document.getElementById('map');
                    const mapOption = {
                        center: new window.daum.maps.LatLng(37.537187, 127.005476),
                        level: 5
                    };

                    const kakaoMap = new window.daum.maps.Map(mapContainer, mapOption);
                    const kakaoGeocoder = new window.daum.maps.services.Geocoder();
                    const kakaoMarker = new window.daum.maps.Marker({
                        position: new window.daum.maps.LatLng(37.537187, 127.005476),
                        map: kakaoMap
                    });

                    setMap(kakaoMap);
                    setMarker(kakaoMarker);
                    setGeocoder(kakaoGeocoder);
                }
            }).catch(err => {
                setError('스크립트 로드 중 오류가 발생했습니다.');
                console.error('스크립트 로드 오류', err);
            });
        };

        loadScripts();
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

                    // 주소로 좌표 찾기
                    if (geocoder) {
                        geocoder.addressSearch(addr, function (results, status) {
                            if (status === window.daum.maps.services.Status.OK) {
                                const result = results[0];
                                const coords = new window.daum.maps.LatLng(result.y, result.x);

                                setLatitude(result.y);
                                setLongitude(result.x);

                                // 지도와 마커 업데이트
                                map.setCenter(coords);
                                marker.setPosition(coords);
                                document.getElementById('map').style.display = 'block';
                            } else {
                                setError('주소 검색에 실패했습니다.');
                            }
                        });
                    }
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
            setError('주소 저장 중 오류가 발생했습니다.');
            console.error('주소 저장 오류', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>1/5 단계</h4>
            <h2>기본 정보 등록부터 시작해 보겠습니다</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit} style={{ marginTop: '5%' }}>
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
                <div id="map" style={{ width: '100%', height: '400px', display: 'none' }}></div>
                <Button variant="primary" type="submit">
                    다음
                </Button>
            </Form>
        </Container>
    );
};

export default AddressForm3;

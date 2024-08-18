import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function Kakao({ id }) {
    const [latitude, setLatitude] = useState(null);
    const [longitude, setLongitude] = useState(null);

    useEffect(() => {
        // API에서 위도와 경도를 가져오는 함수
        const fetchCoordinates = async () => {
            try {
                console.log(id);
                const response = await axios.get(`http://localhost:8080/hotel/details/map/${id}`);
                console.log(id);
                console.log(response.data); // 응답 데이터 확인
                const { latitude, longitude } = response.data;
                setLatitude(latitude);
                console.log(latitude);
                setLongitude(longitude);
                console.log(longitude);
            } catch (error) {
                console.error('Failed to fetch coordinates', error);
            }
        };

        if (id) {
            fetchCoordinates();
        }
    }, [id]);


    useEffect(() => {
        if (latitude !== null && longitude !== null) {
            if (window.kakao && window.kakao.maps) {
                const container = document.getElementById('map');
                const options = {
                    center: new window.kakao.maps.LatLng(latitude, longitude),
                    level: 3,
                };
                const map = new window.kakao.maps.Map(container, options);

                // 지도 타입 컨트롤 추가
                const mapTypeControl = new window.kakao.maps.MapTypeControl();
                map.addControl(mapTypeControl, window.kakao.maps.ControlPosition.TOPRIGHT);

                // 줌 컨트롤 추가
                const zoomControl = new window.kakao.maps.ZoomControl();
                map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);

                // 마커 추가
                const markerPosition = new window.kakao.maps.LatLng(latitude, longitude);
                const marker = new window.kakao.maps.Marker({
                    position: markerPosition
                });
                marker.setMap(map);

            } else {
                console.error('Kakao maps library is not loaded.');
            }
        }
    }, [latitude, longitude]); // latitude와 longitude가 변경될 때마다 실행

    return <div id="map" style={{ width: '250px', height: '250px' }}></div>;
}

export default Kakao;
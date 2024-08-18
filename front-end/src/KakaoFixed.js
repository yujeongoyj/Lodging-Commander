import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import './App.css'; // CSS 파일을 임포트

function KakaoFixed() {
    const location = useLocation();
    const [map, setMap] = useState(null);

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            const container = document.getElementById('map');

            const options = {
                center: new window.kakao.maps.LatLng(37.561766, 127.187683),
                level: 3,
            };

            const mapInstance = new window.kakao.maps.Map(container, options);
            setMap(mapInstance);

            window.kakao.maps.event.addListener(mapInstance, 'center_changed', function() {
                const level = mapInstance.getLevel();
                const latlng = mapInstance.getCenter();

                const message = `<p>지도 레벨은 ${level} 이고</p>
                                 <p>중심 좌표는 위도 ${latlng.getLat()}, 경도 ${latlng.getLng()}입니다</p>`;
                const resultDiv = document.getElementById('result');
                if (resultDiv) {
                    resultDiv.innerHTML = message;
                }
            });
        } else {
            console.error('Kakao maps library is not loaded.');
        }
    }, []);

    return (
        <div className="modal">
            <div className="modal-content">
                <button className="close-button" onClick={() => window.history.back()}></button>
                <div id="map" style={{ width: '100%', height: '500px' }}></div>
                <div id="result"></div>
            </div>
        </div>
    );
}

export default KakaoFixed;
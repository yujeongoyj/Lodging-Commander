import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

function KakaoEntire() {
    const location = useLocation();
    const query = new URLSearchParams(location.search);
    const latitude = query.get('latitude') || latitude;
    const longitude = query.get('longitude') || longitude;

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            const container = document.getElementById('map');

            if (!container) {
                console.error('Map container not found');
                return;
            }

            const options = {
                center: new window.kakao.maps.LatLng(latitude, longitude),
                draggable: false,
                level: 3,
            };

            console.log("여긴 지도 안에거", latitude, longitude)
            const map = new window.kakao.maps.Map(container, options);

        } else {
            console.error('Kakao maps library is not loaded.');
        }
    }, [latitude, longitude]);

    return <div id="map" style={{ width: '500px', height: '500px' }}></div>;
}

export default KakaoEntire;

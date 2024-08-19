import React, { useState, useEffect } from 'react';

const KakaoMapSearch = ({ location }) => {
    const [map, setMap] = useState(null);

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            const container = document.getElementById('map');
            const options = {
                center: new window.kakao.maps.LatLng(37.5665, 126.978), // 기본 좌표 (서울)
                level: 5,
            };
            setMap(new window.kakao.maps.Map(container, options));
        }
    }, []);

    useEffect(() => {
        if (location && map && window.kakao && window.kakao.maps) {
            const geocoder = new window.kakao.maps.services.Geocoder();
            geocoder.addressSearch(location, (result, status) => {
                if (status === window.kakao.maps.services.Status.OK) {
                    const { x, y } = result[0];
                    const position = new window.kakao.maps.LatLng(y, x);
                    map.setCenter(position);

                    new window.kakao.maps.Marker({
                        position,
                        map,
                        title: location,
                    });
                } else {
                    alert('해당 지역을 찾을 수 없습니다.');
                }
            });
        }
    }, [location, map]);

    return (
        <div>
            <div id="map" style={{width: '100%', height: '150px'}}></div>
        </div>

    );
};

export default KakaoMapSearch;
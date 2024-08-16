import React, { useEffect, useRef } from 'react';

const Map = ({ latitude, longitude }) => {
    const mapContainerRef = useRef(null);

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            const mapContainer = mapContainerRef.current;
            const mapOptions = {
                center: new window.kakao.maps.LatLng(latitude, longitude),
                level: 3,
            };
            const map = new window.kakao.maps.Map(mapContainer, mapOptions);

            new window.kakao.maps.Marker({
                position: new window.kakao.maps.LatLng(latitude, longitude),
                map: map,
            });
        }
    }, [latitude, longitude]);

    return (
        <div
            ref={mapContainerRef}
            style={{ width: '100%', height: '400px', marginTop: '20px' }}
        ></div>
    );
};

export default Map;

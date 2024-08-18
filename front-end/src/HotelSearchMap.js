import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

function KakaoFixed() {
    const location = useLocation();

    useEffect(() => {
        if (window.kakao && window.kakao.maps) {
            const container = document.getElementById('map');

            const options = {
                center: new window.kakao.maps.LatLng(37.561766, 127.187683),
                level: 3,
            };

            const map = new window.kakao.maps.Map(container, options);

            // 지도 타입 컨트롤 추가
            const mapTypeControl = new window.kakao.maps.MapTypeControl();
            map.addControl(mapTypeControl, window.kakao.maps.ControlPosition.TOPRIGHT);

            // 줌 컨트롤 추가
            const zoomControl = new window.kakao.maps.ZoomControl();
            map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);

            // 지도가 이동, 확대, 축소로 인해 중심좌표가 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
            window.kakao.maps.event.addListener(map, 'center_changed', function() {
                // 지도의 레벨을 얻어옵니다
                const level = map.getLevel();

                // 지도의 중심좌표를 얻어옵니다
                const latlng = map.getCenter();

                var message = '<p>지도 레벨은 ' + level + ' 이고</p>';
                message += '<p>중심 좌표는 위도 ' + latlng.getLat() + ', 경도 ' + latlng.getLng() + '입니다</p>';

                // setTimeout을 사용하여 비동기적으로 결과 div의 존재 여부를 확인합니다
                setTimeout(() => {
                    const resultDiv = document.getElementById('result');
                    if (resultDiv) {
                        resultDiv.innerHTML = message;
                    } else {
                        console.error('Result div not found.');
                    }
                }, 0);
            });

        } else {
            console.error('Kakao maps library is not loaded.');
        }
    }, [location]);

    return (
        <div>
            <div id="map" style={{ width: '500px', height: '500px' }}></div>
            <div id="result" style={{ marginTop: '20px' }}></div> {/* 결과를 표시할 div 추가 */}
        </div>
    );
}

export default KakaoFixed;
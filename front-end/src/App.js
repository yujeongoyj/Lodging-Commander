import React, { useState } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import Kakao from './Kakao'; // Kakao 컴포넌트 임포트
import LikeList from './LikeList';
import KakaoFixed from "./KakaoFixed";
import KakaoEntire from "./KakaoEntire";
import KakaoMapSearch from "./KakaoMapSearch";
import HotelSearchMap from "./HotelSearchMap";
import LikeList2 from "./LikeList2";

function App() {
    const [hotelId, setHotelId] = useState('');
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const navigate = useNavigate();
    const [isOpen, setIsOpen] = useState(false);

    // openModal 함수 수정
    const openModal = () => {
        setIsOpen(true);
    };

    const closeModal = () => {
        setIsOpen(false);
    };

    const handleInputChange = (event) => {
        setHotelId(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (hotelId) {
            navigate(`/hotel/${hotelId}`);
        }
    };

    const openSearch = (event) => {
        event.preventDefault();
        navigate('/searchmap');
    };

    const hotelSearch = (event) => {
        event.preventDefault();
        navigate('/HotelSearchMap');
    };

    const handleAddressSubmit = (event) => {
        event.preventDefault();
        if (latitude && longitude) {
            navigate(`/hotel-map?latitude=${latitude}&longitude=${longitude}`);
        }
    };

    return (
        <div className="App">
            <header>
                <nav>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li>
                            <form onSubmit={handleSubmit}>
                                <input
                                    type="number"
                                    value={hotelId}
                                    onChange={handleInputChange}
                                    placeholder="호텔 ID"
                                    min="1"
                                />
                                <button type="submit">지도 켜기</button>
                            </form>
                        </li>
                        <li><Link to="/likelist">Like List</Link></li>
                        <li>
                            <form onSubmit={handleAddressSubmit}>
                                <input
                                    type="number"
                                    value={latitude}
                                    onChange={(e) => setLatitude(e.target.value)}
                                    placeholder="Latitude"
                                    step="0.0001"
                                />
                                <input
                                    type="number"
                                    value={longitude}
                                    onChange={(e) => setLongitude(e.target.value)}
                                    placeholder="Longitude"
                                    step="0.0001"
                                />
                                <button type="submit">고정지도</button>
                            </form>
                        </li>
                        <li>
                            <form>
                                <button type="button" onClick={openModal}>지도 열기 (모달 안에 지도 넣기 미완성)</button>
                                {isOpen && (
                                    <div className="modal">
                                        <div className="modal-content">
                                            <h1>지도 열기</h1>
                                            <HotelSearchMap /> {/* 모달 안에 HotelSearchMap 추가 */}
                                            호텔 리스트 지도에 띄우기(미완성)
                                            <button onClick={closeModal}>닫기</button>
                                        </div>
                                    </div>
                                )}
                            </form>
                        </li>
                        <li>
                            <button type="button" onClick={openSearch}>검색 지도 열기</button>
                        </li>
                    </ul>
                </nav>
            </header>
            <main>
                <Routes>
                    <Route path="/" element={<Home/>}/> {/* 홈 페이지 */}
                    <Route path="/hotel/:id" element={<Kakao/>}/> {/* 동적 경로 설정 */}
                    <Route path="/likelist" element={<LikeList/>}/> {/* 찜 버튼 */}
                    <Route path="/hotel-map" element={<KakaoFixed latitude={latitude} longitude={longitude} />}/>
                    <Route path="/exmap" element={<KakaoEntire/>}/>
                    <Route path="/searchmap" element={<KakaoMapSearch/>}/>
                    <Route path="/HotelSearchMap" element={<HotelSearchMap/>}/>
                </Routes>
            </main>
        </div>
    );
}

// Home 컴포넌트 예시
function Home() {
    return (
        <div>
            <h2>Main Page</h2>
            <p>Welcome!</p>
        </div>
    );
}

export default App;
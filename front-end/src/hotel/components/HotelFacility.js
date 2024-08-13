import React from 'react';
import { FaBed, FaBeer, FaCoffee, FaParking, FaSmokingBan, FaSwimmer } from 'react-icons/fa';
import { PiChargingStationLight } from 'react-icons/pi';
import { GiAirplaneDeparture, GiDogHouse, GiWashingMachine } from 'react-icons/gi';
import { CgGym } from 'react-icons/cg';
import { MdOutlineRestaurant } from 'react-icons/md';
import { TbChartBubble } from 'react-icons/tb';
import { Container, Row, Col } from 'react-bootstrap';

let facilitiesIcons = {
    "에어컨": <FaBeer title="Air Conditioning" />,
    "공항 셔틀": <GiAirplaneDeparture title="Airport Shuttle" />,
    "바": <FaBeer title="Bar" />,
    "조식": <FaCoffee title="Breakfast" />,
    "전기차 충전소": <PiChargingStationLight title="EV Charging Station" />,
    "무료 주차": <FaParking title="Free Parking" />,
    "헬스장": <CgGym title="Gym" />,
    "세탁 시설": <GiWashingMachine title="Laundry Facilities" />,
    "금연": <FaSmokingBan title="Non-Smoking" />,
    "반려동물 동반 가능": <GiDogHouse title="Pet Friendly" />,
    "레스토랑": <MdOutlineRestaurant title="Restaurant" />,
    "스파": <TbChartBubble title="Spa" />,
    "수영장": <FaSwimmer title="Swimming Pool" />,
    "24시간 프론트 데스크": <FaBed title="24-Hour Front Desk" />
};

let HotelFacility = ({ amenities = {} }) => {
    return (
        <Container fluid>
            <Row className="d-flex flex-wrap">
                {Object.keys(amenities).map(key =>
                        amenities[key] && (
                            <Col xs="auto" className="d-flex align-items-center mb-2" key={key}>
                                <div className="d-flex align-items-center">
                                    <div style={{fontSize: '1rem'}}>
                                        {facilitiesIcons[key] || null}
                                    </div>
                                    <span style={{marginLeft: '8px', fontSize: '0.875rem'}}>
                                    {key}
                                </span>
                                </div>
                            </Col>
                        )
                )}
            </Row>
        </Container>
    );
};

export default HotelFacility;

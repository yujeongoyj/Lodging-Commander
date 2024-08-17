import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMapMarkerAlt, faCity, faMountain, faSun } from '@fortawesome/free-solid-svg-icons';

const List = (userInfo) => {
    let navigate = useNavigate();

    let handleList = (hotelLocation) => {
        navigate('/hotel/list/' + hotelLocation, { state: { userData: userInfo } });
    };

    const locationData = [
        { name: '서울', icon: faCity },
        { name: '부산', icon: faMapMarkerAlt },
        { name: '제주', icon: faSun },
        { name: '강원', icon: faMountain }
    ];

    return (
        <Container className="d-flex justify-content-center mt-5">
            <Row className="text-center">
                {locationData.map((location, index) => (
                    <Col xs="auto" key={index} className="mb-4">
                        <div
                            className="location-card"
                            onClick={() => handleList(location.name)}
                        >
                            <FontAwesomeIcon icon={location.icon} size="2x" className="mb-2" />
                            <h3>{location.name}</h3>
                        </div>
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default List;

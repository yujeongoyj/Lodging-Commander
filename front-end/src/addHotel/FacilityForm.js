import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';

const FacilityForm = () => {
    const [facilities, setFacilities] = useState({
        freeWifi: false,
        nonSmoking: false,
        airConditioning: false,
        laundryFacilities: false,
        freeParking: false,
        twentyFourHourFrontDesk: false,
        breakfast: false,
        airportShuttle: false,
        spa: false,
        bar: false,
        swimmingPool: false,
        gym: false,
        evChargingStation: false,
        petFriendly: false,
        restaurant: false,
    });

    const [hotelId, setHotelId] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();
    let userInfo = location.state?.userData

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const hotelIdFromParams = queryParams.get('hotelId');
        if (hotelIdFromParams) {
            setHotelId(hotelIdFromParams);
        }
    }, [location.search]);

    const handleChange = (e) => {
        const { name, checked } = e.target;
        setFacilities((prevFacilities) => ({
            ...prevFacilities,
            [name]: checked,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`http://localhost:8080/properties/facility?hotelId=${hotelId}`, facilities,
                { withCredentials: true});
            navigate('/AddHotelSuccess', {state: {userData:userInfo}});
        } catch (error) {
            console.error('Error saving facilities', error);
        }
    };

    return (
        <Container className="mt-4">
            <h4>5/5 단계</h4>
            <h2>편의시설</h2>
            <Form onSubmit={handleSubmit} style={{'margin-top':'5%'}}>
                <Row>
                    {Object.keys(facilities).map((facility) => (
                        <Col md={6} lg={4} key={facility} className="mb-3">
                            <Form.Check
                                type="checkbox"
                                id={facility}
                                label={facility.split(/(?=[A-Z])/).join(' ')}
                                name={facility}
                                checked={facilities[facility]}
                                onChange={handleChange}
                            />
                        </Col>
                    ))}
                </Row>
                <div className="d-flex justify-content-end mt-3">
                    <Button variant="primary" type="submit">제출</Button>
                </div>
            </Form>
        </Container>
    );
};

export default FacilityForm;

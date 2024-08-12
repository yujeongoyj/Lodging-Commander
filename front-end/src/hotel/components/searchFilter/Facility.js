import React from 'react';
import { Col, Container, Form, Row } from 'react-bootstrap';

let Facility = ({ facilities, onFacilityChange }) => {
    let handleCheckboxChange = (key) => {
        let updatedFacilities = {
            ...facilities,
            [key]: !facilities[key],
        };
        onFacilityChange(updatedFacilities);
    };

    console.log('Facilities:', facilities);


    return (
        <Container>
            <Row>
                <Col>
                    <Form>
                        <div className="text-center mb-3">
                            <Form.Label>편의시설</Form.Label>
                        </div>
                        {Object.keys(facilities).map((key) => (
                            <Form.Check
                                key={key}
                                type="checkbox"
                                label={key.replace(/([A-Z])/g, ' $1').toLowerCase()}
                                checked={facilities[key] || false}
                                onChange={() => handleCheckboxChange(key)}
                            />
                        ))}
                    </Form>
                    <hr />
                </Col>
            </Row>
        </Container>
    );
};

export default Facility;

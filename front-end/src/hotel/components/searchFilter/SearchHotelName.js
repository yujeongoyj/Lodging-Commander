import {Col, Container, Form, Row} from "react-bootstrap";
import React from "react";

let SearchHotelName = ({filter, handleFilterChange}) => {
    return (
        <Container>
            <Row>
                <Col>
                    <Form.Group controlId="searchFilter">
                        <div className="text-center mb-3">
                            <Form.Label>숙박 시설 이름으로 검색</Form.Label>
                        </div>
                        <Form.Control
                            type="text"
                            placeholder="예: 메리어트"
                            value={filter}
                            onChange={(e) => handleFilterChange(e.target.value)}
                            className="mb-3"
                        />
                    </Form.Group>
                    <hr/>
                </Col>
            </Row>
        </Container>
    )
}
export default SearchHotelName
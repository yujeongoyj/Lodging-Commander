import {Col, Container, Form, Row} from "react-bootstrap";
import React from "react";

let SelectSize = ({itemsPerPage, onItemsPerPageChange}) => {
    return (
        <Container>
            <Row className="mb-3">
                <Col md={3}>
                    <Form.Select value={itemsPerPage} onChange={(e) => onItemsPerPageChange(Number(e.target.value))}>
                        <option value={10}>10개씩 보기</option>
                        <option value={20}>20개씩 보기</option>
                        <option value={50}>50개씩 보기</option>
                    </Form.Select>
                </Col>
            </Row>
        </Container>
    )
}

export default SelectSize
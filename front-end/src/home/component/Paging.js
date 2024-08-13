import React from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';

let Paging = ({ onPageChange, currentPage, totalItems, itemsPerPage }) => {
    return (
        <Container>
            <Row className="mt-3">
                <Col className="d-flex justify-content-between">
                    <Button
                        onClick={() => onPageChange(currentPage - 1)}
                        disabled={currentPage === 1}
                    >
                        이전
                    </Button>
                    <span>페이지 {currentPage}</span>
                    <Button
                        onClick={() => onPageChange(currentPage + 1)}
                        disabled={currentPage * itemsPerPage >= totalItems}
                    >
                        다음
                    </Button>
                </Col>
            </Row>
        </Container>
    );
};

export default Paging;

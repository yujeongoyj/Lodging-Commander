import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import SelectSize from '../home/component/SelectSize';
import Hotels from './components/Hotels';

let HotelList = ({
                       filteredHotels = [],
                       checkInDate,
                       checkOutDate,
                       itemsPerPage,
                       onItemsPerPageChange,
                       userInfo
                   }) => {
    return (
        <Container>
            <Col>
                <SelectSize
                    itemsPerPage={itemsPerPage}
                    onItemsPerPageChange={onItemsPerPageChange}
                />
                {filteredHotels.length > 0 ? (
                    <Hotels filteredHotels={filteredHotels}
                        checkInDate={checkInDate}
                        checkOutDate={checkOutDate}
                        userInfo={userInfo}
                    />
                ) : (
                    <Row className="justify-content-center align-items-center min-vh-100">
                        <Col className="text-center">
                            <h1>검색 결과가 없습니다.</h1>
                            <h3>조건을 다시 설정해 주세요.</h3>
                        </Col>
                    </Row>
                )}
            </Col>
        </Container>
    );
};

export default HotelList;
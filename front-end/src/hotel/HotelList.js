import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import SelectSize from '../home/component/SelectSize';
import Hotels from './components/Hotels';
import KakaoMapSearch from "./components/KakaoMapSearch";

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
                        </Col>
                    </Row>
                )}
            </Col>
        </Container>
    );
};

export default HotelList;

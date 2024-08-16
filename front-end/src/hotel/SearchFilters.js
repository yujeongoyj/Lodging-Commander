import React from 'react';
import { Form, Col, Row, Card, Button } from 'react-bootstrap';
import SearchHotelName from "./components/searchFilter/SearchHotelName";
import Category from "./components/searchFilter/Category";
import Rating from "./components/searchFilter/Rating";
import Facility from "./components/searchFilter/Facility";
import KakaoMapSearch from "./components/KakaoMapSearch";

// StaticMapCard 컴포넌트 정의
const StaticMapCard = ({ location }) => {
    return (
        <Card className="mb-4">
            <Card.Body className="p-0">
                <KakaoMapSearch location={location} />
            </Card.Body>
            <Card.Body className="text-center">
                <Button
                    variant="primary"
                    onClick={() => {
                        // 마커 맵 호출
                        window.open('https://map.kakao.com', '_blank');
                    }}
                >
                    지도로 보기
                </Button>
            </Card.Body>
        </Card>
    );
};

// SearchFilters 컴포넌트 정의
const SearchFilters = ({
                           filter,
                           handleFilterChange,
                           selectedRating,
                           onRatingChange,
                           categories,
                           selectedCategories,
                           onCategoryChange,
                           selectedFacilities,
                           onFacilityChange,
                           location
                       }) => {
    return (
        <Form>
            <Row>
                <Col>
                    <StaticMapCard location={location} />
                </Col>
            </Row>
            <Row>
                <Col>
                    <SearchHotelName
                        filter={filter}
                        handleFilterChange={handleFilterChange}
                    />
                </Col>
            </Row>
            <Row>
                <Col>
                    <Category
                        categories={categories}
                        selectedCategories={selectedCategories}
                        onCategoryChange={onCategoryChange}
                    />
                </Col>
            </Row>
            <Row>
                <Col>
                    <Rating
                        selectedRating={selectedRating}
                        onRatingChange={onRatingChange}
                    />
                </Col>
            </Row>
            <Row>
                <Col>
                    <Facility
                        facilities={selectedFacilities}
                        onFacilityChange={onFacilityChange}
                    />
                </Col>
            </Row>
        </Form>
    );
};

export default SearchFilters;
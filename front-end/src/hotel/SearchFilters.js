import React from 'react';
import { Form, Col, Row } from 'react-bootstrap';
import SearchHotelName from "./components/searchFilter/SearchHotelName";
import Category from "./components/searchFilter/Category";
import Rating from "./components/searchFilter/Rating";
import Facility from "./components/searchFilter/Facility";

let SearchFilters = ({
                           filter,
                           handleFilterChange,
                           selectedRating,
                           onRatingChange,
                           categories,
                           selectedCategories,
                           onCategoryChange,
                           selectedFacilities,
                           onFacilityChange
                       }) => {

    return (
        <Form>
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

import React, {useCallback, useMemo, useState} from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import axios from 'axios';
import HotelSearchForm from '../hotel/components/HotelSearchForm';
import SearchFilters from "../hotel/SearchFilters";
import HotelList from "../hotel/HotelList";
import List from "./component/List";
import Paging from "./component/Paging";
import {useLocation} from "react-router-dom";
import NewList from "./component/NewList";

let StartPage = () => {

    let location = useLocation()
    const userInfo = location.state?.userData || null;

    let [searchLocation, setSearchLocation] = useState('');
    let [searchResults, setSearchResults] = useState({searchList: []});
    let [showSearchResult, setShowSearchResult] = useState(false);
    let [checkInDate, setCheckInDate] = useState('');
    let [checkOutDate, setCheckOutDate] = useState('');
    let [currentPage, setCurrentPage] = useState(1);
    let [itemsPerPage, setItemsPerPage] = useState(10);
    let [filter, setFilter] = useState('');
    let [selectedRating, setSelectedRating] = useState(null);
    let [selectedCategories, setSelectedCategories] = useState([]);
    let [selectedFacilities, setSelectedFacilities] = useState({
        "에어컨": false,
        "공항 셔틀": false,
        "바": false,
        "조식": false,
        "전기차 충전소": false,
        "무료 주차": false,
        "헬스장": false,
        "세탁 시설": false,
        "금연": false,
        "반려동물 동반 가능": false,
        "레스토랑": false,
        "스파": false,
        "수영장": false,
        "24시간 프론트 데스크": false,
    });


    let handleSearch = useCallback(async (location, checkInDate, checkOutDate, guests, rooms) => {
        if (!location || !checkInDate || !checkOutDate || !guests || !rooms) {
            alert('모든 조건들을 입력해 주세요.');
            return;
        }

        try {
            let response = await axios.get('http://localhost:8080/hotel/search', {
                params: {location, checkInDate, checkOutDate, guests, rooms},
            }, {
                withCredentials: true
            });

            setSearchResults(response.data);
            setSearchLocation(location);
            setCheckInDate(checkInDate);
            setCheckOutDate(checkOutDate);
            setShowSearchResult(true);
            setCurrentPage(1);
            console.log(searchLocation)
        } catch (error) {
            console.error('검색 중 오류 발생:', error);
            alert('검색 중 오류가 발생했습니다. 다시 시도해 주세요.');
        }
    }, []);

    let handleFilterChange = useCallback((newFilter) => {
        setFilter(newFilter);
        setCurrentPage(1);
    }, []);

    let handlePageChange = useCallback((newPage) => {
        setCurrentPage(newPage);
    }, []);

    let handleItemsPerPageChange = useCallback((newItemsPerPage) => {
        setItemsPerPage(newItemsPerPage);
        setCurrentPage(1);
    }, []);

    let handleRatingChange = useCallback((rating) => {
        setSelectedRating(rating);
        setCurrentPage(1);
    }, []);

    let handleCategoryChange = useCallback((categories) => {
        setSelectedCategories(categories);
        setCurrentPage(1);
    }, []);

    let handleFacilityChange = useCallback((facilities) => {
        setSelectedFacilities(facilities);
        setCurrentPage(1);
    }, []);
    console.log(location)

    let filteredHotels = useMemo(() => {
        let {searchList} = searchResults;
        if (!Array.isArray(searchList)) return [];

        return searchList
            .filter(hotel => hotel.hotelName.toLowerCase().includes(filter.toLowerCase()))
            .filter(hotel => selectedRating === null || hotel.grade === selectedRating)
            .filter(hotel => selectedCategories.length === 0 || selectedCategories.includes(hotel.category))
            .filter(hotel =>
                Object.keys(selectedFacilities).every(facility =>
                    !selectedFacilities[facility] || hotel.facilities[facility] === selectedFacilities[facility]
                )
            )
            .slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
    }, [searchResults.searchList, filter, selectedRating, selectedCategories, selectedFacilities, currentPage, itemsPerPage]);

    let categories = useMemo(() => {
        let allCategories = searchResults.searchList.map(hotel => hotel.category);
        return [...new Set(allCategories)];
    }, [searchResults.searchList]);

    return (
        <Container>
            <Row>
                <HotelSearchForm onSearch={handleSearch}/>
            </Row>
            {showSearchResult ? (
                <>
                    <Row>
                        <Col sm={3}>
                            {/*여기다가 위치고정 지도(검색어 받아오기)*/}
                            <SearchFilters
                                filter={filter}
                                handleFilterChange={handleFilterChange}
                                selectedRating={selectedRating}
                                onRatingChange={handleRatingChange}
                                categories={categories}
                                selectedCategories={selectedCategories}
                                onCategoryChange={handleCategoryChange}
                                selectedFacilities={selectedFacilities}
                                onFacilityChange={handleFacilityChange}
                                location={searchLocation} // location 전달
                            />
                        </Col>
                        <Col sm={9}>
                            <HotelList
                                filteredHotels={filteredHotels}
                                checkInDate={checkInDate}
                                checkOutDate={checkOutDate}
                                itemsPerPage={itemsPerPage}
                                onItemsPerPageChange={handleItemsPerPageChange}
                                userInfo={userInfo}
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Paging
                            onPageChange={handlePageChange}
                            currentPage={currentPage}
                            totalItems={searchResults.searchList.length}
                            itemsPerPage={itemsPerPage}
                        />
                    </Row>
                </>
            ) : (
                <Container className="mt-5">
                    <Row className="text-center mb-4">
                        <Col>
                            <h1 className="display-4 font-weight-bold">NEW 호텔</h1>
                        </Col>
                    </Row>
                    <Row className="justify-content-center">
                        <Col xs="auto">
                           <NewList userInfo={userInfo}/>
                        </Col>
                    </Row>
                    <Row className="text-center mb-4 mt-5">
                        <Col>
                            <h1 className="display-4 font-weight-bold">자주 가는 국내 호텔 지역</h1>
                        </Col>
                    </Row>
                    <Row className="justify-content-center">
                        <Col xs="auto">
                            <List userInfo={userInfo} />
                        </Col>
                    </Row>
                </Container>
            )}
        </Container>
    );
};

export default StartPage;

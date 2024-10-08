# Middle Project - Lodging-Commander (숙박 사령관)

## 목차

1. [개요](#개요)
2. [API 엔드포인트](#api-엔드포인트)
3. [기능 목록](#기능-목록)
4. [데이터베이스 스키마](#데이터베이스-스키마)

## 개요

- 이 프로젝트는 호텔스닷컴과 유사한 여행 예약 시스템을 개발하는 것을 목표로 합니다.
- 이 시스템은 매물 등록인, 예약인, 관리자 등 다양한 유형의 사용자를 대상으로 합니다.
- 기능에는 매물 등록, 예약, 사용자 관리, 리워드 포인트, 장바구니 관리, 검색 기능 등이 포함됩니다.

## Git 작업/수정 규칙

- 공유된 엔티티를 임의로 수정하지 말 것 (꼭 수정해야 한다면 다 같이 상의 후 수정)
- 하루 마무리하고 pull, push 할 때는 모여서 확인하며 진행 (상황에 따라)
- 커밋할 때는 작업 내용을 작성하고 기능 단위로 커밋하기

## API 엔드포인트

#### 사용자 인증

- **로그인**
    - 메소드: POST
    - URL: `/login`
- **회원가입**
    - 메소드: POST
    - URL: `/user/register`

#### 매물 관리

- **매물 등록**
    - 메소드: POST
    - URL: `/properties`
- **매물 수정**
    - 메소드: PUT
    - URL: `/properties/{propertyId}`
- **매물 삭제**
    - 메소드: DELETE
    - URL: `/properties/{propertyId}`

#### 주소 관리

- **주소 API**
    - 메소드: GET
    - URL: `/address/{addressId}`

#### 사용자 관리

- **사용자 삭제**
    - 메소드: DELETE
    - URL: `/users/{userId}`
- **사용자 포인트 조회**
    - 메소드: GET
    - URL: `/user/points`
- **사용자 예약**
    - 메소드: POST
    - URL: `/reservations`

#### 예약 관리

- **예약하기**
    - 메소드: POST
    - URL: `/reservations`
- **예약 취소**
    - 메소드: DELETE
    - URL: `/reservations/{reservationId}`
- **예약 취소 여부**
    - 메소드: GET
    - URL: `/reservations/{reservationId}/cancel`

#### 방 및 리워드 관리

- **방 종류 조회**
    - 메소드: GET
    - URL: `/rooms/{hotelId}`
- **예약 시 리워드 조회**
    - 메소드: GET
    - URL: `/rewards/{reservationId}`

#### 장바구니 및 검색 기능

- **장바구니 보기**
    - 메소드: GET
    - URL: `/cart`
- **호텔 검색**
    - 메소드: GET
    - URL: `/hotels`
- **조건별 호텔 검색**
    - 메소드: GET
    - URL: `/hotels?location={location}&date={date}&guests={guests}`

#### 인기 매물 및 지도 기능

- **인기 호텔 조회**
    - 메소드: GET
    - URL: `/hotels/popular`
- **호텔 지도 보기**
    - 메소드: GET
    - URL: `/maps/{hotelId}`

#### QnA 및 리뷰

- **QnA 제출**
    - 메소드: POST
    - URL: `/qna`
- **QnA 조회**
    - 메소드: GET
    - URL: `/qna`
- **리뷰 작성**
    - 메소드: POST
    - URL: `/reviews/{hotelId}`
- **리뷰 수정**
    - 메소드: PUT
    - URL: `/reviews/{hotelId}/{reviewId}`
- **리뷰 삭제**
    - 메소드: DELETE
    - URL: `/reviews/{hotelId}/{reviewId}`

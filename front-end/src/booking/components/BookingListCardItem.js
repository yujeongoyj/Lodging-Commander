import {Button, Image, ListGroupItem} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {dataFormatter} from "../../js/dataFormatter";

const BookingListCardItem = ({ booking, onCancelBooking, userInfo }) => {
    const navigate = useNavigate();

    const checkOutDate = new Date(booking.checkOutDate);
    const currentDate = new Date();

    const showCancel = !booking.cancel && checkOutDate > currentDate;
    const showReview = !booking.cancel && checkOutDate < currentDate;

    const onCancel = async () => {
        try {
            const request = await axios.post(`http://localhost:8080/booking/cancel/${booking.id}`, {
                withCredentials: true
            });
            if (request.status === 200) {
                alert("예약이 취소되었습니다.")
                onCancelBooking()
            }
        } catch (error) {
            console.error("예약 취소 중 오류 발생", error);
            alert("예약 취소에 실패했습니다.")
        }
    }

    const goReview = () => {
        // 은석님 리뷰 이동 버튼
        navigate('', {state: {userData: userInfo}})
    };

    const goHotel = () => {
        navigate(`/hotel/details/${booking.hotelId}`, {
            state: {
                userData: userInfo,
                checkInDate: booking.checkInDate,
                checkOutDate: booking.checkOutDate
            }
        });
    }

    return (
        <ListGroupItem>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
                <div style={{ display: "flex" }}>
                    <Image rounded src={booking.imgPath} style={{ objectFit: "cover", width: "185px", height: "185px", marginRight: "16px"}} />
                    <div style={{ display: "flex", justifyContent: "space-between", flexDirection: "column" }}>
                        <h4 onClick={showCancel ? goHotel : null} style={{ cursor: showCancel ? 'pointer' : 'default' }}>{booking.hotelName} - {booking.roomName}</h4>
                        <div>
                            <div className={"mb-1"}>
                                <p className={"mb-1"}>체크인: {dataFormatter.formatDate(booking.checkInDate)}</p>
                                <p>체크아웃: {dataFormatter.formatDate(booking.checkOutDate)}</p>
                            </div>
                            <div>
                                <p className={"mb-1"}>예약 인원 수: {booking.totalPeople}인</p>
                                <p className={"mb-0"}>총 결제 금액: ₩ {dataFormatter.formatPrice(booking.totalPrice)}</p>
                            </div>
                        </div>
                    </div>
                </div>
                {showCancel && (
                    <div style={{ display: "flex", alignItems: "center" }}>
                        <Button variant="danger" onClick={onCancel}>예약 취소</Button>
                    </div>
                )}
                {showReview && (
                    <div style={{ display: "flex", alignItems: "center" }}>
                        <Button variant="primary" onClick={goReview}>리뷰 작성</Button>
                    </div>
                )}
            </div>
        </ListGroupItem>
    )
}

export default BookingListCardItem;

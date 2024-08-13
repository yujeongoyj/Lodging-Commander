export function calculatePrice(checkInDate, checkOutDate, price) {
    let checkIn = new Date(checkInDate);
    let checkOut = new Date(checkOutDate);
    let timeDifference = checkOut - checkIn;
    let oneDayInMillis = 1000 * 60 * 60 * 24;
    let nights = Math.ceil(timeDifference / oneDayInMillis);
    return nights * price;
};

export function calculateDiscountedPrice(price, userGrade) {
    let discountRates = {VIP: 0.10, Gold: 0.07, Silver: 0.05};
    let discountRate = discountRates[userGrade] || 0;
    return price * (1 - discountRate);
};
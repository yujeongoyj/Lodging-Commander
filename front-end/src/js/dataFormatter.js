// 상민 added this file

const formatPrice = (priceStr) => {
    return new Intl.NumberFormat('ko-KR').format(priceStr);
}

const formatDate = (localDateStr) => {
    const date = new Date(localDateStr);
    if (isNaN(date.getTime())) {
        return null;
    }
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}년 ${month}월 ${day}일`;
}

export const dataFormatter = {
    formatPrice,
    formatDate
}




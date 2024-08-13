// server.js (Express 서버)
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const axios = require('axios');
const crypto = require('crypto');

const app = express();

app.use(cors()); // CORS 설정 추가
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const clientKey = 'S2_6fac0be961254663996f37d33f448f96';
const secretKey = '5fcc953b775e49c498a9dd25206915a6';
const baseURL = 'https://sandbox-api.nicepay.co.kr/v1/payments/'; // 샌드박스 URL

app.post('/paymentAuthorization', (req, res) => {
    console.log('결제 결과 수신:', req.body);

    const { authResultCode, authResultMsg, tid, orderId, amount, clientId, authToken, signature } = req.body;

    // 서명 검증
    const expectedSignature = getSignature(tid, orderId, amount, secretKey);
    if (expectedSignature !== signature) {
        return res.status(400).json({ status: 'error', message: '서명 검증 실패' });
    }

    // 승인 API 호출
    const apiUrl = `${baseURL}${tid}`;
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': getAuthorizationHeader(clientKey, secretKey)
    };

    const requestBodyForApproval = { amount: parseInt(amount) };

    axios.post(apiUrl, requestBodyForApproval, { headers })
        .then(response => {
            console.log('승인 성공:', response.data);
            res.json({ status: 'success', message: '결제가 성공적으로 승인되었습니다.', ...response.data });
        })
        .catch(error => {
            console.error('승인 실패:', error.message);
            res.status(500).json({ status: 'error', message: '결제 승인 실패' });
        });
});

const getSignature = (tid, orderId, amount, secretKey) => {
    const str = tid + orderId + amount + secretKey;
    return crypto.createHash('sha256').update(str).digest('hex');
};

const getAuthorizationHeader = (clientKey, secretKey) => {
    const credentials = `${clientKey}:${secretKey}`;
    return `Basic ${Buffer.from(credentials).toString('base64')}`;
};

app.listen(8080, () => console.log('서버가 8080 포트에서 실행 중입니다.'));

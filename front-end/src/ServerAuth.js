import React, {useState} from 'react';

function ServerAuth() {
    const [authResultCode, setAuthResultCode] = useState('');
    const [authResultMsg, setAuthResultMsg] = useState('');
    const [tid, setTid] = useState('');
    const [orderId, setOrderId] = useState('');
    const [amount, setAmount] = useState('');
    const [clientId, setClientId] = useState('');
    const [authToken, setAuthToken] = useState('');
    const [signature, setSignature] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/paymentAuthorization', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    authResultCode,
                    authResultMsg,
                    tid,
                    orderId,
                    amount,
                    clientId,
                    authToken,
                    signature
                }),
                // withCredentials 옵션을 여기 위치에 추가합니다.
                credentials: 'include', // 쿠키를 함께 보내고자 할 때 사용
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            setMessage(data.message);
        } catch (error) {
            console.error('Error:', error);
            setMessage('An error occurred');
        }
    };


    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={authResultCode}
                    onChange={(e) => setAuthResultCode(e.target.value)}
                    placeholder="Auth Result Code"
                />
                <input
                    type="text"
                    value={authResultMsg}
                    onChange={(e) => setAuthResultMsg(e.target.value)}
                    placeholder="Auth Result Msg"
                />
                <input
                    type="text"
                    value={tid}
                    onChange={(e) => setTid(e.target.value)}
                    placeholder="TID"
                />
                <input
                    type="text"
                    value={orderId}
                    onChange={(e) => setOrderId(e.target.value)}
                    placeholder="Order ID"
                />
                <input
                    type="text"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Amount"
                />
                <input
                    type="text"
                    value={clientId}
                    onChange={(e) => setClientId(e.target.value)}
                    placeholder="Client ID"
                />
                <input
                    type="text"
                    value={authToken}
                    onChange={(e) => setAuthToken(e.target.value)}
                    placeholder="Auth Token"
                />
                <input
                    type="text"
                    value={signature}
                    onChange={(e) => setSignature(e.target.value)}
                    placeholder="Signature"
                />
                <button type="submit">Submit</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default ServerAuth;

import React, { useState } from 'react'
import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Button, Col, Container, FormControl, Row, Table, Form } from 'react-bootstrap'
import '../css/user/Register.css'

function Register() {
    const [user, setUser] = useState({
        email: '',
        password: '',
        nickname: '',
        tel: '',
        grade: 'Silver',
        role: 'USER'
    })

    const [confirmPassword, setConfirmPassword] = useState('')
    const [passwordsMatch, setPasswordsMatch] = useState(true)
    const [passwordStrength, setPasswordStrength] = useState('')
    const [passwordValid, setPasswordValid] = useState(true)
    const [showPasswordWarning, setShowPasswordWarning] = useState(false)
    const [telError, setTelError] = useState('')
    const [emailDomain, setEmailDomain] = useState('@naver.com')
    const [alertVisible, setAlertVisible] = useState(false)
    const [emailCheckMessage, setEmailCheckMessage] = useState('')
    const [emailChecked, setEmailChecked] = useState(false)
    const [emailValid, setEmailValid] = useState(true)
    const [showEmailWarning, setShowEmailWarning] = useState(false)
    const [emailError, setEmailError] = useState('')
    const [nicknameError, setNicknameError] = useState('')
    const [showTelWarning, setShowTelWarning] = useState(false)

    const handleChange = (e) => {
        const { id, value } = e.target
        setUser({ ...user, [id]: value })

        if (id === 'email') {
            setEmailChecked(false)
            setEmailCheckMessage('')
            validateEmail(value)
            setEmailError('')
        }
        if (id === 'password') {
            validatePassword(value)
            setPasswordsMatch(value === confirmPassword)
        }
        if (id === 'nickname') {
            setNicknameError('')
            validateNickname(value)
        }
        if (id === 'tel') {
            setTelError('')
            setShowTelWarning(false)
        }
    }

    const handleConfirmPasswordChange = (e) => {
        setConfirmPassword(e.target.value)
        setPasswordsMatch(e.target.value === user.password)
    }

    const validatePassword = (password) => {
        const length = password.length

        if (length >= 12) {
            setPasswordStrength('strong')
        } else if (length >= 10) {
            setPasswordStrength('medium')
        } else if (length >= 8) {
            setPasswordStrength('weak')
        } else {
            setPasswordStrength('')
        }

        setPasswordValid(length >= 8)
    }

    const validateNickname = (nickname) => {
        const length = nickname.length
        if (length < 2 || length > 10) {
            setNicknameError('닉네임은 2자에서 10자 이내로 입력해야 합니다.')
        } else {
            setNicknameError('')
        }
    }

    const validateEmail = (email) => {
        const length = email.length
        setEmailValid(length >= 8 && length <= 12)
    }

    const getPasswordStrengthColor = () => {
        switch (passwordStrength) {
            case 'weak':
                return 'red'
            case 'medium':
                return 'orange'
            case 'strong':
                return 'green'
            default:
                return ''
        }
    }

    const handleEmailDomainChange = (e) => {
        setEmailDomain(e.target.value)
        setEmailChecked(false)
        setEmailCheckMessage('')
    }

    const handleTelChange = (e) => {
        let value = e.target.value
        value = value.replace(/\D/g, '')

        if (!value.startsWith('010')) {
            setTelError("잘못된 형식입니다. '010'으로 시작해야 합니다.")
            setUser({ ...user, tel: value })
            return
        } else {
            setTelError('')
        }

        if (value.length > 11) {
            value = value.slice(0, 11)
        }

        if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
        }

        setUser({ ...user, tel: value })
    }

    const handleEmailCheck = async () => {
        if (!user.email) {
            setEmailError('이메일을 입력하세요.')
            setEmailCheckMessage('')
            return
        }

        const finalEmail = user.email + emailDomain
        try {
            const response = await axios.post('http://localhost:8080/user/checkEmail', { email: finalEmail })
            if (response.data.exists) {
                setEmailCheckMessage('중복된 이메일입니다.')
            } else {
                setEmailCheckMessage('사용 가능한 이메일입니다.')
                setEmailChecked(true)
            }
        } catch (error) {
            console.error('이메일 중복 확인 오류:', error)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        setShowEmailWarning(true)
        setShowPasswordWarning(true)
        setShowTelWarning(true)

        if (!user.email) {
            setEmailError('이메일을 입력하세요.')
        }

        if (!user.nickname || nicknameError) {
            setNicknameError('닉네임은 2자에서 10자 이내로 입력해야 합니다.')
        }

        if (!user.email || !user.nickname || !user.tel || telError || user.tel.length !== 13) {
            return
        }

        if (!passwordValid || passwordStrength === 'weak') {
            return
        }
        if (user.password !== confirmPassword) {
            setPasswordsMatch(false)
            return
        }

        if (!emailChecked) {
            setEmailCheckMessage('이메일 중복 확인을 해주세요.')
            return
        }

        if (!emailValid) {
            setEmailCheckMessage('이메일은 8자에서 12자 이내로 입력해야 합니다.')
            return
        }

        try {
            const finalEmail = user.email + emailDomain
            const sanitizedTel = user.tel.replace(/-/g, '')
            await axios.post('http://localhost:8080/user/register', { ...user, email: finalEmail, tel: sanitizedTel })
            setAlertVisible(true)
            setTimeout(() => {
                setAlertVisible(false)
                window.location.href = '/'
            }, 1000)
        } catch (error) {
            console.error(error)
        }
    }

    return (
        <Container>
            <Row className='justify-content-center'>
                <Col xs={13} sm={10} md={6} lg={5}>
                    {alertVisible && (
                        <div className='alert-container'>
                            회원가입 완료
                        </div>
                    )}
                    <form onSubmit={handleSubmit} autoComplete='off'>
                        <Table striped hover bordered>
                            <thead className={'text-center'}>
                            <tr>
                                <td colSpan={2}><h3>회원가입</h3></td>
                            </tr>
                            </thead>
                            <tbody className={'text-center'}>
                            <tr>
                                <td>이메일</td>
                                <td>
                                    <Row>
                                        <Col xs={6}>
                                            <FormControl
                                                type='text'
                                                id='email'
                                                value={user.email}
                                                placeholder='이메일 입력'
                                                onChange={handleChange}
                                                autoComplete='off'
                                                maxLength={12}
                                            />
                                        </Col>
                                        <Col xs={6}>
                                            <Form.Select value={emailDomain} onChange={handleEmailDomainChange}>
                                                <option value='@naver.com'>@naver.com</option>
                                                <option value='@gmail.com'>@gmail.com</option>
                                                <option value='@daum.net'>@daum.net</option>
                                                <option value='@yahoo.com'>@yahoo.com</option>
                                            </Form.Select>
                                        </Col>
                                    </Row>
                                    {emailCheckMessage && (
                                        <div style={{color: emailChecked ? 'green' : 'red', marginTop: '5px'}}>
                                            {emailCheckMessage}
                                        </div>
                                    )}
                                    {showEmailWarning && !emailValid && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            이메일은 8자에서 12자 이내로 입력해야 합니다.
                                        </div>
                                    )}
                                    {emailError && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            {emailError}
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <Button className='w-100' onClick={handleEmailCheck}>중복 확인</Button>
                                </td>
                            </tr>
                            <tr>
                                <td>비밀번호</td>
                                <td>
                                    <FormControl
                                        type='password'
                                        id='password'
                                        value={user.password}
                                        placeholder='비밀번호'
                                        onChange={handleChange}
                                        autoComplete='new-password'
                                        style={{borderColor: getPasswordStrengthColor()}}
                                    />
                                    {showPasswordWarning && !passwordValid && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            비밀번호는 최소 8자 이상이어야 합니다.
                                        </div>
                                    )}
                                    {passwordStrength && (
                                        <div style={{color: getPasswordStrengthColor(), marginTop: '5px'}}>
                                            비밀번호 복잡도: {passwordStrength === 'weak' ? '위험' : passwordStrength === 'medium' ? '보통' : '안전'}
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <td>비밀번호 확인</td>
                                <td>
                                    <FormControl
                                        type='password'
                                        value={confirmPassword}
                                        placeholder='비밀번호 확인'
                                        onChange={handleConfirmPasswordChange}
                                        autoComplete='new-password'
                                    />
                                    {!passwordsMatch && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            비밀번호가 일치하지 않습니다.
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <td>닉네임</td>
                                <td>
                                    <FormControl
                                        type='text'
                                        id='nickname'
                                        value={user.nickname}
                                        placeholder='닉네임'
                                        onChange={handleChange}
                                        maxLength={10}
                                    />
                                    {nicknameError && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            {nicknameError}
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <td>연락처</td>
                                <td>
                                    <FormControl
                                        type='text'
                                        id='tel'
                                        value={user.tel}
                                        placeholder='010-1234-5678'
                                        onChange={handleTelChange}
                                        maxLength={13}
                                    />
                                    {showTelWarning && (telError || user.tel.length !== 13) && (
                                        <div style={{color: 'red', marginTop: '5px'}}>
                                            유효한 연락처를 입력하세요.
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr className={'text-center'}>
                                <td colSpan={2}>
                                    <Button type='submit'>회원가입</Button>
                                </td>
                            </tr>
                            </tbody>
                        </Table>
                    </form>
                </Col>
            </Row>
        </Container>
    )
}

export default Register

import {Col, Container, Image, Nav, Navbar, NavDropdown, Row} from "react-bootstrap";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";

let Header = () => {
    let location = useLocation();
    let navigate = useNavigate();

    let userInfo = location.state?.userData || null;
    console.log("location",location.state)

    let changePage = (pageName) => {
        navigate('/' + pageName, {state: {userData: userInfo}});
    }

    const onLogout = async () => {
        try {
            let response = await axios.post('http://localhost:8080/user/logOutSuccess', {
                withCredentials: true,
            });
            if (response.status === 200) {
                navigate('/')
            }
        } catch (error) {
            console.error('로그아웃 에러:', error)
        }
    }

    console.log(userInfo)
    return (
        <Container className='mb-3 mt-3'>
            <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand onClick={() => changePage('')} style={{cursor: 'pointer'}}>
                        <Image src='http://localhost:8080/log.png' alt="Logo" fluid
                               style={{width: '150px', height: 'auto'}}/>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="ms-auto">
                            {userInfo ? (
                                <>
                                    <Row>
                                        <Col>
                                            {userInfo.nickname}님 환영합니다.
                                        </Col>
                                        <Col>
                                            <NavDropdown title="MyPage" id="collapsible-nav-dropdown" className='text-end'>
                                                <NavDropdown.Item href="#action/3.1">다가오는 예약</NavDropdown.Item>
                                                <NavDropdown.Item href="#action/3.2">
                                                    내 정보 수정
                                                </NavDropdown.Item>
                                                <NavDropdown.Item onClick={() => changePage('AddressForm3')}>
                                                    숙소 등록
                                                </NavDropdown.Item>
                                                <NavDropdown.Item href="#action/3.2">
                                                    내 숙소 관리
                                                </NavDropdown.Item>
                                                <NavDropdown.Item href="#action/3.2">
                                                    내가 작성한 Review
                                                </NavDropdown.Item>
                                                <NavDropdown.Item onClick={() => changePage('cart')}>
                                                    Cart
                                                </NavDropdown.Item>
                                                <NavDropdown.Item href="#action/3.2">
                                                    찜 보기
                                                </NavDropdown.Item>
                                                <NavDropdown.Item href="#action/3.2">
                                                    남은 등급 상승포인트
                                                </NavDropdown.Item>
                                                <NavDropdown.Divider/>
                                                <NavDropdown.Item onClick={onLogout}>
                                                    <p className='text-danger text-center m-0'>LOGOUT</p>
                                                </NavDropdown.Item>
                                            </NavDropdown>
                                        </Col>
                                    </Row>
                                </>
                            ) : (
                                <Nav.Link onClick={()=>changePage('Auth')}>LOGIN</Nav.Link>
                            )}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </Container>
    )
}

export default Header;

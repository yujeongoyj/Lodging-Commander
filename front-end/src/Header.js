import {Container, Image, Nav, Navbar, NavDropdown} from "react-bootstrap";
import {useState} from "react";
import {useNavigate} from "react-router-dom";


let Header = () => {
    let [userInfo, setUserInfo] = useState('a')
    let navigate = useNavigate()

    let changePage = (pageName) => {
        navigate('/'+pageName)
    }

    return(
        <Container  className='mb-3 mt-3'>
            <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand onClick={() => changePage('')} style={{ cursor: 'pointer' }}>
                        <Image src='http://localhost:8080/log.png' alt="Logo" fluid style={{ width: '150px', height: 'auto' }} />
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="ms-auto">
                            {userInfo ? (
                                <NavDropdown title="MyPage" id="collapsible-nav-dropdown" className='text-end'>
                                    <NavDropdown.Item href="#action/3.1">다가오는 예약</NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        내 정보 수정
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="/AddressForm">
                                        숙소 등록
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        내 숙소 관리
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        내가 작성한 Review
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        내 정보 수정
                                    </NavDropdown.Item>
                                    <NavDropdown.Item onClick={(e) =>changePage('cart')}>
                                        Cart
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        찜 보기
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="#action/3.2">
                                        남은 등급 상승포인트
                                    </NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item href="#action/3.4">
                                        <p className='text-danger text-center m-0'>LOGOUT</p>
                                    </NavDropdown.Item>
                                </NavDropdown>
                            ):(  <Nav.Link href="#pricing">LOGIN</Nav.Link>)}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </Container>
    )
}

export default Header
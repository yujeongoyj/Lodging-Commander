import { Button, ButtonGroup, Col, Container, Row } from "react-bootstrap";
import '@fortawesome/fontawesome-free/css/all.min.css';

let Rating = ({ selectedRating, onRatingChange }) => {
    let ratings = [1, 2, 3, 4, 5];

    return (
        <Container>
            <Row className="justify-content-center">
                <Col md="auto">
                    <Row>
                        <div className="text-center mb-3">
                            <h5>호텔 등급</h5>
                        </div>
                    </Row>
                    <Row>
                          <ButtonGroup className="mb-3">
                          {ratings.map(rating => (
                                <Button
                                    key={rating}
                                    variant={selectedRating === rating ? 'primary' : 'outline-primary'}
                                    onClick={() => onRatingChange(rating)}
                                    className="me-2 d-flex align-items-center"
                                    size='sm'
                                >
                                    <i className={`fas fa-star ${selectedRating === rating ? 'text-white' : 'text-primary'}`}></i>
                                    {rating}
                                </Button>
                            ))}
                        </ButtonGroup>
                    </Row>
                    <hr />
                </Col>
            </Row>
        </Container>
    );
}

export default Rating;

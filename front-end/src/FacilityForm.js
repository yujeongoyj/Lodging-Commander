import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const FacilityForm = () => {
    const [facilities, setFacilities] = useState({
        freeWifi: false,
        nonSmoking: false,
        airConditioning: false,
        laundryFacilities: false,
        freeParking: false,
        twentyFourHourFrontDesk: false,
        breakfast: false,
        airportShuttle: false,
        spa: false,
        bar: false,
        swimmingPool: false,
        gym: false,
        evChargingStation: false,
        petFriendly: false,
        restaurant: false,
    });

    const navigate = useNavigate();
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const hotelId = params.get('hotelId');

    const handleChange = (e) => {
        const { name, checked } = e.target;
        setFacilities((prevFacilities) => ({
            ...prevFacilities,
            [name]: checked,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/properties/facilities`, { hotelId, ...facilities });
            navigate('/success');
        } catch (error) {
            console.error('Error saving facilities', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Free WiFi:
                <input type="checkbox" name="freeWifi" checked={facilities.freeWifi} onChange={handleChange} />
            </label>
            <label>
                Non-Smoking:
                <input type="checkbox" name="nonSmoking" checked={facilities.nonSmoking} onChange={handleChange} />
            </label>
            <label>
                Air Conditioning:
                <input type="checkbox" name="airConditioning" checked={facilities.airConditioning} onChange={handleChange} />
            </label>
            <label>
                Laundry Facilities:
                <input type="checkbox" name="laundryFacilities" checked={facilities.laundryFacilities} onChange={handleChange} />
            </label>
            <label>
                Free Parking:
                <input type="checkbox" name="freeParking" checked={facilities.freeParking} onChange={handleChange} />
            </label>
            <label>
                24-Hour Front Desk:
                <input type="checkbox" name="twentyFourHourFrontDesk" checked={facilities.twentyFourHourFrontDesk} onChange={handleChange} />
            </label>
            <label>
                Breakfast:
                <input type="checkbox" name="breakfast" checked={facilities.breakfast} onChange={handleChange} />
            </label>
            <label>
                Airport Shuttle:
                <input type="checkbox" name="airportShuttle" checked={facilities.airportShuttle} onChange={handleChange} />
            </label>
            <label>
                Spa:
                <input type="checkbox" name="spa" checked={facilities.spa} onChange={handleChange} />
            </label>
            <label>
                Bar:
                <input type="checkbox" name="bar" checked={facilities.bar} onChange={handleChange} />
            </label>
            <label>
                Swimming Pool:
                <input type="checkbox" name="swimmingPool" checked={facilities.swimmingPool} onChange={handleChange} />
            </label>
            <label>
                Gym:
                <input type="checkbox" name="gym" checked={facilities.gym} onChange={handleChange} />
            </label>
            <label>
                EV Charging Station:
                <input type="checkbox" name="evChargingStation" checked={facilities.evChargingStation} onChange={handleChange} />
            </label>
            <label>
                Pet Friendly:
                <input type="checkbox" name="petFriendly" checked={facilities.petFriendly} onChange={handleChange} />
            </label>
            <label>
                Restaurant:
                <input type="checkbox" name="restaurant" checked={facilities.restaurant} onChange={handleChange} />
            </label>
            <button type="submit">Submit</button>
        </form>
    );
};

export default FacilityForm;

import React, {useState} from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom';

const CategoryForm = () => {
    const [name, setName] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/properties/category', {name});
            const categoryId = response.data.categoryId;

            navigate(`/facilityForm?categoryId=${categoryId}`);
        } catch (error) {
            console.error('Error saving category', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Category Name"
                   required/>
            <button type="submit">Next</button>
        </form>
    );
};

export default CategoryForm;

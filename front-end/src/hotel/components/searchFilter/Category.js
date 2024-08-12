import React from 'react';
import { Form } from 'react-bootstrap';

let Category = ({ categories, selectedCategories, onCategoryChange }) => {
    let handleCheckboxChange = (category) => {
        let newSelectedCategories = selectedCategories.includes(category)
            ? selectedCategories.filter(c => c !== category)
            : [...selectedCategories, category];
        onCategoryChange(newSelectedCategories);
    };

    return (
        <Form>
            <div className="text-center mb-3">
                <Form.Label>호텔 카테고리</Form.Label>
            </div>

            {categories.map(category => (
                <Form.Check
                    key={category}
                    type="checkbox"
                    label={category}
                    checked={selectedCategories.includes(category)}
                    onChange={() => handleCheckboxChange(category)}
                />
            ))}
            <hr/>
        </Form>
    );
};

export default Category;
import React from 'react'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from 'react-slick';
import { topMeals } from './topMeal';
import CarouselItem from './CarouselItem';

const MultiItemCarousel = () => {
  return (
    <div>
        <Slider>
            {topMeals.map((item)=>(
                <CarouselItem image={item.image} title={item.title} />
            ))}
        </Slider>
    </div>
  )
}

export default MultiItemCarousel
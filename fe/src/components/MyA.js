import styled from "styled-components";
import { Link } from "react-router-dom";
import { useState } from 'react';

import { faA } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MyA = ( {aContent, id} ) => {

    const [isHovering, setIsHovering] = useState(false);

    const handleMouseEnter = () => {
        setIsHovering(true);
    };
    
      const handleMouseLeave = () => {
        setIsHovering(false);
    };

    const style = {
        textDecoration: isHovering? "underline": "none",
        color: "#35383a",
        fontWeight: isHovering? "bold": "normal",
    }

    return (
        <Container>
            <FontAwesomeIcon icon={faA} />
            <Link
                to={`/questions/${id}`}
                style={style}
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}
            >
                <div>{aContent}</div>   
            </Link>
        </Container>
    )
};

const Container = styled.div`
    height: 50px;
    display: flex;
    flex-direction: row;
    align-items: center;
    border-bottom: 1px solid darkgray;

    // A
    > *:first-child {
        padding: 0 36px;
        color: #F5862A;
    }
`

export default MyA;
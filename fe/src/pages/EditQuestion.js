import styled from "styled-components";
import Editor from "../components/Editor";
import img from "../background.jpg"

const Container = styled.div`
    flex-basis: 100vw; 
    flex-shrink: 6;
    height: 90vh;
    display: flex;
    flex-direction: column;
    background-image: url(${img});
    background-repeat: no-repeat;
    background-size: cover;
`

const Header = styled.div`
    padding: 50px 0 10px 50px;
    font-size: 28px;
    font-weight: bold;
    overflow: hidden;
`
const EditQuestion = () => {

    const fetchMode = "patch";

    return (
        <Container>
            <Header>
                Edit your question
            </Header>
            <Editor fetchMode={fetchMode} />
        </Container>
    )
};

export default EditQuestion;
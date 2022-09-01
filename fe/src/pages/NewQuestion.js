import styled from "styled-components";
import Editor from "../components/Editor";

const Container = styled.div`
    flex-basis: 100vw; 
    flex-shrink: 6;
    height: 90vh;
    display: flex;
    flex-direction: column;
`

const Header = styled.div`
    padding: 50px 0 0 50px;
    font-size: 28px;
    font-weight: bold;
    overflow: hidden;
`
const NewQuestion = () => {
    return (
        <Container>
            <Header>
                Ask a public question
            </Header>
            <Editor />
        </Container>
    )
};

export default NewQuestion;
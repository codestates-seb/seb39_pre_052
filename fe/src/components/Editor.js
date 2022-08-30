import styled from "styled-components"

const Container = styled.div`
    flex-basis: 100vw;
    flex-shrink: 6;
    > * {
        margin-top: 30px;
    }
    // Title & Body
    > * > h1 {

    }
`

const Header = styled.div`

`

const Suggestion = styled.div`

`

const Body = styled.div`

`

const Button = styled.button`
    background-color: #0A95FF;
    border: none;
    padding: 15px;
    margin-right: 15px;
    color: white;
    font-size: 16px;
    font-weight: bold;
    :hover {
        background-color: #0074CC;
    }
`

const Editor = () => {
    return (
        <Container>
            <Header>
                <h1>Title</h1>
                <p>Be specific and imagine you’re asking a question to another person</p>
                <input type="text"></input>
            </Header>
            <Suggestion>
                <div>Similar questions</div>
                <div>content</div>
            </Suggestion>
            <Body>
                <h1>Body</h1>
                <p>Include all the information someone would need to answer your question</p>
                <textarea></textarea>
            </Body>
            <Button>Submit</Button>
        </Container>
    )
};

export default Editor;


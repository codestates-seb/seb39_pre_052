import Questions from './tmppage/Questions';
import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";

const Main = styled.div`
  display: flex;
`

const App = () => {
  return (
    <>
      <GlobalStyle />
      <Main>
        <Questions />
      </Main>
    </>
  );
}

export default App;

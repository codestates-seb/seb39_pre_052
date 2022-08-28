import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";
import Questions from './pages/Questions';

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

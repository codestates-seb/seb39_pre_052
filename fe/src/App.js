import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";
import GNB from "./Components/GNB";
import SNB from "./Components/SNB";
import Questions from './Pages/Questions';

const Main = styled.div`
  display: flex;
`

const App = () => {
  return (
    <>
      <GlobalStyle />
      <GNB />
      <Main>
        <SNB />
        <Questions />
      </Main>
    </>
  );
}

export default App;

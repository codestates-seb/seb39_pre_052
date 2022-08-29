import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Footer from "./components/Footer"
import Questions from './pages/Questions';


const Main = styled.div`
  display: flex;
`

const App = () => {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <SNB />
          <Questions />
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;

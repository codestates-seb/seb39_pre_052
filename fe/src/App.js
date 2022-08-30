import styled from "styled-components";
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import GlobalStyle from "./GlobalStyle";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Footer from "./components/Footer"


const Main = styled.div`
  display: flex;
`

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <SNB />
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";
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
          <Routes>
            <Route path="/" element={<Questions />} />
          </Routes>
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;

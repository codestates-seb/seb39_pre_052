import { BrowserRouter, Routes, Route } from 'react-router-dom';
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
      <BrowserRouter>
        <Main>
          <Routes>
            <Route path="/" element={<Questions />} />
          </Routes>
        </Main>
      </BrowserRouter>
    </>
  );
}

export default App;

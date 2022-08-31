import styled from "styled-components";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";

import ReduxTest from "./components/ReduxTest";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Questions from "./pages/Questions";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import NewQuestion from "./pages/NewQuestion";
import Footer from "./components/Footer";

const Main = styled.div`
  display: flex;
`;

const App = () => {

  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <SNB />
          <Routes>
            <Route path="redux" element={<ReduxTest />} />
            <Route path="/" element={<Questions />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
            <Route path="/questions/ask" element={<NewQuestion />} />
          </Routes>
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
};

export default App;

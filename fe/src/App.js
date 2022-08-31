import { useState } from "react";
import styled from "styled-components";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Questions from "./pages/Questions";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import Footer from "./components/Footer";

const Main = styled.div`
  display: flex;
`;

const App = () => {
  const [isLoggedin, setIsLoggedin] = useState(false);

  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <SNB />
          <Routes>
            <Route path="/" element={<Questions />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
          </Routes>
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
};

export default App;

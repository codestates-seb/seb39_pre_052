import { useState } from "react";
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
import PostQAC from "./components/PostQAC";

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
            <Route path="redux" element={<ReduxTest />} />
            <Route path="/" element={<Questions />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<LogIn />} />
            <Route path="/questions/ask" element={<NewQuestion />} />
            <Route path="/questions/1" element={<PostQAC />} />
            {/* /questions/{questionId} 대신 임시로 하드코딩 */}
          </Routes>
        </Main>
        <Footer />
      </BrowserRouter>
    </>
  );
};

export default App;

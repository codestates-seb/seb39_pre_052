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
  flex-direction: column;
  margin-top: 70px;
`;

const Body = styled.div`
  display: flex;
`;

const App = () => {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <Body>
            <SNB />
            <Routes>
              <Route path="redux" element={<ReduxTest />} />
              <Route path="/" element={<Questions />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/login" element={<LogIn />} />
              <Route path="/questions/ask" element={<NewQuestion />} />
              <Route path="/questions/1" element={<PostQAC />} />
              {/* /questions/{questionId} 대신 임시로 하드코딩 -> 나중에 slice에 저장되어있는 id로 가져오기*/}
            </Routes>
          </Body>
          <Footer />
        </Main>
      </BrowserRouter>
    </>
  );
};

export default App;

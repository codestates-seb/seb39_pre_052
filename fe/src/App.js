import styled from "styled-components";
import { BrowserRouter, Routes, Route, useParams } from "react-router-dom";
import GlobalStyle from "./GlobalStyle";
import { useSelector } from 'react-redux';

import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Home from "./pages/Home";
import Questions from "./pages/Questions";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import NewQuestion from "./pages/NewQuestion";
import Footer from "./components/Footer";
import PostQAC from "./components/PostQAC";
import MyPage from "./pages/MyPage";
import SearchResult from "./pages/SearchResult";

const Main = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 70px;
`;

const Body = styled.div`
  display: flex;
`;

const App = () => {
  
  const { id } = useParams();
  const questionId = useSelector((state) => {
    return state.editMode.questionId;
  });
console.log(questionId)
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <GNB />
        <Main>
          <Body>
            <SNB />
            <Routes>
              <Route path="/" element={<Home />}/>
              <Route path="/questions" element={<Questions />} />
              <Route path="/search" element={<SearchResult />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/login" element={<LogIn />} />
              <Route path="/mypage" element={<MyPage />} />
              <Route path="/questions/ask" element={<NewQuestion />} />

              {/* <Route path={`/api/questions/1`} element={<PostQAC />} /> */}
              <Route path={`/questions/${questionId}`} element={<PostQAC />} /> 
              {/* <Route path={`/questions/`+id} element={<PostQAC />} />  //useParams 사용*/}
              {/* <Route path={`/api/questions/${questionId}`} element={<PostQAC />} /> */}
              {/* /questions/{questionId} 대신 임시로 하드코딩 -> 나중에 slice에 저장되어있는 id로 가져오기 */}

            </Routes>
          </Body>
          <Footer />
        </Main>
      </BrowserRouter>
    </>
  );
};

export default App;

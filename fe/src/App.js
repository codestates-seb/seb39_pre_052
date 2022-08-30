import { BrowserRouter, Routes, Route } from 'react-router-dom';
import GlobalStyle from "./GlobalStyle";
import NewQuestion from './pages/NewQuestion';

const App = () => {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
          <Routes>
            <Route path="/questions/ask" element={<NewQuestion />} />
          </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;

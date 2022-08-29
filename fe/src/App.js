import GlobalStyle from "./GlobalStyle";
import styled from "styled-components";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Footer from "./components/Footer"
import Questions from './pages/Questions';

// redux toolkit related

// const Counter = () => {
//   const dispatch = useDispatch();
//   const count = useSelector(state => {
//     console.log(state);
//     return state.counter.value;
//   })

//   return (
//     <div>
//       <button onClick={() => {
//         dispatch(up(1));
//         }}>+</button> {count}
//     </div>
//   )
// }


const Main = styled.div`
  display: flex;
`

const App = () => {
  return (
    <>
      <GlobalStyle />
      <GNB />
      <Main>
        <SNB />
        <Questions />
      </Main>
      <Footer />
    </>
  );
}

export default App;

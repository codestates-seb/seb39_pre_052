import styled from "styled-components";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Footer from "./components/Footer"

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

function App() {
  return (
    <>
      <GNB />
      <Main>
        <SNB />
      </Main>
      <Footer />
    </>
  );
}

export default App;

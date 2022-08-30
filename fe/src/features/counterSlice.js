import { createSlice } from "@reduxjs/toolkit";

const counterSlice = createSlice({
    name: 'counter',
    initialState: {value: 0},
    reducers: {
      up: (state, action) => {
        state.value = state.value + action.payload;
      }
    }
});

export default counterSlice;
export const {up} = counterSlice.actions;
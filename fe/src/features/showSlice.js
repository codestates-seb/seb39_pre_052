import { createSlice } from "@reduxjs/toolkit";

const showSlice = createSlice({
    name: 'toggle',
    initialState: {active: false},
    reducers: {
      changeShow: (state, action) => {
        state.active = !state.active;
      }
    }
});

export default showSlice;
export const { changeShow } = showSlice.actions;
import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    currentUser: null,
    isFetching: false,
    error: false,
    errorMessage: "",
  },
  reducers: {
    loginStart: (state) => {
      console.log("Login started");
      state.isFetching = true;
      state.error = false;
      state.errorMessage = "";
    },
    loginSuccess: (state, action) => {
      state.isFetching = false;
      state.currentUser = action.payload;
      state.errorMessage = "";
    },
    loginFailure: (state, action) => {
      state.isFetching = false;
      state.error = true;
      state.errorMessage = action.payload;
    },
    signupStart: (state) => {
      state.isFetching = true;
      state.errorMessage = "";
    },
    signupSuccess: (state, action) => {
      state.isFetching = false;
      // state.currentUser = action.payload;
      state.errorMessage = "";
    },
    signupFailure: (state, action) => {
      state.isFetching = false;
      state.error = true;
      state.errorMessage = action.payload;
    },
    logoutUser(state, action) {
      // Note that this should be left intentionally empty.
    },
    clearErrorMessage(state) {
      console.log("messages cleared");
      state.error = false;
      state.errorMessage = "";
    },
  },
});

export const {
  loginStart,
  loginSuccess,
  loginFailure,
  signupStart,
  signupSuccess,
  signupFailure,
  logoutUser,
  clearErrorMessage,
} = userSlice.actions;
export default userSlice.reducer;

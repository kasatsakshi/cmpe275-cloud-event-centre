import {
  signupStart,
  signupSuccess,
  signupFailure,
  loginStart,
  loginFailure,
  loginSuccess,
  logoutUser,
  clearErrorMessage,
} from "./userRedux";
import axios from "axios";

export const signup = (dispatch, user) => {
  dispatch(signupStart());
  dispatch(clearErrorMessage());
  axios
    .post(`/api/user`, null, { params: user })
    .then((res) => {
      if (res.status === 201) {
        dispatch(signupSuccess(res.data));
      }
    })
    .catch((err) => {
      dispatch(clearErrorMessage());
      console.log(err.response.data.message);
      if (err.response.data.message) {
        const errMsg = err.response.data.message;
        dispatch(signupFailure(errMsg));
      } else {
        dispatch(signupFailure("Internal Server error"));
      }
    });
};

export const login = (dispatch, user) => {
  dispatch(loginStart());
  dispatch(clearErrorMessage());
  axios
    .post(`/api/user`, null, { params: user })
    .then((res) => {
      if (res.status === 200) {
        dispatch(loginSuccess(res.data));
      }
    })
    .catch((err) => {
      dispatch(clearErrorMessage());
      console.log(err.response.data.message);
      if (err.response.data.message) {
        const errMsg = err.response.data.message;
        dispatch(loginFailure(errMsg));
      } else {
        dispatch(loginFailure("Internal Server error"));
      }
    });
};

export const logout = (dispatch) => {
  dispatch(logoutUser());
};

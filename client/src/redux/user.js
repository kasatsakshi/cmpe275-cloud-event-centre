import { signupStart, signupSuccess, signupFailure, loginStart, loginFailure, loginSuccess, logoutUser } from "./userRedux";
import axios from 'axios';

export const signup = async (dispatch, user) => {
  dispatch(signupStart());
  try {
    const res = await axios.post(`/api/user`, null, {
      params: user
    });
    dispatch(signupSuccess(res.data));
  } catch (err) {
    dispatch(signupFailure());
  }
};

export const login = async (dispatch, user) => {
  dispatch(loginStart());
  try {
    const res = await axios.post(`/api/user`, null, { params: user });
    console.log(JSON.stringify(res));
    dispatch(loginSuccess(res.data));
  } catch (err) {
    dispatch(loginFailure());
  }
};

export const logout = (dispatch) => {
  dispatch(logoutUser());
};
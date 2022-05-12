import { signupStart, signupSuccess, signupFailure, loginStart, loginFailure, loginSuccess, logoutUser } from "./userRedux";
import axios from 'axios';

export const signup = async (dispatch, user) => {
  dispatch(signupStart());
  try {
    // const res = await publicRequest.post("/signup", user);
    console.log("user: " + user);
    const res = await axios.post(`/api/user`, null, {
      params: user
    });
    console.log("SK : " + res);
    dispatch(signupSuccess(res.data));
  } catch (err) {
    dispatch(signupFailure());
  }
};

export const logout = (dispatch) => {
  dispatch(logoutUser());
};
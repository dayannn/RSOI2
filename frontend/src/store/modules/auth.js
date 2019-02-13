/* eslint-disable promise/param-names */
import { AUTH_REQUEST, AUTH_ERROR, AUTH_SUCCESS, AUTH_LOGOUT } from '../actions/auth'
import axios from 'axios'
import store from '../../store' // your vuex store

const state = { token: localStorage.getItem('user-token') || '', status: '', hasLoadedOnce: false };

const getters = {
  isAuthenticated: state => !!state.token,
  authStatus: state => state.status,
};

const actions = {
  [AUTH_REQUEST]: ({commit, dispatch}, user) => {
    return new Promise((resolve, reject) => {
      commit(AUTH_REQUEST);
        const data = new FormData();

        data.append('username', user.username);
        data.append('password', user.password);

        let config = {
          headers: {
              Authorization: "Basic " + btoa("webApp:webAppSecret")
          }
      };

      axios.post('api/login', data, config)
      .then(resp => {

        localStorage.setItem('user-token', resp.data);
        // axios.defaults.headers.common['Authorization'] = resp.token

        commit(AUTH_SUCCESS, resp);
        resolve(resp);
      })
      .catch(err => {
        console.log(err);
        commit(AUTH_ERROR, err);
        localStorage.removeItem('user-token');
        reject(err)
      })
    })
  },
  [AUTH_LOGOUT]: ({commit, dispatch}) => {
    return new Promise((resolve, reject) => {
      commit(AUTH_LOGOUT);
      localStorage.removeItem('user-token');
      resolve()
    })
  }
};

const mutations = {
  [AUTH_REQUEST]: (state) => {
    state.status = 'loading'
  },
  [AUTH_SUCCESS]: (state, resp) => {
    console.log("successful auth");
    console.log("state = " + state);
    state.status = 'success';
    state.token = resp.data;
    state.hasLoadedOnce = true;
    console.log("state = " + state);
  },
  [AUTH_ERROR]: (state) => {
    state.status = 'error';
    state.hasLoadedOnce = true;
  },
  [AUTH_LOGOUT]: (state) => {
    state.token = ''
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
}

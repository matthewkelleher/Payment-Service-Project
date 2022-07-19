import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    payClicked: false,
    showStatements: false,
    showPending: false,
    showUserSearch: false,
   
    activeUser: {
    userName: null,
    userId: null,
    accountId: null,
    balance: null,
    firstname: null,
    lastname: null,
    hasProfile: false,
    },
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.payClicked = false;
      state.UserSearch = false;
      state.showPending = false;
      state.showStatements = false;
     
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    PAY_CLICKED(state, bool) {
      state.payClicked = bool;
    },
    SHOW_STATEMENTS(state, bool) {
      state.showStatements = bool;
    },
    SHOW_PENDING(state, bool) {
      state.showPending = bool;
    },
    SHOW_USER_SEARCH(state, bool) {
      state.showUserSearch = bool;
    },
   
    SET_ACTIVE_USER(state, user) {
      state.activeUser = user;
      
      
    },
    CLEAR_ACTIVE_USER(state) {
      state.activeUser = null;
    },
    SET_ALL_FALSE(state) {
      state.payClicked = false;
      state.UserSearch = false;
      state.showPending = false;
      state.showStatements = false;
    },
    PROFILE_NEEDED(state, bool) {
      state.hasProfile = bool;
    },
    SUBTRACT_FUNDS(state, amount) {
      state.activeUser.balance -= amount;
      console.log("This happened.")
      console.log(state.activeUser.balance)
    }
  }
})

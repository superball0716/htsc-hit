import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
 
const store = new Vuex.Store({
 
  state: {
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
    role: localStorage.getItem('role') ? localStorage.getItem('role') : '',
    uName: localStorage.getItem('uName') ? localStorage.getItem('uName') : '',
    loginId: localStorage.getItem('loginId') ? localStorage.getItem('loginId') : '',
    refreshFundList:false,

    getters: {
      uName:   state => {
        return state.uName;
      },
      loginId: state => {
        return state.loginId;
      }
    }
  },
 
  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      console.log(":changeLogin")
      state.Authorization = user.Authorization;
      state.role = user.role;
      state.uName = user.uName;
      state.loginId = user.loginId;

      localStorage.setItem('Authorization', user.Authorization);
      localStorage.setItem('role', user.role);
      localStorage.setItem('uName', user.uName);
      localStorage.setItem('loginId', user.loginId);

      console.log(state)

    }
  }
});
 
export default store;
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vuex from 'vuex'
import axios from 'axios'
import VueAxios from 'vue-axios'
import store from './store/index.js'
import '../config/axios'
import toast from './alert/index'




Vue.config.productionTip = false


Vue.use(ElementUI)
Vue.use(Vuex)
Vue.use(VueAxios,axios)
Vue.use(toast)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
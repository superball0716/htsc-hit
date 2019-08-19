import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import FundProduct from '@/components/FundProduct'
import FundSecKill from '@/components/FundSecKill'
import FundProductEdit from '@/components/FundProductEdit'
import FundProductDetail from '@/components/FundProductDetail'
import ManagerList from '@/components/ManagerList'
import ManagerEdit from '@/components/ManagerEdit'
import Login from '@/components/Login'
import register from '@/components/register'
import store from '../store/index.js'


import demo from '@/components/demo'

Vue.use(Router)

const router =  new Router({
  routes: [
    {   
        path: '/',
        redirect: '/FundProduct'
    },

    {
      path: '/',
      name: 'Index',
      component: Index,
      children:[
        {
          path: 'FundProduct',
          name: 'FundProduct',
          component: FundProduct
        },
        {
          path: 'FundSecKill',
          name: 'FundSecKill',
          component: FundSecKill
        },
        {
          path: 'FundProductEdit',
          name: 'FundProductEdit',
          component: FundProductEdit
        },
        {
          path: 'FundProductDetail',
          name: 'FundProductDetail',
          component: FundProductDetail
        },
        {
          path: 'ManagerList',
          name: 'ManagerList',
          component: ManagerList
        },
        {
          path: 'ManagerEdit',
          name: 'ManagerEdit',
          component: ManagerEdit
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path:'/demo',
      name:'demo',
      component:demo
    }
    
  ],

});

//token校验，如无缓存token跳转login
router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === "/register") {
    store.state.Authorization = null;
    store.state.role = null;
    store.state.uName = null;
    store.state.loginId = null;
      
    localStorage.setItem('Authorization', null);
    localStorage.setItem('role', null);
    localStorage.setItem('uName', null);
    localStorage.setItem('loginId', null);
    next();
  } else {
    let token = localStorage.getItem('Authorization');
    if (token === 'null' || token === '' || token == null || token == "undefined") {
      next('/login');
    } else {
      next();
    }
  }
});

 
export default router;

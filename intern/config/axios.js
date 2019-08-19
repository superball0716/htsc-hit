
import axios from 'axios'  
import router from '../src/router'
 
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
　　// 在发送请求之前做些什么
	if (localStorage.getItem('Authorization')) {
	    config.headers.Authorization = localStorage.getItem('Authorization');
	}
　　return config
}, 
function (error) {
　　// 对请求错误做些什么
	return Promise.reject(error)
});
 
// 添加响应拦截器
axios.interceptors.response.use(function (response) {
	if (response.data.Expire === 1) {

		store.state.Authorization = null;
      	store.state.role = null;
      	store.state.uName = null;
      	store.state.loginId = null;
	    
	    localStorage.setItem('Authorization', null);
	    localStorage.setItem('role', null);
	    localStorage.setItem('uName', null);
	    localStorage.setItem('loginId', null);
	    console.log("changed")
		router.push({
	        path: "/login"
	    });
	}
	else{
		return response
	}
})


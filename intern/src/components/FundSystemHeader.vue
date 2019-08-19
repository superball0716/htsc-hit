<template>
	<div class = "HeaderBack" v-bind:style="{backgroundImage:'url(' + bg + ')',backgroundSize:'100% 100%'}">
		<div v-if="showicon" style="float:right;width:15%;height:100%;" id="useravatar">
			<div>
				<el-button icon="el-icon-user-solid" circle style="float:left;font-size:20px;padding:4px;margin-top:15px;"></el-button>

				<el-dropdown @command="tologout" style="float:left">
					<span class="el-dropdown-link">
						<p style ='float:left;margin-left:10px;
						margin-top:20px;color:white;'>{{fullName}}</p>
						<i style="color:white;margin-top:22px;float:left;margin-left:10px;" 
						class="el-icon-caret-bottom el-icon--right"></i>
					</span>

					<el-dropdown-menu slot="dropdown">
					    <el-dropdown-item command="a" style="padding:0px 40px">
					    	<i class="el-icon-switch-button"></i>登出
					    </el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>

			</div>
		</div>

	</div>
</template>

<script>
import store from '../store/index.js'

export default {

  name: 'FundSystemHeader',
  data () {
  	return{
  		activeIndex:"1",
  		color:"ffffff",
  		showicon:store.state.Authorization == null?false:true,
  		bg: require('../assets/images/HeaderBackground.png'),
  		fullName: store.state.loginId+'('+ store.state.uName + ')',
  	}
  },

  methods:{
  	tologout(command){
  		store.state.Authorization = null;
      	store.state.role = null;
      	store.state.uName = null;
      	store.state.loginId = null;
	    
	    localStorage.setItem('Authorization', null);
	    localStorage.setItem('role', null);
	    localStorage.setItem('uName', null);
	    localStorage.setItem('loginId', null);
		this.$router.push('/login')
  	}

  }
}
</script>

<style>
	.HeaderBack{
		height:60px;
		position:absolute;
		width:100%;
	}

	/*.el-menu{
		background-color:transparent;
	}

	.el-menu--horizontal>.el-submenu .el-submenu__title {
	    color: white;
	}

	.el-menu--horizontal>.el-submenu .el-submenu__title:hover {
		background-color:transparent;
		color: white;
	}

	.el-menu--horizontal>.el-submenu.is-active .el-submenu__title {
		color:white;
		border-bottom:none;
	}*/

</style>

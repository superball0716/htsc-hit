<template>
	<div style="height:100%;">
    <div style="position:relative;height:100%;width:100%">
  		<el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect" style="padding-left:20px;position:fixed;width:-webkit-fill-available" router>
  		  <el-menu-item index="/FundProduct">基金产品管理</el-menu-item>
  		  <!--<el-menu-item index="/FundSecKill">秒杀活动管理</el-menu-item>-->
  		  <el-menu-item index="/ManagerList">经理信息管理</el-menu-item>
  		</el-menu>
      <keep-alive>
      <router-view style="padding-top:45px;"></router-view>
      </keep-alive>
    </div>
	</div>
</template>

<script>

export default {
  name: 'FundSystemTab',
  data () {
  	return{
  		activeIndex: "/FundProduct",
  	}
  },
  methods: {
    handleSelect(key, keyPath) {
        this.activeIndex = key
    }
  },
  mounted() {
    var cur_path = this.$route.path; //获取当前路由
    if (cur_path.indexOf("/Fund") >=0) {
      this.activeIndex = '/FundProduct'
    }
    else{
      this.activeIndex = "/ManagerList"
    }
  },
}
</script>

<style>

.el-menu--horizontal>.el-menu-item {
    height: 30px!important;
    line-height: 30px!important;
    margin: 5px!important;
    color:black!important;
}

.el-menu--horizontal>.el-menu-item.is-active {
    border-bottom: 0!important;
    color: white !important;
    background-color: rgba(183, 147, 98, 1) !important;
}


a{
  text-decoration:none
}
</style>
<template>
	<div style="padding-top:45px;height: -webkit-fill-available;">
		<div class = "bread">
			<el-breadcrumb separator-class="el-icon-arrow-right">
        	<el-breadcrumb-item>基金产品管理</el-breadcrumb-item>
			<el-breadcrumb-item :to="{ path: '/FundProduct' }">产品列表</el-breadcrumb-item>
			<el-breadcrumb-item>产品详情</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

		<div class="fundSec">
		  <div class="tableborder">
				<p class='editHeader'>产品详情</p>
				<div>
					<el-form ref="detailForm" :model="detail" label-width="180px" size="mini" class="detailForm" style="padding-bottom:10px;">
		      		<el-form-item class="labelfont" label="产品编号:" prop="identifier">
		      			  <p class="info">{{detail.identifier}}</p>
		      		</el-form-item>
					    <el-form-item label="产品名称:" prop="pName">
					   	    <p class="info">{{detail.pName}}</p>
					    </el-form-item>
					    <el-form-item label="产品净值:" prop="worth">
                  <p class="info">{{detail.worth}}元</p>
					    </el-form-item>
					    <el-form-item label="投资起点:" prop="startPoint">
					   	     <p class="info">{{detail.startPoint}}元</p>
					    </el-form-item>
					    <el-form-item label="七日年化收益率:" prop="profitRate">
                  <p class="info">{{detail.profitRate}}%</p>
					    </el-form-item>
					    <el-form-item label="产品介绍信息:" prop="description">
                <el-tooltip :content="detail.description" placement="bottom-start" effect="light">
					    	  <p class="info multiLine" :show-overflow-tooltip='true'>{{detail.description}}</p>
                </el-tooltip>
					    </el-form-item>
					    <el-form-item label="经理人介绍:" prop="managerName">
                  <div style="width:110px;height:110px;" >	   
                  <img style="width:110px;height:110px;" :style="{backgroundImage:'url('+detail.img+')',backgroundSize:'100% 100%'}"></img>
                  </div>
                  <p class="info">{{detail.managerName}}</p>
                  <p class="info multiLine">{{detail.managerDes}}</p>
					    </el-form-item>
					    <el-form-item label="产品库存:" prop="num">
                  <p class="info">{{detail.num}}份</p>
					    </el-form-item>
					    <el-form-item v-if="showsec" label="秒杀时间:" prop="secTime">
                  <p class="info">{{detail.secTime}}</p>
    					</el-form-item>
				  </el-form>
          <div class="secDiv" v-if="showsec">
            <el-button type="primary" :disabled = "secDisable" size="medium" @click="secReq()">立即秒杀</el-button>
            <p v-bind:class="readySec?readyText:unreadyText">{{countdown}}</p>
          </div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>

import store from '../store/index.js'

export default {
  name: 'FundProductDetail',
  data () {
   	return{
  		detail:{
  			identifier:'',
  			pName:'',
  			worth:'',
  			startPoint:'',
  			profitRate:'',
  			description:'',
  			mId:'',
  			managerName:'',
        managerDes:'',
  			num:'',
  			secTime:'',
        img:'',
  		},
      pId:'',
      showsec:false,
      secDisable:true,
      readySec:false,
      countdown:'',
      readyText:'readyText',
      unreadyText:'unreadyText',
      startTime:'',
      endTime:'',
  	}
  },
  methods:{
    secReq(){
      let _this = this;
      this.axios({
        method: 'post',
        url: '/api/secRequest',
        data: {loginId:store.state.loginId,pId:_this.pId}
      }).then(res => {
        if(res.data.result == "1"){
          _this.$message({
            showClose: true,
            message: '秒杀成功',
            type: 'success'
          });
        }
        else{
          _this.$message('秒杀失败');

        }
        this.loadFundProductDetail(this.pId)
      }).catch(error => {

      });

    },

    loadFundProductDetail(pId){
      let _this = this
      var fund;
      var secbind;
      var parsedTime = ['',''];
      this.axios({
        method: 'get',
        url: '/api/getFundDetailByPId?pId='+pId,
      }).then(res => {

        fund = res.data.fundDetail.fund;
                  console.log(fund.productNum == 0)

        if(res.data.fundDetail.secTime[0] != null && res.data.fundDetail.secTime[1] != null && fund.productNum>=0){
          _this.showsec = true
          secbind = res.data.fundDetail.secTime[0]+" - "+res.data.fundDetail.secTime[1]
          _this.startTime = _this.getDateFromstr(res.data.fundDetail.secTime[0])
          _this.endTime = _this.getDateFromstr(res.data.fundDetail.secTime[1])

          var now = new Date()
          if (fund.productNum == 0){
              console.log("aaaaaaaaaaaaaaaaaaend")
              _this.secDisable = true
              _this.readySec = false
              _this.countdown = '秒杀已结束'
          }
          else{
            if(_this.startTime < now && _this.endTime > now){
              _this.secDisable = false
              _this.countdown = ''
            }
            else{
              if(now < _this.startTime){
                _this.secDisable = true
                var second = Math.round((_this.startTime.getTime()-now.getTime())/1000)
                if(second < 900){
                  var m = Math.round(second/60)
                  var s = second%60
                  if(m < 10){
                    m = '0' + m
                  }
                  if(s < 10){
                    s = '0' + s
                  }
                  _this.readySec = true
                  _this.countdown = '距离秒杀时间开始还有00:'+m+':'+s
                }
                else{
                  _this.readySec = false
                  _this.countdown = '秒杀未开始'
                }
              }
              else{
                _this.secDisable = true
                _this.readySec = false
                _this.countdown = '秒杀已结束'
              }
            }
          }
        }
        else{
            _this.showsec = false
        }

        _this.detail = {
          pId:pId,
          identifier:fund.identifier,
          pName:fund.pName,
          worth:fund.worth,
          startPoint:fund.startPoint,
          profitRate:fund.profitRate,
          description:fund.description,
          mId:fund.mId,
          managerName:fund.mName+'('+fund.jobId+')',
          managerDes:res.data.fundDetail.md.description,
          num:fund.productNum,
          secTime:secbind,
          img:'/api'+res.data.fundDetail.md.img_url,
        }

      }).catch(error => {

      });
    },


    getDateFromstr(str){
      var date = str.split(" ")[0]
      var second = str.split(" ")[1]
      return new Date(date.split("-")[0],date.split("-")[1]-1,date.split("-")[2],
        second.split(":")[0],second.split(":")[1],second.split(":")[2])
    },
  },
  activated(){
    var pId = location.href.split("?")[1].split("&")[0].split("=")[1]
    this.pId = pId
    this.loadFundProductDetail(pId);
  },
  mounted() {
    let _this = this;
    this.timer = setInterval(() => {
      var now = new Date()
      if (_this.detail.num == 0){
        _this.secDisable = true
        _this.readySec = false
        _this.countdown = '秒杀已结束'
      }
      else{
        if(_this.startTime < now && _this.endTime > now){
          _this.secDisable = false
          _this.countdown = ''
        }
        else{
          if(now < _this.startTime){
            _this.secDisable = true
            var second = Math.round((_this.startTime.getTime()-now.getTime())/1000)
            if(second < 900){
              var m = Math.round(second/60)
              var s = second%60
              if(m < 10){
                m = '0' + m
              }
              if(s < 10){
                s = '0' + s
              }
              _this.readySec = true
              _this.countdown = '距离秒杀时间开始还有00:'+m+':'+s
            }
            else{
              _this.readySec = false
              _this.countdown = '秒杀未开始'
            }
          }
          else{
            _this.secDisable = true
            _this.readySec = false
            _this.countdown = '秒杀已结束'
          }
        }
      }
    }, 1000)
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }
}
</script>

<style scoped>
  @import '../assets/css/FundProductDetail.css';
</style>
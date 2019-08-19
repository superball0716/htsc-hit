<template>
	<div style="padding-top:45px;height: -webkit-fill-available;">
		<div class = "bread">
			<el-breadcrumb separator-class="el-icon-arrow-right">
        	<el-breadcrumb-item>基金产品管理</el-breadcrumb-item>
			<el-breadcrumb-item :to="{ path: '/FundProduct' }">产品列表</el-breadcrumb-item>
			<el-breadcrumb-item>{{editType}}</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

		<div class="fundDetail" id="fundDetailEdit">
		    <div class="tableborder">
				<p class='editHeader'>{{editType}}</p>
				<div style="font-size:13px">
					<el-form ref="checkForm" :model="editForm" :rules="rules" label-width="180px" size="mini" class="editInput">
		      			<el-form-item v-if="showId" class="labelfont" label="产品编号:" prop="identifier">
		      				<p style="margin:0;text-align:left">{{editForm.identifier}}</p>
		      			</el-form-item>
					    <el-form-item label="产品名称:" prop="pName">
					   	    <el-input type="text" placeholder="请输入产品名称最长不超过20个字" v-model="editForm.pName" style="width:35%;	float:left;"/>
					    </el-form-item>
					    <el-form-item label="产品净值:" prop="worth">
					   	    <el-input type="number" placeholder="请输入产品净值" v-model="editForm.worth" style="width:15%;float:left;"/>
					   	    <p style="margin:0;display:inline-block;float:left;margin-left:5px;">元</p>
					    </el-form-item>
					    <el-form-item label="投资起点:" prop="startPoint">
					   	    <el-input type="number" placeholder="请输入产品投资起点" v-model="editForm.startPoint" style="width:15%;float:left;"/>
					   	    <p style="margin:0;display:inline-block;float:left;margin-left:5px;">元</p>
					    </el-form-item>
					    <el-form-item label="七日年化收益率:" prop="profitRate">
					   	    <el-input type="number" placeholder="请输入产品七日年化收益率" v-model="editForm.profitRate" style="width:15%;float:left;"/>
					   	    <p style="margin:0;display:inline-block;float:left;margin-left:5px;">%</p>
					    </el-form-item>
					    <el-form-item label="产品介绍信息:" prop="description">
					    	<div style="width:50%">
						   	    <el-input placeholder="请输入产品介绍不超过200字" type="textarea"
						    	:autosize="{ minRows: 4, maxRows: 6}" v-model="editForm.description" style="width:100%;float:left"/>
						    	<p style="width:50px;float:right;margin:0;font-size: 12px;line-height:25px;">{{descriptionLength}}/200</p>
					    	</div>
					    </el-form-item>
					    <el-form-item label="经理人:" prop="managerName">				   
						    <el-autocomplete id="managerFilter" v-model="editForm.managerName" style="float:left" :fetch-suggestions="queryManagerSearchAsync" placeholder="请输入经理人工号或姓名" @select="handleManagerSelect" v-on:blur="managerNameRefresh()">

			  					<i class="el-icon-arrow-down el-input__icon" slot="suffix" @click="getAllManager">
			  					</i>
		  					</el-autocomplete>
					    </el-form-item>
					    <el-form-item label="产品库存:" prop="num">
					   	    <el-input type="number" placeholder="请输入产品库存份量" style="width:15%;float:left;" v-model="editForm.num"/>
					   	    <p style="margin:0;display:inline-block;float:left;margin-left:5px;">份</p>
					    </el-form-item>
					    <el-form-item label="秒杀时间:" prop="secTime">
					    	<el-date-picker v-model="editForm.secTime" type="datetimerange" align="right" start-placeholder="开始时间" end-placeholder="结束时间" prefix-icon="el-icon-date"  style="float:left" value-format="yyyy-MM-dd HH:mm:ss">
    						</el-date-picker>
    					</el-form-item>

					  	<el-form-item>
					  		<div style="width:50%;padding-bottom:10px;overflow:hidden">
					  			<el-button style="float:right;width:80px;height:38px;" type="primary" v-on:click="saveEdit('checkForm')">保存</el-button>
					  			<el-button style="float:right;margin-right:20px;width:80px;height:38px;" v-on:click="cancelEdit()">取消</el-button>
					    	</div>
					    </el-form-item>
				    </el-form>
				</div>
			</div>
		</div>
	</div>
</template>

<script>

import store from '../store/index.js'

export default {
  name: 'FundProductEdit',
  data () {
  	var validatemId = (rule, value, callback) => {
        if (this.editForm.mId === '' || this.editForm.mId == null) {
          callback(new Error('请正确输入经理人!'));
        } else {
          callback();
        }
    };

    var validateSecTime = (rule, value, callback) => {
    	if(value[0] > value[1]){
    		callback(new Error('结束时间不得小于开始时间'));
    	}else{
    		callback();
    	}
    };

    var validateWorth = (rule, value, callback) => {
    	if(value == null){
    		callback(new Error('请正确输入产品净值!'))
    	}else if(value < 0){
    	    callback(new Error('产品净值不能为负!'))
    	}else if(value > 1e19){
          callback(new Error('产品净值过高!'))

      }
    	else{
    		callback();
    	}
    };

    var validatePoint = (rule, value, callback) => {
    	if( value == null){
    		callback(new Error('请正确输入投资起点!'))
    	}else if(value < 0){
    	    callback(new Error('投资起点不能为负!'))
    	}else if(value > 1e19){
          callback(new Error('投资起点过高!'))
      }
    	else{
    		callback();
    	}
    };

    var validateProfit = (rule,value,callback) => {
      if( value == null){
        callback(new Error('请正确输入七日年化收益率!'))
      }else if(value > 1e19){
          callback(new Error('七日年化收益率过高!'))
      }
      else{
        callback();
      }
    };

    var validateNum = (rule,value,callback) => {
      if( value == null){
        callback(new Error('请正确输入产品库存!'))
      }else if(value < 0){
        callback(new Error('产品库存不能为负!'))
      }else if(value > 1e10){
          callback(new Error('产品库存过高!'))
      }
      else{
        callback();
      }

    }

   	return{
  		editForm:{
  			identifier:'',
  			pName:'',
  			worth:'',
  			startPoint:'',
  			profitRate:'',
  			description:'',
  			mId:'',
  			managerName:'',
  			num:'',
  			secTime:['',''],
  			pId:'',
  		},
  		pNameLength:0,
  		descriptionLength:0,
  		select:'',
  		managerList:'',
  		showId:false,
  		editType:'',
  		rules: {
          pName: [
            {required: true, message: '请正确输入产品名称!', trigger: 'blur'}
          ],
          worth: [
            {required: true, validator:validateWorth, trigger: 'blur'}
          ],
          startPoint: [
            {required: true, validator:validatePoint, trigger: 'blur'}
          ],
          profitRate: [
            {required: true, validator:validateProfit, trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请正确输入产品介绍信息!', trigger: 'blur'}
          ],
          managerName: [
            {required:true ,validator:validatemId, trigger: 'change'}
          ],
          num: [
            {required: true, validator:validateNum, trigger: 'blur'}
          ],
          secTime:[
          	{validator:validateSecTime, trigger: 'blur'}
          ],
        },
  	}
  },
  watch:{
  	'editForm.description':function(val,oldVal){
  		if (this.getByteLen(val) > 400){
  			this.editForm.description = oldVal
  			this.$toast("不能超过200个字")
  		}
  		else{
  			this.descriptionLength = parseInt(this.getByteLen(val)/2)
  		}

  	},
  	'editForm.pName':function(val,oldVal){
  		if (this.getByteLen(val) > 40){
  			this.editForm.pName = oldVal
  			this.$toast("不能超过20个字")
  		}
  	}
  },
  methods:{
  	cancelEdit(){
		window.history.back(-1)
  	},
  	saveEdit(formName){
  		this.$refs[formName].validate((valid)=> {
  			if(valid){
  			console.log(typeof(this.editForm.secTime[0]))
  				if(typeof(this.editForm.secTime[0])== 'object'){
				  	this.editForm.secTime[0] = this.format(this.editForm.secTime[0])
  					this.editForm.secTime[1] = this.format(this.editForm.secTime[1])

  				}
  				var edit = {
  					pId:this.editForm.pId,
  					pName:this.editForm.pName,
  					worth:this.editForm.worth,
  					startPoint:this.editForm.startPoint,
  					profitRate:this.editForm.profitRate,
  					description:this.editForm.description,
  					mId:this.editForm.mId,
  					num:this.editForm.num,
  					startTime:this.editForm.secTime[0],
  					endTime:this.editForm.secTime[1],
  				}
  				
  				this.axios({
		          method: 'post',
		          url: '/api/editFund',
		          data:edit,
		        }).then(res => {
		        	console.log("edit send")
		        	store.state.refreshFundList = true
  					this.$router.push('/FundProduct');
		        }).catch(error => {

		        });
  			}
  		});

  	},

  	getByteLen(val) {
      var len = 0;
      for (var i = 0; i < val.length; i++) {
        var a = val.charAt(i);
        if (a.match(/[^\x00-\xff]/ig) != null) {
          len += 2;
        }
        else {
          len += 1;
        }
      }
      return len;
    },


  	getAllManager(){
  		this.editForm.managerName=""
  	},

  	loadFundInfo(pId){
  		let _this = this
		  var fund;
		  var sec;
		  var parsedTime = ['',''];
        this.axios({
          method: 'get',
          url: '/api/getFundByPId?pId='+pId+'&pageSize=10&pageNum=1'
        }).then(res => {
          fund = res.data.allFund;
          sec = res.data.secTime;
          if(sec[0] != null && sec[1] != null){
          	parsedTime = [_this.getDateFromstr(sec[0]),_this.getDateFromstr(sec[1])]

          }
          _this.editForm = {
          	pId:pId,
          	identifier:fund.identifier,
          	pName:fund.pName,
          	worth:fund.worth,
          	startPoint:fund.startPoint,
          	profitRate:fund.profitRate,
          	description:fund.description,
          	mId:fund.mId,
          	managerName:fund.mName+'('+fund.jobId+')',
          	num:fund.productNum,
          	secTime:parsedTime,
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

    
    format(time){
      var year = time.getFullYear();
      var month = time.getMonth()+1;
      var date = time.getDate();
      var hours = time.getHours();
      var minutes = time.getMinutes();
      var seconds = time.getSeconds();
      return year+'-'+this.add0(month)+'-'+this.add0(date)+' '+this.add0(hours)+':'+this.add0(minutes)+':'+this.add0(seconds);
    },

    add0(m){
      return m<10?'0'+m:m
    },

    loadAllManagerList(){
        let _this = this
        this.axios({
          method: 'get',
          url: '/api/vagueSearchByManager?keyword=',
        }).then(res => {
          _this.managerList = res.data.managerSearch;
        }).catch(error => {

        });
    },
    queryManagerSearchAsync(queryString, cb) {

        var managerList = this.managerList;
        var results = queryString ? managerList.filter(this.createFilter(queryString)) : managerList;
        cb(results);
    },

    //处理经理人模糊匹配项点击
    handleManagerSelect(item) {
        //TODO 选中一项，应该直接搜索，无需点击按钮

        this.editForm.mId = item.mId
        console.log(this.editForm.mId)
        this.managerNameRefresh()
    },
    createFilter(queryString) {
        return (a) => {
          return (a.value.toLowerCase().indexOf(queryString.toLowerCase()) != -1);
        };
    },
    managerNameRefresh(){
    	var i
    	for(i in this.managerList){
    		if(this.managerList[i].mId == this.editForm.mId){
    		    this.editForm.managerName = this.managerList[i].value
    		    break
    		}
    	}
    }
  },

  activated() {
  	var pId = location.href.split("?")[1].split("&")[0].split("=")[1]
    this.loadAllManagerList()

  	if(pId != -1){
  		this.editType = "编辑产品"
  		this.showId = true
  		this.loadFundInfo(pId)
  	}
  	else{
  		this.editType = "新增产品"
  		this.showId = false
  		this.editForm = {
  			pId:'',
  			identifier:'',
  			pName:'',
  			worth:'',
  			startPoint:'',
  			profitRate:'',
  			description:'',
  			mId:'',
  			managerName:'',
  			num:'',
  			secTime:['','']
  		}
  	}

  }
}
</script>

<style scoped>
  @import '../assets/css/FundProductEdit.css';
</style>
<template>
	<div style="padding-top:45px;height: -webkit-fill-available;">
    <div style="height:100%">

  		<div class = "bread">
  			<el-breadcrumb separator-class="el-icon-arrow-right">
          	<el-breadcrumb-item>经理人信息管理</el-breadcrumb-item>
  			</el-breadcrumb>
  		</div>

      <div class="managerInfo">
          <div class = "managerFilterContainer">
            <el-input id="managerFilter" style="width:15%;float:left;padding:10px 0px 10px 20px;" v-model="managerFilterstate" placeholder="请输入经理人工号或姓名" @keyup.enter.native="managerSearch" size="small">
              <i class="el-icon-close el-input__icon" v-if="managerclear" slot="suffix" @click="clearManager"></i>
              <i class="el-icon-search el-input__icon" slot="suffix" style="float:right" @click="managerSearch"></i>
            </el-input>
          </div>

        <div class="managerContainer">
          <div style="background:white;margin:10px">
            <div style="display:flex">
              <el-card class="addCard" style="margin-left:5%;cursor:pointer;background-color:#f7f7f7;border:1px dashed #c0c4cc;" @click.native="addNewManager()">
                <div style="margin-top:60px;font-size:20px;">
                  <i class = "el-icon-plus"></i> 添加经理人
                </div>
              </el-card>

              <el-card class="existCard" v-if="cardExist[0]">
                <div style="height:80%;">
                  <div style="width:30%;float:left;height:100%">
                    <img style="width:110px;height:110px;" :src="managerInfo[0].imgUrl"></img>
                  </div>
                  <div style="width:70%;float:right;">
                    <p style="text-align: left;padding-left: 10px;margin:0px;">{{managerInfo[0].nameId}}</p>
                    <el-tooltip :content="managerInfo[0].description" placement="bottom-start" effect="light">
                      <p class="info multiLine" :show-overflow-tooltip='true'>{{managerInfo[0].description}}</p>
                    </el-tooltip>
                  </div>
                </div>
                <div style="float:left;height: 1px;width: 100%; background: #f7f7f7;"></div> 
                <div style="height:20%;">
                  <el-link style = "width:49%;float:left;margin-top:10px" type="primary" icon="el-icon-edit" @click="editManager(0)">编辑</el-link>

                  <div class="verticalLine"></div> 

                  <el-link style = "width:49%;float:right;margin-top:10px" icon="el-icon-delete" @click="delManager(0)">删除</el-link>
                </div>
              </el-card>

              <el-card class="existCard" v-if="cardExist[1]">
                <div style="height:80%;">
                  <div style="width:30%;float:left;height:100%">
                    <img style="width:110px;height:110px;" :src="managerInfo[1].imgUrl"></img>
                  </div>
                  <div style="width:70%;float:right;">
                    <p style="text-align: left;padding-left: 10px;margin:0px;">{{managerInfo[1].nameId}}</p>
                    <el-tooltip :content="managerInfo[1].description" placement="bottom-start" effect="light">
                      <p class="info multiLine" :show-overflow-tooltip='true'>{{managerInfo[1].description}}</p>
                    </el-tooltip>
                  </div>
                </div>
                <div style="float:left;height: 1px;width: 100%; background: #f7f7f7;"></div> 
                <div style="height:20%;">
                  <el-link style = "width:49%;float:left;margin-top:10px" type="primary" icon="el-icon-edit" @click="editManager(1)">编辑</el-link>

                  <div class="verticalLine"></div> 

                  <el-link style = "width:49%;float:right;margin-top:10px" icon="el-icon-delete" @click="delManager(1)">删除</el-link>
                </div>
              </el-card>
            </div>

            <div style="display:flex;">
              <el-card class="existCard" style="margin-left:5%" v-if="cardExist[2]">
                <div style="height:80%;">
                  <div style="width:30%;float:left;height:100%">
                    <img style="width:110px;height:110px;" :src="managerInfo[2].imgUrl"></img>
                  </div>
                  <div style="width:70%;float:right;">
                    <p style="text-align: left;padding-left: 10px;margin:0px;">{{managerInfo[2].nameId}}</p>
                    <el-tooltip :content="managerInfo[2].description" placement="bottom-start" effect="light">
                      <p class="info multiLine" :show-overflow-tooltip='true'>{{managerInfo[2].description}}</p>
                    </el-tooltip>
                  </div>
                </div>
                <div style="float:left;height: 1px;width: 100%; background: #f7f7f7;"></div> 
                <div style="height:20%;">
                  <el-link style = "width:49%;float:left;margin-top:10px" type="primary" icon="el-icon-edit" @click="editManager(2)">编辑</el-link>

                  <div class="verticalLine"></div> 

                  <el-link style = "width:49%;float:right;margin-top:10px" icon="el-icon-delete" @click="delManager(2)">删除</el-link>
                </div>
              </el-card>

              <el-card class="existCard" v-if="cardExist[3]">
                <div style="height:80%;">
                  <div style="width:30%;float:left;height:100%">
                    <img style="width:110px;height:110px;" :src="managerInfo[3].imgUrl"></img>
                  </div>
                  <div style="width:70%;float:right;">
                    <p style="text-align: left;padding-left: 10px;margin:0px;">{{managerInfo[3].nameId}}</p>
                    <el-tooltip :content="managerInfo[3].description" placement="bottom-start" effect="light">
                      <p class="info multiLine" :show-overflow-tooltip='true'>{{managerInfo[3].description}}</p>
                    </el-tooltip>
                  </div>
                </div>
                <div style="float:left;height: 1px;width: 100%; background: #f7f7f7;"></div> 
                <div style="height:20%;">
                  <el-link style = "width:49%;float:left;margin-top:10px" type="primary" icon="el-icon-edit" @click="editManager(3)">编辑</el-link>

                  <div class="verticalLine"></div> 

                  <el-link style = "width:49%;float:right;margin-top:10px" icon="el-icon-delete" @click="delManager(3)">删除</el-link>
                </div>
              </el-card>

              <el-card class="existCard" v-if="cardExist[4]">
                <div style="height:80%;">
                  <div style="width:30%;float:left;height:100%">
                    <img style="width:110px;height:110px;" :src="managerInfo[4].imgUrl"></img>
                  </div>
                  <div style="width:70%;float:right;">
                    <p style="text-align: left;padding-left: 10px;margin:0px;">{{managerInfo[4].nameId}}</p>
                    <el-tooltip :content="managerInfo[4].description" placement="bottom-start" effect="light">
                      <p class="info multiLine" :show-overflow-tooltip='true'>{{managerInfo[4].description}}</p>
                    </el-tooltip>
                  </div>
                </div>
                <div style="float:left;height: 1px;width: 100%; background: #f7f7f7;"></div> 
                <div style="height:20%;">
                  <el-link style = "width:49%;float:left;margin-top:10px" type="primary" icon="el-icon-edit" @click="editManager(4)">编辑</el-link>

                  <div class="verticalLine"></div> 

                  <el-link style = "width:49%;float:right;margin-top:10px" icon="el-icon-delete" @click="delManager(4)">删除</el-link>
                </div>
              </el-card>
            </div>

            <div style="width:100%;overflow:hidden">

              <el-pagination background class="pageBottom" layout="total, prev, pager, next" 
              :model = "pagenation" :total="pagenation.total" :current-page="pagenation.pageNum" :page-size="pagenation.pageSize" style="margin-top:10px;padding-bottom: 20px;float:right;margin-right:7%"
              @current-change="handleCurrentChange"></el-pagination>

            </div>
          </div>
        </div>
      </div>
    </div>
	</div>
</template>

<script>

import store from '../store/index.js'

export default {
  name: 'ManagerList',
  data () {
   	return{
      managerFilterstate:"",
      pagenation:{
        total:0,
        pageSize:5,
        pageNum:1
      },
      managerInfo:[{},{},{},{},{}],

      cardExist:[false,false,false,false,false],

  	}
  },
  methods:{

      clearManager(){
        this.pagenation.pageNum = 1
        this.managerFilterstate = ""
        this.loadManagerDetail()
      },

      //点击搜索经理人icon
      managerSearch (item) {
        this.pagenation.pageNum = 1
        this.loadManagerDetail()
      },


      handleCurrentChange(item){
        this.pagenation.pageNum = item
        this.loadManagerDetail()
      },

      addNewManager(){
        if (store.state.role == 1){
          this.$router.push('/ManagerEdit?mId=-1');
        }
        else{
          this.$toast("当前帐号无权限")
        }
      },

      editManager(item){
        if (store.state.role == 1){
          this.$router.push('/ManagerEdit?mId='+this.managerInfo[item].managerId)
        }
        else{
          this.$toast("当前帐号无权限")
        }
      },

      delManager(item){
        this.$confirm('确定删除经理人么？')
          .then(res => {
            if(res=="confirm"){
              let _this = this;
              this.axios({
                method: 'post',
                url: '/api/delManager?mId='+this.managerInfo[item].managerId
              }).then(res => {
                _this.loadManagerDetail()
              }).catch(error => {

              });
            }
          })
          .catch(_ => {});

      },

      loadManagerDetail(){
        let _this = this;
        var managerList;
        this.axios({
          method: 'get',
          url: '/api/getManagerDetailByKeyword?keyword='+this.managerFilterstate+'&pageSize='+_this.pagenation.pageSize+'&pageNum='+_this.pagenation.pageNum,
        }).then(res => {
          managerList = JSON.parse(res.data.managerList);
          _this.pagenation.total = managerList.total
          _this.managerInfo = []
          _this.cardExist=[]
          var empty = {imgUrl:'',nameId:'',description:'',managerId:''}
          for(var i=0;i<managerList.list.length;i++){
            _this.managerInfo.push({
              imgUrl:'/api'+managerList.list[i].img_url,
              nameId:managerList.list[i].mName+managerList.list[i].jobId,
              description:managerList.list[i].description,
              managerId:managerList.list[i].managerId,
            })
            _this.cardExist.push(true)
          }
          if(managerList.list.length < 5){
            for(var j = 0;j<5-managerList.list.length;j++){
              _this.managerInfo.push(empty)
              _this.cardExist.push(false)
            }
          }
        }).catch(error => {

        });
      },

  },

  computed:{
    managerclear:function(){
      return this.managerFilterstate == ""?false:true
    }

  },

  activated(){

    this.loadManagerDetail();
  },

  mounted(){
    this.loadManagerDetail();
  }
}
</script>

<style scoped>
  @import '../assets/css/ManagerList.css';
</style>
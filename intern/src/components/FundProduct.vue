<template>
	<div style="padding-top:45px;height: -webkit-fill-available;">
  <div style="height:100%">
		<div class = "bread">
			<el-breadcrumb separator-class="el-icon-arrow-right">
			  <!--<el-breadcrumb-item :to="{ path: '/' }">基金产品管理</el-breadcrumb-item>-->
        <el-breadcrumb-item>基金产品管理</el-breadcrumb-item>
			  <el-breadcrumb-item :to="{ path: '/FundProduct' }">产品列表</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

    <div class="fundInfo">
      <div class="tableborder">
        <!--顶层包含搜索框和新增按钮-->
  			<div class = "topControl">
          <!--两个搜索框-->
          <div class="searchBox"> 
    				<div class = "productFilter">
    					<el-input id="productFilter" v-model="productFilterstate" placeholder="请输入产品编号或名称" @keyup.enter.native="productSearch" size="small">
              <!--:fetch-suggestions="queryProductSearchAsync" @select="handleProductSelect"-->
              <i class="el-icon-close el-input__icon" v-if="productclear" slot="suffix" @click="clearProduct"></i>
    					<i class="el-icon-search el-input__icon" slot="suffix" style="float:right" @click="productSearch"></i>
    					</el-input>
    				</div>

    				<div class = "managerFilter">
    					<el-input id="managerFilter" v-model="managerFilterstate" placeholder="请输入经理人工号或姓名" @keyup.enter.native="managerSearch" size="small">
              <!--:fetch-suggestions="queryManagerSearchAsync" @select="handleManagerSelect"-->
              <i class="el-icon-close el-input__icon" v-if="managerclear" slot="suffix" @click="clearManager"></i>
    					<i class="el-icon-search el-input__icon" slot="suffix" style="float:right" @click="managerSearch"></i>
    					</el-input>
    				</div>
          </div>

          <div class ="addButton">
            <el-button type="primary" icon="el-icon-plus" :disabled = "disable" size="small" @click="addFund()">新增</el-button>
          </div>
  			</div>



        <el-table :data="tableData" style="padding:10px 20px 0px 20px;width:100%;" size="small" :header-cell-style="tableHeaderColor">
            <el-table-column prop="productId" v-if="show" width="0">
            </el-table-column>
            <el-table-column label="产品编号" :min-width="10">
              <template slot-scope="scope">
                <a style="color:#3572b0;cursor:pointer;" @click="toDetail(scope.row)">{{scope.row.identifier}}</a>
              </template>
            </el-table-column>
            <el-table-column prop="productName" :show-overflow-tooltip='true' label="名称" :min-width="24">
            </el-table-column>
            <el-table-column prop="value" label="净值(元)" :min-width="8">
            </el-table-column>
            <el-table-column prop="startPoint" label="投资起点(元)" :min-width="8">
            </el-table-column>
            <el-table-column prop="profitRate" label="七日年化收益率(%)" :min-width="13">
            </el-table-column>
            <el-table-column prop="managerId" label="经理人工号" :min-width="13">
            </el-table-column>
            <el-table-column prop="managerName" label="经理人姓名" :min-width="13">
            </el-table-column>
            <el-table-column label="操作" :min-width="6">
              <template slot-scope="scope">
                <i class="el-icon-edit" v-bind:class="editActive?editable:uneditable" @click="edit(scope.row)"></i>
              </template>
            </el-table-column>
        </el-table>

        <div style="width:100%;overflow:hidden">

          <el-pagination background class="pageBottom" layout="total, prev, pager, next" 
          :model = "pagenation" :total="pagenation.total" :current-page="pagenation.pageNum" :page-size="pagenation.pageSize" style="margin-top:10px;padding-bottom: 20px;"
          @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

        </div>
      </div>
    </div>
  </div>
	</div>

</template>


<script>
import store from '../store/index.js'
export default {
  name: 'FundProduct',
  data () {
  	return{
      show:false,
  		productFilterstate:"",
  		managerFilterstate:"",
      allFundName:"",
      allManagerName:"",
      tableData:[],
      disable:store.state.role == 1?false:true,
      editActive:store.state.role == 1,
      editable:'editable',
      uneditable:'uneditable',
      pagenation:{
        total:0,
        pageSize:10,
        pageNum:1
      },
      keyword:{

      },
      tableData:[]
  	}
  },
  computed:{
    productclear:function(){
      return this.productFilterstate == ""?false:true
    },
    managerclear:function(){
      return this.managerFilterstate == ""?false:true
    }

  },
  methods: {
  	  //根据产品名称或者id模糊搜索
      queryProductSearchAsync(queryString, cb) {
        var allFundName = this.allFundName;
        var results = queryString ? allFundName.filter(this.createFilter(queryString)) : allFundName;
        cb(results);
      },

      //迭代过滤器
      createFilter(queryString) {
        return (a) => {
          return (a.value.toLowerCase().indexOf(queryString.toLowerCase()) != -1);
        };
      },

      //基金id名称模糊匹配项点击
      handleProductSelect(item) {
        //TODO 选中一项，应该直接搜索，无需点击按钮
        this.pagenation.pageNum = 1;
        //document.getElementById("managerFilter").setAttribute("placeholder","请输入经理人工号或姓名")
        //document.getElementById("productFilter").setAttribute("placeholder",this.productFilterstate)
        //this.productFilterstate = ""
        //this.managerFilterstate = ""
        //this.keyword = {pId:item.pId}
        this.loadFundByPId(item.pId)

      },

      clearProduct(){
        this.productFilterstate = ""
        this.loadFundByKeyword(this.productFilterstate,this.managerFilterstate)
      },

      //点击搜索基金icon
      productSearch (item) {
        this.pagenation.pageNum = 1;
        this.loadFundByKeyword(this.productFilterstate,this.managerFilterstate)
      },

      //根据经理名称或工号返回模糊搜索
      queryManagerSearchAsync(queryString, cb) {

        var allManagerName = this.allManagerName;
        var results = queryString ? allManagerName.filter(this.createFilter(queryString)) : allManagerName;
        cb(results);
      },

      //处理经理人模糊匹配项点击
      handleManagerSelect(item) {
        //TODO 选中一项，应该直接搜索，无需点击按钮
        this.pagenation.pageNum = 1;
        //document.getElementById("productFilter").setAttribute("placeholder","请输入产品编号或名称")
        //document.getElementById("managerFilter").setAttribute("placeholder",this.managerFilterstate)
        //this.managerFilterstate = ""
        //this.productFilterstate = ""
        //this.keyword = {mId:item.mId}
        this.loadFundBymId(item.mId)
      },

      clearManager(){
        this.managerFilterstate = ""
        this.loadFundByKeyword(this.productFilterstate,this.managerFilterstate)
      },

      //点击搜索经理人icon
      managerSearch (item) {
      	this.pagenation.pageNum = 1;
        console.log(this.productFilterstate,this.managerFilterstate)
        this.loadFundByKeyword(this.productFilterstate,this.managerFilterstate)
      },

      //预加载所有产品名称用于模糊搜索
      loadAllFundName(){
        let _this = this
        this.axios({
          method: 'get',
          url: '/api/vagueSearchByFund?keyword=',
        }).then(res => {
          _this.allFundName = res.data.fundSearch;
        }).catch(error => {

        });
      },

      //预加载所有经理名称用于模糊搜索
      loadAllManagerName(){
        let _this = this
        this.axios({
          method: 'get',
          url: '/api/vagueSearchByManager?keyword=',
        }).then(res => {
          _this.allManagerName = res.data.managerSearch;
        }).catch(error => {

        });
      },

      //根据基金id返回基金信息，点击模糊匹配项触发
      loadFundByPId(pId){
        let _this = this;
        var allFund;
        this.axios({
          method: 'get',
          url: '/api/getFundByPId?pId='+pId+'&pageSize='+_this.pagenation.pageSize+'&pageNum='+_this.pagenation.pageNum,
        }).then(res => {
          allFund = JSON.parse(res.data.allFund);
          _this.pagenation.total = allFund.total
          _this.tableData = []
          for(var i=0;i<allFund.list.length;i++){
            var fund = allFund.list[i]
            var data = {productId:fund.pId,identifier:fund.identifier, productName:fund.pName, value:fund.worth.toFixed(2), startPoint:fund.startPoint.toFixed(4),
                        profitRate:fund.profitRate.toFixed(4)+"%", managerId:fund.jobId, managerName:fund.mName}

            _this.tableData.push(data)
          }
        }).catch(error => {

        });
      },

      //根据经理人id返回基金信息，点击模糊匹配项触发
      loadFundBymId(mId){
        let _this = this;
        var allFund;
        this.axios({
          method: 'get',
          url: '/api/getFundBymId?mId='+mId+'&pageSize='+_this.pagenation.pageSize+'&pageNum='+_this.pagenation.pageNum,
        }).then(res => {
          allFund = JSON.parse(res.data.allFund);
          _this.pagenation.total = allFund.total
          _this.tableData = []
          for(var i=0;i<allFund.list.length;i++){
            var fund = allFund.list[i]
            var data = {productId:fund.pId,identifier:fund.identifier, productName:fund.pName, value:fund.worth.toFixed(2), startPoint:fund.startPoint.toFixed(4),
                        profitRate:fund.profitRate.toFixed(4)+"%", managerId:fund.jobId, managerName:fund.mName}

            _this.tableData.push(data)
          }
        }).catch(error => {

        });
      },

      //根据搜索框值返回基金信息，点击搜索按钮触发
      loadFundByKeyword(key1,key2){
        let _this = this;
        var allFund;
        this.axios({
          method: 'get',
          url: '/api/getFundByKeyword?fundKey='+key1+'&userKey='+key2+'&pageSize='+_this.pagenation.pageSize+'&pageNum='+_this.pagenation.pageNum,
        }).then(res => {
          allFund = JSON.parse(res.data.allFund);
          _this.pagenation.total = allFund.total
          _this.tableData = []
          for(var i=0;i<allFund.list.length;i++){
            var fund = allFund.list[i]
            var data = {productId:fund.pId, identifier:fund.identifier,productName:fund.pName, value:fund.worth.toFixed(2), startPoint:fund.startPoint.toFixed(4),
                        profitRate:fund.profitRate.toFixed(4)+"%", managerId:fund.jobId, managerName:fund.mName}

            _this.tableData.push(data)
          }
        }).catch(error => {

        });
      },

      //加载表格数据,无关键词
      async loadAllFund(pageNum,pageSize){
        let _this = this;
        var allFund;
        await this.axios({
          method: 'get',
          url: '/api/getAllFund?pageNum='+ pageNum +'&pageSize='+ pageSize,
        }).then(res => {
          allFund = JSON.parse(res.data.allFund);
          _this.pagenation.total = allFund.total
          _this.tableData = []
          for(var i=0;i<allFund.list.length;i++){
            var fund = allFund.list[i]
            var data = {productId:fund.pId,identifier:fund.identifier, productName:fund.pName, value:fund.worth.toFixed(2), startPoint:fund.startPoint.toFixed(4),
                        profitRate:fund.profitRate.toFixed(4)+"%", managerId:fund.jobId, managerName:fund.mName}

            _this.tableData.push(data)
          }
        }).catch(error => {

        });
      },

      //点击修改按钮
      edit(item){
        if (store.state.role == 1){
          this.$router.push('/FundProductEdit?pId='+item.productId)
          //this.$router.push({name:'FundProductEdit',params:{data:item}})
        }
        else{
          this.$toast("当前帐号无权限")
        }
      },

      toDetail(item){
        this.$router.push('/FundProductDetail?pId='+item.productId)
      },

      addFund(){
        this.$router.push('/FundProductEdit?pId=-1')
      },

      //pagenation处理函数
      handleSizeChange(item){
        this.pagenation.pageSize = item
        if(JSON.stringify(this.keyword)=="{}"){
          this.loadAllFund(this.pagenation.pageNum,this.pagenation.pageSize);
        }
        else{
          if(this.keyword.pId != null){
            this.loadFundByPId(this.keyword.pId)
          }
          else if(this.keyword.uId != null){
            this.loadFundByUId(this.keyword.uId)
          }
        }
      },

      handleCurrentChange(item){
        this.pagenation.pageNum = item
        this.loadAllFund(this.pagenation.pageNum,this.pagenation.pageSize);
      },

      //表头颜色
      tableHeaderColor({ row, column, rowIndex, columnIndex }) {
        if (rowIndex === 0) {
          return 'background-color: #f1f5fe'
        }
      },

  },
  created() {
    this.loadAllFund(this.pagenation.pageNum,this.pagenation.pageSize);
    //this.loadAllFundName();
    //this.loadAllManagerName();

  },
  activated(){
    if(store.state.refreshFundList){
        console.log("fresh table")
        this.pagenation.pageSize = 10
        this.pagenation.pageNum = 1
        this.loadAllFund(1,10)
        store.state.refreshFundList = false
    }
  },
}
</script>

<style scoped>
  @import '../assets/css/FundProduct.css';

</style>
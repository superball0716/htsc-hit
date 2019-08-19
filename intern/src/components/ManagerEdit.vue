<template>
	<div style="padding-top:45px;height: -webkit-fill-available;">
		<div class = "bread">
			<el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/ManagerList' }">经理人信息管理</el-breadcrumb-item>
			  <el-breadcrumb-item>{{editType}}</el-breadcrumb-item>
			</el-breadcrumb>
		</div>

		<div class="managerAddMain" id="managerAddMain">
        <div class="container" v-show="panel">  
          <div>  
              <img id="image" :src="url" alt="Picture">  
          </div>  
          <button type="button" id="button" @click="commit">确定</button>  
          <button type="button" id="cancel" @click="cancel">取消</button> 
        </div>  

		    <div class="tableborder">
				<p class='editHeader'>{{editType}}</p>
				<div style="font-size:13px">
					<el-form ref="checkForm" :model="editForm" :rules="rules" label-width="180px" size="mini" class="editInput" enctype="multipart/form-data">
		      		<el-form-item label="姓名:" prop="mName">
		      				<el-input placeholder="请输入姓名不超过4个字" v-model="editForm.mName" style="width:15%;float:left;"/>
		      		</el-form-item>
					    <el-form-item label="照片:" prop="img" style="width:70%;height:200px;">
                <input type="file" id="showupload" style="display:none" accept="image" @change="showUpload"></input>
                <div v-if="noImg">
                  <el-card type="file" class="AvatarImg" style="margin:10px 0px 0px 10px;border:1px dashed #c0c4cc;background:#f7f7f7;" @click.native="changeImg()">
                    <i class = "el-icon-plus" style="color: grey;padding-top: 15px;font-size: 20px;"></i> 
                    <p style="margin:0;font-size:13px;color:grey">上传照片</p>
                  </el-card>
                  <p style="float:left;font-size:13px;color:grey;margin-left:10px;">照片大小不超过500k</p>
                </div>

                <div v-if="!noImg">
                  <div style="width:110px;height:120px;margin:10px 0px 0px 10px;">
                    <img class="AvatarImg" :style="{backgroundImage:'url('+editForm.img+')',backgroundSize:'100% 100%'}" style="float:left;border:0;"></img>
                  </div>
                  <div style="margin-top:10px;float:left;margin-left: 40px;">
                    <el-button type="primary" plain @click="changeImg()">更改</el-button>
                  </div>
                </div>
					    </el-form-item>

					    <el-form-item label="个人介绍:" prop="description" style="width:60%">
					   	    <el-input placeholder="请输入经理人个人介绍信息不超过200字" type="textarea"
                  :autosize="{ minRows: 4, maxRows: 6}" v-model="editForm.description" style="width:100%;float:left"/>
                  <p style="width:50px;float:right;margin:0;font-size: 12px;line-height:25px;">{{descriptionLength}}/200</p>
					    </el-form-item>

					  	<el-form-item>
					  		<div style="width:50%;padding-bottom:20px;overflow:hidden">
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
import Cropper from "cropperjs";

export default {
  name: 'ManagerEdit',
  data () {
   	return{
  		editForm:{
  			mName:'',
        img:'',
        description:'',
  		},
  		pNameLength:0,
  		descriptionLength:0,
      editType:'',
      imgCropperData: {
        accept: "image/png, image/jpg, image/jpeg"
      },
      picValue: "",
      cropper: "",
      croppable: false,
      panel: false,
      url:'',
      noImg:true,
      mId:'',
      files:'',
      imgChanged:false,
      rules:{
          mName: [
            {required: true, message: '经理人姓名不能为空!', trigger: 'blur'}
          ],
          img:[
            {required: true, message: '照片不能为空!', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '经理人介绍信息不能为空!', trigger: 'blur'}
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
  	'editForm.mName':function(val,oldVal){
  		if (this.getByteLen(val) > 8){
  			this.editForm.mName = oldVal
  			this.$toast("不能超过4个字")
  		}
  	}
  },
  methods:{
    cancel() {
        this.panel = false;
        document.getElementById("showupload").value = null

        //var obj = document.getElementById('showupload') ; 
        //obj.outerHTML=obj.outerHTML; 
    },
    //创建url路径
    getObjectURL(file) {
      var url = null;
      if (window.createObjectURL != undefined) {
        // basic
        url = window.createObjectURL(file);
      } else if (window.URL != undefined) {
        // mozilla(firefox)
        url = window.URL.createObjectURL(file);
      } else if (window.webkitURL != undefined) {
        // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
      }
      return url;
    },
  	cancelEdit(){
		  window.history.back(-1)
  	},

  	saveEdit(formName){
      let formData = new FormData()
      let _this = this
      if(this.imgChanged){
        formData.append("avatar",this.dataURItoBlob(this.editForm.img))
      }
      formData.append("mName",this.editForm.mName)
      formData.append("description",this.editForm.description)
      formData.append("mId",this.mId)
  		this.$refs[formName].validate((valid)=> {
  			if(valid){
  			  this.axios.post('/api/editManager',formData, {contentType: false, processData: false, headers:{'Content-Type': 'application/x-www-form-urlencoded'}}
            
          ).then(res => {
            _this.$router.push('/ManagerList')
          }).catch(error => {

          });
  			}
  		});

  	},

    dataURItoBlob (base64Data) {
      var byteString;
      if (base64Data.split(',')[0].indexOf('base64') >= 0)
          byteString = atob(base64Data.split(',')[1]);
      else
          byteString = unescape(base64Data.split(',')[1]);
      var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
      var ia = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
          ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ia], {type: mimeString});
    },


    showUpload(e){
      let files = e.target.files || e.dataTransfer.files;
      this.files = files
      if (!files.length) return;
      let type = files[0].type;
      let size = files[0].size;
      if (this.imgCropperData.accept.indexOf(type) == -1) {
        this.$toast("仅支持jpg,png格式！");
        return false;
      }
      if (size > 500*1024) {
        this.$toast("请选择500K以内的图片！");
        return false;
      }
      this.picValue = files[0];
      this.url = this.getObjectURL(this.picValue);
      if (this.cropper) {
        this.cropper.replace(this.url);
      }
      this.panel = true;
    },

    changeImg(){
      document.getElementById("showupload").click();   
    },

    loadManagerDetail(mId){
      let _this = this
      this.axios({
          method: 'get',
          url: '/api/getManagerDetailBymId?mId='+mId
        }).then(res => {
          this.editForm={
            mName:res.data.managerDetail.mName,
            img:'/api'+res.data.managerDetail.img_url,
            description:res.data.managerDetail.description,
          }
        }).catch(error => {

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

    commit() {
      this.panel = false;
      var croppedCanvas;
      var roundedCanvas;
      if (!this.croppable) {
        return;
      }
      croppedCanvas = this.cropper.getCroppedCanvas();
      this.editForm.img = croppedCanvas.toDataURL();
      this.noImg = false
      document.getElementById("showupload").value = null
      this.imgChanged = true
    },
  },

  activated() {
  	var mId = location.href.split("?")[1].split("&")[0].split("=")[1]
    this.mId = mId
    if(mId == -1){
      this.editType="添加经理人"
      this.editForm={
        mName:'',
        img:'',
        description:'',
      }
      this.noImg = true
      this.imgChanged = false

    }
    else{
      this.editType="编辑经理人"
      this.loadManagerDetail(mId);
      this.noImg = false
      this.imgChanged = false

    }
  },
  mounted(){
    var self = this
    var image = document.getElementById("image");
    this.cropper = new Cropper(image, {
      aspectRatio: 1,
      viewMode: 1,
      background: false,
      zoomable: false,
      ready: function() {
        self.croppable = true;
      }
    });
  }
}
</script>

<style>
  @import '../assets/css/ManagerEdit.css';


.cropper-container {
  font-size: 0;
  line-height: 0;
  position: relative;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  direction: ltr;
  -ms-touch-action: none;
  touch-action: none;
}
.cropper-container img {
  display: block;
  min-width: 0 !important;
  max-width: none !important;
  min-height: 0 !important;
  max-height: none !important;
  width: 100%;
  height: 100%;
  image-orientation: 0deg;
}
.cropper-wrap-box,
.cropper-canvas,
.cropper-drag-box,
.cropper-crop-box,
.cropper-modal {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}
.cropper-wrap-box {
  overflow: hidden;
}
.cropper-drag-box {
  opacity: 0;
  background-color: #fff;
}
.cropper-modal {
  opacity: 0.5;
  background-color: #000;
}
.cropper-view-box {
  display: block;
  overflow: hidden;
  width: 100%;
  height: 100%;
  outline: 1px solid #39f;
  outline-color: rgba(51, 153, 255, 0.75);
}
.cropper-dashed {
  position: absolute;
  display: block;
  opacity: 0.5;
  border: 0 dashed #eee;
}
.cropper-dashed.dashed-h {
  top: 33.33333%;
  left: 0;
  width: 100%;
  height: 33.33333%;
  border-top-width: 1px;
  border-bottom-width: 1px;
}
.cropper-dashed.dashed-v {
  top: 0;
  left: 33.33333%;
  width: 33.33333%;
  height: 100%;
  border-right-width: 1px;
  border-left-width: 1px;
}
.cropper-center {
  position: absolute;
  top: 50%;
  left: 50%;
  display: block;
  width: 0;
  height: 0;
  opacity: 0.75;
}
.cropper-center:before,
.cropper-center:after {
  position: absolute;
  display: block;
  content: " ";
  background-color: #eee;
}
.cropper-center:before {
  top: 0;
  left: -3px;
  width: 7px;
  height: 1px;
}
.cropper-center:after {
  top: -3px;
  left: 0;
  width: 1px;
  height: 7px;
}
.cropper-face,
.cropper-line,
.cropper-point {
  position: absolute;
  display: block;
  width: 100%;
  height: 100%;
  opacity: 0.1;
}
.cropper-face {
  top: 0;
  left: 0;
  background-color: #fff;
}
.cropper-line {
  background-color: #39f;
}
.cropper-line.line-e {
  top: 0;
  right: -3px;
  width: 5px;
  cursor: e-resize;
}
.cropper-line.line-n {
  top: -3px;
  left: 0;
  height: 5px;
  cursor: n-resize;
}
.cropper-line.line-w {
  top: 0;
  left: -3px;
  width: 5px;
  cursor: w-resize;
}
.cropper-line.line-s {
  bottom: -3px;
  left: 0;
  height: 5px;
  cursor: s-resize;
}
.cropper-point {
  width: 5px;
  height: 5px;
  opacity: 0.75;
  background-color: #39f;
}
.cropper-point.point-e {
  top: 50%;
  right: -3px;
  margin-top: -3px;
  cursor: e-resize;
}
.cropper-point.point-n {
  top: -3px;
  left: 50%;
  margin-left: -3px;
  cursor: n-resize;
}
.cropper-point.point-w {
  top: 50%;
  left: -3px;
  margin-top: -3px;
  cursor: w-resize;
}
.cropper-point.point-s {
  bottom: -3px;
  left: 50%;
  margin-left: -3px;
  cursor: s-resize;
}
.cropper-point.point-ne {
  top: -3px;
  right: -3px;
  cursor: ne-resize;
}
.cropper-point.point-nw {
  top: -3px;
  left: -3px;
  cursor: nw-resize;
}
.cropper-point.point-sw {
  bottom: -3px;
  left: -3px;
  cursor: sw-resize;
}
.cropper-point.point-se {
  right: -3px;
  bottom: -3px;
  width: 20px;
  height: 20px;
  cursor: se-resize;
  opacity: 1;
}
@media (min-width: 768px) {
  .cropper-point.point-se {
    width: 15px;
    height: 15px;
  }
}
@media (min-width: 992px) {
  .cropper-point.point-se {
    width: 10px;
    height: 10px;
  }
}
@media (min-width: 1200px) {
  .cropper-point.point-se {
    width: 5px;
    height: 5px;
    opacity: 0.75;
  }
}
.cropper-point.point-se:before {
  position: absolute;
  right: -50%;
  bottom: -50%;
  display: block;
  width: 200%;
  height: 200%;
  content: " ";
  opacity: 0;
  background-color: #39f;
}
.cropper-invisible {
  opacity: 0;
}
.cropper-bg {
  background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQAQMAAAAlPW0iAAAAA3NCSVQICAjb4U/gAAAABlBMVEXMzMz////TjRV2AAAACXBIWXMAAArrAAAK6wGCiw1aAAAAHHRFWHRTb2Z0d2FyZQBBZG9iZSBGaXJld29ya3MgQ1M26LyyjAAAABFJREFUCJlj+M/AgBVhF/0PAH6/D/HkDxOGAAAAAElFTkSuQmCC");
}
.cropper-hide {
  position: absolute;
  display: block;
  width: 0;
  height: 0;
}
.cropper-hidden {
  display: none !important;
}
.cropper-move {
  cursor: move;
}
.cropper-crop {
  cursor: crosshair;
}
.cropper-disabled .cropper-drag-box,
.cropper-disabled .cropper-face,
.cropper-disabled .cropper-line,
.cropper-disabled .cropper-point {
  cursor: not-allowed;
}
</style>
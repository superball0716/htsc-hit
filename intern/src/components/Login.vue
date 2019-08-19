<template>
  <div style="height:100%;position: absolute;width: 100%;">
    <FundSystemHeader></FundSystemHeader>
    <el-row style="padding-top:60px;height:100%;width:100%">
    <div style="height:100%;width:100%;background-size: 100% 100%;width:100%" v-bind:style="{backgroundImage:'url(' + loginbg + ')',}">
      <div style="height:100%;width:100%">
        <div style="width:20%;height:50%;background-color:white;margin-left:60%;margin-top:8%;position:fixed">
          <el-form ref="loginForm" :model="form" :rules="rules" class="login-box">
            <h4 class="login-title">账号密码登录</h4>
            <hr style="height:1px;border:none;border-top:1px solid #f0f0f0;width:80%" /> 

            <div style="margin-top:20px;">
              <el-form-item prop="userName">
                <el-input type="text" class="logininput" prefix-icon= "el-icon-user-solid" placeholder="请输入用户名" v-model="form.userName"/>
              </el-form-item>
              <el-form-item prop="password">
                <el-input type="password" class="logininput" prefix-icon="el-icon-lock" placeholder="请输入密码" v-model="form.password"/>
              </el-form-item>
            </div>
            <el-form-item>
              <el-button style="margin-top:20px;width:80%;height:30px" type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
            </el-form-item>
          </el-form>
          <el-button type="text" style="text-decoration:none;font-size:12px;" @click="toregister">没有账号？立即注册</el-button>
        </div>
      </div>
    </div>
    </el-row>

  </div>
</template>

<script>

  import { mapMutations } from 'vuex';
  import FundSystemHeader from '@/components/FundSystemHeader'

  export default {
    name: "Login",
    components: {
      FundSystemHeader,
    },
    data() {
      return {
        loginbg:require('../assets/images/loginBackground.jpg'),
        form: {
          userName: '',
          password: '',
        },

        // 表单验证，需要在 el-form-item 元素中增加 prop 属性
        rules: {
          userName: [
            {required: true, message: '用户名不可为空', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '密码不可为空', trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      ...mapMutations(['changeLogin']),

      toregister(){
        this.$router.push('/register')
      },

      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let _this = this;
            this.axios({
              method: 'post',
              url: '/api/login',
              data: _this.form
            }).then(res => {
              if(res.data.token == null || res.data.token == ""){
                if(res.data.failReason == "E0001"){
                  this.form.password = ""
                  this.form.userName = ""
                  this.$toast("当前账号不存在，请先注册")
                }
                else if(res.data.failReason == "E0002"){
                  this.form.password = ""
                  this.$toast("密码错误，请重新输入")
                }
              }
              else{
                _this.userToken = res.data.token;
                // 将用户token保存到vuex中
                _this.changeLogin({ Authorization: _this.userToken, role:res.data.role, uName:res.data.uName, loginId:res.data.loginId});

                _this.$router.push('/');
              }
            }).catch(error => {

            });
          }
          else{

          }
        });
      }
    }
  }
</script>

<style>

  .login-box {
    /*border: 1px solid #DCDFE6;
    background-color:white;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 25px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;*/
  }

  .login-title {
    text-align: center;
    margin-top:30px;
    color: #303133;
  }

  .login-box .el-form-item__label{
    text-align:left;
    margin:0;
  }

  .logininput{
    width:80%!important;
  }

  .login-box .el-input__inner {
    height:35px;
    line-height:35px;
  }

  .login-box .el-form-item {
    margin-bottom: 17px;
  }

  .login-box .el-button{
    line-height:0;
  }

  .login-box .el-form-item__error {
    margin-left:10%;
  }
</style>
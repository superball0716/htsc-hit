<template>
  <div style="height:100%;position: absolute;width: 100%;">
    <FundSystemHeader></FundSystemHeader>
    <el-row style="padding-top:60px;height:100%;width:100%">
    <div style="height:100%;width:100%;background-size: 100% 100%;width:100%" v-bind:style="{backgroundImage:'url(' + loginbg + ')',}">
      <div style="height:100%;width:100%">
        <div style="width:20%;height:50%;background-color:white;margin-left:60%;margin-top:8%;position:fixed">
          <el-form ref="registerForm" :model="form" :rules="rules" class="register-box">
            <h4 class="register-title">注册账号</h4>
            <hr style="height:1px;border:none;border-top:1px solid #f0f0f0;width:80%" /> 

            <div style="margin-top:20px;">
              <el-form-item prop="userName">
                <el-input type="text" class="logininput" prefix-icon= "el-icon-user-solid" placeholder="请输入用户名" v-model="form.userName"/>
              </el-form-item>
              <el-form-item prop="password">
                <el-input type="password" class="logininput" prefix-icon="el-icon-lock" placeholder="请输入密码" v-model="form.password"/>
              </el-form-item>
              <el-form-item prop="passwordConfirm">
                <el-input type="password" class="logininput" prefix-icon="el-icon-circle-check" placeholder="请再次输入密码" v-model="form.passwordConfirm"/>
              </el-form-item>
            </div>
            <el-form-item>
              <el-button style="margin-top:20px;width:80%;height:30px" type="primary" v-on:click="onSubmit('registerForm')">提交注册</el-button>
            </el-form-item>
          </el-form>
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
    name: "register",
    components: {
      FundSystemHeader,
    },
    data() {
      var checkUserName = (rule, value, callback) =>{
        if (value === ''){
          callback(new Error('请输入账号'));
        } else {
          this.axios({
            method: 'get',
            url: '/api/userExist?userName='+value,
          }).then(res => {
            if (res.data.Exist == 1){
              callback(new Error('该账号已存在，请重新输入账号'));
            }
            else{
              callback();
            }
          }).catch(error => {

          });

        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不可为空'));
        } else {
          if (this.form.passwordConfirm !== '') {
            this.$refs.registerForm.validateField('passwordConfirm');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('确认密码不可为空'));
        } else if (value !== this.form.password) {
          callback(new Error('密码不一致，请重新输入密码'));
        } else {
          callback();
        }
      };
      return {
        loginbg:require('../assets/images/loginBackground.jpg'),
        form: {
          userName: '',
          password: '',
          passwordConfirm: '',
        },


        // 表单验证，需要在 el-form-item 元素中增加 prop 属性
        rules: {
          userName: [
            {validator:checkUserName, trigger: 'blur'}
          ],
          password: [
            {validator:validatePass, trigger: 'blur'}
          ],
          passwordConfirm: [
            {validator:validatePass2, trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      ...mapMutations(['changeLogin']),

      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let _this = this;
            this.axios({
              method: 'post',
              url: '/api/register',
              data: {userName:_this.form.userName,password:_this.form.password}
            }).then(res => {
              if(res.data.success == 1){
                _this.$router.push('/login');
              }
              else if(res.data.failReason = "E0003"){
                this.$toast("该账号已存在，请重新输入账号")
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
  .register-title {
    text-align: center;
    margin-top:30px;
    color: #303133;
  }

  .register-box .el-form-item__label{
    text-align:left;
    margin:0;
  }

  .logininput{
    width:80%;
  }

  .register-box .el-input__inner {
    height:35px;
    line-height:35px;
  }

  .register-box .el-form-item {
    margin-bottom: 17px;
  }

  .register-box .el-button{
    line-height:0;
  }

  .register-box .el-form-item__error {
    margin-left:10%;
  }
</style>
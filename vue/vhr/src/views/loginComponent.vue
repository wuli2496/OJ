<template>
  <div>
    <el-form
        ref="loginForm"
        :rules="rules"
        v-loading="loading"
        element-loading-text="正在登录..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        :model="loginForm"
        class="loginContainer"
    >
      <h3 class="loginTitle">系统登录</h3>
      <el-form-item prop="username">
        <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                  placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                  placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                  placeholder="点击图片更换验证码" style="width: 250px"></el-input>
        <img :src="vcUrl"  alt="" style="cursor: pointer" @click="updateVerifyCode">
      </el-form-item>
      <el-checkbox size="normal" class="loginRemember" v-model="checked"></el-checkbox>
      <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>
  export default {
      data() {
        return {
          loading : false,
          vcUrl : 'http://localhost:8080/verifyCode',
          loginForm: {
            username: 'admin',
            password: '123'
          },
          checked: true,
          rules: {
            username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
            password: [{required: true, message: '请输入密码', trigger: 'blur'}],
            code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
          }
        }
      },
      methods: {

        submitLogin() {
          this.$refs.loginForm.validate((valid) => {
            if (valid) {
              this.loading = true;
              this.postRequest('/doLogin', this.loginForm).then(resp => {
                this.loading = false;
                if (resp) {
                 console.log(resp)
                }else{
                  this.vcUrl = '/verifyCode?time='+new Date();
                }
              })
            } else {
              return false;
            }
          });

        },
        updateVerifyCode() {
          this.vcUrl = '/verifyCode?time='+new Date();
        }
      }

  }
</script>

<style>
  .loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .loginRemember {
    text-align: left;
    margin: 0px 0px 15px 0px;
  }

  .loginTitle {
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
  }

  .el-form-item__content{
    display: flex;
    align-items: center;
  }
</style>
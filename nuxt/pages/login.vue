<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>
    <div class="sign-up-container">
      <el-form ref="userForm" :model="user" :rules="Rules">
        <el-form-item class="input-prepend restyle" prop="mobile">
          <el-input type="text" placeholder="手机号" v-model="user.mobile"/>
          <i class="iconfont icon-phone" />
        </el-form-item>
        <el-form-item class="input-prepend" prop="password">
          <div>
            <el-input type="password" placeholder="密码" v-model="user.password" />
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>
        <div class="btn">
          <input type="button" class="sign-up-button" value="登录" @click="submitLogin()" />
        </div>
        <p class="sign-up-msg">
          点击“登录” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="https://www.jianshu.com/p/c44d171298ce">用户协议</a>和
          <a target="_blank" href="https://www.jianshu.com/p/2ov8x3">隐私政策</a>
        </p>
      </el-form>
      <div class="more-sign">
        <h6>社交帐号直接登录</h6>
        <ul>
          <li>
            <a id="weixin" class="weixin" target="_blank"
               href="https://www.ngrok.cc/go?type=notFound&url=huaan.free.idcfengye.com">
              <i class="iconfont icon-weixin"></i>
            </a>
          </li>
          <li>
            <a id="qq" class="qq" target="_blank" href="#">
              <i class="iconfont icon-qq"></i>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import cookie from 'js-cookie'
import login from "~/api/login";
export default {
  layout: 'sign',
  data(){
    return{
      user:{
        mobile:'',
        password:''
      },
      loginInfo:{},
      Rules:{
        mobile:[{ required:true,message:'请输入手机号',trigger:'blur'},{ validator:this.checkPhone,trigger: 'blur'}],
        password:[{ required:true,message:'请输入密码',trigger:'blur'}],
      }
    }
  },
  methods:{
    submitLogin(){
      login.submitLogin(this.user).then(res=>{
        if(res.data.success){
          cookie.set('token',res.data.data.token,{domain:'localhost'})
          login.getLoginInfo().then(res=>{
            this.loginInfo = res.data.data.item
            cookie.set('ucenter',this.loginInfo,{domain:'localhost'})
            window.location.href = "/";
          })
        }
      })
    },
    checkPhone(rule, value, callback){
      //debugger
      if (!(/^1[34578]\d{9}$/.test(value))) {
        return callback(new Error('手机号码格式不正确'))
      }
      return callback()
    }
  }
}
</script>
<style>
.el-form-item__error{
  z-index: 9999999;
}
</style>

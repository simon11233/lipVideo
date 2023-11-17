<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="param" :rules="Rules">
        <el-form-item class="input-prepend restyle" prop="nickname">
          <el-input type="text" placeholder="昵称" v-model="param.nickname"/>
          <i class="iconfont icon-user" />
        </el-form-item>
        <el-form-item class="input-prepend restyle no-radius" prop="mobile">
          <el-input type="text" placeholder="手机号" v-model="param.mobile"/>
          <i class="iconfont icon-phone" />
        </el-form-item>
        <el-form-item class="input-prepend restyle no-radius" prop="code">
          <div style="width: 100%;display: block;float: left;position: relative">
            <el-input type="text" placeholder="验证码" v-model="param.code"/>
            <i class="iconfont icon-phone"/>
          </div>
          <div class="btn" style="position:absolute;right: 0;top: 6px;width:40%;">
            <a href="javascript:" type="button" @click="getCodeFun()"
               :value="codeTest" style="border: none;text-decoration:none;color: #3399ff;background-color: none">{{codeTest}}
            </a>
          </div>
        </el-form-item>
        <el-form-item class="input-prepend" prop="password">
          <el-input type="password" placeholder="设置密码" v-model="param.password" />
          <i class="iconfont icon-password"/>
        </el-form-item>
        <div class="btn">
          <input type="button" class="sign-up-button" value="注册" @click="submitRegister()" />
        </div>
        <p class="sign-up-msg">
          点击“注册” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="https://www.jianshu.com/p/c44d171298ce">用户协议</a>和
          <a target="_blank" href="https://www.jianshu.com/p/2ov8x3">隐私政策</a>
        </p>
      </el-form>
      <div class="more-sign">
        <h6>社交帐号直接注册</h6>
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
import register from "~/api/register";
export default {
  layout: 'sign',
  data(){
    return{
      param:{
        nickname:'',
        mobile:'',
        code:'',
        password:''
      },
      codeTest: '获取验证码',
      sending: true, //是否发送验证码
      second: 60,
      Rules:{
        nickname:[{ required:true,message:'请输入昵称',trigger:'blur'}],
        mobile:[{ required:true,message:'请输入手机号',trigger:'blur'},{ validator:this.checkPhone,trigger: 'blur'}],
        code:[{ required:true,message:'请输入验证码',trigger:'blur'}],
        password:[{ required:true,message:'请输入密码',trigger:'blur'}],
      }
    }
  },
  methods:{
    getCodeFun(){
      if(!this.sending){
        return
      }
      this.$refs.userForm.validateField('mobile',(errMsg)=>{
        if(errMsg===''){
          register.getMobile(this.param.mobile).then(res=>{
            console.log(res)
            this.sending=false
            this.timeDown()
          })
        }
      })
    },
    timeDown(){
     let result = setInterval(()=>{
        --this.second
       this.codeTest = this.second
       if(this.second<1){
         clearInterval(result)
         this.sending = true
         this.codeTest = '获取验证码'
         this.second = 60
       }
     },1000)
    },
    submitRegister(){
      this.$refs['userForm'].validate((valid)=>{
        if(valid){
          register.submitRegister(this.param).then(res=>{
            console.log(res)
            this.$message({
              type: 'success',
              message: "注册成功"
            })
            this.$router.push({path: '/login'})
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
    },
  }
}
</script>

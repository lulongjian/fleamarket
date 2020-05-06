<template>
  <div id="Login">
    <h2>平台登陆</h2>
    <div class="Login">
      <form class="LoginBd">
        <div class="user">
          <input type="text" id="user" name="user" v-model="userName" placeholder="请输入学号或邮箱" maxlength="20">
        </div>
        <div class="password">
          <input type="password" id="password" name="password" v-model="passWord" placeholder="请输入登陆密码" maxlength="16">
        </div>
        <div class="clear">
          <div class="left">
            <input id="remember" type="checkbox">
            <label for="remember">
              <span class="remember">记住密码</span>
            </label>
          </div>
          <div class="right">
            <el-link type="primary" @click="wj">忘记密码？</el-link>
          </div>
        </div>
        <div class="bottonbox">
          <input type="button" value="登陆" class="button pointer" @click="updataInfo">
        </div>
        <p>
          <span class="on">还没有账号？</span>
          <span class="reg pointer" @click="goReg">去注册</span>
        </p>
      </form>
    </div>
    <div>
      <el-dialog
        title="修改密码"
        :visible.sync="centerDialogVisible1"
        append-to-body
        :modal="false"
        lock-scroll
        center>
        <div class="Reg1">
          <form action="" class="RegBd">
            <div>
              <input type="email" v-model.trim="email" @click="op = true" placeholder="请输入正确的邮箱地址用于验证">
              <!-- <p v-show="op" class="oo">提示:为了节省成本，团队没有花钱购买企业邮箱，当没收取到验证码时，请去垃圾箱找一下</p> -->
            </div>
            <div id="verify">
              <input class="verify" type="email" v-model.trim="code" placeholder="请输入验证码" maxlength="4">
              <div v-on:click="verifyClick(verify)" :disabled="verifycood">{{verify}}</div>
            </div>
            <div>
              <input type="password" v-model.trim="passWord" name="password" placeholder="请要修改的输入密码" maxlength="16">
            </div>
            <div>
              <input type="password" v-model.trim="passWord" name="password" placeholder="请要修改的输入密码" maxlength="16">
            </div>
            <div>
              <input type="button" value="提交" id="button" class="pointer" @click="reggo">
            </div>
          </form>
        </div>
      </el-dialog>
    </div>
  </div>


</template>

<script>
  import "@/assets/css/login.css"
  import {Message} from 'element-ui';
  import "../../assets/css/reg.css"
  // import "@/network/request.js"
  import JSEncrypt from 'jsencrypt';


  export default {
    name: 'Login',
    data() {
      return {
        userName: '',
        passWord: '',
        centerDialogVisible1: false,
        email: '',
        code: '',
        confirm_password: '',
        verify: '获取验证码',
        verifycood: false,
        op: false,
      }
    },
    methods: {
      getRsaCode(str){ // 注册方法
        let pubKey = `-----BEGIN PUBLIC KEY-----
      MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYYuxhoSASdeViU3Fd9IIGrsf8eWBYyy/dLU+gm39YgDB5IoaIJg8gRqhMvQ4+u2VDgm26NRbV5YJqruqXJVbGA4LN76uKHWiLsAq2OnQ70raB1UhhgAn0kAoekSsFTIjf2g5VGtTlpYUblOvVee5XmPDN0lt9daXWP7qwxIz4OwIDAQAB
        -----END PUBLIC KEY-----`;// ES6 模板字符串 引用 rsa 公钥
        let encryptStr = new JSEncrypt();
        encryptStr.setPublicKey(pubKey); // 设置 加密公钥
        let  data = encryptStr.encrypt(str.toString());  // 进行加密
        return data;
      },
      reggo() {
        this.$http
          .post('http://112.74.113.75:8843/user/updatePassword',{
            code: this.code,
            email: this.email,
            passWord: this.getRsaCode(this.passWord)
          })
          .then(response=>{
            if(response.data.code === 0){
              this.centerDialogVisible1 = false
              this.$message.success("修改密码成功")
            }
            else {
              this.$message.error("网络异常，请稍后重试")
            }
          })
      },
      verifyClick(verify) {
        const regemail = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
        const reguser = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
        if (this.email!='') {
          this.verify = '60';
          this.verifycood = true
          let kong = '';
          clearInterval(kong);
          kong = setInterval(() => {
            this.verify--
            if (this.verify <= 0) {
              clearInterval(kong);
              this.verifycood = false;
              this.verify = '重新获取';
            }
          }, 1000)
           /**
           * 发送邮箱验证码
           */
          let prom = {
            email: this.email
          };
          this.$http
            .post('http://112.74.113.75:8843/sys/getCodeUpdatePassword', prom)
            .then(response => {
              if(response.data.code==0){
                this.$message.success(response.data.msg);
              }else{
                this.$message.error(response.data.msg);
                clearInterval(kong);
                this.verifycood = false;
                this.verify = '重新获取';
              }
            })
        }else {
          this.$message.error("请输入正确的邮箱")
        }
      },
      wj() {
        this.centerDialogVisible1 = true
      },
      goReg() {
        this.$router.replace('/reg')
      },
      updataInfo() {
        let prom = {
          userName: this.userName,
          passWord: this.getRsaCode(this.passWord),
        };
        this.$http
          .post('http://112.74.113.75:8843/sys/login', prom)
          .then(response => {
            if (response.data.code != 0) {
              this.$message.error(response.data.msg);
            } else if (response.data.code == 0) {
              this.$message({
                message: '登录成功',
                type: 'success'
              });
              this.$router.replace('/home')
              this.$store.state.img = true;
              this.$store.state.name = response.data.data.userName
              sessionStorage.setItem("user", response.data.data.userName.toString())
              sessionStorage.setItem("userId", response.data.data.user.id)
              sessionStorage.setItem("userName", response.data.data.userName)
            }
          })
      }
    },
    computed: {
      getVal: {
        get() {
          // 这里也是用了Vuex里的 modules 大家可以当成普通的变量来看
          return this.$store.state.userName
        },
        set(newuserName) {
          this.$store.commit('handleVal', newuserName)
        }
      }
    }
  }

</script>

<style>

</style>

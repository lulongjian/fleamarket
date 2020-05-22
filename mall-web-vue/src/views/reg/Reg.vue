<template>
  <div id="Reg">
    <h2>平台注册</h2>
    <div class="Reg">
      <form action="" class="RegBd">
        <div>
          <input type="text" v-model.trim="name" placeholder="请输入姓名" @input="konguser" maxlength="4">
        </div>
        <div>
          <input type="text" v-model.number="sno" placeholder="请输入学号" @input="kongstudent_number" maxlength="12">
        </div>
        <div>
          <input type="email" v-model.trim="email" @click="op = true" placeholder="请输入正确的邮箱地址用于验证">
          <!-- <p v-show="op" class="oo">提示:为了节省成本，团队没有花钱购买企业邮箱，当没收取到验证码时，请去垃圾箱找一下</p> -->
        </div>
        <div id="verify">
          <input class="verify" type="email" v-model.trim="code" placeholder="请输入验证码" maxlength="4">
          <div v-on:click="verifyClick(verify)" :disabled="verifycood">{{verify}}</div>
        </div>
        <div>
          <input type="password" v-model.trim="passWordxxx" name="passwordxxx" placeholder="请输入密码" maxlength="16">
        </div>
        <div>
          <input type="password" v-model.trim="passWord" name="password" placeholder="请输入密码" maxlength="16">
        </div>
        <div>
          <input type="password" v-model.trim="confirm_password" name="password" placeholder="请再次确认密码" maxlength="16">
          <p v-show="ps" class="oo">两次输入的密码不一致</p>
        </div>
        <!-- <div>
          <input type="text" @input="kongpassword" v-model.number="qqopenid" placeholder="请输入QQ或微信便于联系" maxlength="12">
        </div> -->
        <div>
          <input @input="kongpassword" type="phone" v-model.number="phone" placeholder="请输入电话便于联系" maxlength="11">
        </div>
        <div>
          <input type="button" value="注册" id="button" class="pointer" @click="reggo">
        </div>
        <p>
          <span class="on">已有账号？</span>
          <span class="reg pointer" @click="goLogin">去登陆</span>
        </p>
      </form>
    </div>
  </div>
</template>

<script>
  import {Message} from 'element-ui';
  import "../../assets/css/reg.css"
  import "@/network/request.js"
  import request from "@/network/request"

  export default {
    name: 'Register',
    data() {
      return {
        baseHttp: "http://127.0.0.1:8843",
        name: '',
        sno: '',
        code: '',
        email: '',
        passWord: '',
        passWordxxx: '',
        confirm_password: '',
        verify: '获取验证码',
        verifycood: false,
        verify_ccood: false,
        phone: '',
        qqopenid: null,
        op: false,
        ps: false,
      }
    },
    methods: {
      open2() {
        this.$message({
          message: this.msg,
          type: 'success'
        });
      },
      open4() {
        this.$message.error(this.msg);
      },
      kongstudent_number() {
        const regstudent_number = /^[0-9]*$/;
        if (regstudent_number.test(this.student_number) == false) {
          this.student_number = '';
        }
      },
      konguser() {
        const reguser = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{0,20}$/;
        if (reguser.test(this.name) == false) {
          this.name = '';
        }
      },
      kongpassword() {
        if (this.passWord != this.confirm_password) {
          this.ps = true
        } else {
          this.ps = false
        }
      },
      verifyClick(verify) {
        const regemail = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
        const reguser = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
        if (this.name == "" || reguser.test(this.name) == false) {
          this.$message.error('用户名输入为空，或格式不正确！');
        } else if (this.sno.length < 12) {
          this.$message.error('学号格式不正确！');
        } else if (regemail.test((this.email))) {
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
            .post(this.baseHttp+'/sys/getCode', prom)
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
        }else{
          this.$message.error('邮箱格式错误格式不正确！');
          clearInterval(kong);
          this.verifycood = false;
          this.verify = '重新获取';
        }
      },
      goLogin() {
        this.$router.push('/login')
      },
      reggo() {
        let prom = {
          name: this.name,
          sno: this.sno,
          email: this.email,
          code: this.code,
          password: this.passWord,
          phone: this.phone,
          confirm_password: this.confirm_password,
        };
        this.$http
            .post(this.baseHttp+'/user/add', prom)
          .then(res => {
              if (res.data.code == 0) {
                this.$notify({
                  title: '注册成功',
                  message: '跳转至登录页面',
                  type: 'success'
                });
                setTimeout(function () {
                },1000)
                this.$router.push('/login')
              } else {
                this.$message.error(res.data.msg)
              }
            })
      }
    },
    computed: {},
    created() {
    }
  }
</script>

<style>

</style>

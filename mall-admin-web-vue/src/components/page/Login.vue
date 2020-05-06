<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">广科大二手交易后台管理</div>
            <el-form :model="param" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="请输入登陆邮箱">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="请输入密码"
                        v-model="param.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <p class="login-tips">Tips : 仅广科大二手校园系统管理员登陆</p>
            </el-form>
        </div>
    </div>
</template>

<script>
import JSEncrypt from 'jsencrypt';
export default {
    data: function() {
        return {
            param: {
                username: '',
                password: '',
            }
        };
    },
    methods: {
        getRsaCode(str){ // 注册方法
            let pubKey = `-----BEGIN PUBLIC KEY-----
        MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYYuxhoSASdeViU3Fd9IIGrsf8eWBYyy/dLU+gm39YgDB5IoaIJg8gRqhMvQ4+u2VDgm26NRbV5YJqruqXJVbGA4LN76uKHWiLsAq2OnQ70raB1UhhgAn0kAoekSsFTIjf2g5VGtTlpYUblOvVee5XmPDN0lt9daXWP7qwxIz4OwIDAQAB
            -----END PUBLIC KEY-----`;// ES6 模板字符串 引用 rsa 公钥
            let encryptStr = new JSEncrypt();
            encryptStr.setPublicKey(pubKey); // 设置 加密公钥
            let  data = encryptStr.encrypt(str);  // 进行加密
            return data;
        },
        submitForm() {
            let prom = {
                userName: this.param.username,
                passWord: this.getRsaCode(this.param.password),
                };
                this.$http
                .post('http://112.74.113.75:8888/admin/sys/login', prom)
                .then(response => {
                    if (response.data.code != 0) {
                        this.$message.error(response.data.msg);
                    } else if (response.data.code == 0) {
                        this.$message.success('好久不见，欢迎光临');
                        localStorage.setItem('ms_username', response.data.data.userName);
                        this.$router.push('/Index');
                        sessionStorage.setItem("user", response.data.data.userName.toString())
                        sessionStorage.setItem("userId", response.data.data.user.id)
                    }
            })
        },
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
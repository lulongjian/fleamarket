<template>
  <header>
    <nav class="clear">
      <div class="left">
        <div class="imgbox left">
          <img src="@/assets/img/Top_Logo.jpg" class="top_img" alt="">
        </div>
        <ul class="right">
          <router-link class="px16 pointer" to='/home' tag='li' replace><span @click="flagT();homelist()">首页</span>
          </router-link>
          <router-link class="px16 pointer" to='/release' tag='li' replace @click.native="xxx">发布
          </router-link>
          <router-link class="px16 pointer" to='/car' tag='li' replace @click.native="xx">购物车
          </router-link>
          <router-link class="px16 pointer" to='/order' tag='li' replace @click.native="order">订单
          </router-link>
          <router-link class="px16" style="cursor:help" to='/help' tag='li' replace><span>帮助</span></router-link>
        </ul>
      </div>
      <div class="right px14">
        <div class="px14" v-if="$store.state.img" style="padding-top: 20px">
          <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
         {{$store.state.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="a">个人中心</el-dropdown-item>
              <el-dropdown-item command="b">我的账户</el-dropdown-item>
              <el-dropdown-item command="c">我的商品</el-dropdown-item>
              <el-dropdown-item command="d">我的地址</el-dropdown-item>
              <el-dropdown-item command="e">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <ul class="px14" v-else>
          <router-link class="px14 pointer" to="/login" tag='li' replace><span>登陆</span></router-link>
          <li>|</li>
          <router-link class="px14 pointer" to="/reg" tag='li' replace><span>注册</span></router-link>
        </ul>
      </div>
    </nav>
    <div>
      <el-dialog
        title="我发布的商品"
        :visible.sync="productCenterDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="50%"
        center>
        <div style="overflow:auto;height: 600px;margin: 20px">
          <div style="margin-top: 10px;margin-left: 10px;" class="single xx"
               v-for="(item, index) in collect"
               :key="item.id">
            <div class="imgbox">
              <!-- 图片展示 -->
              <img v-bind:src="item.imgUrl" alt="">
            </div>
            <ul>
              <li>
                <!-- 描述 -->
                <p class="describe">
                  {{item.des}}
                </p>
              </li>
              <li class="clear">
                <!-- 二手价/原价 -->
                <p class="pricebox">
                  <span class="priTitle">{{item.title}}</span>
                </p>
              </li>
              <li style="padding-left:10px;">
                <span class="price">￥{{item.price}}</span><span>/</span><span class="cost">￥{{item.oldPrice}}</span>
              </li>
              <li class="lisc">
								<span class="licount">
									<!-- <span>浏览:</span><span>{{item.count}}</span> -->
									<span>收藏:</span><span>{{item.collectNum}}</span>
									<img src="@/assets/img/gouwuche.svg"
                       alt="收藏">
								</span>
              </li>
              <li class="clear">
                <p>
                  <!-- 时间 -->
                  <span class="item" style="padding-left: 8px">发布时间:{{item.createTime.substring(0,10)}}</span>
                  <!-- 收藏次数  浏览次数 点击收藏 -->
                </p>
              </li>
              <li>
                <div style="float: left">
                  <el-button type="warning" size="medium" @click="sxj(item.id)">修改</el-button>
                </div>
                <div style="float: right">
                  <el-button type="danger" size="medium" @click="sc(item.id)">删除</el-button>
                </div>
              </li>
              <div></div>
            </ul>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="个人中心"
        :visible.sync="myCenterDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="30%"
        center>
        <div style="overflow:auto;height: 350px;margin: 20px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
            <div>
              <div style="">
                <div style="display:inline-block;margin:20px">
                  <span>姓名:</span>
                  <el-input v-model="loginUser.name" disabled="" style="width:200px" placeholder="55"></el-input>
                </div>
                <div style="display:inline-block; margin:20px">
                  <span>学号: </span>
                  <el-input v-model="loginUser.sno" disabled="" style="width:200px"></el-input>
                </div>
                <div style="display:inline-block; margin:20px">
                  <span>邮箱: </span>
                  <el-input v-model="loginUser.email" style="width:200px"></el-input>
                </div>
                <div style="display:inline-block; margin:20px">
                  <span>角色: </span>
                  <el-input v-model="loginUser.role" disabled="" style="width:200px"></el-input>
                </div>
              </div>
              <div>
                <div style="display:inline-block; margin:20px">
                  <span>电话: </span>
                  <el-input v-model="loginUser.phone" style="width:200px"></el-input>
                </div>
                <div style="display:inline-block; margin:-5px">
                  <span>注册时间: </span>
                  <el-input v-model="loginUser.createTime" disabled="" style="width:200px"></el-input>
                </div>
              </div>
              <div style="margin-left: 200px;margin-right: 200px; margin-top:20px">
                <div style="float: left">
                  <el-button type="primary" size="medium" @click="updateUser(loginUser.id)">修改</el-button>
                </div>
                <div style="float: right">
                  <el-button type="info" size="medium" @click="centerBack()">返回</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="我的账户"
        :visible.sync="accountCenterDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="50%"
        center>
          <div style="">
            <div style="display:inline-block;margin:20px">
              <span>姓名: {{account.userName}}</span>
            </div>
            <div style="display:inline-block; margin:20px">
              <span>余额: {{account.balance}}</span>
            </div>
          </div>
        <div style="overflow:auto;height: 350px;margin: 20px">
          <div style="margin-top: 10px;margin-left: 10px; position: relative"
          >
            <div>
              <el-table :data="accountRecord" v-if="accountRecord.length> 0">
                <el-table-column align="center" v-for="(item,index) in activePlayerDataPropLabelArray" :prop="item.prop" :label="item.label"
                  :key="item.prop">
                  <template slot-scope="scope">
                    {{scope.row[item.prop]?scope.row[item.prop]:'暂无数据'}}
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>
        <div style="margin-left: 230px;margin-right: 230px; margin-top:20px;">
          <div style="text-align:center">
            <el-button type="primary" size="medium" @click="openAddBalance(loginUser.id)">充值/提现</el-button>
            <el-button type="info" size="medium" @click="centerBack()">返回</el-button>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="充值"
        :visible.sync="addMyBalance"
        append-to-body
        :modal="false"
        lock-scroll
        width="20%"
        center>
        <div style="overflow:auto;height: 200px;margin: 30px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
            <div>
              <div style="text-align:center">
                <div style="display:inline-block;margin：10px auto">
                  <el-input style="" v-model.trim="addMoney" placeholder="请输入充值/提现金额"></el-input>
                </div>
              </div>

              <div style="margin-left: 20px;margin-right: 20px; margin-top:20px">
                  <el-button type="primary" size="medium" @click="addBalance(loginUser.id)">充值</el-button>
                  <el-button type="primary" size="medium" @click="getBalance(loginUser.id)">提现</el-button>
                  <el-button type="info" size="medium" @click="addBalanceBack()">取消</el-button>   
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="修改商品信息"
        :visible.sync="updateProductCenterDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="60%"
        center>
        <div style="overflow:auto;height: 800px;margin: 20px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
           
           <div id="Release">
              <div class="title"></div>
              <div class="ReleaseBd">
                <ul>
                  <li>
                    <span>商品图片:</span>
                    <img style="width:180px;height:180px" v-bind:src="productList.imgUrl" alt="">
                  </li>
                  <li>
                    <span>更改图片:</span>
                    <div style="padding-left: 95px">
                    <el-upload
                      class="avatar-uploader"
                      action="http://112.74.113.75:8843/sys/uploadImg"
                      :show-file-list="false"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload">
                      <img v-if="imageUrl" :src="imageUrl" class="avatar">
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                    </div>
                  </li>
                  <li>
                    <span>商品类别:</span>
                    <el-select v-model="sort" placeholder="请选择类别">
                      <el-option v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                      </el-option>
                    </el-select>
                  </li>
                  <li>
                    <span>商品名称:</span>
                    <input v-model="productList.title" type="text">
                  </li>
                  <li>
                    <span>商品原价格:</span>
                    <input v-model="productList.oldPrice" type="number">
                  </li>
                  <li>
                    <span>商品卖出价格:</span>
                    <input v-model="productList.price" type="number">
                  </li>
                  <li>
                    <span class="describe">商品描述:</span>
                    <textarea v-model="productList.des" name="textarea" id="" placeholder="请对您发布的商品进行描述">
                    </textarea>
                  </li>
                  <li>
                    <span></span>
                    <button @click="updateProduct">修改</button>
						        <input type="reset" value="重置" id="reset">
                  </li>
                </ul>
              </div>
          </div>

          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="我的地址"
        :visible.sync="addrCenterDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="50%"
        center>
        <div style="overflow:auto;height: 600px;margin: 20px">

          <div style="margin-left: 0px;margin-right: 200px; margin-top:20px; margin-bottom: 5px">
            <el-button type="primary" size="medium" @click="openAddAddress()">添加</el-button>
            <!-- <el-button type="warning" size="medium" @click="updateUser(loginUser.id)">修改</el-button>
            <el-button type="danger" size="medium" @click="updateUser(loginUser.id)">删除</el-button> -->
            <el-button type="info" size="medium" @click="closeAddrCenterDialogVisible()">返回</el-button>
          </div>

          <el-table :data="address" stripe border style="width:100%" highlight-current-row>
        <!-- 　　<el-table-column type="selection" width="55">
        　　</el-table-column> -->
        　　<el-table-column prop="userName" label="收货人" align="center" min-width="60">
        　　</el-table-column>
        　　<el-table-column prop="userPhone" label="手机号码" align="center" min-width="60">
        　　</el-table-column>
        　　<el-table-column prop="addr" label="地址" align="center" min-width="120">
        　　</el-table-column>
        　　<el-table-column label="操作" align="center" min-width="100">
        　　　　<template slot-scope="scope">
        　　　　　　<el-button type="warning" @click="openUpdateAddress(scope.row.id)">修改</el-button>
        　　　　　　<el-button type="danger" @click="deleteAddress(scope.row.id)">删除</el-button>
        　　　　</template>
        　　</el-table-column>
          </el-table>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="添加/修改地址"
        :visible.sync="updateAddressDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="30%"
        center>
        <div style="overflow:auto;height: 250px;margin: 15px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
            <div>
              <div style="text-align:center">
                <el-input v-model="addr.id" style="width:276px" placeholder="" type="hidden"></el-input>
                <div style="display:inline-block;margin:10px">
                  <span>收货人:</span>
                  <el-input v-model="addr.userName" style="width:276px" placeholder=""></el-input>
                </div>
                <div style="display:inline-block;margin:10px">
                  <span>手机号码:</span>
                  <el-input v-model="addr.userPhone" style="width:268px" placeholder=""></el-input>
                </div>
                <div style="display:inline-block;margin:10px">
                  <span>地址:</span>
                  <el-input v-model="addr.addr" style="width:300px" placeholder=""></el-input>
                </div>
              </div>

              <div style="margin-left: 20px;margin-right: 20px; margin-top:20px">
                  <el-button type="warning" size="medium" @click="UpdateAddress()">{{addOrUpdate}}</el-button>
                  <el-button type="info" size="medium" @click="closeUpdateAddressDialogVisible()">取消</el-button>   
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>
  </header>


</template>

<script>
  import "../../assets/css/heder.css"
  import '@/assets/css/car.css'
  import "@/assets/css/home.css"
  import '@/assets/css/goods.css'
  import '@/assets/css/details.css'
  import '@/assets/css/release.css'

  export default {
    name: 'TopBar',
    data() {
      return {
        imageUrl: '',
        dbimgUrl:'',
        Acolor: '',
        path: '/home',
        username: '',
        loginUser:[],
        account:[],
        activePlayerDataPropLabelArray:[{
                label:'交易日期',
                prop:'createTime'
            },{
                label:"交易类型",
                prop:"category"
            },{
                label:'交易金额',
                prop:'moneyStr'
            },{
                label:'对方账户',
                prop:'sellerName'
            }],
        accountRecord:[],
        addMoney: '',
        productCenterDialogVisible: false,
        myCenterDialogVisible: false,
        accountCenterDialogVisible: false,
        updateProductCenterDialogVisible: false,
        addMyBalance: false,
        addrCenterDialogVisible: false,
        updateAddressDialogVisible: false,
        collect: [],
        address: [],
        addr: [],
        addOrUpdate: '',
        http: "http://127.0.01:8843",
        msg: [],
        pname:[],
        productList:[],
        options: [{
          value: '手机数码',
          label: '手机数码'
        }, {
          value: '学习用品',
          label: '学习用品'
        }, {
          value: '运动户外',
          label: '运动户外'
        }, {
          value: '生活用品',
          label: '生活用品'
        }, {
          value: '服装饰品',
          label: '服装饰品'
        }],
        sort: ''
      }
    },
    methods: {
      /**
       * 控制修改商品信息时间
       */
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        this.dbimgUrl = res.data
        this.$message.success(res.msg)
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      del(id){
        this.$confirm('此操作将永久删除此留言信息，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http
            .post('https://fleamarket.fun:8843/message/delete',{
              id:id,
              username:sessionStorage.getItem("user")
            })
            .then(response=>{
              this.$message.success(response.data.msg)
              setTimeout(function () {
                location.reload();
              }, 1000)
            })
        })
      },
      /**
       * 删除商品
       */
      sc(id){
        this.$confirm('此操作将永久删除关于此商品的所有信息，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http
            .post('http://112.74.113.75:8843/product/delete',{
              productId:id,
              userId:sessionStorage.getItem("userId")
            })
            .then(response=>{
              this.$message.success(response.data.msg)
              setTimeout(function () {
                location.reload();
              }, 1000)
            })
        })
      },
      /**
       * 查看商品/修改商品信息
       */
      sxj(id){
        this.updateProductCenterDialogVisible = true
        //查询商品的信息
        this.$http
          .post('http://112.74.113.75:8843/product/findProductById',{
            id: id
          })
          .then(response=>{
            this.productList = response.data.data;
            this.sort = response.data.data.sort;
            this.imageUrl = response.data.data.imageUrl;
          })
      },
      /**
       * 修改商品信息
       */
      updateProduct(){
        this.$http
        .post('http://112.74.113.75:8843/product/updateProduct',{
          id:this.productList.id,
          imgUrl:this.dbimgUrl,
          title:this.productList.title,
          oldPrice:this.productList.oldPrice,
          price:this.productList.price,
          des:this.productList.des,
          userId:sessionStorage.getItem("userId"),
          sort:this.productList.sort
        })
        .then(response=>{
          this.$message.success(response.data.msg)
          this.updateProductCenterDialogVisible = false
          //更新数据
          this.myproduct()
        })
      },
      xxx() {
        this.pd();
      },
      xx() {
        this.pd1();
      },
      order(){
        this.pdorder();
      },
      flagT() {
        if (this.path != this.$route.path) {
          this.$router.replace(this.path)
        }
        this.$store.commit('decrementT')
      },
      homelist() {
        this.$store
          .dispatch('aHome')
          .then(res => {
          })
      },
      pd() {
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录账号后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 0)
        } else {
          this.$router.push('/release')
        }
      },
      pd1() {
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录账号后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 0)
        } else {
          this.$router.push('/car')
        }
      },
      pdorder(){
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录账号后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 0)
        } else {
          this.$router.push('/order')
        }
      },
      handleCommand(command) {
        
        if (command == 'a') {
          //个人中心
          this.myCenter()
        }
        if (command == 'b') {
          //我的账户
          this.myAccount()
        }
        if (command == 'c') {
          //我发布的商品
          this.myproduct()
        }
        
        if (command == 'd') {
          //我的地址
          this.myadds()
        }
        if (command == 'e') {
          this.out()
        }
      },
      /**
       * 查看我的商品
       */
      myproduct() {
        this.$http
          .post('http://112.74.113.75:8843/product/productListByUser', {
            page: 1,
            number: 1000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.collect = response.data.data
          })
        this.productCenterDialogVisible = true
      },
      /**
       * 个人中心
       */
      myCenter() {
        this.myCenterDialogVisible = true
        this.$http
          .post('http://112.74.113.75:8843/user/findUser', {
            page: 1,
            number: 10000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.loginUser = response.data.data;
          })
      },
      /**
       * 修改个人信息
       */
      updateUser(id){
        this.$confirm('是否修改个人信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$http
          .put('http://112.74.113.75:8843/user/updateUser',{
            id:id,
            email:this.loginUser.email,
            phone:this.loginUser.phone
          })
          .then(response=>{
             this.$message.success(response.data.msg)
          })
        })
      },
      /**
       * 返回
       */
      centerBack(){
        this.myCenterDialogVisible = false
        this.accountCenterDialogVisible = false
      },
      /**
       * 我的账户
       */
      myAccount(){
        this.accountCenterDialogVisible = true
        this.$http
          .post('http://112.74.113.75:8843/account/myAccount', {
            page: 1,
            number: 10000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.account = response.data.data.account;
            this.accountRecord = response.data.data.accountRecord;
          })
      },
      /**
       * 打开用户账户充值
       */
      openAddBalance(id){
        this.addMyBalance = true
      },
      /**
       * 充值
       */
      addBalance(id){
          this.$http
          .post('http://112.74.113.75:8843/account/addAccountByUserId', {
            userId: sessionStorage.getItem("userId"),
            money:this.addMoney,
            type: '3'
          })
          .then(response => {
            if(response.data.code == 0){
              //充值成功
              this.$message.success(response.data.msg)
              this.addMyBalance = false
              this.accountCenterDialogVisible = true
            }else{
              this.$message.error("充值失败！！！请联系管理员")
            }
            //刷新账户界面
            this.myAccount()
          })
      },
      /**
       * 提现
       */
      getBalance(id){
          this.$http
          .post('http://112.74.113.75:8843/account/addAccountByUserId', {
            userId: sessionStorage.getItem("userId"),
            money:this.addMoney,
            type: '4'
          })
          .then(response => {
            if(response.data.code == 0){
              //提现成功
              this.$message.success(response.data.msg)
              this.addMyBalance = false
              this.accountCenterDialogVisible = true
            }else{
              this.$message.error(response.data.msg)
            }
            //刷新账户界面
            this.myAccount()
          })
      },
      addBalanceBack(){
        this.addMyBalance = false
      },
      /**
       * 打开我的地址
       */
      myadds(){
        this.addrCenterDialogVisible = true;
        this.$http
          .post('http://112.74.113.75:8843/address/list', {
            page: 1,
            number: 10000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.address = response.data.data;
          })
      },
      /**
       * 删除地址，根据地址id
       */
      deleteAddress(id){
        this.$http
          .post('http://112.74.113.75:8843/address/deleteByAddressId', {
            page: 1,
            number: 10000,
            addressId: id
          })
          .then(response => {
            this.address = response.data.data;
            this.$message.success("删除地址成功");
            //刷新我的地址
            this.myadds();
          })
      },
      /**
       * 查看地址-修改地址页面
       */
      openUpdateAddress(id){
        this.addOrUpdate = '修改';
        this.updateAddressDialogVisible = true;
        this.$http
          .post('http://112.74.113.75:8843/address/findAddressById',{
            addressId: id
          })
          .then(response =>{
            this.addr = response.data.data;
          })
      },
      /**
       * 修改地址
       */
      UpdateAddress(){
        this.updateAddressDialogVisible = true;
        this.$http
          .post('http://112.74.113.75:8843/address/addOrUpdateAddressById',{
            id: this.addr.id,
            userName: this.addr.userName,
            userPhone: this.addr.userPhone,
            addr: this.addr.addr,
            userId: sessionStorage.getItem("userId")
          })
          .then(response =>{
            this.$message.success(response.data.msg);
            this.updateAddressDialogVisible = false;
            //刷新我的地址
            this.myadds();
          })
      },
      /**
       * 打开添加页面
       */
      openAddAddress(){
        this.addr = [];
        this.addOrUpdate = '添加';
        this.updateAddressDialogVisible = true;
      },
      /**
       * 关闭添加/修改页面
       */
      closeUpdateAddressDialogVisible(){
          this.updateAddressDialogVisible = false;
      },
      /**
       * 关闭我的地址
       */
      closeAddrCenterDialogVisible(){
        this.addrCenterDialogVisible = false;
      },
      /**
       * 注销登陆
       */
      out() {
        this.$confirm('确定退出账号吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          sessionStorage.clear()
          this.$message({
            type: 'success',
            message: '退出登录成功!'
          });
          this.$router.replace('/home')
          setTimeout(function () {
            location.reload();
          }, 1000)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '退出失败'
          });
        });
      }
    },
    created() {
      this.homelist();
      //
      if (sessionStorage.getItem("user") != null) {
        this.$store.state.img = true
        this.$store.state.name = sessionStorage.getItem("user")
      }
    }
  }
</script>

<style scoped>
  .colorblue {
    color: #4DA5FF;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  #paging {
    width: 1200px;
    margin: 0 auto;
  }

  #paging > div {
    text-align: center;
  }

  /**隐藏下拉条 */
   ::-webkit-scrollbar {
     width: 0 !important;
   }
   ::-webkit-scrollbar {
     width: 0 !important;height: 0;
   }

</style>

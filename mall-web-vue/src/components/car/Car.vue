<template>
  <div id="Goods">
    <div class="CarBd">
      <div v-if="collect.length <= 0" style="margin-top: 50px;width: 600px">
        <el-card :body-style="{ padding: '0px' }" style="margin-left: 200px">
          <img src="@/assets/img/car.png" class="image">
          <div style="padding: 14px;">
            <span>您的购物车没有任何宝贝哦</span>
            <p><a href="/home">去随便逛逛吧</a><span>,看看有没有喜欢的</span></p>
          </div>
        </el-card>
      </div>
      <div class="GoodsBd" v-else>
        <p style="font-size: 20px;color: #0DA5DD">您总共添加了{{collect.length}}个心仪的商品</p>
        <div style="margin-top: 20px" class="single xx"
             v-for="(item, index) in collect"
             :key="item.id" >
          <div class="imgbox">
            <!-- 图片展示 -->
            <img :src="item.imgUrl" alt="">
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
                <span class="item" style="padding-left: 8px">收藏时间:{{car[index].createTime.substring(0,10)}}</span>
                <!-- 收藏次数  浏览次数 点击收藏 -->
              </p>
            </li>
            <li class="elButtin">
                <el-button type="primary" size="mini" @click="dk(item)">详情</el-button>
                <el-button type="success" size="mini" @click="openBuyDialog(item)" v-bind:disabled="item.status != 1">购买</el-button>
                <el-button  type="danger" size="mini" @click="hover(car[index].id)">删除</el-button>
            </li>
          </ul>
        </div>
        <div id="paging">
          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-size="pagesize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </div>
        <el-dialog
            title="商品详情"
            :visible.sync="drawer"
            append-to-body
            :modal="false"
            width="60%"
            center>
          <div id="Details" style='height: 600px;'>
            <p>
              商品详情<span style="font-size: 10px;color: red">(提示:点击下面空白处可关闭)</span>
            </p>
            <div class="clearfix">
              <div class="left">
                <ul>
                  <li>
                    <img :src="datas.imgUrl" alt="">
                  </li>
                </ul>
              </div>
              <div class="right">
                <ul>
                  <li>
                    <span>商品名称:</span><span class="name">{{datas.title}}</span>
                  </li>
                  <li>
                    <span>商品原价:</span><span>￥</span><span>{{datas.oldPrice}}</span>
                  </li>
                  <li>
                    <span>商品二手价:</span><span><span style="color: red;font-size: 20px">￥{{datas.price}}</span></span>
                  </li>
                  <li class="des">
                    <span>商品描述:</span><span>{{datas.des}}</span>
                  </li>
                  <li>
                    <span>卖家联系电话:</span><span>{{datas.phone}}</span>
                  </li>
                  <li>
                    <span>商品上架时间:</span><span>{{datas.createTime}}</span>
                  </li>
                  <li>
                    <span>收藏次数:<span style="color: red"> {{datas.collectNum}}</span></span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </el-dialog>
        <el-dialog
            title="待完成订单"
            :visible.sync="orderDialog"
            append-to-body
            :modal="false"
            width="60%"
            center>
          <div id="Details" style='height: 600px;'>
            <p>
              商品详情<span style="font-size: 10px;color: red">(提示:点击下面空白处可关闭)</span>
            </p>
            <div class="clearfix">
              <div class="left">
                <ul>
                  <li>
                    <img :src="datas.imgUrl" alt="">
                  </li>
                </ul>
              </div>
              <div class="right">
                <ul>
                  <li>
                    <span>商品名称:</span><span class="name">{{datas.title}}</span>
                  </li>
                  <li>
                    <span>商品原价:</span><span>￥</span><span>{{datas.oldPrice}}</span>
                  </li>
                  <li>
                    <span>商品二手价:</span><span><span style="color: red;font-size: 20px">￥{{datas.price}}</span></span>
                  </li>
                  <li class="des">
                    <span>商品描述:</span><span>{{datas.des}}</span>
                  </li>
                  <li>
                    <span>卖家联系电话:</span><span>{{datas.phone}}</span>
                  </li>
                  <li>
                    <span>商品上架时间:</span><span>{{datas.createTime}}</span>
                  </li>
                  <li>
                    <span>收藏次数:<span style="color: red"> {{datas.collectNum}}</span></span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </el-dialog>
      </div>
    </div>

    <div>
      <el-dialog
        title="订单已生成---等待付款"
        :visible.sync="OrderDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="30%"
        center>
        <div style="overflow:auto;height: 350px;margin: 20px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
            <div>
              <div style="text-align: center">
                <div style="margin: 20px 0px">
                  <span>商品标题: {{orderMsg.title}}</span>
                </div>
                <div style="margin:20px">
                  <span>商品描述: {{orderMsg.des}}</span>
                </div>
                <div style="margin:20px">
                  <span>现价/原价: {{orderMsg.price}}/{{orderMsg.oldPrice}} </span>
                </div>
                <div style=" margin:20px">
                  <span>卖家名: {{orderMsg.productUserName}} </span>
                </div>
                <div style="margin:20px">
                  <span>卖家联系方式: {{orderMsg.phone}} </span>
                </div>
                <div style="margin:20px">
                  <span>地址:</span>
                  <el-select v-model="address" placeholder="请选择地址">
                    <el-option v-for="item in addressList"
                    :key="item.id"
                    :label="item.addr"
                    :value="item.id">
                    </el-option>
                  </el-select>
                  <el-button type="primary" size="medium" @click="openAddAddress()">添加地址</el-button>
                </div>
                <div style="margin:20px">
                  <el-button type="primary" size="medium" @click="payment(orderMsg, address)">下单/付款</el-button>
                  <el-button type="info" size="medium" @click="closeOrderDialog">取消</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="添加地址"
        :visible.sync="updateAddressDialogVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="30%"
        center>
        <div style="overflow:auto;height: 280px;margin: 15px">
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

              <div style="text-align: center">
                  <el-button type="success" size="medium" @click="UpdateAddress()">{{addOrUpdate}}</el-button>
                  <el-button type="info" size="medium" @click="closeUpdateAddressDialogVisible()">取消</el-button>   
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog
        title="付款"
        :visible.sync="payVisible"
        append-to-body
        :modal="false"
        lock-scroll
        width="20%"
        center>
        <div style="overflow:auto;height: 150px;margin: 15px">
          <div style="margin-top: 10px;margin-left: 10px;"
          >
            <div>
              <div style="text-align:center">
                <div style="display:inline-block;margin:10px">
                  <span>我的余额: {{account.balance}}元</span>
                </div>
                <div style="display:inline-block;margin:10px">
                  <span>需要支付: {{orderMsg.price}}元</span>
                </div>
              </div>

              <div style="text-align: center">
                  <el-button type="success" size="medium" @click="payFor(orderMsg.price)">确定付款</el-button>
                  <el-button type="info" size="medium" @click="closePayVisible()">取消</el-button>   
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </div>

  </div>


</template>

<script>
  import '@/assets/css/car.css'
  import "@/assets/css/home.css"
  import '@/assets/css/goods.css'
  import '@/assets/css/details.css'
  import request from "@/network/request"

  export default {
    name: 'Car',
    data() {
      return {
        baseHttp: "http://127.0.0.1:8843",
        account:[],
        payVisible: false,
        addr: [],
        addOrUpdate: '',
        updateAddressDialogVisible: false,
        address: '',
        addressList: {},
        orderMsg: [],
        OrderDialogVisible: false,
        orderDialog: false,
        drawer: false,
        modal:false,
        close:true,
        withHeader:false,
        imgremove: false,
        collect: [],
        datas:{},
        car: [],
        tel:'',
        collectSwitchCount: 2,
        total: 0,
        currentPage: 1,
        pagesize: 10,
        drawer:false,
        direction:'ttb'
      }
    },
    methods: {
      mopen(id,userId,count){
        this.$http
          .get(this.baseHttp+'/user/selectById?id='+userId)
          .then(response=>{
            this.tel=response.data.data.tel
          });
      },
      dk(item){
        this.drawer = true
        this.datas = item;
      },
      handleClose(done){
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      /**
       * 删除收藏
       */
      hover(id) {
        this.$confirm('是否不再收藏此商品?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$http
            .post(this.baseHttp+'/car/delete',{
               id:id
            })
            .then(response=>{
              if(response.data.code == 0){
                this.$message.success("删除成功")
                let pagehomt = {
                  userId: sessionStorage.getItem("userId")
                }
                request({
                  url: this.baseHttp+'/car/carList',
                  method: 'post',
                  data: pagehomt
                  }).then(res => {
                    this.car = response.data.data;
                    let index = response.data.data;
                    this.collect = response.data.data;
                    //刷新列表
                    this.addCarList()
                  }).catch(err => {
                })
              }
            })
        })
      },
      handleSizeChange(val) {
        this.pagesize = val
      },
      handleCurrentChange(val) {
        // 当前页
      },
      handlePageSizes() {
      },
      carDetailsEl() {
        this.$router.replace('/details')
      },
      addCarList(){
        if (sessionStorage.getItem("user") == null) {
          setTimeout(() => {
            this.$router.push('/login')
          }, 0)
        } else {
          this.$http
          /**
           * 获取商品列表，点进购物车时查询该连接
           */
            .post(this.baseHttp+'/car/carList', {
              page: this.currentPage,
              number: this.pagesize,
              userId: sessionStorage.getItem("userId")
            })
            .then(response => {
              this.car = response.data.data;
              let index = response.data.data;
              this.collect = response.data.data
              let pId = new Array();
            })
        }
      },
      //打开订单页面
      openBuyDialog(item){
        if(item.status != '1'){
          this.$message({
            type: 'error',
            message: '该商品已被购买或已下架'
          });
          return
        }
        //查询地址
        this.$http
          .post(this.baseHttp+'/address/list', {
            page: 1,
            number: 10000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.addressList = response.data.data;
            this.addressList.forEach(element => {
              element.addr = "收货人:"+element.userName
                +"  地址:"+element.addr
            });
          })
        this.orderMsg = item
        this.OrderDialogVisible = true
      },
      //下单付款
      payment(orderMsg, addressId){
        if(addressId == ''){
          this.$message.error("请选择地址")
          return 
        }
        //判断商品是否购买
        this.$http
          .post(this.baseHttp+'/product/findProductById', {
            id: orderMsg.productId,
            addressId: addressId
          })
          .then(response => {
            if(response.data.data.status != '1'){
              this.$message.error("该商品暂不可以购买，请刷新重新尝试下单")
              return;
            }
          })
          this.$message.success("正在创建订单......")
          //跳转付款界面
          setTimeout(() => {
            this.payVisible = true
            this.myAccount()
          }, 1.5 * 1000) 
      },
      //付款
      payFor(price){
        this.$http
          .post(this.baseHttp+'/account/addAccountByUserId', {
            userId: sessionStorage.getItem("userId"),
            money: price,
            type: '0',
            productUserId: this.orderMsg.productUserId
          })
          .then(response => {
            if(response.data.code == 0){
              setTimeout(() => {
                //支付成功
                this.$message.success("支付成功")
                this.payVisible = false
              }, 1.5 * 1000) 
              //生成订单
              this.$http
                .post(this.baseHttp+'/order/addOrder', {
                  productId: this.orderMsg.productId,
                  productTitle: this.orderMsg.title,
                  productPrice: this.orderMsg.price,
                  buyerId: sessionStorage.getItem("userId"),
                  buyerName: sessionStorage.getItem("userName"),
                  sellerId: this.orderMsg.productUserId,
                  sellerName: this.orderMsg.productUserName,
                  addressId: this.address
                })
                .then(response => {
                  if(response.data.code == 0){
                    setTimeout(() => {
                      //支付成功
                      this.$message.success("下单成功,请等待卖家发货完成交易")
                      this.payVisible = false
                      this.OrderDialogVisible = false
                    }, 1.5 * 1000)
                  }else{
                    this.$message.error(response.data.msg)
                  }
                })
            }else{
              this.$message.error(response.data.msg)
            }
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
       * 添加地址
       */
      UpdateAddress(){
        this.updateAddressDialogVisible = true;
        this.$http
          .post(this.baseHttp+'/address/addOrUpdateAddressById',{
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
            this.$http
            .post(this.baseHttp+'/address/list', {
              page: 1,
              number: 10000,
              userId: sessionStorage.getItem("userId")
            })
            .then(response => {
              this.addressList = response.data.data;
              this.addressList.forEach(element => {
                element.addr = "收货人:"+element.userName
                  +"  地址:"+element.addr
              });
            })
          })
      },
      myAccount(){
        this.$http
          .post(this.baseHttp+'/account/myAccount', {
            page: 1,
            number: 10000,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.account = response.data.data.account;
          })
      },
      //关闭支付页面
      closePayVisible(){
        this.payVisible = false
      },
      //关闭生成订单界面
      closeOrderDialog(){
        this.OrderDialogVisible = false
      },
    },
    created() {
      this.addCarList()
    },
    activated(){
      this.addCarList()
    }
  }
</script>

<style>
  #paging {
    width: 1200px;
    margin: 0 auto;
  }

  #paging > div {
    text-align: center;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>

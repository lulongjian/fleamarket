<template>
	<div id="Release">
    <div style="background:#CCFFFF">
      <div style="text-align: center; margin:10px auto; background:#409EFF; color:#FFF">我的订单</div>
      <el-table :data="order" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="id" label="订单编号" align="center" min-width="60" v-if="false">
          </el-table-column>
      　　<el-table-column prop="orderNo" label="订单编号" align="center" min-width="60">
          </el-table-column>
          <el-table-column prop="imgUrl" label="商品信息" align="center" min-width="120">
            <template   slot-scope="scope">            
              <div style="display: inline-block">
                <!-- 图片展示 -->
                <img :src="scope.row.imgUrl"  min-width="100" height="100" />
              </div>
              <div style="display: inline-block">
                <span>商品：{{scope.row.productTitle}}</span>
              </div>
              <div style="display: inline-block">
                <el-button type="primary" @click="findProductDetails(scope.row.productId)">查看详情</el-button>
              </div>
            </template> 
          </el-table-column>
      　　<el-table-column prop="sellerName" label="卖家名" align="center" min-width="55">
      　　</el-table-column>
      　　<el-table-column prop="sellerPhone" label="卖家电话" align="center" min-width="68">
      　　</el-table-column>
          <el-table-column prop="buyerUserName" label="收货人" align="center" min-width="55">
      　　</el-table-column>
          <el-table-column prop="buyerUserPhone" label="联系电话" align="center" min-width="68">
      　　</el-table-column>
          <el-table-column prop="productPrice" label="实付款" align="center" min-width="30">
      　　</el-table-column>
          <el-table-column prop="status" label="订单状态" align="center" min-width="25">
      　　</el-table-column>
          <el-table-column prop="createTime" label="下单时间" align="center" min-width="60">
      　　</el-table-column>
      　　<el-table-column label="操作" align="center" min-width="155" prop="status">
    　　　　<template slot-scope="scope">
              <el-button type="success" size="mini" @click="enterOrder(scope.row.id)" v-bind:disabled="scope.row.status != '已发货'">确认收货</el-button>
    　　　　　 <el-button type="danger" size="mini" @click="deleteOrder(scope.row.id)" v-bind:disabled="scope.row.status == '已完成'">取消</el-button>
    　　　　</template>
    　　  </el-table-column>
      </el-table>

      <div style="text-align: center; margin:10px auto; background:#CC33FF; color:#FFF">我的出售订单</div>
      <el-table :data="sellerOrder" stripe border style="width:100%" highlight-current-row>
          <el-table-column prop="id" label="订单编号" align="center" min-width="60" v-if="false">
          </el-table-column>
      　　<el-table-column prop="orderNo" label="订单编号" align="center" min-width="60">
          </el-table-column>
          <el-table-column prop="imgUrl" label="商品信息" align="center" min-width="120">
            <template   slot-scope="scope">            
              <div style="display: inline-block">
                <!-- 图片展示 -->
                <img :src="scope.row.imgUrl"  min-width="100" height="100" />
              </div>
              <div style="display: inline-block">
                <span>商品：{{scope.row.productTitle}}</span>
              </div>
              <div style="display: inline-block">
                <el-button type="primary" @click="findProductDetails(scope.row.productId)">查看详情</el-button>
              </div>
            </template> 
          </el-table-column>
      　　<el-table-column prop="buyerName" label="买家名" align="center" min-width="55">
      　　</el-table-column>
      　　<el-table-column prop="buyerPhone" label="买家电话" align="center" min-width="68">
      　　</el-table-column>
          <el-table-column prop="buyerUserName" label="收货人" align="center" min-width="55">
      　　</el-table-column>
          <el-table-column prop="buyerUserPhone" label="联系电话" align="center" min-width="68">
      　　</el-table-column>
          <el-table-column prop="productPrice" label="实付款" align="center" min-width="30">
      　　</el-table-column>
          <el-table-column prop="status" label="订单状态" align="center" min-width="25">
      　　</el-table-column>
          <el-table-column prop="createTime" label="下单时间" align="center" min-width="60">
      　　</el-table-column>
      　　<el-table-column label="操作" align="center" min-width="155" prop="status">
    　　　　<template slot-scope="scope">
              <el-button type="success" size="mini" @click="setOutOrder(scope.row.id)" v-bind:disabled="scope.row.status != '已付款'">发货</el-button>
    　　　　　 <el-button type="danger" size="mini" @click="deleteOrder(scope.row.id)" v-bind:disabled="scope.row.status == '已完成'">取消</el-button>
    　　　　</template>
    　　  </el-table-column>
      </el-table>
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
	</div>
</template>

<script>
import request from "@/network/request"

export default {
	name: "Order",
	data () {
		return {
      order: [],
      drawer:false,
      datas:{},
      direction:'ttb',
      close:true,
      http: "http://112.74.113.75:8843",
      sellerOrder: null
		}
	},
	methods: {
    /**
     * 查看商品详情
     */
    findProductDetails(productId){
      this.drawer = true
      //发送后台请求获取数据
      this.$http
        .get('http://112.74.113.75:8843/product/findProductById/'+productId+'',{
        })
        .then(response => {
          if(response.data.code != 0){
            this.$message.error("网络错误，或该商品已被删除，如有疑问请联系管理员")
          }else if(response.data.code == 0){
            this.datas = response.data.data
          }
        })
    },
    //关闭商品详情
    handleClose(done){
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
    //确认收货
    enterOrder(id){
      this.$confirm('确定已收到物品并无异议吗?确定收货付款金额将支付到卖家手中', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发送后台请求获取数据
      this.$http
      .post('http://112.74.113.75:8843/order/enterOrder',{
        id: id
      })
        .then(response => {
          if(response.data.code != 0){
            this.$message.error(response.data.msg)
          }else if(response.data.code == 0){
            this.$message.success("确认收货成功")
            this.datas = response.data.data
            this.orderList();
          }
        })
      })
    },
    // 发货
    setOutOrder(id){
      this.$confirm('确定商品已寄出?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //发送后台请求获取数据
      this.$http
      .post('http://112.74.113.75:8843/order/setOutOrder',{
        id: id
      })
        .then(response => {
          if(response.data.code != 0){
            this.$message.error(response.data.msg)
          }else if(response.data.code == 0){
            this.$message.success("确认发货成功")
            this.datas = response.data.data
            this.orderList();
          }
        })
      })
    },
    getOrderList(){
       if (sessionStorage.getItem("user") == null) {
        setTimeout(() => {
          this.$router.push('/login')
        }, 0)
      } else {
        this.$router.push('/order');
        this.$http
        /**
         * 获取我的订单列表
         */
          .post('http://112.74.113.75:8843/order/buyList', {
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.order = response.data.data;
          })
          /**
           * 获取我 的出售订单列表
           */
          this.$http
          .post('http://112.74.113.75:8843/order/sellerList', {
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.sellerOrder = response.data.data;
          })
      }
    },
    //订单
    orderList(){
      this.$http
        /**
         * 获取我的订单列表
         */
          .post('http://112.74.113.75:8843/order/buyList', {
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.order = response.data.data;
          })
          /**
           * 获取我 的出售订单列表
           */
          this.$http
          .post('http://112.74.113.75:8843/order/sellerList', {
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            this.sellerOrder = response.data.data;
          })
    },
    // 取消订单
    deleteOrder(orderId){
      this.$confirm('确定要取消该订单吗?确定取消后金额将原路并删除订单信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http
          .post('http://112.74.113.75:8843/order/deleteOrder', {
            orderId: orderId,
            userId: sessionStorage.getItem("userId")
          })
          .then(response => {
            if(response.data.code == 0){
              // 提示取消成功---订单已删除
              this.$message.success(response.data.msg)
              // 刷新订单列表
              this.orderList()
            }else{
              this.$message.erroe(response.data.msg)
            }
          })
      })
      
    }
  },
  created() {
     this.getOrderList();
    },
    activated(){
      this.getOrderList();
    }
}
</script>

<style>

</style>

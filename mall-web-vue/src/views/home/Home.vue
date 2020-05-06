<template>
  <div>
    <div id="Home" v-if="$store.state.flag == true">
      <div style="width: 100%;height:130px;background-color: #f6f6f6;">
      <div class="HomeBd clear">
        <div class="left">
          <div class="leftBd">
            <div class="q322"><img src="@/assets/img/fav.png" alt=""></div>
            <div class="pipe"></div>
            <div class="manifesto">
              <p>校园二手交易</p>
              <p>为校友量身打造的二手交易平台</p>
            </div>
          </div>
        </div>
        <div class="right">
          <div class="rightBd">
            <div id="input">
              <div class="inputBd" @click="focusClick">
                <input @blur="focusState = false" v-model="pkey" type="text" maxlength="12" v-focus="focusState">
              </div>
              <div id="search">
                <button class="searchBtn" @click="ss">搜索</button>
              </div>
            </div>
            <div id="release">
              <div class="releaseBd" @click="goReleaseClick">
                <div class="circle"><span id="sell">卖</span></div>
                <div class="reisin">
                  <p>发布闲置</p>
                  <p>
                    <span>闲置换钱</span><span>快速出售</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      </div>
      <h3>注意:本校园二手交易市场完全不收取中介费用，仅限广西科技大学校内师生使用，如有不便请多见谅！！！</h3>
     
      
      <div id="Goods">
         <!-- 分类搜索商品 -->
        <div>
          <span>商品类别:</span>
          <el-select v-model="sort" placeholder="全部"
                    @change="selectSortFindProductList()">
            <el-option v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
          </el-select>
          <!-- 按排行搜索商品 -->
          <span style="margin-left: 30px">排序:</span>
          <el-select v-model="order" placeholder="默认"
                    @change="selectSortFindProductList()">
            <el-option v-for="item in orderProduct"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
          </el-select>
        </div>
    
        <div class="GoodsBd">
          <div class="single"
               v-for="(item, index) in $store.state.Single.slice(0,pagesize)"
               :key="item.id" @click="flagF(item.id,item.userId,item.count);count(index)">
            <div class="imgbox">
              <!-- 图片展示 -->
              <img v-bind:src="item.imgUrl" alt="">
            </div>
            <ul>
              <li>
                <!-- 描述 -->
                <p class="describe">
                  <!-- {{item.des}} -->
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
									<span></span><span></span>
									<span>收藏:</span><span>{{item.collectNum}}</span>
                  <img src="@/assets/img/gouwuche.svg" title="收藏" @click="collectSwitchClickT(index,item.id,item.sc)">
                </span>
              </li>
              <li class="clear">
                <p>
                  <!-- 时间 -->
                  <span class="item">发布日期{{item.createTime}}</span>
                  <!-- 收藏次数  浏览次数 点击收藏 -->
                </p>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 分页 -->
      <div id="paging">
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="pageCount"
            :page-size="pagesize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
    </div>
    <div id="Details" v-else>
      <p>
        商品详情
      </p>
      <div class="clearfix">
        <div class="left">
          <ul>
            <li>
              <el-card shadow="always">
                <img :src="$store.state.Single[countrun].imgUrl" alt="">
              </el-card>
            </li>
          </ul>
        </div>
        <div class="right">
          <ul>
            <li>
              <span>商品名称:</span><span class="name">{{$store.state.Single[countrun].title}}</span>
            </li>
            <li>
              <span>商品原价:</span><span>￥</span><span>{{$store.state.Single[countrun].oldPrice}}</span>
            </li>
            <li>
              <span>商品二手价:</span><span><span
              style="color: red;font-size: 20px">￥{{$store.state.Single[countrun].price}}</span></span>
            </li>
            <li class="des">
              <span>商品描述:</span><span>{{$store.state.Single[countrun].des}}</span>
            </li>
            <li>
              <span>卖家联系电话:</span><span>{{$store.state.Single[countrun].phone}}</span>
            </li>
            <li>
              <span>商品上架时间:</span><span>{{$store.state.Single[countrun].createTime}}</span>
            </li>
            <!-- <li>
              <span>浏览次数: <span style="color: red">{{$store.state.Single[countrun].count}}</span></span>
            </li> -->
            <li>
              <span>收藏次数:<span style="color: red"> {{$store.state.Single[countrun].collectNum}}</span></span>
            </li>
            <li>
              <el-button type="primary" @click="sc($store.state.Single[countrun].id,$store.state.Single[countrun].sc)">
                收藏商品
              </el-button>
            </li>
          </ul>
        </div>
        <div style="float: left">
          <el-divider content-position="left"><span style="font-size: 15px">商品留言信息</span></el-divider>
          <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
          <el-link icon="el-icon-edit" style="float: right" @click="ly($store.state.Single[countrun].id)">发布留言</el-link>
          <el-card class="box-card">
            <div class="block">
              <el-timeline>
                <el-timeline-item v-for="(item,index) in  msg" :key="item.id" :timestamp="item.time" placement="top">
                  <el-card>
                    <h4>{{item.text}}</h4>
                    <br>
                    <p> <small>用户：</small>{{item.name}} <small>提交于</small> {{item.createTime}}</p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import "@/assets/css/home.css"
  import '@/assets/css/goods.css'
  import '@/assets/css/details.css'
  import request from "@/network/request"

  export default {
    name: 'Home',
    components: {},
    data() {
      return {
        userName: '',
        countrun: 0,
        focusState: false,
        collectSwitch: false,
        collectSwitchCount: 2,
        single: [],
        currentPage: 1,
        pagesize: 10,
        pageCount: [],
        http: "http://112.74.113.75:8843",
        pkey: '',
        total: 0,
        tel: '',
        msg: [],
        username: [],
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
        }, {
          value: '',
          label: '全部'
        }],
        sort: '',
        orderProduct: [{
          value: 'moneyHight',
          label: '价格降序'
        }, {
          value: 'moneyLow',
          label: '价格升序'
        }, {
          value: 'collectNum',
          label: '收藏数'
        }, {
          value: 'time',
          label: '发布时间'
        }, {
          value: '',
          label: '默认'
        }],
        order: ''
      }
    },
    methods: {
      ly(id) {
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 1000)
        } else {
          this.$prompt('请输入留言内容', '发布留言信息', {
            confirmButtonText: '提交',
            cancelButtonText: '取消',
            inputType: 'textarea'
          }).then(({value}) => {
            if (value == null || value == '') {
              this.$message.error("斌能为空，请输入内容")
            } else {
              this.$http
                .post('http://112.74.113.75:8843/message/addMessage', {
                  pId: id,
                  text: value,
                  name: sessionStorage.getItem("user")
                })
                .then(response => {
                  if (response.data.code == 0) {
                    this.$message({
                      type: 'success',
                      message: '增加留言成功'
                    });
                    //刷新留言栏
                    this.flagF(id)
                    this.$store.commit('decrementF')
                  } else {
                    this.$message.error("增加留言失败")
                  }
                })
            }

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消输入'
            });
          });
        }
      },
      //加入购物车
      sc(id, sc) {
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 1000)
        } else {
          this.$confirm('是否将此商品加入收藏?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            //商品详情加入购物车
            var userId = sessionStorage.getItem("userId");
            this.$http
              .post('http://112.74.113.75:8843/car/addCar', {
              productId: id,
              userId: userId
            })
            .then(response => {
              if(response.data.code == 0){
                //添加购物车成功
                this.$message({
                  type: 'success',
                  message: '添加成功'
                })
              }else{
              }
            })

          })
        }
      },
      focusClick() {
        this.focusState = true
      },
      collectSwitchClickT(index, id, sc) {
        /**
         * 商品列表的添加收藏功能
         */
        setTimeout(() => {
          this.$store.commit('decrementT')
        }, 10)
        //判断有没有登陆
        var userId = sessionStorage.getItem("userId");
        if(userId == null){
          this.$message.error("您还没有登录，请登录账号后再进行操作")
          return;
        }
        this.$confirm('是否将此商品加入收藏?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http
          /**
           * 检测是否已存在购物车中
           */
            .post('http://112.74.113.75:8843/car/addCar', {
              productId: id,
              userId: userId
            })
            .then(response => {
              if(response.data.code == 0){
                //添加购物车成功
                this.$message({
                  type: 'success',
                  message: '添加成功'
                })
              }else{
                //添加失败
                this.$message.error(response.data.msg);
              }
            })

        })
      },
      collectSwitchClickF(index, id) {
        this.single[index].sc--;
      },
      goReleaseClick() {
        if (sessionStorage.getItem("user") == null) {
          this.$message.error("请登录账号后再进行操作")
          setTimeout(() => {
            this.$router.push('/login')
          }, 0)
        } else {
          this.$router.push('/release')
        }
      },
      handleSizeChange(val) {
        // 每页多条
        // this.currentPage = val
        // this.pageCount
        this.pagesize = val
      },
      handleCurrentChange(val) {
        /**
         * 跳转第几页
         */
       this.$store.state.Single = []
        this.single = []
        // 当前页
        this.currentPage = val;
        let pagehomt = {
          page: this.currentPage,
          number: this.pagesize,
          sort: this.sort,
          order: this.order
        }
        request({
          url: '/product/productList',
          method: 'post',
          data: pagehomt
        }).then(res => {
          this.$store.state.Single = res.data
          this.single = this.$store.state.Single
          this.total = res.count
          let pageArr = [];
          let count = Math.ceil(this.$store.state.Single.length / this.pagesize);
          for (let i = 1; i < count + 1; i++) {
            pageArr.push(this.pagesize * i);
          }
          this.pageCount = pageArr
        }).catch(err => {
        })
      },
      handlePageSizes() {
        let pagehomt = {
          page: this.currentPage,
          number: this.pagesize,
          sort: this.sort,
          order: this.order
        };
        request({
          /**
           * 进来查询列表的
           */
          url: 'http://112.74.113.75:8843/product/productList',
          method: 'post',
          data: pagehomt
        }).then(res => {
          this.$store.state.Single = res.data
          this.single = this.$store.state.Single
          this.total = res.count;
          let pageArr = [];
          this.currentPage = 1;
          let count = Math.ceil(this.single.length / this.pagesize);
          for (let i = 1; i < count + 1; i++) {
            pageArr.push(this.pagesize * i);
          }
          this.pageCount = pageArr

        }).catch(err => {
        })

      },
      count(index) {
        this.countrun = index
      },
      flagF(id, userId, count) {
        this.$store.commit('decrementF')
        this.$http
          .post('http://112.74.113.75:8843/message/messageList', {
            page: 1,
            number: 1000,
            pId: id
          })
          .then(response => {
            this.msg = response.data.data
            let index = new Array()
            for (let i = 0; i < response.data.data.length; i++) {
              index.push(response.data.data[i].tid)
            }
          })

      },
      ss() {
        if (this.pkey == '1651646') {
          this.$message.error('请输入要搜索的值')
        } else {
          let pagehomt = {
            page: 1,
            number: 10,
            title: this.pkey,
            sort: this.sort,
            order: this.order
          }
          request({
            /**
             * 搜索框获取
             */
            url: 'http://112.74.113.75:8843/product/productList',
            method: 'post',
            data: pagehomt
          }).then(res => {
            this.$store.state.Single = res.data
            this.single = this.$store.state.Single
            this.total = res.count
            let pageArr = [];
            let count = Math.ceil(this.$store.state.Single.length / this.pagesize);
            for (let i = 1; i < count + 1; i++) {
              pageArr.push(this.pagesize * i);
            }
            this.pageCount = pageArr
          }).catch(err => {
          })
        }
      },
      /**
       * 分类搜索列表
       */
      selectSortFindProductList(){
        let pagehomt = {
            page: 1,
            number: 10,
            sort: this.sort,
            order: this.order
          }
        request({
            /**
             * 分类/排序搜索
             */
            url: 'http://112.74.113.75:8843/product/productList',
            method: 'post',
            data: pagehomt
          }).then(res => {
            this.$store.state.Single = res.data
            this.single = this.$store.state.Single
            this.total = res.count
            let pageArr = [];
            let count = Math.ceil(this.$store.state.Single.length / this.pagesize);
            for (let i = 1; i < count + 1; i++) {
              pageArr.push(this.pagesize * i);
            }
            this.pageCount = pageArr
          }).catch(err => {

          })
      }
    },
    directives: {
      focus: {
        update: function (el, value) {//表示元素插入到DOM中的时候会执行这个函数
          if (value) {
            el.focus()
          }
        }
      }
    },
    computed: {},
    created() {
      this.handlePageSizes();
      this.ceil;
      this.ceil1;
      const h = this.$createElement;
      this.$notify({
        title: '公告',
        message: h('i', { style: 'color: teal'}, '欢迎光临，本平台仅为广西科技大学全校师生服务，谢谢。')
        ,position: 'bottom-right'
      });
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

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 1200px;
  }
</style>

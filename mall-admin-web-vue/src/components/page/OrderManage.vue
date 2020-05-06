<template>
    <div class="">
        <div class="UserManage">
        </div>
        <div class="container">
            <el-tabs v-model="message" @tab-click="handleClick">
                <el-tab-pane :label="`全部订单`" name="first">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            class="handle-del mr10"
                            @click="delAllSelection"
                        >批量取消
                        </el-button>
                        <el-input v-model="query.name" placeholder="买家/卖家姓名" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        :header-cell-style="{'text-align':'center'}"
                        :cell-style="{'text-align':'center'}"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="orderNo" label="订单编号" width="150" align="center"></el-table-column>
                        <el-table-column prop="sellerName" label="卖家姓名" width="100"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerName" label="买家姓名" width="100"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerUserName" label="收货人姓名" width="110"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="productPrice" label="商品详情" width="120">
                          <template slot-scope="scope">
                    　　　  <img :src="scope.row.imgUrl" width="60" height="60"/>
                            <el-button type="primary" size="mini"  @click="findProductDetails(scope.row.productId)">查看详情</el-button>
                    　　  </template>
                        </el-table-column>
                        <el-table-column prop="productPrice" label="实付款" width="100">
                          <template slot-scope="scope">
                            <el-tag style="color: red">¥{{scope.row.productPrice}}</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column prop="name" label="订单状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: red" v-if="scope.row.status==0">未付款</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==1">已付款/等待发货</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==2">已发货</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==3">已完成</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="220" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="orderDelete(scope.$index, scope.row, scope.row.id)"
                                >取消</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                        <div class="pagination">
                            <el-pagination
                                background
                                layout="total, sizes, prev, pager, next, jumper"
                                :current-page="query.pageIndex"
                                :page-size="query.pageSize"
                                :total="pageTotal"
                                :page-sizes="[5, 10, 15, 20]"
                                @current-change="handlePageChange"
                                @size-change="handleSizeChange"
                            ></el-pagination>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已完成订单`" name="second">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            class="handle-del mr10"
                            @click="delAllSelection"
                        >批量取消
                        </el-button>
                        <el-input v-model="query.name" placeholder="买家/卖家姓名" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        :header-cell-style="{'text-align':'center'}"
                        :cell-style="{'text-align':'center'}"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="orderNo" label="订单编号" width="150" align="center"></el-table-column>
                        <el-table-column prop="sellerName" label="卖家姓名" width="100"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerName" label="买家姓名" width="100"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerUserName" label="收货人姓名" width="110"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="productPrice" label="商品详情" width="120">
                          <template slot-scope="scope">
                    　　　  <img :src="scope.row.imgUrl" width="60" height="60"/>
                            <el-button type="primary" size="mini"  @click="findProductDetails(scope.row.productId)">查看详情</el-button>
                    　　  </template>
                        </el-table-column>
                        <el-table-column prop="productPrice" label="实付款" width="100">
                          <template slot-scope="scope">
                            <el-tag style="color: red">¥{{scope.row.productPrice}}</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column prop="name" label="订单状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: red" v-if="scope.row.status==0">未付款</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==1">已付款/等待发货</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==2">已发货</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==3">已完成</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="220" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="orderDelete(scope.$index, scope.row, scope.row.id)"
                                >取消</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                        <div class="pagination">
                            <el-pagination
                                background
                                layout="total, sizes, prev, pager, next, jumper"
                                :current-page="query.pageIndex"
                                :page-size="query.pageSize"
                                :total="pageTotal"
                                :page-sizes="[5, 10, 15, 20]"
                                @current-change="handlePageChange"
                                @size-change="handleSizeChange"
                            ></el-pagination>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`未完成订单`" name="third">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            class="handle-del mr10"
                            @click="delAllSelection"
                        >批量取消
                        </el-button>
                        <el-input v-model="query.name" placeholder="买家/卖家姓名" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        :header-cell-style="{'text-align':'center'}"
                        :cell-style="{'text-align':'center'}"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="orderNo" label="订单编号" width="150" align="center"></el-table-column>
                        <el-table-column prop="sellerName" label="卖家姓名" width="100"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerName" label="买家姓名" width="100"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="buyerUserName" label="收货人姓名" width="110"></el-table-column>
                        <el-table-column prop="buyerUserPhone" label="联系方式" width="120"></el-table-column>
                        <el-table-column prop="productPrice" label="商品详情" width="120">
                          <template slot-scope="scope">
                    　　　  <img :src="scope.row.imgUrl" width="60" height="60"/>
                            <el-button type="primary" size="mini"  @click="findProductDetails(scope.row.productId)">查看详情</el-button>
                    　　  </template>
                        </el-table-column>
                        <el-table-column prop="productPrice" label="实付款" width="100">
                          <template slot-scope="scope">
                            <el-tag style="color: red">¥{{scope.row.productPrice}}</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column prop="name" label="订单状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: red" v-if="scope.row.status==0">未付款</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==1">已付款/等待发货</el-tag>
                            <el-tag style="color: blue" v-if="scope.row.status==2">已发货</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==3">已完成</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="220" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="orderDelete(scope.$index, scope.row, scope.row.id)"
                                >取消</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                        <div class="pagination">
                            <el-pagination
                                background
                                layout="total, sizes, prev, pager, next, jumper"
                                :current-page="query.pageIndex"
                                :page-size="query.pageSize"
                                :total="pageTotal"
                                :page-sizes="[5, 10, 15, 20]"
                                @current-change="handlePageChange"
                                @size-change="handleSizeChange"
                            ></el-pagination>
                        </div>
                    </div>
                </el-tab-pane>
                <!-- 新增弹出框 -->
                    <el-dialog title="新增会员" :visible.sync="addlUserDialog" width="30%">
                        <el-form ref="form" :model="form" label-width="70px">
                            <el-form-item label="学号">
                                <el-input v-model="form.sno"></el-input>
                            </el-form-item>
                            <el-form-item label="角色">
                                <el-cascader :options="roles" v-model="form.role"></el-cascader>
                            </el-form-item>
                            <el-form-item label="会员名">
                                <el-input v-model="form.name"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式">
                                <el-input v-model="form.phone"></el-input>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input v-model="form.email"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="editVisible = false">取 消</el-button>
                            <el-button type="primary" @click="saveEdit">确 定</el-button>
                        </span>
                    </el-dialog>

                    <!-- 编辑弹出框 -->
                    <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
                        <el-form ref="form" :model="form" label-width="70px">
                            <el-form-item label="学号">
                                <el-input v-model="form.sno"></el-input>
                            </el-form-item>
                            <el-form-item label="角色">
                                <el-cascader :options="roles" v-model="form.role"></el-cascader>
                            </el-form-item>
                            <el-form-item label="会员名">
                                <el-input v-model="form.name"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式">
                                <el-input v-model="form.phone"></el-input>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input v-model="form.email"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="editVisible = false">取 消</el-button>
                            <el-button type="primary" @click="saveEdit">确 定</el-button>
                        </span>
                    </el-dialog>
            </el-tabs>
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
          <div id="right">
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
    export default {
        name: 'UserManage',
        data() {
                return {
                    query: {
                    address: '',
                    name: '',
                    pageIndex: 1,
                    pageSize: 10
                },
                selectValue: false,
                UserTableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {},
                idx: -1,
                id: -1,
                message: 'first',
                showHeader: false,
                unread: [],
                roles: [
                    {
                        value: '管理员',
                        label: '管理员'
                    },{
                        value: '普通用户',
                        label: '普通用户'
                    }],
                addlUserDialog: false,
                permission : '',
                status: '',
                drawer: false,
                datas: {}
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
            handleRead(index) {
                const item = this.unread.splice(index, 1);
                this.read = item.concat(this.read);
            },
            handleDel(index) {
                const item = this.read.splice(index, 1);
                this.recycle = item.concat(this.recycle);
            },
            handleRestore(index) {
                const item = this.recycle.splice(index, 1);
                this.read = item.concat(this.read);
            },
        /**
         * 切换tab
         */
        handleClick(tab) {
            if(tab.name ==='second'){
                //刷新table
                this.permission = '3';
                this.getData();
            }else if(tab.name ==='third'){
                //刷新table
                this.permission = '2';
                this.getData();
            }else if(tab.name ==='first'){
                //刷新table
                this.permission = null;
                this.getData();
            }else{
                //刷新table
                this.permission = '2';
                this.getData();
            }
        },
        /**
         * 获取商品数据
         */
        getData() {
            let prom = {
                page: this.query.pageIndex,
                number: this.query.pageSize,
                name: this.query.name,
                status: this.permission,
            };
            this.$http
                .post('http://112.74.113.75:8888/admin/order/getOrder', prom)
            .then(response => {
                if(response.data.code === 0){
                    this.UserTableData = response.data.data;
                    this.pageTotal = response.data.count;
                    this.$message.success(response.data.msg);
                }else{
                    this.$message.error(response.data.msg);
                }
                
            })
        },
        /**
         * 搜索框搜索用户
         */
        userSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
        },
        // 取消订单
        orderDelete(index, row, id) {
            // 二次确认删除
            this.$confirm('确定要取消这笔订单吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get('http://112.74.113.75:8888/admin/order/delete/'+id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(response.data.msg);
                        this.UserTableData.splice(index, 1);
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
                
            })
            .catch(() => {});
        },
        //解冻单个用户
        userReset(index, row, id){
            this.$confirm('确定要解冻该用户吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get('http://112.74.113.75:8888/admin/user/resetUser/'+id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(response.data.msg);
                        this.UserTableData.splice(index, 1);
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
                
            })
            .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        //多选冻结
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            let orderIds = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
                orderIds += this.multipleSelection[i].id + ',';
            }
            this.$http
                .delete('http://112.74.113.75:8888/admin/order/deleteList/'+orderIds)
            .then(response => {
                if(response.data.code == 0){
                    this.$message.success(`取消了订单 ${str} 共${length}笔`);
                    //刷新table
                    this.getData();
                }else{
                    this.$message.error(response.data.msg);
                }
            })
            this.multipleSelection = [];
        },
        //多选授权
        permissionAllSelection(){
            const length = this.multipleSelection.length;
            let str = '';
            let userIds = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
                userIds += this.multipleSelection[i].id + ',';
            }
            this.$http
                .get('http://112.74.113.75:8888/admin/user/authUser/'+userIds)
            .then(response => {
                if(response.data.code == 0){
                    this.$message.success(`授权了用户 ${str} 共${length}个用户`);
                    //刷新table
                    this.getData();
                }else{
                    this.$message.error(response.data.msg);
                }
            })
            this.multipleSelection = [];
        },
        //打开添加用户界面
        openAddlUserDialog(){
            this.form = {role:'普通用户'}
            this.addlUserDialog = true
        },
        //打开用户编辑页面
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        //授权
        roleEdit(index,row){
            this.idx = index;
            this.form = row;
            this.$confirm('确定要给'+row.name+'授权成为平台的正式会员吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get('http://112.74.113.75:8888/admin/user/getPermission/'+this.form.id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(this.form.name+""+response.data.msg);
                        //刷新table
                        this.getData();
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
                
            })
            .catch(() => {});
        },
        //重置密码
        passwordEdit(index,row){
            this.idx = index;
            this.form = row;
            this.$confirm('确定要重置密码吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get('http://112.74.113.75:8888/admin/user/passwordReset/'+this.form.id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(this.form.name+""+response.data.msg);
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
                
            })
            .catch(() => {});
        },
        //新增或更新用户信息
        saveEdit() {
            let prom = {
                id: this.form.id,
                sno: this.form.sno,
                name: this.form.name,
                phone: this.form.phone,
                email: this.form.email,
                role: this.form.role[0]
            };
            let addProm = {
                sno: this.form.sno,
                name: this.form.name,
                phone: this.form.phone,
                email: this.form.email,
                role: this.form.role[0]
            };
            if(this.editVisible){
                //更新
                this.$http
                .post('http://112.74.113.75:8888/admin/user/updateUser',prom)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(response.data.msg);
                        this.editVisible = false;
                        this.$set(this.UserTableData, this.idx, this.form);
                    }else{
                        this.$message.error(response.data.msg);
                        //刷新table
                        this.getData();
                    }
                })
            }else{
                //新增
                this.$http
                .post('http://112.74.113.75:8888/admin/user/addUser',addProm)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(response.data.msg);
                        //刷新table
                        this.getData();
                        //关闭窗口
                        this.addlUserDialog = false
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
            }
            
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        },
        handleSizeChange(val){
            this.$set(this.query, 'pageSize', val);
            this.getData();
        }
        },
        computed: {
            unreadNum(){
                return this.unread.length;
            }
        },
        created(){
            this.getData();
        }
    }

</script>

<style>
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

#Details {
	width: 1200px;
	margin: 0 auto;
}

#Details>p {
	color: #4DA5FF;
	margin: 30px 0;
	font-size: 18px;
	line-height: 50px;
	text-align: center;
	height: 50px;
	border-bottom: 1px solid #4DA5FF;
}

#Details>div>.left>ul {
	width: 500px;
}

#Details>div>.left img {
	width: 400px;
	height: 400px;
	/*padding: 0px 50px 25px 50px;*/
}

#Details>div>.left>ul li:last-of-type {
	padding: 0 50px;
	color: #000;
	font-size: 18px;
}

#right {
	width: 650px;
	padding-left: 20px;
	background-color:#eee;
    position: absolute;
    left:600px;
    top: 200px;
}

#right ul li {
	margin: 30px 0;
}

#right ul li>span:first-of-type{
	color: #999;
	font-size: 14px;
	margin-right: 10px;
}

#right ul .des {
	white-space: normal;
	word-break: break-all;
	overflow: hidden;
	width: 600px;
}

</style>


<template>
    <div class="">
        <div class="UserManage">
        </div>
        <div class="container">
            <el-tabs v-model="message" @tab-click="handleClick">
                <el-tab-pane :label="`全部商品`" name="first">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="primary"
                            icon="el-icon-success"
                            class="handle-del mr10"
                            @click="updAllSelection"
                        >批量上架
                        </el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            class="handle-del mr10"
                            @click="delAllSelection"
                        >批量下架
                        </el-button>
                        <el-input v-model="query.name" placeholder="商品标题" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="id" label="商品id" width="110" v-if="false"></el-table-column>
                        <el-table-column prop="sno" label="学号" width="110"></el-table-column>
                        <el-table-column prop="name" label="会员名" width="150"></el-table-column>
                        <el-table-column prop="sort" label="商品类别"></el-table-column>
                        <el-table-column prop="title" label="商品标题"></el-table-column>
                        <el-table-column prop="imgUrl" label="商品图片" width="110" align="center">
                           <template slot-scope="scope">
                        　　　<img :src="scope.row.imgUrl" width="60" height="60"/>
                        　　</template>
                        </el-table-column>
                        <el-table-column prop="name" label="商品状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: green" v-if="scope.row.permission==1">在售</el-tag>
                            <el-tag style="color: red" v-if="scope.row.permission==0">未上架</el-tag>
                            <el-tag style="color: red" v-if="scope.row.status==0">已售出</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==1">未售出</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="price" label="在售价格" width="100"></el-table-column>
                        <el-table-column prop="collectNum" label="收藏数" width="100"></el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="330" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    :disabled="scope.row.permission==1"
                                    type="primary"
                                    size="mini"
                                    @click="upProduct(scope.$index, scope.row)"
                                >上架</el-button>
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="deProduct(scope.$index, scope.row, scope.row.id)"
                                >下架</el-button>
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
                <el-tab-pane :label="`在售商品`" name="second">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            class="handle-del mr10"
                            @click="delAllSelection"
                        >批量下架
                        </el-button>
                        <el-input v-model="query.name" placeholder="商品标题" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="id" label="商品id" width="110" v-if="false"></el-table-column>
                        <el-table-column prop="sno" label="学号" width="110"></el-table-column>
                        <el-table-column prop="name" label="会员名" width="150"></el-table-column>
                        <el-table-column prop="sort" label="商品类别"></el-table-column>
                        <el-table-column prop="title" label="商品标题"></el-table-column>
                        <el-table-column prop="imgUrl" label="商品图片" width="110" align="center">
                           <template slot-scope="scope">
                        　　　<img :src="scope.row.imgUrl" width="60" height="60"/>
                        　　</template>
                        </el-table-column>
                        <el-table-column prop="name" label="商品状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: green" v-if="scope.row.permission==1">在售</el-tag>
                            <el-tag style="color: red" v-if="scope.row.permission==0">未上架</el-tag>
                            <el-tag style="color: red" v-if="scope.row.status==0">已售出</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==1">未售出</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="price" label="在售价格" width="100"></el-table-column>
                        <el-table-column prop="collectNum" label="收藏数" width="100"></el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="330" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    :disabled="scope.row.permission==1"
                                    type="primary"
                                    size="mini"
                                    @click="upProduct(scope.$index, scope.row)"
                                >上架</el-button>
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="deProduct(scope.$index, scope.row, scope.row.id)"
                                >下架</el-button>
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
                <el-tab-pane :label="`待上架商品`" name="third">
                    <div class="container">
                        <div class="handle-box">
                        <el-button
                            type="primary"
                            icon="el-icon-success"
                            class="handle-del mr10"
                            @click="updAllSelection"
                        >批量上架
                        </el-button>
                        <el-input v-model="query.name" placeholder="商品标题" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
                    </div>
                    <el-table
                        :data="UserTableData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        @selection-change="handleSelectionChange"
                    >
                        <el-table-column type="selection" width="55" align="center"></el-table-column>
                        <el-table-column prop="id" label="商品id" width="110" v-if="false"></el-table-column>
                        <el-table-column prop="sno" label="学号" width="110"></el-table-column>
                        <el-table-column prop="name" label="会员名" width="150"></el-table-column>
                        <el-table-column prop="sort" label="商品类别"></el-table-column>
                        <el-table-column prop="title" label="商品标题"></el-table-column>
                        <el-table-column prop="imgUrl" label="商品图片" width="110" align="center">
                           <template slot-scope="scope">
                        　　　<img :src="scope.row.imgUrl" width="60" height="60"/>
                        　　</template>
                        </el-table-column>
                        <el-table-column prop="name" label="商品状态" width="150">
                          <template slot-scope="scope">
                            <el-tag style="color: green" v-if="scope.row.permission==1">在售</el-tag>
                            <el-tag style="color: red" v-if="scope.row.permission==0">未上架</el-tag>
                            <el-tag style="color: red" v-if="scope.row.status==0">已售出</el-tag>
                            <el-tag style="color: green" v-if="scope.row.status==1">未售出</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column prop="price" label="在售价格" width="100"></el-table-column>
                        <el-table-column prop="collectNum" label="收藏数" width="100"></el-table-column>
                        <el-table-column prop="createTime" label="商品发布时间" width="180"></el-table-column>
                        <el-table-column prop="updateTime" label="上次修改时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="330" align="center">
                            <template slot-scope="scope">
                                <el-button
                                    :disabled="scope.row.permission==1"
                                    type="primary"
                                    size="mini"
                                    @click="upProduct(scope.$index, scope.row)"
                                >上架</el-button>
                                <el-button
                                    type="danger"
                                    size="mini"
                                    :disabled="scope.row.permission==0"
                                    @click="deProduct(scope.$index, scope.row, scope.row.id)"
                                >下架</el-button>
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
            </el-tabs>
        </div>
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
                baseHttp: 'http://127.0.0.1:8888/',
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
            }
        },
        methods: {
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
                this.permission = '1';
                this.getData();
            }else if(tab.name ==='third'){
                //刷新table
                this.permission = '0';
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
                title: this.query.name,
                permission: this.permission,
                status: this.status
            };
            this.$http
                .post(this.baseHttp+'/admin/product/getProduct', prom)
            .then(response => {
                this.UserTableData = response.data.data;
                this.pageTotal = response.data.count;
            })
        },
        /**
         * 搜索框搜索用户
         */
        userSearch() {
            this.$set(this.query, 'pageIndex', 1);
            this.getData();
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 批量下架
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            let productIds = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
                productIds += this.multipleSelection[i].id + ',';
            }
            this.$http
                .delete(this.baseHttp+'/admin/product/deProducts/'+productIds)
            .then(response => {
                if(response.data.code == 0){
                    this.$message.success(`下架了商品 ${str} 共${length}个商品`);
                    //刷新table
                    this.getData();
                }else{
                    this.$message.error(response.data.msg);
                }
            })
            this.multipleSelection = [];
        },
        // 批量上架
        updAllSelection(){
            const length = this.multipleSelection.length;
            let str = '';
            let productIds = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
                productIds += this.multipleSelection[i].id + ',';
            }
            this.$http
                .get(this.baseHttp+'/admin/product/upProducts/'+productIds)
            .then(response => {
                if(response.data.code == 0){
                    this.$message.success(`上架了商品 ${str} 共${length}个商品`);
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
                .get(this.baseHttp+'/admin/user/authUser/'+userIds)
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
        // 上架商品
        upProduct(index,row){
            this.idx = index;
            this.form = row;
            this.$confirm('确定上架【'+row.title+'】吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get(this.baseHttp+'/admin/product/upProduct/'+this.form.id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(this.form.title+"---"+response.data.msg);
                        //刷新table
                        this.getData();
                    }else{
                        this.$message.error(response.data.msg);
                    }
                })
                
            })
            .catch(() => {});
        },
        // 下架
        deProduct(index, row, id){
            this.idx = index;
            this.form = row;
            this.$confirm('确定下架【'+row.title+'】吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get(this.baseHttp+'/admin/product/deProduct/'+this.form.id)
                .then(response => {
                    if(response.data.code == 0){
                        this.$message.success(this.form.title+"---"+response.data.msg);
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
                    .get(this.baseHttp+'/admin/user/passwordReset/'+this.form.id)
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
</style>


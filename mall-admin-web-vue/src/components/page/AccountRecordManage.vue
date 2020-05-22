<template>
    <div class="">
        <div class="UserManage">
        </div>
        <div class="container">
            <el-tabs v-model="message" @tab-click="handleClick">
                <el-tab-pane :label="`全部交易记录`" name="first">
                    <div class="container">
                        <div class="handle-box">
                        <el-input v-model="query.name" placeholder="会员名/学号" class="handle-input mr10"></el-input>
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
                        <el-table-column prop="userSno" label="学号"></el-table-column>
                        <el-table-column prop="userName" label="会员名" width="200"></el-table-column>
                        <el-table-column prop="userPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="sellerName" label="对方账户" width="200"></el-table-column>
                        <el-table-column prop="sellerSno" label="学号" align="center"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="category" label="交易类型" width="200" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">支出</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">收入</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">充值</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">提现</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="money" label="交易金额(¥)" width="110" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">-{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">-{{scope.row.money}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="生成时间" width="200"></el-table-column>
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
                <el-tab-pane :label="`充值/提现记录`" name="second">
                    <div class="container">
                        <div class="handle-box">
                        <el-input v-model="query.name" placeholder="会员名/学号" class="handle-input mr10"></el-input>
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
                        <el-table-column prop="userSno" label="学号"></el-table-column>
                        <el-table-column prop="userName" label="会员名" width="200"></el-table-column>
                        <el-table-column prop="userPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="sellerName" label="对方账户" width="200"></el-table-column>
                        <el-table-column prop="sellerSno" label="学号" align="center"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="category" label="交易类型" width="200" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">支出</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">收入</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">充值</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">提现</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="money" label="交易金额(¥)" width="110" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">-{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">-{{scope.row.money}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="生成时间" width="200"></el-table-column>
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
                <el-tab-pane :label="`支出/收入记录`" name="third">
                    <div class="container">
                        <div class="handle-box">
                        <el-input v-model="query.name" placeholder="会员名/学号" class="handle-input mr10"></el-input>
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
                        <el-table-column prop="userSno" label="学号"></el-table-column>
                        <el-table-column prop="userName" label="会员名" width="200"></el-table-column>
                        <el-table-column prop="userPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="sellerName" label="对方账户" width="200"></el-table-column>
                        <el-table-column prop="sellerSno" label="学号" align="center"></el-table-column>
                        <el-table-column prop="sellerPhone" label="联系方式" width="200"></el-table-column>
                        <el-table-column prop="category" label="交易类型" width="200" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">支出</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">收入</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">充值</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">提现</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="money" label="交易金额(¥)" width="110" align="center">
                            <template slot-scope="scope">
                                <el-tag style="color: red" v-if="scope.row.category == 0">-{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 1">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: green" v-if="scope.row.category == 3">+{{scope.row.money}}</el-tag>
                                <el-tag style="color: red" v-if="scope.row.category == 4">-{{scope.row.money}}</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="生成时间" width="200"></el-table-column>
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
                category1: null,
                category2: null
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
                this.category1 = '3';
                this.category2 = '4';
                this.getData();
            }else if(tab.name ==='third'){
                //刷新table
                this.category1 = '0';
                this.category2 = '1';
                this.getData();
            }else if(tab.name ==='first'){
                //刷新table
                this.category1 = null;
                this.category2 = null;
                this.getData();
            }else{
                //刷新table
                this.category = null;
                this.getData();
            }
        },
        /**
         * 获取数据
         */
        getData() {
            let prom = {
                page: this.query.pageIndex,
                number: this.query.pageSize,
                userName: this.query.name,
                category1: this.category1,
                category2: this.category2
            };
            this.$http
                .post(this.baseHttp+'/admin/account/getAccountRecord', prom)
            .then(response => {
                if(response.data.code == 0){
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
        // 删除单个用户
        userDelete(index, row, id) {
            // 二次确认删除
            this.$confirm('确定要冻结吗？', '提示', {
                type: 'warning'
            })
            .then(() => {
                this.$http
                    .get(this.baseHttp+'/admin/user/deleteUser/'+id)
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
                    .get('http://112.74.113.75/admin/user/resetUser/'+id)
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
            let userIds = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
                userIds += this.multipleSelection[i].id + ',';
            }
            this.$http
                .delete('http://112.74.113.75/admin/user/deleteUser/'+userIds)
            .then(response => {
                if(response.data.code == 0){
                    this.$message.success(`冻结了用户 ${str} 共${length}个用户`);
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
                .get('http://112.74.113.75/admin/user/authUser/'+userIds)
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
                    .get('http://112.74.113.75/admin/user/getPermission/'+this.form.id)
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
                    .get('http://112.74.113.75/admin/user/passwordReset/'+this.form.id)
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
                .post('http://112.74.113.75/admin/user/updateUser',prom)
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
                .post('http://112.74.113.75/admin/user/addUser',addProm)
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
</style>


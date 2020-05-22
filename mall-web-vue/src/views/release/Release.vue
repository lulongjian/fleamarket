<template>
	<div id="Release">
			<div class="title">发布闲置:</div>
			<div class="ReleaseBd">
				<ul>
          <li>
            <span>商品图片:</span>
            <div style="padding-left: 95px">
            <el-upload
              class="avatar-uploader"
              action="http://127.0.0.1:8843/sys/uploadImg"
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
						<input v-model="title" type="text">
					</li>
					<li>
						<span>商品原价格:</span>
						<input v-model="bprice" type="number">
					</li>
          <li>
            <span>商品卖出价格:</span>
            <input v-model="price" type="number">
          </li>
					<li>
						<span class="describe">商品描述:</span>
						<textarea v-model="content" name="textarea" id="" placeholder="请对您发布的商品进行描述">
						</textarea>
					</li>
					<li>
						<span></span>
						<button @click="sunm">发布</button>
						<input type="reset" value="重置" id="reset">
					</li>
				</ul>
			</div>
	</div>
</template>

<script>
import '@/assets/css/release.css'
import request from "@/network/request"

export default {
	name: "Release",
	data () {
		return {
      baseHttp: "http://127.0.0.1:8843",
      imageUrl: '',
      dbimgUrl:'',
      title:'',
      bprice:'',
      price:'',
      content:'',
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
    sunm(){
      this.$http
       .put(this.baseHttp+'/product/addProduct',{
         imgUrl:this.dbimgUrl,
         title:this.title,
         oldPrice:this.bprice,
         price:this.price,
         des:this.content,
         userId:sessionStorage.getItem("userId"),
         sort:this.sort
       })
      .then(response=>{
        setTimeout(()=>{
          location.reload();
          this.$router.push('/home')
          location.reload();
        })
    })
    }
	},
  created() {
    if(sessionStorage.getItem("user")==null){
      setTimeout(()=>{
        this.$router.push('/login')
      },0)
    }else {
      this.$router.push('/release')
    }
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

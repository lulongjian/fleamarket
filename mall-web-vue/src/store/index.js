import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations'
import actions from './actions'
import getters from './getters';


// 1.安装Vuex
Vue.use(Vuex)

// 2.创建对象
const store = new Vuex.Store({
	state:{
		defaultImg: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
		userName:null,
		passWord:null,
		img:false,
		flag: true,
		Single: [],
    name:''
	},
	mutations,
	actions,
	getters
})

// 3.导出
export default store

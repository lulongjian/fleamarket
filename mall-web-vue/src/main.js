import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import qs from 'qs';
import axios from 'axios';
import vueRsource from 'vue-resource'
import Vuelidate from 'vuelidate'
import SlideVerify from 'vue-monoplasty-slide-verify';
import Validator from 'vue-validator'
import 'default-passive-events'

Vue.use(Validator)
Vue.use(SlideVerify);

Vue.prototype.$http = axios
Vue.prototype.$qs = qs;
Vue.config.productionTip = false;

/* eslint-disable no-new */
Vue.use(ElementUI)
Vue.use(vueRsource)
Vue.use(Vuelidate)
// import request from "./network/request"
new Vue({
  el: '#app',
	router,
	store,
  render: h => h(App)
})
/**
 * vue中的一个坑，将请求Request payload 转为 from data 格式
 */
Vue.http.options.emulateJSON = true;
Vue.http.options.headers = {
  'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
};
/**
 * vue中的一个坑，将请求Request payload 转为 from data 格式（上个对一些方法起作用，一些不起）
 */
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.transformRequest = [function (data) {
    let src = ''
    for (let item in data) {
      src += encodeURIComponent(item) + '=' + encodeURIComponent(data[item]) + '&'
    }
    return src
}]

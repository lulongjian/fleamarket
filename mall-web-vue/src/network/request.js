import axios from 'axios'
import qs from 'qs';
// axios.defaults.withCredentials = true;//允许跨域携带cookie
// axios.defaults.headers.common['Authorization'] = res.headers.authorization
// axios.defaults.headers.common['Authorization'] = store.getState().session.token;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded';
export default function request(config) {
			// 1.创建axios的实例
			const instence = axios.create({
				baseURL: 'http://127.0.0.1:8843',
				// timeout: 5000,
				// // responseType: 'json',
				// dataType: 'json',
				// transformRequest: data => qs.stringify(data),
				// headers:{'Access-Control-Allow-Origin':'*'},
			})

			// 2.axios的拦截器
			// 2.1请求拦截的作用
			// axios.interceptors 拦截全 响应局
			// 请求拦截
			instence.interceptors.request.use(
				config => {
					// 1.比如config中的一些信息不符合服务器要求

					// 2.每次发送请求时，界面显示图标

					// 3.某些网络请求（比如登陆（token），必须携带特殊信息）
					// console.log(config);
					return config;
				},
				err => {
					// console.log(err)
				}
			)
			// 2.2响应拦截
			instence.interceptors.response.use(
				res => {
					return res.data
				},
				err => {
					// console.log(err)
				}
			)
			// 3.发送真正的网络请求
			// instence返回Promise
			return instence(config)
}
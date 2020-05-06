import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const Home = () => import('../views/home/Home')
const Help = () => import('../views/help/Help')
const Login = () => import('../views/login/Login')
const Reg = () => import('../views/reg/Reg')
const Release = () => import('../views/release/Release')
const Car = () => import('../components/car/Car')
const Order = () => import('../views/order/order')

const routes = [
	{
		path: '', 
		redirect: '/home' 
	},
	{
		path: '/home',
		component: Home,
		meta: {
			title: '首页' 
		},
	},
	{
		path: '/help',
		component: Help,
		meta: { 
			title: '帮助' 
		}
	},
	{
		path: '/login',
		component: Login,
		meta: { 
			title: '登陆' 
		}
	},
	{
		path: '/reg',
		component: Reg,
		meta: {
			title: '注册' 
		}
	},
	{
		path: '/release',
		component: Release,
		meta: {
			title: '发布' 
		}
	},
	{
		path: '/car',
		component: Car,
		meta: {
			title: '收藏'
		}
	},
	{
		path: '/order',
		component: Order,
		meta: {
			title: '订单' 
		}
	}
]

const router = new Router({
	routes,
	mode: 'hash',
	linkActiveClass: 'nav_active'
})

router.beforeEach((to, from, next) => {
	document.title = to.matched[0].meta.title
	next()
})

export default router

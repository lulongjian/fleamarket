import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/Index',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Index.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/UserManage',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/UserManage.vue'),
                    meta: { title: '会员管理' }
                },
                {
                    path: '/ProductManage',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/ProductManage.vue'),
                    meta: { title: '商品管理' }
                },
                {
                    path: '/OrderManage',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/OrderManage.vue'),
                    meta: { title: '订单管理' }
                },
                {
                    path: '/AccountRecordManage',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/AccountRecordManage.vue'),
                    meta: { title: '交易记录' }
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});

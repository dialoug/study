import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

// 重置路由
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        routes
    })
}

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {  // 如果已登录
        // 拼装动态路由
        const manageRoute = {
            path: '/', name: 'Manage', component: () => import('../views/Manage.vue'), redirect: "/home", children: [
                {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
                {path: 'password', name: '修改密码', component: () => import('../views/Password.vue')},
                {path: 'dateSelect', name: 'DateSelect', component: () => import('../views/patient/DateSelect.vue')},
                {path: 'deal', name: 'Deal', component: () => import('../views/doctor/Deal.vue')},
            ]
        }
        // 根据返回信息动态拼装当前用户角色的菜单面板
        const menus = JSON.parse(storeMenus)
        menus.forEach(item => {
            if (item.path !== null) {
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu)  // 放入路由
            }
        })

        // 获取当前的路由对象名称数组
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if (!currentRouteNames.includes('Manage')) {
            // 动态添加到现在的路由对象中去
            router.addRoute(manageRoute)
        }
    }
}

// 每次刷新页面都要重新设置路由，否则路由就会被重置
setRoutes()

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
    store.commit("setPath")
    if (!to.matched.length) {  // 路由长度为空
        const menus = localStorage.getItem("menus")
        if (!menus) {
            next("/login")  // 当前状态未登录 跳转至登录界面
        } else {
            next("/404")
        }
    } else {
        next()
    }
})

export default router

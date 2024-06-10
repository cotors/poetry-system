import { createRouter,createWebHistory } from "vue-router"

const router = createRouter({
    history:createWebHistory(),
    routes:[
        {path: '/',
        component: () => import('@/views/Layout.vue'),
        redirect: '/home',
        children:[
            {path: '/home',component: () => import('@/views/user/Home.vue')},
            {path: '/manager',component: () => import('@/views/user/Manager.vue')},
            {path: '/persion',component: () => import('@/views/user/Persion.vue')},
            {path: '/status',component: () => import('@/views/find/Status.vue')},
            {path: '/location',component: () => import('@/views/find/Location.vue')},
            {path: '/time',component: () => import('@/views/find/Time.vue')},
            {path: '/vision',component: () => import('@/views/find/Vision.vue')},
            {path: '/publish',component: () => import('@/views/find/Publish.vue')},
            {path: '/data',component: () => import('@/views/find/Data.vue')},
            {path: '/author',component: () => import('@/views/find/Author.vue')},
            {path: '/china',component: () => import('@/views/find/China.vue')},
            {path: '/repassword',component: () => import('@/views/user/rePassword.vue')}
        ]
        },
        {path: '/login',component: () => import('@/views/Login.vue')},
        {path: '/register',component: () => import('@/views/Register.vue')}
    ]
})
export default router
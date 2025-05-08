import {createRouter, createWebHistory} from 'vue-router'
import store from "@/store";

const routes = [
    {
        path: '/login',
        component: () => import('../views/login.vue')
    }, {
        path: '/',
        component: () => import('../views/main.vue'),
        meta: {
            loginRequire: true
        },
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue')
        },{
            path: 'passenger',
            component: ()=>import('../views/main/passenger.vue')
        },{
            path: 'ticket',
            component: ()=>import('../views/main/ticket.vue')
        },
        ]
    },
    {
        path: '',
        redirect: '/welcome'
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
router.beforeEach((to, from, next) => {
    if (to.matched.some(function (item) {
        return item.meta.loginRequire
    })) {
        const _member = store.state.member;
        if (!_member.token) {
            next('/login');
        } else {
            next();
        }
    } else {
        next();
    }
})

export default router

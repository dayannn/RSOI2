import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import store from './store' // your vuex store

Vue.use(Router);

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next();
        return
    }
    next('/')
};

const ifAuthenticated = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next();
        return
    }
    next('/login')
};

export default new Router({
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        },
        {
            path: '/users',
            name: 'users',
            component: () => import('./views/Users.vue'),
            beforeEnter: ifAuthenticated
        },
        {
            path: '/books',
            name: 'books',
            component: () => import('./views/Books.vue'),
            beforeEnter: ifAuthenticated
        },
        {
            path: '/book/:id',
            name: 'book',
            component: () => import('./views/Book.vue'),
            beforeEnter: ifAuthenticated
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('./views/Login.vue'),
            beforeEnter: ifNotAuthenticated
        }
  ]
})

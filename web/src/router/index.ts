import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

// TODO : add about page
// TODO : add github

const routes = [
  {
    path: '/',
    name: 'home',
    // component: HomeView
    component: () => import(/* webpackChunkName: "products" */ '../views/ProductsView.vue')
  },
  {
    path: '/legal',
    name: 'legal',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "legal" */ '../views/LegalView.vue')
  },
  {
    path: '/products',
    name: 'products',
    libel: ',',
    component: () => import(/* webpackChunkName: "products" */ '../views/ProductsView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

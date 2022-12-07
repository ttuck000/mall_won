
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import OrderManager from "./components/listers/OrderCards"
import OrderDetail from "./components/listers/OrderDetail"

import MenuSearchView from "./components/MenuSearchView"
import MenuSearchViewDetail from "./components/MenuSearchViewDetail"
import CheckOederAdressManager from "./components/listers/CheckOederAdressCards"
import CheckOederAdressDetail from "./components/listers/CheckOederAdressDetail"

import PaymentManager from "./components/listers/PaymentCards"
import PaymentDetail from "./components/listers/PaymentDetail"

import FoodCookingManager from "./components/listers/FoodCookingCards"
import FoodCookingDetail from "./components/listers/FoodCookingDetail"

import DeliveryOrderProcessingManager from "./components/listers/DeliveryOrderProcessingCards"
import DeliveryOrderProcessingDetail from "./components/listers/DeliveryOrderProcessingDetail"


import OrderstatusView from "./components/OrderstatusView"
import OrderstatusViewDetail from "./components/OrderstatusViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/orders',
                name: 'OrderManager',
                component: OrderManager
            },
            {
                path: '/orders/:id',
                name: 'OrderDetail',
                component: OrderDetail
            },

            {
                path: '/menuSearches',
                name: 'MenuSearchView',
                component: MenuSearchView
            },
            {
                path: '/menuSearches/:id',
                name: 'MenuSearchViewDetail',
                component: MenuSearchViewDetail
            },
            {
                path: '/checkOederAdresses',
                name: 'CheckOederAdressManager',
                component: CheckOederAdressManager
            },
            {
                path: '/checkOederAdresses/:id',
                name: 'CheckOederAdressDetail',
                component: CheckOederAdressDetail
            },

            {
                path: '/payments',
                name: 'PaymentManager',
                component: PaymentManager
            },
            {
                path: '/payments/:id',
                name: 'PaymentDetail',
                component: PaymentDetail
            },

            {
                path: '/foodCookings',
                name: 'FoodCookingManager',
                component: FoodCookingManager
            },
            {
                path: '/foodCookings/:id',
                name: 'FoodCookingDetail',
                component: FoodCookingDetail
            },

            {
                path: '/deliveryOrderProcessings',
                name: 'DeliveryOrderProcessingManager',
                component: DeliveryOrderProcessingManager
            },
            {
                path: '/deliveryOrderProcessings/:id',
                name: 'DeliveryOrderProcessingDetail',
                component: DeliveryOrderProcessingDetail
            },


            {
                path: '/orderstatuses',
                name: 'OrderstatusView',
                component: OrderstatusView
            },
            {
                path: '/orderstatuses/:id',
                name: 'OrderstatusViewDetail',
                component: OrderstatusViewDetail
            },


    ]
})

import './assets/main.css'
import 'bootstrap'

import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
import router from './router'
import {store} from './store'


//const store = createStore({modules})

const app = createApp(App)
app.use(router)
app.use(store)
app.mount('#app')

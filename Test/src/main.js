import '@/assets/global.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
//引进element-plus中文语言包
import locale from 'element-plus/dist/locale/zh-cn.js'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createPinia } from 'pinia'
//添加pinia-persistedstate-plugin实现数据持久化存储
import { createPersistedState } from 'pinia-persistedstate-plugin'

const pinia = createPinia()

const persis = createPersistedState()

const app = createApp(App)

app.use(router)
app.use(ElementPlus,{ locale })
app.use(pinia)
pinia.use(persis)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

app.mount('#app')

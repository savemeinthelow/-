import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import * as Icons from '@ant-design/icons-vue'

const app = createApp(App)
app.use(store).use(Antd).use(router).mount('#app')

const icons = Icons;
for (const icon in icons){
    app.component(icon,icons[icon])
}


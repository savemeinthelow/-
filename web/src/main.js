import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd, {notification} from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as Icons from '@ant-design/icons-vue'
import axios from 'axios';
import "./assets/js/enums";

const app = createApp(App)
app.use(store).use(Antd).use(router).mount('#app')

const icons = Icons;
for (const icon in icons){
    app.component(icon,icons[icon])
}

axios.interceptors.request.use(function (config){
    console.log('请求参数',config);
    const _token = store.state.member.token
    if (_token){
        config.headers.token = _token;
    }
    return config;
},error => {
    return Promise.reject(error)
});
axios.interceptors.response.use(function (response){
    console.log('返回结果',response);
    return response;
},error => {
    console.log('返回错误',error)
    const response = error.response;
    const status = response.status;
    if(status===401){
        store.commit("setMember",{})
        notification.error({ description:'未登录或登录超时'})
        router.push("/login")
    }
    return Promise.reject(error);
});
axios.defaults.baseURL = process.env.VUE_APP_SERVER;
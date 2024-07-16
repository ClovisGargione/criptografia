import axios from 'axios'
import services from './services'
import * as storage from '../modules/auth/storage'


function getAxios() {
    let instance = axios.create()

    instance.interceptors.response.use((response) => {
        if (response.status === 401) {
            storage.deleteLocalToken()
            //window._Vue.$router.push({ name: 'login' })
        }
            return response
        }, ( error ) => {
        if (error.response.status === 401) {
            storage.deleteLocalToken()
            //window._Vue.$router.push({ name: 'login' })
            return Promise.reject(error.response.data)
        }
        return Promise.reject(error.response)
    })
    return instance
}

function request (config) {
    config.baseURL = config.baseURL == null ? 'http://localhost:8084/' : config.baseURL
    let instance = getAxios()
    return instance.request(config)
}


Object.keys(services).map(service => {
    Object.entries(services[service]).map(([key, value]) => {
        services[service][key] = value
    })
})

const setBearerToken = token => {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
}

export  { services, setBearerToken }
export default request
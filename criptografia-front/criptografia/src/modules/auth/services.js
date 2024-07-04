export default {
    login: { headers: {'Authorization': 'Basic Y3JpcHRvLWFwcDphYmNk', 'Content-Type': 'application/x-www-form-urlencoded', 'Access-Control-Allow-Origin': '*'}, baseURL:'http://localhost:8085/', method: 'post', url: 'oauth2/token' },
    user: { method: 'get', url: 'api/v1/secure/usuario-logado' },
    cripto: { method: 'post', url: 'api/v1/public/teste-cripto' }
}
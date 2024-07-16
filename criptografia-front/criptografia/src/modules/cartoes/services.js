export default {
    post: { method: 'post', url: 'api/v1/secure/dados-pagamento' },
    get: { method: 'get', url: 'api/v1/secure/dados-pagamento/{id}' },
    all: { method: 'get', url: 'api/v1/secure/dados-pagamento' },
    delete: { method: 'delete', url: 'api/v1/secure/dados-pagamento?id=' },
    buscarEndereco: { method: 'get', baseURL: 'https://viacep.com.br/ws/', url: '{cep}/json'}
}
import http, { services } from '@/http'
import * as types from './mutation-types'
import { encrypt, decrypt } from '@/crypto'

export  const  ActionDoPost = async ({ dispatch }, payload) => {

    services.cartoes.post.data = await encryptForm(payload)
    return http(services.cartoes.post)
}

export  const  ActionDoBuscarEndereco = async ({ dispatch }, payload) => {
    services.cartoes.buscarEndereco.url = payload + "/json"
    return http(services.cartoes.buscarEndereco)
}

export  const  ActionDoGetAll = async ({ dispatch }, payload) => {
    return http(services.cartoes.all).then(async res => {
        let cartoes = []
        for(let i = 0; i < res.data.length; i++){
            let cartao = await decryptForm(res.data[i])
            cartoes.push(cartao)
        }
        dispatch('ActionSetCartoes', cartoes)
      })
}

export const ActionSetCartoes = ({ commit }, payload) => {
    commit(types.SET_CARTOES, payload)
}

const encryptForm = async (payload) => {
    let form = {
        numeroCartao: "",
        nomeCartao: "",
        validadeCartao: "",
        cvvCartao: 0,
        enderecoCobranca: {
            cep: "",
            rua: "",
            numero: 0,
            complemento: "",
            bairro: "",
            cidade: "",
            estado: ""
        }
    }
    form.numeroCartao = await encrypt(payload.numeroCartao)
    form.nomeCartao = await encrypt(payload.nomeCartao)
    form.validadeCartao = await encrypt(payload.validadeCartao)
    form.cvvCartao = await encrypt(payload.cvvCartao)
    form.enderecoCobranca.cep = await encrypt(payload.enderecoCobranca.cep)
    form.enderecoCobranca.rua = await encrypt(payload.enderecoCobranca.rua)
    form.enderecoCobranca.numero = await encrypt(payload.enderecoCobranca.numero)
    form.enderecoCobranca.bairro = await encrypt(payload.enderecoCobranca.bairro)
    form.enderecoCobranca.cidade = await encrypt(payload.enderecoCobranca.cidade)
    form.enderecoCobranca.estado = await encrypt(payload.enderecoCobranca.estado)
    form.enderecoCobranca.complemento = payload.enderecoCobranca.complemento ? await encrypt(payload.enderecoCobranca.complemento) : null
    return form
}

const decryptForm = async (payload) => {
    let form = {
        numeroCartao: "",
        nomeCartao: "",
        validadeCartao: "",
        cvvCartao: 0,
        enderecoCobranca: {
            cep: "",
            rua: "",
            numero: 0,
            complemento: "",
            bairro: "",
            cidade: "",
            estado: ""
        }
    }
    form.id = payload.id
    form.numeroCartao = await decrypt(payload.numeroCartao)
    form.nomeCartao = await decrypt(payload.nomeCartao)
    form.validadeCartao = await decrypt(payload.validadeCartao)
    form.cvvCartao = await decrypt(payload.cvvCartao)
    form.enderecoCobranca.cep = await decrypt(payload.enderecoCobranca.cep)
    form.enderecoCobranca.rua = await decrypt(payload.enderecoCobranca.rua)
    form.enderecoCobranca.numero = await decrypt(payload.enderecoCobranca.numero)
    form.enderecoCobranca.bairro = await decrypt(payload.enderecoCobranca.bairro)
    form.enderecoCobranca.cidade = await decrypt(payload.enderecoCobranca.cidade)
    form.enderecoCobranca.estado = await decrypt(payload.enderecoCobranca.estado)
    form.enderecoCobranca.complemento = payload.enderecoCobranca.complemento ? await decrypt(payload.enderecoCobranca.complemento) : null
    return form
}
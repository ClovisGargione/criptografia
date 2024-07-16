import http, { services } from '@/http'
import { encrypt, decrypt } from '@/crypto'

export  const  ActionDoRegister = async ({ dispatch }, payload) => {
    let form = {
        nome: "",
        credenciais: {
        email: "",
        senha: "",
        habilitado: true,
        dataUltimaRedefinicaoDeSenha: new Date(),
        contaNaoExpirada: true,
        contaNaoBloqueada: true,
        credencialNaoExpirada: true},
        authorities: [ {name: "ROLE_USER" } ]
    
    }
    form.nome = await encrypt(payload.name)
    form.credenciais.email = await encrypt(payload.username)
    form.credenciais.senha = await encrypt(payload.password)
    services.registrar.post.data = form
    return http(services.registrar.post)
  }
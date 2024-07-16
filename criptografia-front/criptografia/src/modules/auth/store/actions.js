import http, { services } from '@/http'
import * as storage from '../storage'
import * as types from './mutation-types'
import { encrypt, decrypt } from '@/crypto'

export  const  ActionDoLogin = async ({ dispatch }, payload) => {
  let form = {}
  form.grant_type = "password"
  form.scope = "read write"
  form.username = await encrypt(payload.username)
  form.password = await encrypt(payload.password)
  services.auth.login.data = form
  return http(services.auth.login).then(res => {
    const { refresh_token, access_token } = res.data
    dispatch('ActionSetToken', access_token)
    dispatch('ActionSetRefreshToken', refresh_token)
  })
}

export const ActionGetUser = ({ dispatch}) => {
  return http(services.auth.user).then(async res => {
    const user = res.data
    user.nome = await decrypt(user.nome)
    user.credenciais.email = await decrypt(user.credenciais.email)
    dispatch('ActionSetUser', user)
  })
}

export const ActionSetUser = ({ commit }, payload) => {
  storage.setUser(JSON.stringify(payload))
  commit(types.SET_USER, payload)
}

export const ActionSetToken = ({ commit }, payload) => {
  storage.setLocalToken(payload)
  storage.setHeaderToken(payload)
  commit(types.SET_TOKEN, payload)
}

export const ActionSetRefreshToken = ({ commit }, payload) => {
  storage.setRefreshToken(payload)
  commit(types.SET_REFRESHTOKEN, payload)
}

export const ActionSignOut = ({ dispatch }) => {
  storage.setHeaderToken('')
  storage.deleteLocalToken()
  storage.deleteRefreshToken()
  dispatch('ActionSetUser', {})
  dispatch('ActionSetToken', '')
  dispatch('ActionSetRefreshToken', '')
}

export const ActionCheckToken = ({ dispatch, state }) => {
  if (state.token) {
    return Promise.resolve(state.token)
  }

  const token = storage.getLocalToken()
  const refreshToken = storage.getRefreshToken()

  if (!token) {
    dispatch('ActionSignOut')
    // eslint-disable-next-line prefer-promise-reject-errors
    return Promise.reject('Token não informado')
  }

  const user = storage.getUser()
  if (user) {
    dispatch('ActionSetUser', JSON.parse(user))
  }
  dispatch('ActionSetToken', token)
  dispatch('ActionSetRefreshToken', refreshToken)
  return Promise.resolve(token)
}
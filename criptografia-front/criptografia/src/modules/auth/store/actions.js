import http, { services } from '@/http'
import * as storage from '../storage'
import * as types from './mutation-types'
import publicKey from '../../../keys/public.key?raw'
import privateKey from '../../../keys/private.key?raw'
import { encrypt, decrypt } from '@/crypto'

function _base64StringToArrayBuffer(b64str) {
  const byteStr = atob(b64str)
  const bytes = new Uint8Array(byteStr.length)
  for (let i = 0; i < byteStr.length; i++) {
    bytes[i] = byteStr.charCodeAt(i)
  }
  return bytes.buffer
}

function _arrayBufferToBase64(arrayBuffer) {
  const byteArray = new Uint8Array(arrayBuffer);
  let byteString = '';
  for(let i=0; i < byteArray.byteLength; i++) {
      byteString += String.fromCharCode(byteArray[i]);
  }
  const b64 = window.btoa(byteString);

  return b64;
}

function _convertPemToArrayBuffer(pem) {
  const lines = pem.split('\n')
  let encoded = ''
  for(let i = 0;i < lines.length;i++){
    if (lines[i].trim().length > 0 &&
        lines[i].indexOf('-----BEGIN PRIVATE KEY-----') < 0 &&
        lines[i].indexOf('-----BEGIN PUBLIC KEY-----') < 0 &&
        lines[i].indexOf('-----END PRIVATE KEY-----') < 0 &&
        lines[i].indexOf('-----END PUBLIC KEY-----') < 0) {
      encoded += lines[i].trim()
    }
  }
  return _base64StringToArrayBuffer(encoded)
}

function _convertPemToString(pem) {
  const lines = pem.split('\n')
  let encoded = ''
  for(let i = 0;i < lines.length;i++){
    if (lines[i].trim().length > 0 &&
        lines[i].indexOf('-----BEGIN PRIVATE KEY-----') < 0 &&
        lines[i].indexOf('-----BEGIN PUBLIC KEY-----') < 0 &&
        lines[i].indexOf('-----END PRIVATE KEY-----') < 0 &&
        lines[i].indexOf('-----END PUBLIC KEY-----') < 0) {
      encoded += lines[i].trim()
    }
  }
  return encoded
}

function byteArrayToText(bytes, encoding)
{
    let blob = new Blob([bytes], { type: "application/octet-stream" });
    let reader = new FileReader();
    let done = function() { };

    reader.onload = event =>
    {
        done(event.target.result);
    };

    if(encoding) { reader.readAsText(blob, encoding); } else { reader.readAsText(blob); }

    return { done: function(callback) { done = callback; } }
}

export  const  ActionDoLogin = async ({ dispatch }, payload) => {
  debugger
  payload.grant_type = "password"
  payload.scope = "read write"
  payload.username = await encrypt(payload.username)
  payload.password = await encrypt(payload.password)
  return http(services.auth.login).then(res => {
    const { refresh_token, access_token } = res.data
    dispatch('ActionSetToken', access_token)
  })
}

export const ActionGetUser = ({ dispatch}) => {
  return http(services.auth.user).then(res => {
    const user = res.data
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

export const ActionSignOut = ({ dispatch }) => {
  storage.setHeaderToken('')
  storage.deleteLocalToken()
  dispatch('ActionSetUser', {})
  dispatch('ActionSetToken', '')
}

export const ActionCheckToken = ({ dispatch, state }) => {
  if (state.token) {
    return Promise.resolve(state.token)
  }

  const token = storage.getLocalToken()

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
  return Promise.resolve(token)
}
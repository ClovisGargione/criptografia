import * as types from './mutation-types'

export default {
  [types.SET_CARTOES] (state, payload) {
    state.cartoes = payload
  }
}
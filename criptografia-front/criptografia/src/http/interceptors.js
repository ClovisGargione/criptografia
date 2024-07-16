import * as storage from '../modules/auth/storage'

export default {
 response: ((response) => {
  if (response.status === 401) {
    storage.deleteLocalToken()
    window._Vue.$router.push({ name: 'login' })
  }
    return response
  }, ({ error }) => {
      if (error.response && error.response.data) {
        return Promise.reject(error.response.data);
    }
    return Promise.reject(error.message);
  }),
  request: () =>{}
}


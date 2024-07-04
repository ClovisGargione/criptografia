import { setBearerToken } from '@/http'

export const setHeaderToken = token => setBearerToken(token)
export const getLocalToken = () => localStorage.getItem('token')
export const deleteLocalToken = () => localStorage.removeItem('token')
export const setLocalToken = token => localStorage.setItem('token', token)
export const getUser = () => localStorage.getItem('user')
export const deleteUser = () => localStorage.removeItem('user')
export const setUser = user => localStorage.setItem('user', user)
import { routes as home } from '../views/home'
import { routes as auth } from '../modules/auth'
import { routes as registrar } from '../modules/registrar'
import { routes as cartoes } from '../modules/cartoes'

export default [
  ...auth,
  ...home,
  ...registrar,
  ...cartoes
]
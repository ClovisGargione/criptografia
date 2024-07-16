<template>
  <div class="container pt-3">
    <div class="mt-4 p-5 bg-black text-white rounded">
      <h1>Carteira digital</h1>
      <p>Todos os seus cartões em um só lugar!!!</p>
    </div>
    <br />
    <div class="row">
      <div class="col">
        <h3>Olá, {{ user.nome }}!</h3>

        <p>
          Você está acessando sua carteira digital
        </p>
        <p>
        <div>
          <button class="btn btn-danger" @click="logout()">Logout</button>
        </div>
        </p>
      </div>
      <div class="col">
        <h3>Veja os cartões que você já cadastrou</h3>
        <p>Aqui você pode ver os cartões que já cadastrou ou adicionar
          novos cartões. <br>Todos os seus dados estão protegidos por criptografia de ponta a ponta!</p>
        <p>
          <router-link to="/cartoes" tag="button">
            <button class="btn btn-primary">Controle de
              cartões</button>
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  data: () => ({

  }),
  computed: {
    ...mapState('auth', ['user'])
  },
  methods: {
    ...mapActions('auth', ['ActionSignOut']),
    async logout() {
      await this.ActionSignOut()
      this.$router.push({ name: 'login' })
    }
  }
}
</script>
<style lang="scss" scoped></style>

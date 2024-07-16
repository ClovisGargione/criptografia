<template>
    <div class="sidenav">
        <div class="login-main-text">
            <h2>Application<br> Login Page</h2>
            <p>Login or register from here to access.</p>
        </div>
    </div>
    <div class="main">
        <div class="d-flex align-items-center justify-content-center h-100">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Cadastro</h5>
                    <form @submit.prevent="submit()">
                        <div class="mb-3">
                            <input type="text" class="form-control" v-model="form.name" placeholder="Nome">
                        </div>
                        <div class="mb-3">
                            <input type="text" class="form-control" v-model="form.username" placeholder="E-mail">
                        </div>
                        <div class="mb-3">
                            <input type="password" class="form-control" v-model="form.password" placeholder="Senha">
                        </div>
                        <div class="mb-3">
                            <input type="password" class="form-control" v-model="form.passwordConfirm"
                                placeholder="Confirmação da senha">
                        </div>
                        <div class="row">
                            <div class="col">
                                <button type="submit" class="btn btn-black w-100"
                                    :disabled="!isEmptyForm()">Salvar</button>
                            </div>
                            <div class="col">
                                <router-link to="/login" tag="button"><button type="button"
                                        class="btn btn-secondary w-100">voltar</button></router-link>
                            </div>
                        </div>
                    </form>
                </div>
                <div v-if="mensagemErro">
                    <div class="px-3">
                        <div class="alert alert-warning" role="alert">
                            <strong>E-mail já cadastrado!</strong>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
    data: () => ({
        form: {
            name: '',
            username: '',
            password: ''
        },
        mensagemErro: false
    }),
    methods: {
        ...mapActions('registrar', ['ActionDoRegister']),
        ...mapActions('auth', ['ActionDoLogin', 'ActionGetUser']),
        async submit() {
            try {
                //const loader = this.$loading.show()
                await this.ActionDoRegister(this.form)
                await this.ActionDoLogin(this.form)
                await this.ActionGetUser()
                this.$router.push({ name: 'home' })
                //loader.hide()
            } catch (err) {
                this.mensagemErro = (err.status == 422)
            }
        },
        isEmptyForm() {
            return this.form.name && this.form.username && this.form.password && this.form.passwordConfirm && (this.form.password == this.form.passwordConfirm)
        }
    }
}
</script>

<style scoped>
body {
    font-family: "Lato", sans-serif;
}

.main-head {
    height: 150px;
    background: #FFF;

}

.sidenav {
    height: 100%;
    background-color: #000;
    overflow-x: hidden;
    padding-top: 20px;
}

.main {
    padding: 0px 10px;
}

@media screen and (max-height: 450px) {
    .sidenav {
        padding-top: 15px;
    }
}

@media screen and (max-width: 450px) {
    .login-form {
        margin-top: 10%;
    }

    .register-form {
        margin-top: 10%;
    }
}

@media screen and (min-width: 768px) {
    .main {
        height: 100%;
        margin-left: 40%;
        position: absolute;
        width: 60%;
    }

    .sidenav {
        width: 40%;
        position: fixed;
        z-index: 1;
        top: 0;
        left: 0;
    }

    .login-form {
        margin-top: 80%;
    }

    .register-form {
        margin-top: 20%;
    }
}

.login-main-text {
    margin-top: 20%;
    padding: 60px;
    color: #fff;
}

.login-main-text h2 {
    font-weight: 300;
}

.btn-black {
    background-color: #000 !important;
    color: #fff;
}

.login-page {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;

}

.login-page>.card {
    width: 30%;
}
</style>
<template>
    <div class="container pt-3">
        <div class="mt-4 p-5 bg-black text-white rounded">
            <h1>Carteira digital</h1>
            <p>Todos os seus cartões em um só lugar!!!</p>
        </div>
        <br />
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Endereço</h5>
                        <form @submit.prevent="adicionarCartao()">
                            <div class="mb-3">
                                <input type="text" @blur="buscarEnderecoPorCep()" class="form-control"
                                    v-model="form.enderecoCobranca.cep" placeholder="CEP">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.enderecoCobranca.rua"
                                    placeholder="Rua">
                            </div>
                            <div class="mb-3">
                                <input type="number" class="form-control" v-model="form.enderecoCobranca.numero"
                                    placeholder="Número">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.enderecoCobranca.complemento"
                                    placeholder="Complemento">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.enderecoCobranca.bairro"
                                    placeholder="Bairro">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.enderecoCobranca.cidade"
                                    placeholder="Cidade">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.enderecoCobranca.estado"
                                    placeholder="Estado">
                            </div>
                            <h5 class="card-title">Cartão</h5>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.numeroCartao"
                                    placeholder="Número do cartão">
                            </div>
                            <div class="mb-3">
                                <input type="text" class="form-control" v-model="form.nomeCartao"
                                    placeholder="Nome no cartão">
                            </div>
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3">
                                        <input type="text" class="form-control" v-model="form.validadeCartao"
                                            placeholder="Validade do cartão">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <input type="text" class="form-control" v-model="form.cvvCartao"
                                            placeholder="CVV do cartão">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <button type="submit" class="btn btn-black w-100">Adicionar</button>
                                </div>
                                <div class="col">
                                    <router-link to="/home" tag="button"><button type="button"
                                            class="btn btn-secondary w-100">voltar</button></router-link>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="accordion" id="accordion">
                    <div :key="cartao.id" v-for="cartao in cartoes">
                        <div class="accordion-item">
                            <h2 class="accordion-header" :id="'heading' + cartao.id">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                    :data-bs-target="'#collapse' + cartao.id" aria-expanded="true"
                                    :aria-controls="'collapse' + cartao.id">
                                    Cartão {{ cartao.id }}
                                </button>
                            </h2>
                            <div :id="'collapse' + cartao.id" class="accordion-collapse collapse"
                                :aria-labelledby="'heading' + cartao.id" data-bs-parent="#accordion">
                                <div class="accordion-body">
                                    <div class="row">
                                        <div class="col">
                                            <small class="fw-normal text-decoration-underline">Dados do
                                                Cartão</small><br>
                                            <small class="fw-light"><span class="fw-normal">Número do cartão:</span> {{
                            inputMask(cartao.numeroCartao,
                                'cartao')
                        }}</small><br>
                                            <small class="fw-light"><span class="fw-normal">Nome impresso no
                                                    cartão:</span> {{ cartao.nomeCartao
                                                }}</small><br>
                                            <small class="fw-light"><span class="fw-normal">Validade do cartão:</span>
                                                {{ cartao.validadeCartao
                                                }}</small><br>
                                            <small class="fw-light"><span class="fw-normal">CVV:</span> {{
                            cartao.cvvCartao }}</small>
                                        </div>
                                        <div class="col">
                                            <small class="fw-normal text-decoration-underline">Endereço da
                                                Fatura</small><br>
                                            <small class="fw-light"><span class="fw-normal">CEP:</span> {{
                            inputMask(cartao.enderecoCobranca.cep,
                                'cep') }}</small><br>
                                            <small class="fw-light"><span class="fw-normal">Rua:</span> {{
                            cartao.enderecoCobranca.rua }}, {{
                            cartao.enderecoCobranca.numero }}</small><br>
                                            <span v-if="cartao.enderecoCobranca.complemento"><small
                                                    class="fw-light"><span class="fw-normal">Complemento:</span> {{
                            cartao.enderecoCobranca.complemento }}</small><br></span>
                                            <small class="fw-light"><span class="fw-normal">Bairro:</span> {{
                            cartao.enderecoCobranca.bairro
                        }}</small><br>
                                            <small class="fw-light"><span class="fw-normal">Cidade:</span> {{
                                                cartao.enderecoCobranca.cidade }} - {{
                                                cartao.enderecoCobranca.estado }}</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
    data: () => ({
        form: {
            numeroCartao: "",
            nomeCartao: "",
            validadeCartao: "",
            cvvCartao: null,
            usuario: 0,
            enderecoCobranca: {
                cep: "",
                rua: "",
                numero: null,
                complemento: "",
                bairro: "",
                cidade: "",
                estado: ""
            }
        }
    }),
    computed: {
        ...mapState('auth', ['user']),
        ...mapState('cartoes', ['cartoes'])
    },
    mounted() {
        this.init()
    },
    methods: {
        ...mapActions('cartoes', ['ActionDoBuscarEndereco', 'ActionDoPost', 'ActionDoGetAll']),
        buscarEnderecoPorCep() {
            try {
                //const loader = this.$loading.show()
                if (this.form.enderecoCobranca.cep) {
                    this.ActionDoBuscarEndereco(this.form.enderecoCobranca.cep).then(res => {
                        this.form.enderecoCobranca.rua = res.data.logradouro
                        this.form.enderecoCobranca.bairro = res.data.bairro
                        this.form.enderecoCobranca.cidade = res.data.localidade
                        this.form.enderecoCobranca.estado = res.data.uf
                    }).catch(err => {

                    })
                }
                //loader.hide()
            } catch (err) {
            }
        },
        async adicionarCartao() {
            await this.ActionDoPost(this.form)
            await this.ActionDoGetAll()
            this.form = {
                numeroCartao: "",
                nomeCartao: "",
                validadeCartao: "",
                cvvCartao: null,
                usuario: 0,
                enderecoCobranca: {
                    cep: "",
                    rua: "",
                    numero: null,
                    complemento: "",
                    bairro: "",
                    cidade: "",
                    estado: ""
                }
            }
        },
        async init() {
            await this.ActionDoGetAll()
        },
        inputMask(value, tipo) {
            if (tipo == "cep") {
                return value.replace(/\D/g, "").replace(/^(\d{5})(\d{3})+?$/, "$1-$2")
            }
            if (tipo == "cartao") {
                return value.replace(/\D/g, "").replace(/^(\d{4})(\d{4})(\d{4})(\d{4})+?$/, "$1 $2 $3 $4")
            }

        }
    }
}
</script>

<style scoped>
.btn-black {
    background-color: #000 !important;
    color: #fff;
}
</style>
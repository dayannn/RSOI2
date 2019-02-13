<template>
    <div>
        <form class="login" @submit.prevent="login" style="margin: auto; padding-top: 80px">
            <h1>Sign in</h1>
            <br/>
            <label>User name</label>
            <b-form-input required v-model="username" type="text" placeholder="Enter login"> </b-form-input>
            <br/>
            <label>Password</label>
            <b-form-input required v-model="password" type="password" placeholder="Enter password"></b-form-input>
            <hr/>
            <button class="btn btn-info btn-sm shadowed-button" type="submit">Login</button>
        </form>
    </div>
</template>


<style>
    .login {
        display: flex;
        flex-direction: column;
        width: 300px;
        padding: 10px;
    }
</style>

<script>
    import {AUTH_REQUEST} from '../store/actions/auth'

    export default {
        name: 'login',
        data () {
            return {
                username: '',
                password: '',
            }
        },
        methods: {
            login: function () {
                const { username, password } = this;
                this.$store.dispatch(AUTH_REQUEST, { username, password }).then(() => {
                    this.$router.push('/')
                })
            }
        },
    }
</script>

<style scoped>
    .shadowed-button {
        box-shadow: 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12), 0 2px 4px -1px rgba(0, 0, 0, .2)
    }
    .shadowed-button:hover{
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)
    }
</style>
<template>
    <div class="header" v-bind:class="{'active':scrolled}">
        <h1 >
            <router-link to="/" class="logo">
                КнигоПоиск
            </router-link>
        </h1>
        <div class="header-right">
            <router-link to="/books">Книги</router-link>
            <router-link to="/users">Пользователи</router-link>
            <router-link to="/about">О сайте</router-link>
            <b-button
                    v-if="(this.$store.getters.isAuthenticated)"
                    v-on:click="doLogOut">
                Logout
            </b-button>
        </div>
    </div>
</template>

<script>
    import { AUTH_LOGOUT } from '../../store/actions/auth';
    export default{
        name: "Header",
        data () {
            return {
                scrolled: false
            };
        },
        methods: {
            handleScroll () {
                this.scrolled = window.scrollY > 0;
            },
            doLogOut(){
                this.$store.dispatch(AUTH_LOGOUT).then(() => this.$router.push('/login'));
            }
        },
        created () {
            window.addEventListener('scroll', this.handleScroll);
        },
        destroyed () {
            window.removeEventListener('scroll', this.handleScroll);
        }
    }
</script>

<style scoped>
    .header a.logo {
        font-size: 32px;
        font-weight: bold;
        color: #ffffff;
    }

    .header a.logo:hover {
        color: #ffffff;
    }

    .header{
        background: #444444;
        overflow: hidden;
        position: fixed;
        width: 100%;
        z-index: 9000;
        box-shadow: 0px 0px 6px rgba(0,0,0,.6);
        transition: all 0.3s linear;
    }
    .active {
        box-shadow: 0px 0px 8px rgba(0,0,0,.6);
    }

    /* Change the background color on mouse-over */
    .header a:hover {
        color: #ffffff;
    }

    .header a {
        float: left;
        color: #d9d9d9;
        text-align: center;
        padding: 12px;
        text-decoration: none;
        font-size: 18px;
        line-height: 25px;
        border-radius: 1px;
    }

    .header-right {
        float: right;
        padding-right: 5%;
        font-family: Candara, sans-serif;
        font-weight: bolder;
    }

    @media screen and (max-width: 600px) {
        .header a {
            float: none;
            display: block;
            text-align: left;
        }
        .header-right {
            float: none;
        }
    }
</style>
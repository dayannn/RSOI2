<template>
    <div class="header">
        <h1 >
            <router-link to="/" class="logo">
                КнигоПоиск
            </router-link>
        </h1>
        <div class="header-right">
            <router-link to="/users">Пользователи</router-link>
            <router-link to="/about">О сайте</router-link>
        </div>
    </div>
</template>

<script>
    export default{
        name: "Header"
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
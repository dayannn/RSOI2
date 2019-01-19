<template>
  <div id="app">
    <Header />
    <router-view/>
  </div>
</template>

<script>
  import Header from "./components/layout/header"
  export default {
      name:"app",
      components:{Header},
      comments:{
          Header
      }
  }
</script>

<style>
#app {
  font-family: tahoma,verdana,arial,serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #333;
  background: #eeeeee;
  min-height: 100vh;
}
</style>

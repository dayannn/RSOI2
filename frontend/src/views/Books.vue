<template>
    <div class="books">
        <h1>Книги</h1>
        <div class="books-list" v-bind:key="book.id" v-for="book in books">
            <BookItem  v-bind:book="book"/>
        </div>
    </div>
</template>

<script>
    import BookItem from '../components/BookItem';
    import axios from 'axios';
    export default {
        name: "books",
        components:{BookItem},
        data(){
            // return{
            //     books:[{id:"1", name:"book", description:"blablalba"}]
            // }
            return{
                books:[]
            }
        },
        created() {
            axios.get("/api/books")
                .then(res => this.books = res.data)
                .catch(err => console.log(err))
        }
    }

</script>

<style scoped>
    .books-list{
        width: 60%;
        margin: auto;
        padding: 8px;
    }
</style>
<template>
    <div class="card flex-row flex-wrap shadow" style="padding: 10px">
        <b-row>
            <b-col md="2">
                <img src="../assets/book.svg" width="120" alt="Book cover" style="background: #f5f5f5;">
            </b-col>
            <b-col class="card-block px-2" style="margin-left: 20px; text-align: left">
                <h4 class="card-title"><strong>{{book.name}}</strong></h4>
                <p class="card-text">
                    <strong>Автор:</strong> {{book.author}}<br/>
                    <strong>Описание:</strong> <truncate clamp=" Развернуть ↓" :length="500" less=" Свернуть ↑" :text=book.description></truncate><br/>
                </p>
                <star-rating v-bind="config"></star-rating>
                <router-link :to="'book/' + this.book.id" class="btn btn-primary shadowed-button" style="margin-top: 20px">Страница книги</router-link>
            </b-col>
        </b-row>
        <div class="w-100"></div>
    </div>
</template>

<script>
    import StarRating from 'vue-dynamic-star-rating';
    import truncate from './truncate';
    export default{
        name: "BookItem",
        components: {StarRating, truncate},
        data(){
            return{
                config: {
                    rating: Number((this.book.rating).toFixed(2)),
                    isIndicatorActive: true,
                    starStyle: {
                        fullStarColor: '#ed8a19',
                        emptyStarColor: '#737373',
                        starWidth: 30,
                        starHeight: 30
                    }
                }
            }
        },
        props: ["book"]

    }
</script>

<!--TODO make it a global style-->
<style scoped>
    .shadowed-button {
        box-shadow: 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12), 0 2px 4px -1px rgba(0, 0, 0, .2)
    }
    .shadowed-button:hover{
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)
    }
</style>
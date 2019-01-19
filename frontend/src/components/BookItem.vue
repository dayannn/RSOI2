<template>
    <div class="card flex-row flex-wrap" style="padding: 10px">
        <b-row>
            <b-col md="2" >
                <img src="../assets/book.svg" width="120" alt="Book cover" style="background: #f5f5f5;">
            </b-col>
            <b-col class="card-block px-2" style="margin-left: 20px; text-align: left">
                <h4 class="card-title"><strong>{{book.name}}</strong></h4>
                <p class="card-text">
                    <strong>Author:</strong> {{book.author}}<br/>
                    <strong>Description:</strong> <truncate class="customClass" clamp=" Развернуть ↓" :length="500" less=" Свернуть ↑" :text=book.description></truncate><br/>
                    <strong>Pages:</strong> {{book.pagesNum}}<br/>
                </p>
                <star-rating v-bind="config"></star-rating>
                <a href="/book/{this.book.id}" class="btn btn-primary" style="margin-top: 20px">BUTTON</a>
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
                    rating: this.book.rating,
                    isIndicatorActive: false,
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
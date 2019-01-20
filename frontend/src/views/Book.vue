<template>
    <div style="padding-top: 75px; padding-bottom: 25px">
        <div class="card shadow" style="width: 60%; margin: auto; padding: 20px; text-align: left">
            <h4 class="card-title"><b>Рецензии на книгу «{{book.name}}»</b></h4>
            <h5><i>{{book.author}}</i></h5>
            <br/>
            <b-row>
                <b-col cols="3">
                    <img src="../assets/book.svg" width="120px" alt="Book cover" style="background: #f5f5f5;">
                    <br/>
                </b-col>
                <b-col cols="9">
                    <p class="card-text" style="text-align: left">
                        <b-row>
                            <b-col cols="4">
                                <star-rating v-if="this.renderStars" v-bind="starsConfig"/>
                            </b-col>
                            <b-col cols="8" style="text-align: right">
                                {{book.reviewsNum}} рецензий
                            </b-col>
                        </b-row>
                        <br/>
                        <b-row>
                            <b-col cols="3"><b>Страниц:</b></b-col>
                            <b-col>{{book.pagesNum}}</b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="3"><b>ISBN:</b></b-col>
                            <b-col>34235-345-2534-2<br/></b-col>
                        </b-row>
                        <br/>
                        {{book.description}}<br/>
                    </p>
                </b-col>
            </b-row>
        </div>

        <div class="card card-item shadow">
            <b-row class="card-top">
                <h5 class="card-title"><b>Добавить рецензию</b></h5>
            </b-row>
            <hr style="color: #cfcfcf; size: 2px; margin: 0"/>
            <b-row class="card-content">
                <b-col>
                    <AddReviewForm v-bind:book="book"/>
                </b-col>
            </b-row>
        </div>

        <div class="card card-item shadow">
            <b-row class="card-top">
                <h5 class="card-title"><b>Рецензии пользователей</b></h5>
            </b-row>
            <hr style="color: #cfcfcf; size: 2px; margin: 0"/>
            <b-row class="card-content">
                <div v-bind:key="review.id" v-for="review in reviews">
                    <ReviewItem v-bind:review="review" v-bind:book="book"/>
                </div>
            </b-row>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import StarRating from 'vue-dynamic-star-rating';
    import ReviewItem from '../components/ReviewItem';
    import AddReviewForm from '../components/AddReviewForm';
    export default {
        name:"Book",
        components: {StarRating, ReviewItem, AddReviewForm},
        methods: {

        },
        data(){
            return {
                book: {
                    name: "",
                    author: "",
                    description: "",
                    reviewsNum: "",
                    pagesNum: ""
                },
                reviews:[],
                starsConfig: {
                    rating: 0,
                    isIndicatorActive: true,
                    starStyle: {
                        fullStarColor: '#ed8a19',
                        emptyStarColor: '#737373',
                        starWidth: 20,
                        starHeight: 20
                    }
                },
                renderStars: false
            }
        },
        created() {
            axios.get("/api/book/" + this.$route.params.id)
                .then(res => {
                    this.book = res.data;
                    this.starsConfig.rating = Number((this.book.rating).toFixed(2));
                    this.renderStars = true;
                })
                .catch(err => console.log(err))
            axios.get("/api/book/" + this.$route.params.id + "/reviews")
                .then(res => this.reviews = res.data)
                .catch(err => console.log(err))
        }
    }

</script>

<style scoped>
    .card-item{
        width: 60%;
        margin-top: 25px;
        margin-left: auto;
        margin-right: auto;
        text-align: left
    }

    .card-top{
        background: #f5f5f5;
        margin: 0;
        padding-left: 20px;
        padding-right: 20px;
        padding-top: 10px
    }

    .card-content{
        margin: 0;
        padding: 20px
    }

</style>
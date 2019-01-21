<template>
    <b-card style="margin: 10px;">
        <b-row>
        <b-col cols="11">

            <b-row style="padding-top: 20px; padding-bottom: 20px">
                <b-col cols="2">
                    <img slot="aside" src="../assets/avatar.jpg" style="width: 50px; height: 50px" alt="avatar" />
                </b-col>
                <b-col cols="8">
                    <div style="font-size: 18px">{{user.name}} {{user.lastName}} написал(а) рецензию на книгу</div>
                    <div style="font-size: 16px" ><i>{{this.book.name}}</i> автора <i>{{this.book.author}}</i></div>
                </b-col>
                <b-col cols="2" style="text-align: right; margin-top: 5px">
                    <div style="font-size: 12px"><i>{{date | dateFormat('DD/MM/YYYY hh:mm')}}</i></div>

                </b-col>

            </b-row>
            <div style="background: #f2f7fd; padding: 10px">
                <b-row>
                    <b-col cols="2">
                        Оценка:
                    </b-col>
                    <b-col cols="2" style="margin-top:2px">
                        <star-rating v-bind="starsConfig"></star-rating>
                    </b-col>
                </b-row>


                <br/>
                <p>
                    {{review.text}}
                </p>
            </div>

        </b-col>
        <b-col cols=1>
            <b-button @click="deleteReview" variant="outline-danger" >x</b-button>
        </b-col>
        </b-row>
    </b-card>
</template>

<script>
    import axios from 'axios';
    import StarRating from 'vue-dynamic-star-rating';
    export default{
        name: "ReviewItem",
        components: {StarRating},
        methods: {
            deleteReview(){
                axios.delete('/api/reviews/' + this.review.id)
                    .then(this.$emit('review-deleted'))
                    .catch(err => console.log(err));
            }
        },
        data(){
            return{
                user:{},
                starsConfig: {
                    rating: this.review.rating,
                    isIndicatorActive: false,
                    starStyle: {
                        fullStarColor: '#ed8a19',
                        emptyStarColor: '#737373',
                        starWidth: 20,
                        starHeight: 20
                    }
                },
                date:{}
            }
        },
        created(){
            axios.get('/api/users/' + this.review.uid)
                .then(res => {
                    this.user = res.data
                })
                .catch(err => console.log(err));

            this.date = new Date(this.review.postedTime);
        },
        props: ["review", "book"]
    }
</script>



<style scoped>

</style>
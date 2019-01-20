<template>
    <div>
        <b-form @submit="onSubmit" style="padding: 15px">
            Пользователь:
            <br/>
            <b-form-select
                    v-model="userSelected"
                    :state="this.selectorState"
                    v-on:change="selectorState = null"
                    :options="userOptions"
                    class="mb-3">
            </b-form-select>
            Ваша оценка книги:
            <br/>
            <div v-on:click="textInputState = null">
                <b-form-textarea id="textarea1"
                                 v-model="reviewText"
                                 :state="this.textInputState"
                                 placeholder="Напишите, что вы думаете об этой книге"
                                 :rows="3"
                                 :max-rows="6">
                </b-form-textarea>
            </div>
            <br/>
            <b-button class="shadowed-button" type="submit" variant="primary">Добавить</b-button>
            <b-button class="shadowed-button" @click="onCancel" type="cancel" variant="danger">Отмена</b-button>
        </b-form>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name:"AddReviewForm",
        data () {
            return {
                review: {},
                users: [],
                userSelected: null,
                userOptions: [
                    { value: null, text: 'Выберите пользователя' }
                ],
                reviewText:'',
                selectorState: null,
                textInputState: null
            }
        },
        methods: {
            onSubmit (evt) {
                evt.preventDefault();

                if (this.userSelected === null)
                    this.selectorState = false;

                if (this.reviewText.length <= 0)
                    this.textInputState = false;
                else
                    this.textInputState = null;

            },
            onCancel (evt) {
                evt.preventDefault();

            }
        },
        created(){
            axios.get('/api/users/')
                .then(res => {
                    const users = res.data;
                    for (let i = 0; i < users.length; i++) {
                        const user = res.data[i];
                        this.userOptions.push({
                            value: user.id,
                            text: user.name + " " + user.lastName
                        })
                    }
                })
                .catch(err => console.log(err));
        },
        props: ["book"]
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
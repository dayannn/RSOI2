<template>
    <div>
        <b-form @submit="onSubmit" v-if="show">
            <b-form-group
                    label="Логин:"
                    label-for="input0">
                <b-form-input
                        id="input0"
                        type="text"
                        v-model="user.login"
                        required
                        placeholder="Введите логин">
                </b-form-input>
            </b-form-group>

            <b-form-group
                    label="Имя:"
                    label-for="input1">
                <b-form-input
                        id="input1"
                        type="text"
                        v-model="user.name"
                        required
                        placeholder="Введите имя">
                </b-form-input>
            </b-form-group>

            <b-form-group
                    label="Фамилия:"
                    label-for="input2">
                <b-form-input
                        id="input2"
                        type="text"
                        v-model="user.lastName"
                        placeholder="Введите фамилию">
                </b-form-input>
            </b-form-group>

            <b-button type="submit" variant="primary">Добавить</b-button>
            <b-button @click="onCancel" type="cancel" variant="danger">Отмена</b-button>
        </b-form>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                user: {
                    name: '',
                    lastName: '',
                    login: ''
                },
                show: true
            }
        },
        methods: {
            onSubmit (evt) {
                evt.preventDefault();
                this.$emit('add-user', this.user);
            },
            onCancel (evt) {
                evt.preventDefault();
                /* Reset our form values */
                this.user.name = '';
                this.user.lastName = '';
                this.user.login = '';

                this.$emit('hide-add-user-form');
            }
        }
    }
</script>
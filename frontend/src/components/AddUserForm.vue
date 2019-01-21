<template>
    <div>
        <b-form @submit="onSubmit"  v-if="show" style="padding: 15px">
            <b-form-group
                    label="Логин:"
                    label-for="input0">
                <b-form-input
                        id="input0"
                        type="text"
                        class="shadow-sm"
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
                        class="shadow-sm"
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
                        class="shadow-sm"
                        type="text"
                        v-model="user.lastName"
                        placeholder="Введите фамилию">
                </b-form-input>
            </b-form-group>

            <b-button class="shadowed-button" type="submit" variant="primary">Добавить</b-button>
            <b-button class="shadowed-button" @click="onCancel" type="cancel" variant="danger">Отмена</b-button>
        </b-form>
    </div>
</template>

<script>
    export default {
        name: "AddUserForm",
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

<style scoped>
    .shadowed-button {
        box-shadow: 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12), 0 2px 4px -1px rgba(0, 0, 0, .2)
    }
    .shadowed-button:hover{
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)
    }
</style>
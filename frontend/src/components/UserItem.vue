<template>
    <div class = "user-item">
        <b-card>
            <p class="card-text">
                <strong>Name:</strong> {{user.name}} {{user.lastName}}<br/>
                <strong>Login:</strong> {{user.login}}<br/>
                <strong>Rating:</strong> {{user.rating}}<br/>
                <strong>Reviews:</strong> {{user.reviewsNum}}<br/>
            </p>
            <b-button class="btn btn-info btn-sm">Изменить</b-button>
            <b-button class="btn btn-danger btn-sm" @click="deleteUser">Удалить</b-button>

            <!--<b-modal id="modalConfirmUserDelete"-->
                     <!--ref="modal"-->
                     <!--title="Вы действительно ходите удалить поьзователя?"-->
                     <!--@ok="handleOk">-->
            <!--</b-modal>-->
        </b-card>

    </div>
</template>

<script>
    export default{
        name: "UserItem",
        props: ["user"],
        methods:{
            deleteUser(){
                this.$emit('del-user', this.user.id)
            }
        }
    }
</script>

<style scoped>
    .user-item{
        background: #f4f4f4;
        padding: 5px;
        border-bottom: 1px #ccc solid;
        text-align: left;
        width: 50%;
        margin: auto;
    }
</style>

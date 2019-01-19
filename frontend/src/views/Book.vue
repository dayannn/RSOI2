<template>
    <div style="padding-top: 75px;">
        <div class="card shadow"   style="width: 60%; margin: auto; ">
            <h4 class="card-title"><strong>Фауст</strong></h4>
            <div class="row">
                <div class="column" style="width: 25%">
                    <img src="../assets/book.svg" width="120" alt="Book cover" style="background: #f5f5f5;">
                    <br/>
                    READ
                </div>
                <div class="columm" style="width: 75%">
                    <p class="card-text" style="alignment: left">
                        <strong>Author: </strong>Гёте<br/>
                        <strong>Description: </strong>Классная книга<br/>
                        <strong>Pages: </strong>128<br/>
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .column{
        float: left;
    }

    .row:after {
        content: "";
        display: table;
        clear: both;
    }
</style>
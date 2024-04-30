<template>
    <div class="welcome_board">
        <div class="row-6">
            <div class="welcome_word">
                请选择入住方式：
            </div>
        </div>
        <div class="row-6 button-container">
            <router-link to="direct_check_in">
                <button type="button" :class="'btn ' + 'btn-lg ' + 'btn-info'">直接入住</button>
            </router-link>
            <router-link to="reserve_check_in">
                <button type="button" :class="'btn ' + 'btn-lg ' + 'btn-info'">已预约入住</button>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
div.welcome_board {
    width: 50vw;
    /* 60vw : 60%浏览器宽度 */
    height: 50vh;
    /* 70vw : 70%浏览器高度 */
    margin: 16vh auto;
    background-color: rgba(74, 101, 102, 0.636);
}

div.welcome_word {
    text-align: center;
    font-size: 60px;
    font-weight: 900;
    color: white;
    padding-top: 7vh;
    font-family: monospace;
}

.button-container {
    margin-top: 15vh;
    display: grid;
    grid-template-columns: 1fr 1fr;
    align-items: stretch;
    justify-items: center;
    align-content: center;
}
</style>
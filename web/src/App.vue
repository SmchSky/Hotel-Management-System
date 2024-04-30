<template>
    <NavBar />
    <router-view />
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default {
    components: {
        NavBar,
    },
    setup () {

    },
}
</script>

<style>
body {
    background: url('@/assets/images/background.jpg') no-repeat;
    background-size: 100% 100%;
    background-attachment: fixed;
}
</style>

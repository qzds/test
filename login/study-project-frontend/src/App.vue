<script setup>
import {get} from "@/net";
import {useStore} from "@/stores";
import router from "@/router";

const store = useStore()

if(store.auth.user == null) {
    get('/api/user/me', (message) => {
        store.auth.user = message
        router.push('/index')
    }, () => {
        store.auth.user = null
    })
}
</script>

<template>
  <router-view/>
</template>

<style>

*, *:before, *:after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}
:root {
	/* COLORS */
	--white: #e9e9e9;
	--gray: #333;
	--blue: #0367a6;
	--lightblue: #008997;

	/* RADII */
	--button-radius: 0.7rem;

	/* SIZES */
	--max-width: 758px;
	--max-height: 420px;

	font-size: 16px;
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
		Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

</style>

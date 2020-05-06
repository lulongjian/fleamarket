<template>
  <div id="app">
		<top-bar></top-bar>
		<main>
				<keep-alive>
					<router-view/>
				</keep-alive>
		</main>
    <bottom></bottom>
  </div>
</template>

<script>
import TopBar from "./components/topbar/TopBar"
import bottom from "./components/bottom/bottom"
export default {
	name: 'App',
	components: {
		TopBar,
    bottom
	}
}
</script>

<style>
  html{
    position:relative;
    bottom:0;
    left:0;
  }
  body{
    position:relative;
    bottom:0;
    left:0;
  }
</style>

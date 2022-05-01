<template>
  <nav>
    <ul class="menu align-center">
      <!-- TODO : make it a component
              See https://router.vuejs.org/api/
      -->
      <router-link
        to="/"
        custom
        v-slot="{ href, route, navigate, isActive, isExactActive }"
      >
        <li
          :class="[isActive && 'is-active', isExactActive && 'is-active']"
        >
          <a :href="href" @click="navigate">{{ route.name }}</a>
        </li>
      </router-link>
      <router-link
        to="/products"
        custom
        v-slot="{ href, route, navigate, isActive, isExactActive }"
      >
        <li
          :class="[isActive && 'is-active', isExactActive && 'is-active']"
        >
          <a :href="href" @click="navigate">{{ route.name }}</a>
        </li>
      </router-link>
      <router-link
        to="/legal"
        custom
        v-slot="{ href, navigate, isActive, isExactActive }"
      >
        <li
          :class="[isActive && 'is-active', isExactActive && 'is-active']"
        >
          <a :href="href" @click="navigate">Mentions légales</a>
        </li>
      </router-link>
    </ul>
  </nav>
  <router-view/>
</template>

<script>
import { ref } from 'vue'
// import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'LayoutDefault',

  components: {
    // HelloWorld
  },

  setup () {
    return {
      leftDrawerOpen: ref(false)
    }
  }
}
</script>

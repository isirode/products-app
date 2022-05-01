import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'foundation-sites/dist/css/foundation.min.css'
// import Foundation from 'foundation-sites'
import $ from 'jquery'
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'
// import { Accordion } from 'foundation-sites'

// import { Accordion } from 'foundation-sites/js/foundation.accordion'
// $(document).foundation()
// $('.data-accordion').foundation()
// Foundation.reInit(['tooltip', 'accordion', 'reveal'])

createApp(App).use(Quasar, quasarUserOptions).use(router).mount('#app')

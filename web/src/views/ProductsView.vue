<template>
  <div class="grid-container">
    <!--<h1>Products</h1>-->
    <label>
      Type your <a href="https://orientdb.org/docs/3.2.x/sql/SQL-Introduction.html">OrientDB SQL Query below</a>
      <div id="container" style="width: 100%; height: 300px; border: 1px solid grey"></div>
      <!-- <textarea v-model="sqlQuery" placeholder="Select * From Product Limit 10"></textarea>-->
    </label>
    <div class="input-group-button">
      <input type="submit" class="button" value="Submit" @click.prevent="onSubmit">
    </div>
    <br>
    <!-- TODO : Add a spinner here, for loading the query -->
    <!-- TODO : Add a system to list kwnown classes here, with their schema if possible -->
    <!-- TODO : Use a SQL syntax helper -->
    <ul v-if="products.length !== 0">
      <li v-for="(product, index) in products" :key="index">
        <ProductComponent :product="product" /><br>
      </li>
    </ul>
  </div>
</template>

<style scoped>

ul {
    list-style-type: none;
    margin-left: 0%;
}

</style>

<script>
// TODO : use typescript lang="ts"
// import { Product } from '@/models/product'
// import type { PropType } from 'vue'

import ProductComponent from '@/components/ProductComponent.vue'

// TODO : add OrientDB syntax https://microsoft.github.io/monaco-editor/monarch.html
// TODO : add schema of classes to monaco
import * as monaco from 'monaco-editor'

// TODO : look for alternatives to Monaco, it slow down the page
self.MonacoEnvironment = {
  getWorker (moduleId, label) {
    return new Worker('./editor.worker.js')
  }
}
/*
monaco.editor.createWebWorker({

})
*/
// eslint-disable-next-line no-unused-vars
// const worker = new Worker('./editor.worker.js')
/*
self.MonacoEnvironment = {
  getWorkerUrl (moduleId, label) {
    return './editor.worker.js'
  }
}
*/
/*
(window as any).MonacoEnvironment = {
  getWorkerUrl (moduleId: string, label: string) {
    return './editor.worker.bundle.js'
  }
}
*/

const api = process.env.VUE_APP_PRODUCTS_API

if (api === null || api === undefined) {
  throw Error('api is not defined')
}

let editor = null

export default {
  name: 'ProductsView',
  components: {
    ProductComponent
  },
  data () {
    return {
      sqlQuery: 'Select * From Product Limit 10',
      products: {
        type: Array,
        default: []
      }
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {
    editor = monaco.editor.create(document.getElementById('container'), {
      value: [this.sqlQuery].join('\n'),
      language: 'sql'
    })
  },
  methods: {
    async fetchData () {
      const query = editor?.getValue() === undefined ? this.sqlQuery : editor?.getValue()
      const response = await window.fetch(api + '/product/queryMultiple', {
        method: 'POST',
        headers: {
          'content-type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
          query: query
        })
      })
      const data = await response.json()
      if (response.ok) {
        this.products = data
      } else {
        console.log(response)
      }
    },
    async onSubmit () {
      this.fetchData()
    }
  }
}
</script>

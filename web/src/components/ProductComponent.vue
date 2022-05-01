<template>
  <div class="product-container">
    <div class="grid-x grid-margin-x">
      <div class="cell small-4">
        <div>
          <div>
            <span class="product-name">
              <span style="float: left">{{ product.name }}</span>
              <span v-if="product.type && product.type === 'software.library'" class="product-type">&ensp;(Library)</span>
              <span v-if="product.type && product.type === 'software.language'" class="product-type">&ensp;(Language)</span>
            </span>
          </div>
          <br>
          <br>
          <div class="cell small-6 medium-4 large-6 product-description">
            {{ product.description }}
          </div>
          <br>
          <div class="cell small-6 medium-4 large-6 product-description">
            <p v-if="product.officialWebsiteURL !== ''">
              <!-- TODO : explain somewhere why span then a works and not text then a then span -->
              <span class="url">Official website: <a :href="product.officialWebsiteURL">{{ product.officialWebsiteURL }}</a></span>
            </p>
            <p v-if="product.officialSourceURL !== ''">
              <span class="url">Source: <a :href="product.officialSourceURL">{{ product.officialSourceURL }}</a></span>
            </p>
            <p v-if="product.officialDocumentationURL !== ''">
              <span class="url">Documentation: <a :href="product.officialDocumentationURL">{{ product.officialDocumentationURL }}</a></span>
            </p>
            <p v-if="product.wikipediaURL !== ''">
              <span class="url">Wikipedia URL: <a :href="product.wikipediaURL">{{ product.wikipediaURL }}</a></span>
            </p>
          </div>
        </div><!-- end grid-y -->
      </div>
      <!-- TODO : maybe make a tab sutff, search one which can scroll -->
      <div class="cell small-8">
        <q-card>
          <!-- TODO : fix the problem where we cant the selected tab -->
          <q-tabs
            model-value="tab"
            @update:modelValue="changeTab"
            dense
            class="text-grey"
            active-color="primary"
            indicator-color="primary"
            align="justify"
            narrow-indicator
          >
            <q-tab :name="'panel-characteristics-' + product.name" label="Characteristics"></q-tab>
            <q-tab :name="'panel-raw-' + product.name" label="Raw"></q-tab>
            <q-tab name="wip" label="WIP"></q-tab>
          </q-tabs>

          <q-separator></q-separator>

          <q-tab-panels v-model="tab" transition-duration="500" transition-prev="fade" transition-next="fade" animated>
            <q-tab-panel :name="'panel-characteristics-' + product.name">
              <p v-for="(value, index) in getFirstLevelCharacteristics(product)" :key="index">
                <span>{{ formatKey(value.key) }} : {{ value.value }}</span>
              </p>
              <div v-if="productAsTree !== null">
                <q-tree
                  :nodes="productAsTree"
                  default-expand-all
                  node-key="label"
                >
                  <!-- TODO : make a template https://quasar.dev/vue-components/tree#qtree-api -->
                </q-tree>
              </div>
            </q-tab-panel>

            <q-tab-panel :name="'panel-raw-' + product.name">
              <textarea v-bind:value="JSON.stringify(product, null, 4)" style="height: 300px"></textarea>
            </q-tab-panel>

            <q-tab-panel name="wip">
              <div class="text-h6">WIP</div>
              Lorem ipsum dolor sit amet consectetur adipisicing elit.
            </q-tab-panel>
          </q-tab-panels>
        </q-card>
      </div>
    </div>
  </div>
</template>

<style scoped>

.product-container {
  /* border: 1px solid blue; */
  background: aliceblue;
}

.product-name {
  float: left;
  margin-left: 1%;
  border-bottom: 1px solid black;
}

.product-type {
  float: left;
}

.product-description {
  text-align: left;
}

span.url {
  overflow: hidden;
  white-space: nowrap; /* Don't forget this one */
  text-overflow: ellipsis;
  max-width: 100%;
  display: inline-block;
}

</style>

<script lang="ts">

// import $ /* , { type } */ from 'jquery'

// eslint-disable-next-line no-unused-vars
import { ref, defineComponent } from 'vue'
import type { PropType } from 'vue'

import { Product } from '@/models/product'

// const Foundation = require('foundation-sites')

interface Node {
  label?: string
  icon?: string
  header?: string
  body?: string
  caption?: string
  children?: Node[]
}

export default defineComponent({
  props: {
    product: {
      type: Object as PropType<Product>
      // TODO : i am not sure how to use this
      // default: () => (null),
      // validator: (product: Product) => true
    }
  },
  data: () => {
    return {
      currentPanel: ''
    }
  },
  computed: {
    // Info : documentation indicate to use ref() but it does not work
    tab () {
      if (this.product === undefined) return ''
      const panel = this.currentPanel !== '' ? this.currentPanel : 'panel-characteristics-' + this.product.name
      return panel
    },
    productAsTree () {
      if (this.product === undefined) return null
      return this.getProductTree(this.product)
    }
  },
  methods: {
    isOfficialProperty (property: String) {
      switch (property) {
        case 'type':
        case 'name':
        case 'description':
        case 'officialWebsiteURL':
        case 'officialSourceURL':
        case 'officialDocumentationURL':
        case 'wikipediaURL':
        case 'properties':
          return true
      }
      return false
    },
    formatKey (key: String) {
      const splitted = key.split(/(?=[A-Z])/)
      const firstWord = splitted[0].charAt(0).toUpperCase() + splitted[0].slice(1)
      const modifiedSplitted: string[] = []
      for (const word of splitted.slice(1)) {
        modifiedSplitted.push(word.charAt(0).toLowerCase() + word.slice(1))
      }
      return firstWord + ' ' + modifiedSplitted.join(' ')
    },
    getFirstLevelCharacteristics (product: Product) {
      const result = []
      let key: keyof Product
      for (key in product) {
        if (this.isOfficialProperty(key)) continue
        if (typeof product[key] === 'object') continue
        if (product[key] === '') continue

        result.push({
          key: key,
          value: product[key]
        })
      }
      return result
    },
    getObjectCharacteristicsOfProduct (product: Product) {
      const result = []
      let key: keyof Product
      for (key in product) {
        if (this.isOfficialProperty(key)) continue
        if (typeof product[key] !== 'object') continue
        if (product[key] === null || product[key] === undefined) continue
        if (Object.keys(product[key]).length === 0) continue

        result.push({
          key: key,
          value: product[key]
        })
      }
      return result
    },
    getFirstLevelPropertyOfObject (object: any) {
      const result = []
      let key: keyof any
      for (key in object) {
        if (typeof object[key] === 'object') continue
        if (object[key] === '') continue

        result.push({
          key: key,
          value: object[key]
        })
      }
      return result
    },
    getProductTree (product: Product) {
      const root: Node[] = []
      const objectCharacteristics = this.getObjectCharacteristicsOfProduct(product)
      for (const item of objectCharacteristics) {
        const node = this.getNode(item.value, item.key)
        root.push(node)
      }

      return root
    },
    getNode (object: any, name: string) {
      const result: Node = {}
      result.children = []
      result.label = name
      // result.icon = 'restaurant_menu'
      let key: keyof any
      for (key in object) {
        if (typeof object[key] === 'object') {
          result.children.push(this.getNode(object[key], key))
          continue
        }
        if (object[key] === '') continue

        result.children.push({
          label: key + ' : ' + object[key]
        })
      }
      return result
    },
    changeTab (event: any) {
      this.currentPanel = event
    }
  },
  mounted () {
  }
})
</script>
